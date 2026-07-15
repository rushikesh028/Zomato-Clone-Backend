package com.tastybuddy.tastybuddy_backend.service;

import com.razorpay.RazorpayClient;
import com.tastybuddy.tastybuddy_backend.dto.PaymentOrderResponse;
import com.tastybuddy.tastybuddy_backend.entity.Order;
import com.tastybuddy.tastybuddy_backend.exception.ServiceUnavailableException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

@Service
public class PaymentService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    public PaymentOrderResponse createOrder(Order appOrder) throws Exception {
        validateCredentials();

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", Math.round(appOrder.getTotalAmount() * 100));
        options.put("currency", "INR");
        options.put("receipt", "order-" + appOrder.getId());

        com.razorpay.Order gatewayOrder = client.orders.create(options);
        return new PaymentOrderResponse(
                key,
                gatewayOrder.get("id"),
                gatewayOrder.get("amount"),
                gatewayOrder.get("currency"),
                Long.parseLong(gatewayOrder.get("receipt").toString().replace("order-", "")),
                gatewayOrder.get("status")
        );
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {
        validateCredentials();

        try {
            String data = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);

            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String generatedSignature = HexFormat.of().formatHex(hash);

            return generatedSignature.equals(signature);
        } catch (Exception ex) {
            return false;
        }
    }

    private void validateCredentials() {
        if (key == null || key.isBlank() || secret == null || secret.isBlank()) {
            throw new ServiceUnavailableException("Payment endpoints are disabled until RAZORPAY_KEY and RAZORPAY_SECRET are configured.");
        }
    }
}
