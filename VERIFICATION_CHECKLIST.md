# Developer Verification Checklist

## Pre-Launch Verification

Use this checklist to verify the implementation is complete and working correctly.

### Phase 1: Build & Compilation ✅

- [ ] Clone/pull latest code
- [ ] Run `mvn clean compile` - no errors
- [ ] Verify pom.xml has quarkus-qute and quarkus-websockets
- [ ] Check that Java files compile without warnings
- [ ] Verify template file exists: `src/main/resources/templates/sensorReadings.html`

### Phase 2: Unit Tests ✅

```bash
mvn clean test
```

- [ ] All tests pass (14 tests in SensorReadingsUIResourceTest)
- [ ] No test failures or errors
- [ ] Test coverage includes:
    - [ ] UI rendering
    - [ ] Filtering functionality
    - [ ] Sorting functionality
    - [ ] Pagination functionality
    - [ ] JSON API
    - [ ] WebSocket presence
    - [ ] Auto-refresh checkbox
    - [ ] Connection status indicator

### Phase 3: Development Mode ✅

```bash
mvn quarkus:dev
```

- [ ] Server starts without errors
- [ ] Application logs show successful startup
- [ ] No ERROR or WARN messages in startup logs
- [ ] Wait for "Quarkus X.XX.X started"

### Phase 4: UI Access ✅

Open browser to: `http://localhost:8080/iot-air-q/ui/readings`

**Visual Verification:**

- [ ] Page loads with title "Sensor Readings Dashboard"
- [ ] Filter form displays with 4 input fields
- [ ] Table shows headers: MAC Address, Sensor Name, Reading Type, Reading Data, Package ID, Created
- [ ] Pagination controls visible (Previous, Next, page numbers)
- [ ] "Page Size" input field visible
- [ ] "Sort by Created" dropdown visible
- [ ] "Auto-refresh on new data" checkbox visible
- [ ] Connection status indicator visible
- [ ] Gradient background displays correctly
- [ ] CSS styling applied (colors, spacing, fonts)

### Phase 5: UI Functionality ✅

**Filtering:**

- [ ] Enter "temperature" in Sensor Name field
- [ ] Click "Apply Filters"
- [ ] URL contains `?sensorName=temperature`
- [ ] Table updates (or shows "No sensor readings found")
- [ ] Form field retains value
- [ ] Click "Reset Filters"
- [ ] All form fields clear
- [ ] URL returns to base

**Sorting:**

- [ ] "Sort by Created" dropdown shows two options
- [ ] Default is "Newest First"
- [ ] Select "Oldest First"
- [ ] URL contains `sortOrder=ASC`
- [ ] Page resets to 1

**Pagination:**

- [ ] Page size input shows "10"
- [ ] Can change to different value (e.g., 5)
- [ ] Page resets to 1
- [ ] If data exists: Previous/Next buttons work
- [ ] If data exists: Can click page numbers

**Combined Test:**

- [ ] Enter filter + select sort + change page size
- [ ] All state preserved in URL
- [ ] Table updates correctly

### Phase 6: Browser Console ✅

Open DevTools: `F12` → Console tab

- [ ] No red error messages (warnings OK)
- [ ] Check console for WebSocket logs:
    - [ ] "WebSocket connected" message appears
    - [ ] Or "WebSocket error" (acceptable if WebSocket fails)

**Check Network Tab:**

- [ ] GET requests to `/iot-air-q/ui/readings` with 200 status
- [ ] GET requests to `/iot-air-q/api/readings` with 200 status (if API used)
- [ ] WebSocket connection attempt to `ws://localhost:8080/iot-air-q/ws/sensor-readings`

### Phase 7: REST API Testing ✅

**Test basic API:**

```bash
curl http://localhost:8080/iot-air-q/api/readings
```

- [ ] Returns 200 status
- [ ] Response is valid JSON
- [ ] Contains: readings, totalRecords, totalPages, currentPage, pageSize

**Test with filters:**

```bash
curl "http://localhost:8080/iot-air-q/api/readings?sensorName=temperature&pageSize=5"
```

- [ ] Returns 200 status
- [ ] pageSize is 5
- [ ] totalRecords matches filter results

### Phase 8: Data Processing ✅

**If test data exists:**

- [ ] Table displays rows correctly
- [ ] Timestamps format properly (YYYY-MM-DD HH:MM:SS)
- [ ] Numeric values display correctly
- [ ] UUIDs display completely
- [ ] MAC addresses display with colons

**Pagination with data:**

- [ ] "Page 1 of X" shows correctly
- [ ] Shows correct record count
- [ ] Previous button disabled on page 1
- [ ] Next button disabled on last page
- [ ] Can navigate between pages

### Phase 9: Responsive Design ✅

**Desktop (1920x1080):**

- [ ] All elements visible
- [ ] Layout looks good
- [ ] No horizontal scroll needed

**Tablet (768x1024):**

- [ ] Grid layout adapts
- [ ] Table scrolls horizontally
- [ ] Buttons remain clickable

**Mobile (375x667):**

- [ ] Single column filter form
- [ ] All buttons accessible
- [ ] Text readable
- [ ] No overlapping elements

### Phase 10: Event Integration ✅

**If sensor data arrives (e.g., via API call):**

- [ ] SensorReadingsFetchedEvent fires
- [ ] SensorReadingsInboundEventHandler receives event
- [ ] WebSocket receives "REFRESH" message
- [ ] If auto-refresh checked: UI updates with new data
- [ ] If auto-refresh unchecked: Manual refresh still works

**Manual WebSocket Test:**

```javascript
// In browser console:
let ws = new WebSocket('ws://localhost:8080/iot-air-q/ws/sensor-readings');
ws.onmessage = (e) => console.log('Received:', e.data);
```

- [ ] Connection establishes
- [ ] Can send/receive messages

### Phase 11: Error Handling ✅

**Test invalid inputs:**

- [ ] Page number = 0 → corrects to 1
- [ ] Page number = 999 → corrects to last page
- [ ] Page size = 0 → defaults to 10
- [ ] Page size = 1000 → caps at 100
- [ ] Non-existent filter → empty results (no error)

**Test missing data:**

- [ ] No readings in database → "No sensor readings found" displays
- [ ] Filter returns no results → message displays
- [ ] Empty form submits → works correctly

**Test connection issues:**

- [ ] Disconnect network briefly
- [ ] WebSocket should attempt reconnection
- [ ] Status changes to "Disconnected"
- [ ] HTTP requests still work
- [ ] Status changes back to "Connected" when network returns

### Phase 12: Production Build ✅

```bash
mvn clean package
```

- [ ] Build completes successfully
- [ ] JAR/WAR file created in target/
- [ ] Size is reasonable (10-100MB typical)
- [ ] Can run: `java -jar target/iot-air-q-0.0.1-SNAPSHOT.jar`
- [ ] Application starts
- [ ] UI accessible at `http://localhost:8080/iot-air-q/ui/readings`

### Phase 13: Code Quality ✅

**Review Code:**

- [ ] Java files follow naming conventions
- [ ] Java files have proper imports
- [ ] HTML template is well-formatted
- [ ] CSS follows best practices
- [ ] JavaScript is readable and commented
- [ ] No hardcoded values in code
- [ ] Logging statements present
- [ ] Error handling in place

**Check Documentation:**

- [ ] README updated with UI instructions
- [ ] API endpoints documented
- [ ] Code comments explain complex logic
- [ ] User guide available (SENSOR_UI_QUICKSTART.md)
- [ ] Architecture document present (ARCHITECTURE.md)

### Phase 14: Security Verification ✅

**SQL Injection Test:**

- [ ] Try entering SQL in filter fields
- [ ] Application doesn't break
- [ ] No unexpected behavior
- [ ] Queries use parameterized statements

**XSS Test:**

- [ ] Try entering `<script>alert('test')</script>` in filters
- [ ] Script doesn't execute
- [ ] Text displays as literal string

**Input Validation:**

- [ ] Invalid UUIDs handled gracefully
- [ ] Special characters in filters work
- [ ] Very long inputs handled

### Phase 15: Performance Baseline ✅

**With small dataset (< 1000 records):**

- [ ] Page load time < 1 second
- [ ] Filter application < 500ms
- [ ] Sort operation < 500ms
- [ ] Pagination change < 500ms

**WebSocket Performance:**

- [ ] Connection establishes quickly (< 100ms)
- [ ] Refresh message received within 100ms
- [ ] Multiple clients don't cause issues

### Phase 16: Documentation Validation ✅

Verify all documentation files exist and are accurate:

- [ ] `UI_DOCUMENTATION.md` - Feature documentation
- [ ] `SENSOR_UI_QUICKSTART.md` - User guide
- [ ] `REST_API_EXAMPLES.md` - API examples
- [ ] `ARCHITECTURE.md` - Technical architecture
- [ ] `IMPLEMENTATION_CHECKLIST.md` - Requirements
- [ ] `IMPLEMENTATION_SUMMARY.md` - This summary

**Check accuracy:**

- [ ] URLs in docs are correct
- [ ] Examples work as documented
- [ ] Screenshots/descriptions match actual UI
- [ ] API parameters documented

---

## Sign-Off

### Developer Checklist

- [ ] All 16 phases completed
- [ ] No blockers found
- [ ] Code review passed
- [ ] Tests all pass
- [ ] Documentation complete

### QA Checklist

- [ ] UI tested on desktop/tablet/mobile
- [ ] All features working correctly
- [ ] Error handling verified
- [ ] Performance acceptable
- [ ] Security review passed

### Product Checklist

- [ ] Meets all requirements
- [ ] User guide provided
- [ ] API documented
- [ ] Ready for launch
- [ ] No known issues

---

## Rollback Plan

If issues found:

1. **Stop server**: Ctrl+C
2. **Revert changes**: `git checkout HEAD`
3. **Rebuild**: `mvn clean install`
4. **Restart**: `mvn quarkus:dev`

## Support Resources

- Architecture: See `ARCHITECTURE.md`
- User Guide: See `SENSOR_UI_QUICKSTART.md`
- API Reference: See `REST_API_EXAMPLES.md`
- Troubleshooting: See `UI_DOCUMENTATION.md`

## Post-Launch Monitoring

Monitor these metrics:

- [ ] WebSocket connection count
- [ ] Average page load time
- [ ] API response times
- [ ] Error rates
- [ ] Browser/device distribution
- [ ] User engagement

---

**Checklist Created**: 2026-02-15  
**Version**: 1.0.0  
**Status**: Ready for Verification

