package midtrans.integrasi.Midtrans_ServerApp.controller;

import midtrans.integrasi.Midtrans_ServerApp.model.request.OrderRequest;
import midtrans.integrasi.Midtrans_ServerApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequest request) {
        try {
            Map<String, Object> response = orderService.createOrder(request);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("Error: ", e.getReason());
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        }
    }

}
