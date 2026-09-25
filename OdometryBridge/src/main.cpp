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
}

// reading
void loop()
{
  mutex_enter_blocking(&reading_serial_mtx);

  // int data = Serial.read();
  // Serial.flush();

  mutex_exit(&reading_serial_mtx);

  sleep_ms(1);
}

void loop1()
{
  mutex_enter_blocking(&pinpoint_mtx);
  goBILDA::Pose2D position = pinpoint.getPosition();

  float positions_data[6] = {
      position.x,
      position.y,
      position.heading,

      pinpoint.getVelocityX(),
      pinpoint.getVelocityY(),
      pinpoint.getVelocityHeading(),
  };

  mutex_exit(&pinpoint_mtx);

  // converts data into a buffer of bytes.
  size_t buffer_size = sizeof(positions_data);
  const unsigned char *buffer = reinterpret_cast<const unsigned char *>(positions_data);

  mutex_enter_blocking(&writing_serial_mtx);

  // sends data and waits for it to complete.
  Serial.write(buffer, buffer_size);
  Serial.flush();

  mutex_exit(&writing_serial_mtx);

  sleep_ms(1);
}
