# Sensor Readings UI - Quick Start Guide

## Overview

The Sensor Readings UI is a modern web interface for managing and visualizing IoT sensor data. It provides real-time
updates, filtering, sorting, and pagination capabilities.

## Starting the Application

```bash
mvn clean quarkus:dev
```

The application will start at `http://localhost:8080`

## Accessing the UI

After the application starts, open your browser and navigate to:

```
http://localhost:8080/iot-air-q/ui/readings
```

## Features

### 1. Filtering Data

The UI provides four filter fields:

- **MAC Address**: Enter or paste the device MAC address (e.g., `AA:BB:CC:DD:EE:FF`)
- **Sensor Name**: Enter the sensor name (e.g., `temperature`, `humidity`)
- **Reading Type**: Enter the reading type (e.g., `CELSIUS`, `FAHRENHEIT`, `PERCENT`)
- **Package ID**: Enter or paste the package UUID

**To apply filters:**

1. Fill in one or more filter fields
2. Click the **"Apply Filters"** button
3. The table will update to show only matching records

**To clear filters:**

- Click the **"Reset Filters"** button to clear all filter fields and reload without filters

### 2. Sorting

The UI supports sorting by the "Created" timestamp field in two directions:

- **Newest First** (DESC): Shows the most recently created readings at the top
- **Oldest First** (ASC): Shows the oldest readings at the top

**To change sort order:**

1. Use the "Sort by Created:" dropdown
2. Select your preferred order
3. The table will automatically refresh and reset to page 1

### 3. Pagination

The UI supports dynamic pagination with configurable page size:

**To navigate pages:**

- Click **"← Previous"** to go to the previous page
- Click **"Next →"** to go to the next page
- Click a page number to jump directly to that page

**To change page size:**

1. Enter a value in the "Page Size:" field (1-100 records)
2. Press Enter or click outside the field
3. The page will reset to page 1 with the new page size

### 4. Real-time Auto-Refresh

The UI can automatically refresh when new data arrives on the server:

**To enable auto-refresh:**

- Check the **"Auto-refresh on new data"** checkbox

**To disable auto-refresh:**

- Uncheck the **"Auto-refresh on new data"** checkbox

When enabled, the UI will automatically fetch new data whenever:

1. A new sensor reading is received by the server
2. The auto-refresh event is broadcast via WebSocket

**Connection Status:**

- **Green indicator "Connected"**: WebSocket connection is active and working
- **Red indicator "Disconnected"**: WebSocket connection is not available
    - The UI will continue to function with manual refresh
    - The UI will automatically attempt to reconnect

### 5. Data Table

The table displays the following columns for each sensor reading:

| Column       | Description                            |
|--------------|----------------------------------------|
| MAC Address  | Device MAC address                     |
| Sensor Name  | Name of the sensor                     |
| Reading Type | Type of measurement (e.g., CELSIUS)    |
| Reading Data | The actual sensor value                |
| Package ID   | Unique identifier for the data package |
| Created      | Timestamp when the reading was created |

## Example Workflows

### Workflow 1: Find All Temperature Readings

1. Enter `temperature` in the "Sensor Name" field
2. Click "Apply Filters"
3. The table will show all temperature readings

### Workflow 2: Monitor a Specific Device

1. Enter the MAC address in the "MAC Address" field (e.g., `AA:BB:CC:DD:EE:FF`)
2. Click "Apply Filters"
3. Change sort order to "Newest First" to see latest readings
4. Check "Auto-refresh on new data" to see updates in real-time

### Workflow 3: Export Readings (Manual)

1. Apply desired filters
2. Set page size to maximum (100)
3. Navigate through pages and copy data as needed
4. (Future feature: Direct CSV export button)

### Workflow 4: Analyze Historical Data

1. Leave filters empty to see all data
2. Select "Oldest First" sort order
3. Adjust page size as needed
4. Review timestamps to identify trends

## API Endpoints

### HTML UI

```
GET /iot-air-q/ui/readings
```

### REST API (JSON)

```
GET /iot-air-q/api/readings
```

Parameters:

- `macAddress`: Filter by MAC address
- `sensorName`: Filter by sensor name
- `readingType`: Filter by reading type
- `packageId`: Filter by package ID
- `sortOrder`: ASC or DESC (default: DESC)
- `pageNumber`: Page number (default: 1)
- `pageSize`: Records per page (default: 10)

Example:

```
GET /iot-air-q/api/readings?sensorName=temperature&sortOrder=DESC&pageSize=20
```

### WebSocket

```
ws://localhost:8080/iot-air-q/ws/sensor-readings
```

## Keyboard Shortcuts

- **Enter** in filter fields: Apply filters
- **Enter** in page size field: Update pagination

## Mobile Support

The UI is fully responsive and supports:

- Tablets (landscape and portrait)
- Mobile phones
- Different screen sizes

The layout will adapt automatically:

- Single column filters on mobile
- Scrollable table for small screens
- Touch-friendly buttons and controls

## Troubleshooting

### No Data Displays

1. Check if sensor readings exist in the database
2. Verify filters are not too restrictive
3. Try resetting filters
4. Check browser console for JavaScript errors (F12 → Console)

### WebSocket Connection Issues

1. Verify the server is running
2. Check if browser supports WebSocket
3. Look for firewall or proxy blocking WebSocket connections
4. The UI will work without WebSocket; uncheck auto-refresh

### Slow Performance

1. Reduce the page size (fewer records per page)
2. Apply more specific filters
3. Clear browser cache (Ctrl+Shift+Del)
4. Consider archiving old data in the database

### Cannot Find Readings

1. Check MAC address format (should be XX:XX:XX:XX:XX:XX)
2. Verify sensor name spelling (case-sensitive)
3. Try broader filters first
4. Check server logs for errors

## Browser Requirements

- Modern web browser (Chrome, Firefox, Safari, Edge)
- JavaScript enabled
- WebSocket support (all modern browsers)
- Cookies enabled (for session management)

## Performance Tips

1. **Use filters** to reduce the number of records displayed
2. **Adjust page size** based on your preference (larger = fewer requests)
3. **Sort by Created** for better performance than other sorts
4. **Disable auto-refresh** if not actively monitoring updates

## Security Notes

- Filters are parameterized to prevent SQL injection
- Input is validated on the server
- WebSocket connections are scoped to authenticated clients
- No sensitive data is logged in the browser console

## Support and Feedback

For issues or feature requests, please refer to the project documentation or contact the development team.

---

**Last Updated:** 2026-02-15  
**Version:** 1.0.0

