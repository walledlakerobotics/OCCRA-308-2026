#include <Arduino.h>
// #include <ArduinoJson.h>
#include <pico/mutex.h>
#include <pico.h>
#include <goBILDA_Pinpoint.h>
#include <Serial.h>

// I want perfect memory management so it can send it fast.

goBILDA::Pinpoint pinpoint = goBILDA::Pinpoint();

float positions_data[5] = {
    0.0, // x
    0.0, // y
    0.0, // rotation

    0.0, // velocity x
    0.0, // velocity y
};

mutex_t data_mtx = mutex();

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
  mutex_init(&data_mtx);
}

// writing
void loop()
{
  sleep_ms(1);

  mutex_enter_blocking(&data_mtx);

  // points to the positions allocated in the heap,so memory doesn't need to be copied.
  const u_int8_t *bytes_ptr = (u_int8_t *)positions_data;

  // needs todo smth Im done for now.

    mutex_exit(&data_mtx);
}

// reading
void loop1()
{
  sleep_ms(1);

  mutex_enter_blocking(&data_mtx);

  goBILDA::Pose2D position = pinpoint.getPosition();

  // I know this is a horrible way of handling reading, but directly sending information fast with stringfiying this is the only way I could think of.
  positions_data[0] = position.x;
  positions_data[1] = position.y;
  positions_data[2] = pinpoint.getNormalizedHeading();

  positions_data[3] = pinpoint.getVelocityX();
  positions_data[4] = pinpoint.getVelocityY();

  mutex_exit(&data_mtx);
}

// todo needs to convert to string.
