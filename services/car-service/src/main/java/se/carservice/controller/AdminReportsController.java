package se.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.carservice.dto.response.ApiResponse;
import se.carservice.dto.response.RentalStatistics;
import se.carservice.service.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
@Tag(name = "Admin Reports", description = "Statistical reports and analytics for car rental data")
public class AdminReportsController {
    private final ReportService reportService;

    @GetMapping("/rental-statistics")
    @Operation(summary = "Get Rental Statistics by Period",
            description = "Create a report statistic by the period from StartDate to EndDate, sorted by revenue in descending order")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid date range"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied - Admin role required")
    })

    public ResponseEntity<ApiResponse<List<RentalStatistics>>> getRentalStatistics(
            @Parameter(description = "Start date for the report period", required = true, example = "2024-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @Parameter(description = "End date for the report period", required = true, example = "2024-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            
            @RequestHeader("X-User-Email") String userEmail) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<List<RentalStatistics>>builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .message("Start date must be before end date")
                            .error("Invalid date range")
                            .build()
            );
        }

        List<RentalStatistics> statistics = reportService.getRentalStatistics(startDate, endDate);
        return ResponseEntity.ok(
                ApiResponse.<List<RentalStatistics>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Rental statistics retrieved successfully")
                        .data(statistics)
                        .build()
        );
    }
}
