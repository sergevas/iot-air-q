#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>
#include <Wire.h>
#include <SensirionI2cSht3x.h>
#include "ccs811.h"

CCS811 ccs811(-1, CCS811_SLAVEADDR_1);
uint16_t ccs811_baseline;

const char *WIFI_SSID = "********";
const char *WIFI_PASSWORD = "********";
const int WIFI_NUM_OF_RETRIES = 20;
const uint32_t NETWORK_ERROR_RECOVERY_DELAY = 600000000;
const uint8_t HTTP_REST_PORT = 80;
const char *GATEWAY_HOST = "localhost";
const uint16_t GATEWAY_PORT = 8080;
const char *GATEWAY_IP_RECEIVE_API = "/iot-air-q/gateway/sensor/ip-address";

const String SHT31X = "SHT31X";
const String CCS811 = "CCS811";
const String READINGS_TYPE_TEMP = "TEMP";
const String READINGS_TYPE_HUMID = "HUMID";
const String READINGS_TYPE_CO2 = "CO2";
const String READINGS_TYPE_TVOC = "TVOC";
const String READINGS_TYPE_BASELINE = "BASELINE";
const String SENSOR_READINGS = "sensorReadings";
const String READINGS = "readings";
const String NAME = "name";
const String TYPE = "type";
const String DATA = "data";
const String IP = "ip";
const String MAC_ADDRES = "macAddress";

String baseUrl;
ESP8266WebServer httpRestServer(HTTP_REST_PORT);
WiFiClient httpClient;

typedef struct {
  float temperature;
  float humidity;
} SHT3x_Readings;

SHT3x_Readings sht3x_readings;

typedef struct {
  uint16_t co2;
  uint16_t tvoc;
} CCS811_Readings;

CCS811_Readings ccs811_readings;

void initWiFi() {
  Serial.println();
  Serial.println("WiFi connect");
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  int retriesCount = 0;
  while (WiFi.status() != WL_CONNECTED) {
    retriesCount++;
    if (retriesCount > WIFI_NUM_OF_RETRIES) {
      Serial.println();
      Serial.print("Max number of retries for WiFi exceeded ");
      Serial.println(WIFI_NUM_OF_RETRIES);
      Serial.print("Sleeping for ");
      Serial.println(NETWORK_ERROR_RECOVERY_DELAY);
      ESP.deepSleep(NETWORK_ERROR_RECOVERY_DELAY);
    }
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.print("WiFi connected. IP ");
  Serial.println(WiFi.localIP());
}

String build_ip_advertising_request_body() {

}

void advertise_ip_address() {
  Serial.print("Connecting to gateway...\nStatus: ");
  Serial.println(httpClient.connect(GATEWAY_HOST, GATEWAY_PORT));
}

String createResourceUrl(String resourceName) {
  return baseUrl + "/" + resourceName;
}

String build_sensor_readings_resp_body() {
  read_SHT3x(&sht3x_readings);
  read_CCS811(&ccs811_readings, &sht3x_readings);

  JsonDocument doc;
  JsonArray sensorReadings = doc[SENSOR_READINGS].to<JsonArray>();
  JsonObject sensorReadings_0 = sensorReadings.add<JsonObject>();
  sensorReadings_0[NAME] = SHT31X;
  JsonArray sensorReadings_0_readings = sensorReadings_0[READINGS].to<JsonArray>();
  JsonObject sensorReadings_0_readings_0 = sensorReadings_0_readings.add<JsonObject>();
  sensorReadings_0_readings_0[TYPE] = READINGS_TYPE_TEMP;
  sensorReadings_0_readings_0[DATA] = sht3x_readings.temperature;
  JsonObject sensorReadings_0_readings_1 = sensorReadings_0_readings.add<JsonObject>();
  sensorReadings_0_readings_1[TYPE] = READINGS_TYPE_HUMID;
  sensorReadings_0_readings_1[DATA] = sht3x_readings.humidity;
  JsonObject sensorReadings_1 = sensorReadings.add<JsonObject>();
  sensorReadings_1[NAME] = CCS811;
  JsonObject sensorReadings_1_readings_0 = sensorReadings_1[READINGS].add<JsonObject>();
  sensorReadings_1_readings_0[TYPE] = READINGS_TYPE_TVOC;
  sensorReadings_1_readings_0[DATA] = ccs811_readings.tvoc;
  JsonObject sensorReadings_1_readings_1 = sensorReadings_1[READINGS].add<JsonObject>();
  sensorReadings_1_readings_1[TYPE] = READINGS_TYPE_CO2;
  sensorReadings_1_readings_1[DATA] = ccs811_readings.co2;
  String resp_body;
  doc.shrinkToFit();
  serializeJson(doc, resp_body);
  Serial.println("\nSensor readings response body: " + resp_body);
  return resp_body;
}

String build_ccs811_baseline_resp_body() {
  JsonDocument doc;
  JsonObject sensor_reading = doc.add<JsonObject>();
  sensor_reading[NAME] = CCS811;
  JsonArray readings = sensor_reading[READINGS].to<JsonArray>();
  JsonObject baseline_reading = readings.add<JsonObject>();
  baseline_reading[TYPE] = READINGS_TYPE_BASELINE;
  baseline_reading[DATA] = ccs811_baseline;
  String resp_body;
  doc.shrinkToFit();
  serializeJson(doc, resp_body);
  Serial.println("\nCCS811 baseline response body: " + resp_body);
  return resp_body;
}

void get_ccs811_baseline() {
  bool ok = read_ccs811_baseline();
  if (!ok) {
    httpRestServer.send(500, F("application/json"), "{}");
  } else {
    httpRestServer.send(200, F("application/json"), build_ccs811_baseline_resp_body());
  }
}

void update_ccs811_baseline() {
  String putBody = httpRestServer.arg("plain");
  JsonDocument doc;
  DeserializationError error = deserializeJson(doc, putBody);
  if (error) {
    Serial.print("update_ccs811_baseline deserializeJson() failed: ");
    Serial.println(error.c_str());
    httpRestServer.send(500, F("application/json"), "{}");
  }
  bool ok = write_ccs811_baseline(doc["readings"][0]["data"]);
  if (!ok) {
    httpRestServer.send(500, F("application/json"), "{}");
  } else {
    httpRestServer.send(200, F("application/json"), build_ccs811_baseline_resp_body());
  }
}

void get_sensor_readings() {
  httpRestServer.send(200, F("application/json"), build_sensor_readings_resp_body());
}

void restServerRouting() {
  httpRestServer.on(createResourceUrl("readings"), HTTP_GET, get_sensor_readings);
  httpRestServer.on(createResourceUrl("ccs811/baseline"), HTTP_GET, get_ccs811_baseline);
  httpRestServer.on(createResourceUrl("ccs811/baseline"), HTTP_PUT, update_ccs811_baseline);
}

static char sht3x_errorMessage[64];
static int16_t sht3x_error;
SensirionI2cSht3x sht3x_sensor;

void setup_sht3x() {
  sht3x_sensor.begin(Wire, SHT30_I2C_ADDR_44);
  sht3x_sensor.stopMeasurement();
  delay(1);
  sht3x_sensor.softReset();
  delay(100);
  uint16_t status_register = 0u;
  sht3x_error = sht3x_sensor.readStatusRegister(status_register);
  if (sht3x_error != NO_ERROR) {
    Serial.print("SHT31x error trying to execute readStatusRegister(): ");
    errorToString(sht3x_error, sht3x_errorMessage, sizeof sht3x_errorMessage);
    Serial.println(sht3x_errorMessage);
    return;
  }
  Serial.print("SHT31x status register (BIN): ");
  Serial.println(status_register, BIN);
}

void read_SHT3x(SHT3x_Readings *sht3x_readings) {
  sht3x_error = sht3x_sensor.measureSingleShot(REPEATABILITY_MEDIUM, false, sht3x_readings->temperature, sht3x_readings->humidity);
  if (sht3x_error != NO_ERROR) {
    Serial.print("Error trying to execute SHT31x measureSingleShot(): ");
    errorToString(sht3x_error, sht3x_errorMessage, sizeof sht3x_errorMessage);
    Serial.println(sht3x_errorMessage);
    return;
  }
}

void setup_ccs811() {
  Serial.println("Setup CCS811");
  Serial.println("CCS811 library version: ");
  Serial.println(CCS811_VERSION);
  // Enable CCS811  
  ccs811.set_i2cdelay(50);  // Needed for ESP8266 because it doesn't handle I2C clock stretch correctly
  bool ok;
  ok = ccs811.begin();
  if (!ok) {
    Serial.println("CCS811 setup failed");
  }
  // Start CCS811 (measure every 1 second)
  Serial.print("CCS811 start");
  ok = ccs811.start(CCS811_MODE_1SEC);
  if (!ok) {
    Serial.println("CCS811 start failed");
  }
}

void read_CCS811(CCS811_Readings *ccs811_readings, SHT3x_Readings *sht3x_readings) {
  bool ok;
  uint16_t errstat;
  uint16_t raw;
  ok = ccs811.set_envdata_Celsius_percRH(sht3x_readings->temperature, sht3x_readings->humidity);
  if (!ok) {
    Serial.println("CCS811 ENV_DATA setup failed");
  }
  while (true) {
    ccs811.read(&ccs811_readings->co2, &ccs811_readings->tvoc, &errstat, &raw);
    if (CCS811_ERRSTAT_OK == errstat) {
      break;
    } else if (errstat & CCS811_ERRSTAT_I2CFAIL) {
      Serial.println("CCS811 I2C FATAL error");
      break;
    } else if (CCS811_ERRSTAT_OK_NODATA == errstat) {
      Serial.print(".");
      delay(100);
      continue;
    } else {
      Serial.print("CCS811 error: ");
      Serial.println(ccs811.errstat_str(errstat));
    }
  }
}

boolean read_ccs811_baseline() {
  bool ok;
  ok = ccs811.get_baseline(&ccs811_baseline);
  if (!ok) {
    Serial.println("get_baseline: CCS811 I2C FATAL error");
  }
  return ok;
}

boolean write_ccs811_baseline(uint16_t ccs811_baseline) {
  bool ok;
  ok = ccs811.set_baseline(ccs811_baseline);
  if (!ok) {
    Serial.println("set_baseline: CCS811 I2C FATAL error");
  }
  return ok;
}

void setup() {
  Serial.begin(115200);
  while (!Serial) {
    delay(100);
  }
  Wire.begin();
  setup_sht3x();
  setup_ccs811();
  restServerRouting();
  httpRestServer.begin();
}

void loop() {
  if (WiFi.status() != WL_CONNECTED) {
    initWiFi();
  }
  httpRestServer.handleClient();
}