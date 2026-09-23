#include <Arduino.h>
#include <pico/mutex.h>
#include <pico.h>
#include <goBILDA_Pinpoint.h>
#include <Serial.h>

goBILDA::Pinpoint pinpoint = goBILDA::Pinpoint();
goBILDA::Pose2D position = goBILDA::Pose2D();
goBILDA::Pose2D velocity = goBILDA::Pose2D();

float yaw_rotation = 0.0;

mutex_t data_mtx;

void setup()
{
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

  // sending data over ic2

  mutex_exit(&data_mtx);
}

// reading
void loop1()
{
  sleep_ms(1);

  mutex_enter_blocking(&data_mtx);

  position = pinpoint.getPosition();

  // this isn't my fault.
  velocity.x = pinpoint.getVelocityX();
  velocity.y = pinpoint.getVelocityY();
  velocity.heading = pinpoint.getVelocityHeading();

  // rotation
  yaw_rotation = pinpoint.getYawScalar();

  mutex_exit(&data_mtx);
}

// this takes the data and converts into a hashmap, and coverting into a json dataset.
String get_serialized_json()
{

  return "";
}