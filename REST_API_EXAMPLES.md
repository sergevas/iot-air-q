# REST API Examples

## Base URLs

- **HTML UI**: `http://localhost:8080/iot-air-q/ui/readings`
- **REST API**: `http://localhost:8080/iot-air-q/api/readings`
- **Raw JSON API**: `http://localhost:8080/iot-air-q/readings`
- **WebSocket**: `ws://localhost:8080/iot-air-q/ws/sensor-readings`

## REST API Endpoints

### 1. Get All Sensor Readings (JSON)

```bash
curl http://localhost:8080/iot-air-q/api/readings
```

**Response:**

```json
{
  "readings": [
    {
      "packageId": "550e8400-e29b-41d4-a716-446655440000",
      "macAddress": "AA:BB:CC:DD:EE:FF",
      "sensorName": "temperature",
      "readingType": "CELSIUS",
      "readingData": 22.5,
      "created": "2026-02-15T13:45:30Z"
    }
  ],
  "totalRecords": 1,
  "totalPages": 1,
  "currentPage": 1,
  "pageSize": 10
}
```

### 2. Filter by MAC Address

```bash
curl "http://localhost:8080/iot-air-q/api/readings?macAddress=AA:BB:CC:DD:EE:FF"
```

### 3. Filter by Sensor Name

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sensorName=temperature"
```

### 4. Filter by Reading Type

```bash
curl "http://localhost:8080/iot-air-q/api/readings?readingType=CELSIUS"
```

### 5. Filter by Package ID

```bash
curl "http://localhost:8080/iot-air-q/api/readings?packageId=550e8400-e29b-41d4-a716-446655440000"
```

### 6. Multiple Filters (AND condition)

```bash
curl "http://localhost:8080/iot-air-q/api/readings?macAddress=AA:BB:CC:DD:EE:FF&sensorName=temperature"
```

### 7. Sort by Created (Newest First)

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sortOrder=DESC"
```

### 8. Sort by Created (Oldest First)

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sortOrder=ASC"
```

### 9. Pagination - Page 2 with 25 Records per Page

```bash
curl "http://localhost:8080/iot-air-q/api/readings?pageNumber=2&pageSize=25"
```

### 10. Complex Query - Multiple Filters, Sorted, Paginated

```bash
curl "http://localhost:8080/iot-air-q/api/readings?macAddress=AA:BB:CC:DD:EE:FF&sensorName=temperature&sortOrder=DESC&pageNumber=1&pageSize=50"
```

## JavaScript Examples

### Basic Fetch

```javascript
fetch('/iot-air-q/api/readings')
    .then(response => response.json())
    .then(data => {
        console.log('Readings:', data.readings);
        console.log('Total records:', data.totalRecords);
        console.log('Current page:', data.currentPage);
    });
```

### Fetch with Filters

```javascript
const params = new URLSearchParams({
    macAddress: 'AA:BB:CC:DD:EE:FF',
    sensorName: 'temperature',
    sortOrder: 'DESC',
    pageSize: 20
});

fetch(`/iot-air-q/api/readings?${params}`)
    .then(response => response.json())
    .then(data => {
        data.readings.forEach(reading => {
            console.log(`${reading.sensorName}: ${reading.readingData} ${reading.readingType}`);
        });
    });
```

### Async/Await

```javascript
async function fetchSensorReadings(filters = {}) {
    const params = new URLSearchParams(filters);
    try {
        const response = await fetch(`/iot-air-q/api/readings?${params}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching sensor readings:', error);
        throw error;
    }
}

// Usage
const readings = await fetchSensorReadings({
    sensorName: 'temperature',
    sortOrder: 'DESC',
    pageSize: 10
});
```

## Raw JSON API (without paging/sorting wrapper)

```bash
curl "http://localhost:8080/iot-air-q/readings?macAddress=AA:BB:CC:DD:EE:FF"
```

**Response:**

```json
[
  {
    "packageId": "550e8400-e29b-41d4-a716-446655440000",
    "macAddress": "AA:BB:CC:DD:EE:FF",
    "sensorName": "temperature",
    "readingType": "CELSIUS",
    "readingData": 22.5,
    "created": "2026-02-15T13:45:30Z"
  }
]
```

## WebSocket Examples

### Connect to WebSocket

```javascript
const ws = new WebSocket('ws://localhost:8080/iot-air-q/ws/sensor-readings');

ws.addEventListener('open', function () {
    console.log('Connected to WebSocket');
});

ws.addEventListener('message', function (event) {
    console.log('Message from server:', event.data);
    if (event.data === 'REFRESH') {
        console.log('New sensor data available, refreshing...');
        // Fetch new data
    }
});

ws.addEventListener('close', function () {
    console.log('Disconnected from WebSocket');
});

ws.addEventListener('error', function (event) {
    console.error('WebSocket error:', event);
});
```

## cURL Examples with Pretty Output

### Using jq for formatted output

```bash
curl http://localhost:8080/iot-air-q/api/readings | jq '.'
```

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sensorName=temperature&sortOrder=DESC&pageSize=5" | jq '.readings[] | {sensor: .sensorName, type: .readingType, value: .readingData, time: .created}'
```

## Query Parameter Reference

| Parameter   | Type    | Default | Example                              | Notes                              |
|-------------|---------|---------|--------------------------------------|------------------------------------|
| macAddress  | String  | null    | AA:BB:CC:DD:EE:FF                    | Device MAC address                 |
| sensorName  | String  | null    | temperature                          | Sensor identifier                  |
| readingType | String  | null    | CELSIUS                              | Unit or type of reading            |
| packageId   | UUID    | null    | 550e8400-e29b-41d4-a716-446655440000 | Package identifier                 |
| sortBy      | String  | created | created                              | Currently only "created" supported |
| sortOrder   | String  | DESC    | ASC, DESC                            | Sort direction                     |
| pageNumber  | Integer | 1       | 2                                    | Page number (1-based)              |
| pageSize    | Integer | 10      | 25                                   | Records per page (1-100)           |

## Response Schema

### Paginated Response (API)

```json
{
  "readings": [
    {
      "packageId": "UUID",
      "macAddress": "String",
      "sensorName": "String",
      "readingType": "String",
      "readingData": "Double",
      "created": "ISO 8601 Timestamp"
    }
  ],
  "totalRecords": "Integer",
  "totalPages": "Integer",
  "currentPage": "Integer",
  "pageSize": "Integer"
}
```

### Array Response (Raw API)

```json
[
  {
    "packageId": "UUID",
    "macAddress": "String",
    "sensorName": "String",
    "readingType": "String",
    "readingData": "Double",
    "created": "ISO 8601 Timestamp"
  }
]
```

## Common Use Cases

### Get Latest 10 Readings

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sortOrder=DESC&pageSize=10"
```

### Get All Temperature Readings from a Device

```bash
curl "http://localhost:8080/iot-air-q/api/readings?macAddress=AA:BB:CC:DD:EE:FF&sensorName=temperature"
```

### Get Readings from Specific Package

```bash
curl "http://localhost:8080/iot-air-q/api/readings?packageId=550e8400-e29b-41d4-a716-446655440000"
```

### Paginate Through All Results

```bash
# Page 1
curl "http://localhost:8080/iot-air-q/api/readings?pageNumber=1&pageSize=100"

# Page 2
curl "http://localhost:8080/iot-air-q/api/readings?pageNumber=2&pageSize=100"
```

## Error Handling

### Invalid Page Number

If `pageNumber` exceeds `totalPages`, it will be capped at the last page.

### Invalid Page Size

If `pageSize` is outside 1-100 range, defaults to 10.

### Invalid Query Parameters

Non-matching filters will return empty results (not an error).

---

For more information, see the [UI Documentation](UI_DOCUMENTATION.md) and [Quick Start Guide](SENSOR_UI_QUICKSTART.md).

