package dev.sergevas.iot.support;

public class TestData {

    public static final String SENSOR_DATA_JSON_01 = """
            [
                   {
                       "created": "2026-01-21T06:00:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174000",
                       "readingData": 400.0,
                       "readingType": "CO2",
                       "sensorName": "CCS811"
                   },
                   {
                       "created": "2026-01-21T06:00:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174000",
                       "readingData": 75.0,
                       "readingType": "TVOC",
                       "sensorName": "CCS811"
                   },
                   {
                       "created": "2026-01-21T06:00:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174000",
                       "readingData": 10.8,
                       "readingType": "TEMP",
                       "sensorName": "SHT31X"
                   },
                   {
                       "created": "2026-01-21T07:00:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174000",
                       "readingData": 58.2,
                       "readingType": "HUMID",
                       "sensorName": "SHT31X"
                   },
                   {
                       "created": "2026-02-14T12:22:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174002",
                       "readingData": 500.0,
                       "readingType": "CO2",
                       "sensorName": "CCS811"
                   },
                   {
                       "created": "2026-02-14T12:22:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174002",
                       "readingData": 75.0,
                       "readingType": "TVOC",
                       "sensorName": "CCS811"
                   },
                   {
                       "created": "2026-02-14T12:22:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174002",
                       "readingData": 27.93866,
                       "readingType": "TEMP",
                       "sensorName": "SHT31X"
                   },
                   {
                       "created": "2026-02-14T12:22:00Z",
                       "macAddress": "00:1B:44:11:3A:EA",
                       "packageId": "123e4567-e89b-12d3-a456-426614174002",
                       "readingData": 35.47471,
                       "readingType": "HUMID",
                       "sensorName": "SHT31X"
                   }
               ]
            """;

    public static final String SENSOR_DATA_JSON_02 = """
            [
                  {
                      "macAddress": "00:1B:44:11:3A:EA",
                      "packageId": "123e4567-e89b-12d3-a456-426614174000",
                      "readingData": 400.0,
                      "readingType": "CO2",
                      "sensorName": "CCS811"
                  },
                  {
                      "macAddress": "00:1B:44:11:3A:EA",
                      "packageId": "123e4567-e89b-12d3-a456-426614174000",
                      "readingData": 75.0,
                      "readingType": "TVOC",
                      "sensorName": "CCS811"
                  },
                  {
                      "macAddress": "00:1B:44:11:3A:EA",
                      "packageId": "123e4567-e89b-12d3-a456-426614174002",
                      "readingData": 500.0,
                      "readingType": "CO2",
                      "sensorName": "CCS811"
                  },
                  {
                      "macAddress": "00:1B:44:11:3A:EA",
                      "packageId": "123e4567-e89b-12d3-a456-426614174002",
                      "readingData": 75.0,
                      "readingType": "TVOC",
                      "sensorName": "CCS811"
                  }
              ]
            """;

    public static final String SENSOR_DATA_JSON_03 = """
            [
                {
                    "macAddress": "00:1B:44:11:3A:EA",
                    "packageId": "123e4567-e89b-12d3-a456-426614174000",
                    "readingData": 75.0,
                    "readingType": "TVOC",
                    "sensorName": "CCS811"
                },
                {
                    "macAddress": "00:1B:44:11:3A:EA",
                    "packageId": "123e4567-e89b-12d3-a456-426614174002",
                    "readingData": 75.0,
                    "readingType": "TVOC",
                    "sensorName": "CCS811"
                }
            ]
            """;

    public static final String SENSOR_DATA_JSON_04 = """
            [
                {
                    "macAddress": "00:1B:44:11:3A:B8",
                    "packageId": "123e4567-e89b-12d3-a456-426614174003",
                    "readingData": 600.0,
                    "readingType": "CO2",
                    "sensorName": "CCS811"
                },
                {
                    "macAddress": "00:1B:44:11:3A:B8",
                    "packageId": "123e4567-e89b-12d3-a456-426614174003",
                    "readingData": 275.0,
                    "readingType": "TVOC",
                    "sensorName": "CCS811"
                },
                {
                    "macAddress": "00:1B:44:11:3A:B8",
                    "packageId": "123e4567-e89b-12d3-a456-426614174003",
                    "readingData": 28.93866,
                    "readingType": "TEMP",
                    "sensorName": "SHT31X"
                },
                {
                    "macAddress": "00:1B:44:11:3A:B8",
                    "packageId": "123e4567-e89b-12d3-a456-426614174003",
                    "readingData": 36.47471,
                    "readingType": "HUMID",
                    "sensorName": "SHT31X"
                }
            ]
            """;

    public static final String SENSOR_DATA_JSON_05 = """
            [
                 {
                     "macAddress": "00:1B:44:11:3A:B7",
                     "packageId": "f8a3c816-4722-40e1-8cce-f20cf7bbabc5",
                     "readingData": 26.93866,
                     "readingType": "TEMP",
                     "sensorName": "SHT31X"
                 },
                 {
                     "macAddress": "00:1B:44:11:3A:B7",
                     "packageId": "f8a3c816-4722-40e1-8cce-f20cf7bbabc5",
                     "readingData": 34.47471,
                     "readingType": "HUMID",
                     "sensorName": "SHT31X"
                 },
                 {
                     "macAddress": "00:1B:44:11:3A:B7",
                     "packageId": "f8a3c816-4722-40e1-8cce-f20cf7bbabc5",
                     "readingData": 6.0,
                     "readingType": "TVOC",
                     "sensorName": "CCS811"
                 },
                 {
                     "macAddress": "00:1B:44:11:3A:B7",
                     "packageId": "f8a3c816-4722-40e1-8cce-f20cf7bbabc5",
                     "readingData": 445.0,
                     "readingType": "CO2",
                     "sensorName": "CCS811"
                 }
             ]
            """;
}
