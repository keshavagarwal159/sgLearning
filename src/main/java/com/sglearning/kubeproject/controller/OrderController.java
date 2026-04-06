package com.sglearning.kubeproject.controller;

import com.sglearning.kubeproject.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController {

    private final OrderService orderService = new OrderService();

    public Map<String, Object> createOrder(String orderId, String customerName, double amount) {
        Map<String, Object> response = new HashMap<>();
        response.put("requestType", "CREATE_ORDER");
        response.put("timestamp", System.currentTimeMillis());

        if (orderId == null || orderId.trim().isEmpty()) {
            response.put("success", false);
            response.put("errorCode", "INVALID_ID");
            response.put("errorMessage", "The provided ID cannot be null or empty");
            return response;
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            response.put("success", false);
            response.put("errorCode", "INVALID_NAME");
            response.put("errorMessage", "The provided name cannot be null or empty");
            return response;
        }
        if (amount <= 0) {
            response.put("success", false);
            response.put("errorCode", "INVALID_AMOUNT");
            response.put("errorMessage", "The amount must be greater than zero");
            return response;
        }

        try {
            Map<String, Object> result = orderService.processOrder(orderId, customerName, amount);
            response.put("success", true);
            response.put("data", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorCode", "PROCESSING_ERROR");
            response.put("errorMessage", "Failed to process: " + e.getMessage());
        }

        return response;
    }

    public Map<String, Object> getReport() {
        Map<String, Object> response = new HashMap<>();
        response.put("requestType", "GET_REPORT");
        response.put("timestamp", System.currentTimeMillis());

        try {
            List<Map<String, Object>> report = orderService.getOrderReport();
            response.put("success", true);
            response.put("data", report);
            response.put("totalRecords", report.size());
        } catch (Exception e) {
            response.put("success", false);
            response.put("errorCode", "REPORT_ERROR");
            response.put("errorMessage", "Failed to generate report: " + e.getMessage());
        }

        return response;
    }
}
