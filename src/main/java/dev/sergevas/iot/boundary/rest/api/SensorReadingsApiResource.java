package dev.sergevas.iot.boundary.rest.api;

import dev.sergevas.iot.boundary.persistence.SensorDataRepository;
import dev.sergevas.iot.control.Mapper;
import dev.sergevas.iot.entity.model.SensorReadings;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/readings")
public class SensorReadingsApiResource {

    @Inject
    SensorDataRepository sensorDataRepository;

    @GET
    @Produces(APPLICATION_JSON)
    public SensorReadingsResponse getSensorReadings(
            @QueryParam("macAddress") String macAddress,
            @QueryParam("sensorName") String sensorName,
            @QueryParam("readingType") String readingType,
            @QueryParam("packageId") UUID packageId,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") String sortOrder,
            @QueryParam("pageNumber") Integer pageNumber,
            @QueryParam("pageSize") Integer pageSize) {

        Log.infof("Enter getSensorReadings(): macAddress=%s, sensorName=%s, readingType=%s, packageId=%s, " +
                        "sortBy=%s, sortOrder=%s, pageNumber=%s, pageSize=%s",
                macAddress, sensorName, readingType, packageId, sortBy, sortOrder, pageNumber, pageSize);

        // Set default values
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "created";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "DESC";
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // Get sensor data
        var sensorDataEntities = sensorDataRepository.find(macAddress, sensorName, readingType, packageId);
        var sensorReadingsList = sensorDataEntities.stream()
                .map(Mapper::toSensorReadings)
                .toList();

        // Sort
        List<SensorReadings> sortedList = sensorReadingsList;
        if ("created".equals(sortBy)) {
            Comparator<SensorReadings> comparator = (a, b) -> {
                if (a.created() == null && b.created() == null) return 0;
                if (a.created() == null) return 1;
                if (b.created() == null) return -1;
                return a.created().compareTo(b.created());
            };
            if ("ASC".equals(sortOrder)) {
                sortedList = sensorReadingsList.stream()
                        .sorted(comparator)
                        .toList();
            } else {
                sortedList = sensorReadingsList.stream()
                        .sorted(comparator.reversed())
                        .toList();
            }
        }

        // Pagination
        int totalRecords = sortedList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (pageNumber > totalPages && totalPages > 0) {
            pageNumber = totalPages;
        }

        int startIdx = (pageNumber - 1) * pageSize;
        int endIdx = Math.min(startIdx + pageSize, totalRecords);
        List<SensorReadings> pageContent = startIdx < totalRecords ?
                sortedList.subList(startIdx, endIdx) : List.of();

        Log.infof("Found %d records, page %d of %d with %d records per page, returning %d records",
                totalRecords, pageNumber, totalPages, pageSize, pageContent.size());

        return new SensorReadingsResponse(pageContent, totalRecords, totalPages, pageNumber, pageSize);
    }

    public static class SensorReadingsResponse {
        public List<SensorReadings> readings;
        public int totalRecords;
        public int totalPages;
        public int currentPage;
        public int pageSize;

        public SensorReadingsResponse(List<SensorReadings> readings, int totalRecords, int totalPages, int currentPage, int pageSize) {
            this.readings = readings;
            this.totalRecords = totalRecords;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }
    }
}

