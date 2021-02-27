#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <WiFiClient.h>

#include <ArduinoJson.h>

const char* ssid = "11";
const char* password = "91108752";

String SERVER_IP = "http://192.168.100.6:8080";
String TOKEN = "jxCcAKvy72kyCttUiZhU";
String STATE = "off";

void setup() {
  Serial.begin(115200);
  Serial.println();

  WiFi.begin("11", "password");

  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println();

  Serial.print("Connected, IP address: ");
  Serial.println(WiFi.localIP());
  
  pinMode(D7, OUTPUT);     // Initialize the LED_BUILTIN pin as an output
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
        STATE = "off";

    } else if(STATE == "on"){
        digitalWrite(D7, HIGH);
        STATE = "on";
    }
}

void request(){
    if(WiFi.status()== WL_CONNECTED){
      HTTPClient http;

      String serverPath = SERVER_IP + "/deviceState/" + TOKEN + "?state=" + STATE;

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

