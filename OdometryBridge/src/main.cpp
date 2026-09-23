#include <Arduino.h>
#include <ArduinoJson.h>
#include <pico/mutex.h>
#include <pico.h>
#include <goBILDA_Pinpoint.h>
#include <Serial.h>

goBILDA::Pinpoint pinpoint = goBILDA::Pinpoint();
ArduinoJson::JsonDocument pinpoint_data_doc = ArduinoJson::JsonDocument();

mutex_t data_mtx;

void setup()
{
  // todo what port
  Serial.begin(0);

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

  // write json

  mutex_exit(&data_mtx);
}

// reading
void loop1()
{
  sleep_ms(1);

  mutex_enter_blocking(&data_mtx);

  pinpoint_data_doc = get_json_doc();

  mutex_exit(&data_mtx);
}

// todo needs to convert to string.
ArduinoJson::JsonDocument get_json_doc()
{
  ArduinoJson::JsonDocument doc = ArduinoJson::JsonDocument();
  goBILDA::Pose2D position = pinpoint.getPosition();
  goBILDA::Pose2D velocity = goBILDA::Pose2D();

  // this isn't my fault.
  velocity.x = pinpoint.getVelocityX();
  velocity.y = pinpoint.getVelocityY();
  velocity.heading = pinpoint.getVelocityHeading();

  // rotation
  float yaw_rotation = pinpoint.getYawScalar();

  doc["x"] = position.x;
  doc["y"] = position.y;
  doc["rotation"] = yaw_rotation;

  doc["velX"] = velocity.x;
  doc["velY"] = velocity.y;

  return doc;
}