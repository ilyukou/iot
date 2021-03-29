#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

#include <ArduinoJson.h>

#include "DHT.h"

#define DHTTYPE DHT11
uint8_t DHTPin = D8;

// Initialize DHT sensor.
DHT dht(DHTPin, DHTTYPE);

float Temperature;
float Humidity;

const char* ssid = "11";
const char* password = "91108752";

String SERVER_IP = "http://192.168.100.6:8080";
String TOKEN = "hetlpPgV4vn63D3VZtnVTaflV4GZZO3x";

void setup() {
  Serial.begin(115200);
  Serial.println();

  WiFi.begin(ssid, password);

  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());

  pinMode(DHTPin, INPUT);
}

// the loop function runs over and over again forever
void loop() {
  Temperature = dht.readTemperature(); // Gets the values of the temperature
  Humidity = dht.readHumidity(); // Gets the values of the humidity
  request();
  delay(1000);
}

void request(){
    if(WiFi.status()== WL_CONNECTED){
      HTTPClient http;

      String serverPath = SERVER_IP + "/sensor/value/" + TOKEN;

      // Your Domain name with URL path or IP address with path
      http.begin(serverPath.c_str());

      http.addHeader("Content-Type", "application/json");

      String ms = "{\"value\": " + String(Temperature) +" }";

      // Send HTTP GET request
      int httpResponseCode = http.POST("Message from ESP8266");



      if (httpResponseCode > 0) {

           Serial.print("HTTP Response code: ");
           Serial.println(String(httpResponseCode));
           String payload = http.getString();
           Serial.println(payload);
      }
      // Free resources
      http.end();
    } else {
      Serial.println("WiFi Disconnected");
    }
}

String getState(String payload){
    DynamicJsonDocument doc(1024);
    deserializeJson(doc, payload);
    const char* jsonState = doc["body"]["state"];
    Serial.println(jsonState);

  return jsonState;
}

