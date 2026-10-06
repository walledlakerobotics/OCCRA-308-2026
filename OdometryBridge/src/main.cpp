#include <Arduino.h>
#include <pico/mutex.h>
#include <pico.h>
#include <goBILDA_Pinpoint.h>
#include <Serial.h>

// I want perfect memory management so it can send it fast.

goBILDA::Pinpoint pinpoint = goBILDA::Pinpoint();

mutex_t pinpoint_mtx = mutex();
mutex_t writing_serial_mtx = mutex();
mutex_t reading_serial_mtx = mutex();

p_data data = p_data();
mutex_t data_mtx = mutex();

#pragma pack(push)
struct p_data
{
  const uint32_t signature = 0x7E4A7A52;
  float position_x = 0.0;
  float position_y = 0.0;
  float rotation_heading = 0.0;

  float velocity_x = 0.0;
  float velocity_y = 0.0;
  float velocity_rotation_heading = 0.0;
};
#pragma pack(pop)

void setup()
{
  // port
  Serial.begin(115200);

  // configurating pinpoint
  pinpoint.begin();
  pinpoint.setEncoderDirections(goBILDA::EncoderDirection::Forward, goBILDA::EncoderDirection::Forward);

  // this might change
  pinpoint.setEncoderResolution(goBILDA::EncoderResolution::goBILDA_4_BAR_POD);

  // inits thread safe device, mutex look it up fool.
  mutex_init(&writing_serial_mtx);

  mutex_init(&reading_serial_mtx);

  mutex_init(&pinpoint_mtx);

  mutex_init(&data_mtx);
}

// reading
void loop()
{
  mutex_enter_blocking(&data_mtx);
  float write_sum = data_sum(&data);
  mutex_exit(&data_mtx);

  mutex_enter_blocking(&reading_serial_mtx);

  float read_sum;
  Serial.readBytes((char *)&read_sum, sizeof(read_sum));

  if (read_sum != write_sum)
  {
  }

  mutex_exit(&reading_serial_mtx);

  sleep_ms(1);
}

void loop1()
{
  mutex_enter_blocking(&pinpoint_mtx);
  goBILDA::Pose2D position = pinpoint.getPosition();
  p_data position_data = p_data();

  position_data.position_x = position.x;
  position_data.position_y = position.y;
  position_data.rotation_heading = position.heading;

  position_data.velocity_x = pinpoint.getVelocityX();
  position_data.velocity_y = pinpoint.getVelocityY();
  position_data.velocity_rotation_heading = pinpoint.getVelocityHeading();

  mutex_exit(&pinpoint_mtx);

  // converts data into a buffer of bytes.

  size_t buffer_size = sizeof(position_data);
  u_int8_t buffer[buffer_size];

  memcpy(buffer, &position_data, buffer_size);

  mutex_enter_blocking(&writing_serial_mtx);

  // sends data and waits for it to complete.
  Serial.write(buffer, buffer_size);

  mutex_exit(&writing_serial_mtx);

  mutex_enter_blocking(&data_mtx);
  data = position_data;
  mutex_exit(&data_mtx);

  sleep_ms(1);
}

float data_sum(p_data *data)
{
  return data->position_x +
         data->position_y +
         data->rotation_heading +
         data->velocity_x +
         data->velocity_y +
         data->velocity_rotation_heading;
}
