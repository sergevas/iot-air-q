# Sensor Readings UI Documentation

## Overview

The Sensor Readings UI provides a modern web interface for viewing, filtering, sorting, and paginating sensor data. It
includes real-time updates via WebSocket and supports auto-refresh functionality.

## Features

### 1. **Tabular Display**

- Displays sensor readings in a responsive HTML table
- Columns include:
    - MAC Address
    - Sensor Name
    - Reading Type
    - Reading Data
    - Package ID
    - Created (timestamp)

### 2. **Filtering**

The UI supports filtering by the following fields:

- **MAC Address**: Filter by device MAC address
- **Sensor Name**: Filter by sensor name (e.g., "temperature")
- **Reading Type**: Filter by reading type (e.g., "CELSIUS")
- **Package ID**: Filter by package UUID

Filters are applied via the "Apply Filters" button and can be reset with the "Reset Filters" button.

### 3. **Sorting**

- Sort by "Created" timestamp field
- Two directions supported:
    - **Newest First** (DESC): Shows latest readings first
    - **Oldest First** (ASC): Shows oldest readings first
- Sorting automatically resets pagination to page 1

### 4. **Pagination**

- Dynamic page size control (1-100 records per page, default: 10)
- Page navigation buttons (Previous, Next, direct page selection)
- Displays current page and total number of pages
- Changing page size resets to page 1

### 5. **Real-time Updates**

- WebSocket connection to server for live updates
- Auto-refresh capability controlled by checkbox
- Shows connection status (Connected/Disconnected)
- Automatic reconnection with exponential backoff
- State preservation: Filter and sort settings maintained after refresh

## Access Points

### HTML UI

- **URL**: `/iot-air-q/ui/readings`
- Renders the complete dashboard with all features
- Responsive design for desktop and mobile devices

### REST API (JSON)

- **URL**: `/iot-air-q/api/readings`
- Query Parameters:
    - `macAddress` (optional): Filter by MAC address
    - `sensorName` (optional): Filter by sensor name
    - `readingType` (optional): Filter by reading type
    - `packageId` (optional): Filter by package ID
    - `sortBy` (optional): Sort field (default: "created")
    - `sortOrder` (optional): ASC or DESC (default: DESC)
    - `pageNumber` (optional): Page number (default: 1)
    - `pageSize` (optional): Records per page (default: 10)

### WebSocket

- **Endpoint**: `ws://localhost:8080/iot-air-q/ws/sensor-readings`
- **Message**: When new sensor data is received, the server broadcasts "REFRESH" message
- Clients with auto-refresh enabled will automatically reload data

## Architecture

### Components

1. **SensorReadingsUIResource.java**
    - Serves HTML page with pre-rendered Qute template
    - Handles filtering, sorting, and pagination
    - URL: `/iot-air-q/ui/readings`

2. **SensorReadingsApiResource.java**
    - RESTful API for JSON data retrieval
    - Supports the same filtering, sorting, pagination as UI
    - URL: `/iot-air-q/api/readings`

3. **SensorReadingsWebSocket.java**
    - WebSocket endpoint for real-time updates
    - Broadcasts "REFRESH" message when new data arrives
    - URL: `ws://localhost:8080/iot-air-q/ws/sensor-readings`

4. **sensorReadings.html (Qute Template)**
    - Client-side UI implementation
    - Responsive design with gradient background
    - JavaScript for interactivity and WebSocket communication

5. **SensorReadingsInboundEventHandler.java**
    - Listens for SensorReadingsFetchedEvent
    - Forwards events to WebSocket for broadcasting

## Usage Examples

### Accessing the UI

```
http://localhost:8080/iot-air-q/ui/readings
```

### Filtering Example

```
http://localhost:8080/iot-air-q/ui/readings?macAddress=00:1A:2B:3C:4D:5E&sensorName=temperature
```

### API Call with JavaScript

```javascript
const params = new URLSearchParams({
    macAddress: '00:1A:2B:3C:4D:5E',
    sensorName: 'temperature',
    sortOrder: 'DESC',
    pageNumber: 1,
    pageSize: 20
});

fetch('/iot-air-q/api/readings?' + params.toString())
    .then(response => response.json())
    .then(data => {
        console.log('Current page:', data.currentPage);
        console.log('Total records:', data.totalRecords);
        console.log('Readings:', data.readings);
    });
```

## Configuration

### Database Query Parameters

- The underlying query uses JPA with dynamic WHERE clauses
- Only non-null filter parameters are included in the query
- All parameters are parameterized to prevent SQL injection

### Pagination Calculation

```
totalPages = ceil(totalRecords / pageSize)
startIndex = (pageNumber - 1) * pageSize
endIndex = min(startIndex + pageSize, totalRecords)
```

### WebSocket Reconnection Strategy

- Maximum reconnection attempts: 5
- Initial reconnection delay: 3000ms
- Periodic check interval: 30 seconds
- Auto-reconnects if connection is lost

## Frontend Features

### Auto-Refresh Checkbox

- When checked: UI automatically updates when new data arrives via WebSocket
- When unchecked: Manual refresh only via page navigation or filter changes
- State is not persisted (resets on page reload)

### Connection Status Indicator

- Green indicator with "Connected" text: WebSocket is active
- Red indicator with "Disconnected" text: WebSocket is not connected
- Pulsing animation to indicate connection status

### Responsive Design

- Desktop layout: Multi-column filter form, full table display
- Tablet layout: Adjusted grid columns, full functionality
- Mobile layout: Single column filters, scrollable table

## Performance Considerations

1. **In-Memory Sorting and Pagination**
    - All sorting and pagination happens in Java memory after database query
    - Suitable for datasets up to ~10,000 records
    - For larger datasets, consider moving sorting/pagination to database

2. **WebSocket Broadcasting**
    - Broadcasts to all connected clients
    - Filtered per client (each client refreshes their own filtered view)
    - No payload sent with refresh message (client handles the reload)

3. **Template Rendering**
    - Qute templates are compiled at build time
    - Fast server-side rendering
    - No additional template processing at runtime

## Browser Support

- Modern browsers with ES6+ support
- WebSocket support (all modern browsers)
- CSS Grid support for responsive layout
- Tested on:
    - Chrome/Edge 90+
    - Firefox 88+
    - Safari 14+

## Error Handling

1. **WebSocket Failures**
    - Automatic reconnection with exponential backoff
    - Manual operations continue to work via HTTP
    - Error messages logged to browser console

2. **Failed Data Refresh**
    - Error logged to console
    - Loading indicator disappears
    - User can retry manually

3. **Empty Results**
    - User-friendly message: "No sensor readings found. Try adjusting your filters."
    - Table remains visible but empty

## Future Enhancements

- [ ] Additional sort fields (macAddress, sensorName, readingType)
- [ ] Export to CSV/Excel functionality
- [ ] Data visualization (charts/graphs)
- [ ] Configurable column visibility
- [ ] Database-level sorting/pagination for large datasets
- [ ] User preferences persistence (filters, page size)
- [ ] Batch operations on multiple records
- [ ] Advanced search with date range filters

