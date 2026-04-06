package com.sglearning.kubeproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final Map<String, Map<String, Object>> orders = new HashMap<>();

    public Map<String, Object> processOrder(String orderId, String customerName, double amount) {
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("customerName", customerName);
        result.put("amount", amount);
        result.put("status", "PENDING");

        double tax = amount * 0.18;
        double discount = 0;
        if (amount > 1000) {
            discount = amount * 0.10;
        } else if (amount > 500) {
            discount = amount * 0.05;
        } else if (amount > 200) {
            discount = amount * 0.02;
        }

        double finalAmount = amount + tax - discount;
        result.put("tax", tax);
        result.put("discount", discount);
        result.put("finalAmount", finalAmount);

        if (finalAmount > 5000) {
            result.put("priority", "HIGH");
            result.put("shippingMethod", "EXPRESS");
            result.put("estimatedDays", 1);
        } else if (finalAmount > 2000) {
            result.put("priority", "MEDIUM");
            result.put("shippingMethod", "STANDARD");
            result.put("estimatedDays", 3);
        } else {
            result.put("priority", "LOW");
            result.put("shippingMethod", "ECONOMY");
            result.put("estimatedDays", 7);
        }

        orders.put(orderId, result);
        return result;
    }

    public List<Map<String, Object>> getOrderReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        for (Map.Entry<String, Map<String, Object>> entry : orders.entrySet()) {
            Map<String, Object> reportItem = new HashMap<>();
            reportItem.put("orderId", entry.getKey());
            reportItem.put("customerName", entry.getValue().get("customerName"));
            reportItem.put("amount", entry.getValue().get("amount"));
            reportItem.put("tax", entry.getValue().get("tax"));
            reportItem.put("discount", entry.getValue().get("discount"));
            reportItem.put("finalAmount", entry.getValue().get("finalAmount"));
            reportItem.put("priority", entry.getValue().get("priority"));
            reportItem.put("shippingMethod", entry.getValue().get("shippingMethod"));
            reportItem.put("estimatedDays", entry.getValue().get("estimatedDays"));
            reportItem.put("status", entry.getValue().get("status"));
            report.add(reportItem);
        }
        return report;
    }

    public Map<String, Object> validateOrder(String orderId, String customerName, double amount) {
        Map<String, Object> validationResult = new HashMap<>();
        validationResult.put("orderId", orderId);
        validationResult.put("customerName", customerName);
        validationResult.put("amount", amount);
        validationResult.put("isValid", true);

        if (orderId == null || orderId.isEmpty()) {
            validationResult.put("isValid", false);
            validationResult.put("error", "Order ID is required");
            return validationResult;
        }
        if (customerName == null || customerName.isEmpty()) {
            validationResult.put("isValid", false);
            validationResult.put("error", "Customer name is required");
            return validationResult;
        }
        if (amount <= 0) {
            validationResult.put("isValid", false);
            validationResult.put("error", "Amount must be positive");
            return validationResult;
        }

        double tax = amount * 0.18;
        double discount = 0;
        if (amount > 1000) {
            discount = amount * 0.10;
        } else if (amount > 500) {
            discount = amount * 0.05;
        } else if (amount > 200) {
            discount = amount * 0.02;
        }

        double finalAmount = amount + tax - discount;
        validationResult.put("tax", tax);
        validationResult.put("discount", discount);
        validationResult.put("finalAmount", finalAmount);

        if (finalAmount > 5000) {
            validationResult.put("priority", "HIGH");
            validationResult.put("shippingMethod", "EXPRESS");
            validationResult.put("estimatedDays", 1);
        } else if (finalAmount > 2000) {
            validationResult.put("priority", "MEDIUM");
            validationResult.put("shippingMethod", "STANDARD");
            validationResult.put("estimatedDays", 3);
        } else {
            validationResult.put("priority", "LOW");
            validationResult.put("shippingMethod", "ECONOMY");
            validationResult.put("estimatedDays", 7);
        }

        return validationResult;
    }
}
