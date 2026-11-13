
package com.sumpositive.ticketing.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumpositive.ticketing.dashboard.dto.DashboardResponse;
import com.sumpositive.ticketing.dashboard.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<DashboardResponse> getDashboardStatistics() {
        DashboardResponse response =dashboardService.getDashboardStatistics();
        return ResponseEntity.ok(response);
    }
}
