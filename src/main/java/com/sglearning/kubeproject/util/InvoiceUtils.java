package com.sglearning.kubeproject.util;

import java.util.HashMap;
import java.util.Map;

public class InvoiceUtils {

    public static Map<String, Object> calculatePricing(double amount) {
        Map<String, Object> pricing = new HashMap<>();
        pricing.put("amount", amount);

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
        pricing.put("tax", tax);
        pricing.put("discount", discount);
        pricing.put("finalAmount", finalAmount);

        if (finalAmount > 5000) {
            pricing.put("priority", "HIGH");
            pricing.put("shippingMethod", "EXPRESS");
            pricing.put("estimatedDays", 1);
        } else if (finalAmount > 2000) {
            pricing.put("priority", "MEDIUM");
            pricing.put("shippingMethod", "STANDARD");
            pricing.put("estimatedDays", 3);
        } else {
            pricing.put("priority", "LOW");
            pricing.put("shippingMethod", "ECONOMY");
            pricing.put("estimatedDays", 7);
        }

        return pricing;
    }

    public static String formatInvoiceSummary(String id, String name, double amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Invoice Summary ===\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Customer: ").append(name).append("\n");
        sb.append("Amount: $").append(String.format("%.2f", amount)).append("\n");

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
        sb.append("Tax: $").append(String.format("%.2f", tax)).append("\n");
        sb.append("Discount: $").append(String.format("%.2f", discount)).append("\n");
        sb.append("Final Amount: $").append(String.format("%.2f", finalAmount)).append("\n");

        if (finalAmount > 5000) {
            sb.append("Priority: HIGH\n");
            sb.append("Shipping: EXPRESS\n");
            sb.append("Estimated Days: 1\n");
        } else if (finalAmount > 2000) {
            sb.append("Priority: MEDIUM\n");
            sb.append("Shipping: STANDARD\n");
            sb.append("Estimated Days: 3\n");
        } else {
            sb.append("Priority: LOW\n");
            sb.append("Shipping: ECONOMY\n");
            sb.append("Estimated Days: 7\n");
        }

        sb.append("=====================\n");
        return sb.toString();
    }
}
