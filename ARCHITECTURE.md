# UI Implementation Architecture

## Overview

The Sensor Readings UI is implemented using a multi-layered architecture combining server-side Qute templating with
client-side JavaScript for interactivity and real-time updates via WebSocket.

## Architecture Layers

```
┌─────────────────────────────────────────────────────────────────┐
│                        Web Browser                              │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │              HTML/CSS/JavaScript (sensorReadings.html)     │ │
│  │  - Table Display                                           │ │
│  │  - Filter Form                                             │ │
│  │  - Pagination Controls                                     │ │
│  │  - Sort Controls                                           │ │
│  │  - WebSocket Connection                                    │ │
│  └────────────────────────────────────────────────────────────┘ │
└──────────────┬──────────────────────────────────────────────────┘
               │
    ┌──────────┼──────────┬──────────┐
    │          │          │          │
    ▼          ▼          ▼          ▼
  HTTP       HTTP       WS         HTTP
  GET        GET        CONNECT    GET
  UI         API        WebSocket  API
  │          │          │          │
┌─┴──────────┴──────────┴──────────┴──┐
│      Quarkus REST API Layer          │
│  ┌──────────────────────────────────┐│
│  │ SensorReadingsUIResource         ││ (Serves HTML + Qute rendering)
│  ├──────────────────────────────────┤│
│  │ SensorReadingsApiResource        ││ (Serves JSON with pagination)
│  ├──────────────────────────────────┤│
│  │ SensorReadingsWebSocket          ││ (WebSocket endpoint)
│  └──────────────────────────────────┘│
└─┬────────────────────────────────────┘
  │
  │ (Filtering, Sorting, Pagination)
  │
┌─┴────────────────────────────────────┐
│    Business Logic Layer              │
│  ┌──────────────────────────────────┐│
│  │ SensorReadingsInboundEventHandler││ (Event listener)
│  ├──────────────────────────────────┤│
│  │ Mapper                           ││ (DTO conversion)
│  └──────────────────────────────────┘│
└─┬────────────────────────────────────┘
  │
  │ (Data Retrieval)
  │
┌─┴────────────────────────────────────┐
│    Persistence Layer                 │
│  ┌──────────────────────────────────┐│
│  │ SensorDataRepository             ││ (JPA queries)
│  ├──────────────────────────────────┤│
│  │ SensorDataEntity                 ││ (ORM mapping)
│  └──────────────────────────────────┘│
└─┬────────────────────────────────────┘
  │
  ▼
┌────────────────────────────────────────┐
│        Database (H2/PostgreSQL)        │
│  ┌──────────────────────────────────┐  │
│  │ iot_air_q_sensor_data table      │  │
│  └──────────────────────────────────┘  │
└────────────────────────────────────────┘
```

## Component Descriptions

### 1. Client-Side Components

#### sensorReadings.html (Qute Template)

- **Location**: `src/main/resources/templates/sensorReadings.html`
- **Purpose**: HTML template rendered by Qute engine
- **Technology**: HTML5, CSS3 (Grid, Flexbox, Responsive Design), Vanilla JavaScript (ES6+)
- **Responsibilities**:
    - Render static HTML structure with Qute tags
    - Populate form fields with current filter values
    - Display table data with dynamic rows
    - Generate pagination controls
    - Initialize JavaScript for interactivity

#### Client-Side JavaScript

- **Embedded in**: sensorReadings.html
- **Key Functions**:
    - `connectWebSocket()`: Establishes WebSocket connection
    - `refreshData()`: Fetches updated data while preserving state
    - `goToPage(pageNumber)`: Navigate to specific page
    - `handleSortChange()`: Handle sort order changes
    - `handlePageSizeChange()`: Handle pagination size changes
    - `resetFilters()`: Clear all filters
- **Features**:
    - Automatic WebSocket reconnection with exponential backoff
    - Real-time update handling
    - Client-side pagination calculation
    - Connection status monitoring

### 2. Server-Side REST API Components

#### SensorReadingsUIResource

- **Location**: `src/main/java/dev/sergevas/iot/boundary/rest/api/SensorReadingsUIResource.java`
- **Path**: `/iot-air-q/ui/readings`
- **HTTP Method**: GET
- **Produces**: TEXT_HTML
- **Purpose**: Render HTML page with data
- **Process Flow**:
    1. Receive query parameters (filters, sort, pagination)
    2. Set default values for missing parameters
    3. Query database via SensorDataRepository
    4. Apply in-memory sorting
    5. Apply in-memory pagination
    6. Pass data to Qute template
    7. Return rendered HTML

#### SensorReadingsApiResource

- **Location**: `src/main/java/dev/sergevas/iot/boundary/rest/api/SensorReadingsApiResource.java`
- **Path**: `/iot-air-q/api/readings`
- **HTTP Method**: GET
- **Produces**: APPLICATION_JSON
- **Purpose**: Provide JSON API with pagination metadata
- **Response Structure**:
  ```java
  {
    readings: List<SensorReadings>,
    totalRecords: int,
    totalPages: int,
    currentPage: int,
    pageSize: int
  }
  ```

#### SensorReadingsWebSocket

- **Location**: `src/main/java/dev/sergevas/iot/boundary/ws/SensorReadingsWebSocket.java`
- **Endpoint**: `ws://localhost:8080/iot-air-q/ws/sensor-readings`
- **Purpose**: Real-time push updates to connected clients
- **Methods**:
    - `onOpen(Session)`: Register new client connection
    - `onClose(Session)`: Unregister disconnected client
    - `onSensorReadingsFetched(Event)`: Broadcast refresh message

### 3. Data Processing Components

#### SensorReadingsInboundEventHandler

- **Location**: `src/main/java/dev/sergevas/iot/boundary/SensorReadingsInboundEventHandler.java`
- **Type**: CDI ApplicationScoped Bean
- **Purpose**: Event listener that bridges domain events to UI notifications
- **Process**:
    1. Receives `SensorReadingsFetchedEvent` (async)
    2. Forwards to `SensorReadingsWebSocket`
    3. WebSocket broadcasts "REFRESH" to all clients
    4. Clients with auto-refresh enabled reload their data

#### Mapper

- **Location**: `src/main/java/dev/sergevas/iot/control/Mapper.java`
- **Purpose**: Convert between entity and model objects
- **Key Method**: `toSensorReadings(SensorDataEntity)` → `SensorReadings`

#### SensorDataRepository

- **Location**: `src/main/java/dev/sergevas/iot/boundary/persistence/SensorDataRepository.java`
- **Purpose**: Data access layer for sensor readings
- **Key Method**: `find(macAddress, sensorName, readingType, packageId)`
    - Builds dynamic JPA query based on non-null parameters
    - Prevents SQL injection through parameterized queries

### 4. Data Models

#### SensorReadings (Record)

```java
public record SensorReadings(
        UUID packageId,
        String macAddress,
        String sensorName,
        String readingType,
        Double readingData,
        Instant created
)
```

#### SensorDataEntity (JPA Entity)

```java

@Entity
@Table(name = "iot_air_q_sensor_data")
public class SensorDataEntity {
    Long id;
    String macAddress;
    UUID packageId;
    String sensorName;
    String readingType;
    Double readingData;
    Instant created;
}
```

## Data Flow Scenarios

### Scenario 1: User Applies Filters

```
User Input (Browser)
    ↓
Form Submission (GET Request)
    ↓
SensorReadingsUIResource.getSensorReadingsUI()
    ↓
SensorDataRepository.find() [Dynamic JPA Query]
    ↓
Database Query Results
    ↓
Mapper.toSensorReadings() [Convert Entities → Records]
    ↓
In-Memory Sorting [if enabled]
    ↓
In-Memory Pagination [calculate page slice]
    ↓
Qute Template Rendering [inject data]
    ↓
HTML Response [browser displays table]
```

### Scenario 2: New Sensor Data Arrives

```
SensorReadingsFetchedEvent
    ↓
SensorReadingsInboundEventHandler.onSensorReadingsFetchedEvent()
    ↓
SensorReadingsWebSocket.onSensorReadingsFetched()
    ↓
Broadcast "REFRESH" to all connected clients
    ↓
Client JS: ws.onmessage(event)
    ↓
If (autoRefresh checkbox checked)
    ├─→ Call refreshData()
    │   ├─→ Preserve current filter state
    │   ├─→ Preserve current sort order
    │   ├─→ Preserve current page (if valid)
    │   └─→ Fetch updated HTML via GET
    │       ├─→ Replace table content
    │       ├─→ Update pagination controls
    │       └─→ Update record count
    │
    └─→ Else: Log message (manual refresh available)
```

### Scenario 3: User Navigates Pagination

```
User Click: nextPage() button
    ↓
JavaScript: goToPage(pageNumber)
    ↓
Build URLSearchParams with:
    - Current filters (from form)
    - New pageNumber
    - Current pageSize
    - Current sortOrder
    ↓
Navigate browser to new URL
    ↓
New GET Request with updated parameters
    ↓
[Same as Scenario 1]
```

## Processing Logic

### Filtering Process

```java
// Dynamic query building in SensorDataRepository
String baseQuery = "select s from SensorDataEntity s";
List<String> conditions = [];

        if(macAddress !=null){
        conditions.

add("s.macAddress = :macAddress");
// Set parameter
}
        if(sensorName !=null){
        conditions.

add("s.sensorName = :sensorName");
// Set parameter
}
// ... repeat for other filters

String finalQuery = baseQuery + " where " + String.join(" and ", conditions);
```

### Sorting Process

```java
// In-memory sorting after database query
List<SensorReadings> sorted;
if("ASC".

equals(sortOrder)){
sorted =list.

stream()
        .

sorted((a, b) ->a.

created().

compareTo(b.created()))
        .

toList();
}else{
sorted =list.

stream()
        .

sorted((a, b) ->b.

created().

compareTo(a.created()))
        .

toList();
}
```

### Pagination Process

```java
// Calculate page boundaries
int totalRecords = sortedList.size();
int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

// Adjust if pageNumber exceeds totalPages
if(pageNumber >totalPages &&totalPages >0){
pageNumber =totalPages;
}

// Extract page slice
int startIdx = (pageNumber - 1) * pageSize;
int endIdx = Math.min(startIdx + pageSize, totalRecords);
List<SensorReadings> pageContent = sortedList.subList(startIdx, endIdx);
```

## Technology Stack

| Layer      | Technology                  | Version |
|------------|-----------------------------|---------|
| Framework  | Quarkus                     | 3.31.2  |
| Templating | Qute                        | 3.31.2  |
| REST API   | JAX-RS (Resteasy)           | 3.31.2  |
| WebSocket  | Jakarta WebSocket           | 3.31.2  |
| Frontend   | Vanilla JavaScript          | ES6+    |
| Styling    | CSS3                        | Modern  |
| Database   | H2 (dev), PostgreSQL (prod) | -       |
| ORM        | Hibernate                   | 6.2+    |

## Dependencies

```xml
<!-- Added to pom.xml -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-qute</artifactId>
</dependency>
<dependency>
<groupId>io.quarkus</groupId>
<artifactId>quarkus-websockets</artifactId>
</dependency>
```

## Performance Characteristics

### Strengths

- Fast server-side template rendering (compiled at build time)
- Efficient in-memory sorting/pagination (suitable for < 10K records)
- WebSocket for real-time updates (minimal overhead)
- No additional frameworks or libraries (vanilla JS)
- Responsive design with CSS Grid

### Limitations

- In-memory sorting/pagination not scalable for > 10K records
- No client-side caching of data
- Full page refresh on sort/page change
- WebSocket broadcast to all clients (not filtered)

### Scalability Recommendations

- For large datasets: Move sorting/pagination to database queries
- For high volume: Consider data aggregation or archiving
- For many clients: Implement selective WebSocket subscription
- For better UX: Add AJAX updates without page refresh

## Security Considerations

1. **SQL Injection Prevention**
    - All query parameters are parameterized
    - JPA handles parameter binding

2. **XSS Prevention**
    - Qute automatically escapes user input
    - No `{value!safe}` used in output
    - User-provided filter values are displayed as form values only

3. **WebSocket Security**
    - Endpoint is not authenticated (can be added)
    - Broadcast messages don't contain sensitive data
    - Session-based connection management

4. **Input Validation**
    - Page number validated (adjusted if out of range)
    - Page size limited (1-100)
    - Sort order validated (ASC/DESC)
    - UUID format validated by Java

## Future Enhancements

### Short Term

- [ ] Multiple sort fields support
- [ ] Export to CSV functionality
- [ ] Advanced date range filtering
- [ ] Column visibility toggle

### Medium Term

- [ ] Database-level sorting/pagination (for large datasets)
- [ ] Data visualization (charts/graphs)
- [ ] User preferences persistence
- [ ] Batch operations

### Long Term

- [ ] Real-time streaming view (Server-Sent Events)
- [ ] Data analytics dashboard
- [ ] Alert/notification system
- [ ] Multi-tenant support

---

**Document Version**: 1.0  
**Last Updated**: 2026-02-15  
**Architecture Review**: Approved

