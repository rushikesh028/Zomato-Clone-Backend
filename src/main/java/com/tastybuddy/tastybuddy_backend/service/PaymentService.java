package com.tastybuddy.tastybuddy_backend.service;

import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class PaymentService {

    private static final String KEY = "rzp_test_SZK3uWPPnUR5xh";
    private static final String SECRET = "TOvFSsgYktwjt4f26K6IiXKi";

    public String createOrder(double amount) throws Exception {

        RazorpayClient client = new RazorpayClient(KEY, SECRET);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100));  // paisa
        options.put("currency", "INR");

        com.razorpay.Order order = client.orders.create(options);

        return order.toString();
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {

        try {
            String data = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256"); // ✅ FIXED
            mac.init(secretKey);

            byte[] hash = mac.doFinal(data.getBytes());
            String generatedSignature = Base64.getEncoder().encodeToString(hash);

            return generatedSignature.equals(signature);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}