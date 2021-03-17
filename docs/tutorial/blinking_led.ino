#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

#include <ArduinoJson.h>

const char* ssid = "WIFI_SSID";
const char* password = "WIFI_PASSWORD";

String SERVER_IP = "SERVER_IP";
String TOKEN = "DEVICE_TOKEN";

String STATE = "off"; // default state

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
  
  pinMode(D7, OUTPUT);     // Initialize the LED_BUILTIN pin as an output. Port D7
}

// the loop function runs over and over again forever
void loop() {
    request();
    execute();
    delay(100);
}

void execute(){
    if(STATE == "off"){
        digitalWrite(D7, LOW);
    } else if(STATE == "on"){
        digitalWrite(D7, HIGH);
    } else {
        Serial.println("Not found such state for execute ");
        Serial.print(STATE);
    }
}

void request(){
    if(WiFi.status()== WL_CONNECTED){
      HTTPClient http;

      String serverPath = SERVER_IP + "/deviceStateDto/" + TOKEN + "?state=" + STATE;

      // Your Domain name with URL path or IP address with path
      http.begin(serverPath.c_str());

      // Send HTTP GET request
      int httpResponseCode = http.GET();



      if (httpResponseCode > 0) {

           Serial.print("HTTP Response code: ");
           Serial.println(String(httpResponseCode));
           String payload = http.getString();
           Serial.println(payload);

        if(httpResponseCode == 200){

            STATE = getState(payload);

        } else if(httpResponseCode == 204){
            Serial.println("Not change state");
        } else {
            Serial.println("Not supported http code");
        }
      }
      // Free resources
      http.end();
    }
    else {
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

