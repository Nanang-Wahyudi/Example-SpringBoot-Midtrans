package midtrans.integrasi.Midtrans_ServerApp.service;

import com.midtrans.service.MidtransSnapApi;
import midtrans.integrasi.Midtrans_ServerApp.entity.Customer;
import midtrans.integrasi.Midtrans_ServerApp.entity.Order;
import midtrans.integrasi.Midtrans_ServerApp.entity.Product;
import midtrans.integrasi.Midtrans_ServerApp.model.request.OrderRequest;
import midtrans.integrasi.Midtrans_ServerApp.repository.CustomerRepository;
import midtrans.integrasi.Midtrans_ServerApp.repository.OrderRepository;
import midtrans.integrasi.Midtrans_ServerApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MidtransSnapApi midtransSnapApi;


    @Transactional
    @Override
    public Map<String, Object>  createOrder(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product Not Found"));

        Customer customer;
        if (customerRepository.existsByEmail(request.getCustomerEmail())) {
            customer = customerRepository.findByEmail(request.getCustomerEmail());
        } else {
            customer = Customer.builder()
                    .email(request.getCustomerEmail())
                    .name(request.getCustomerName())
                    .build();
            customerRepository.save(customer);
        }

        BigDecimal orderAmount = product.getPrice().multiply(new BigDecimal(request.getQuantity()));
        Order order = Order.builder()
                .product(product)
                .customer(customer)
                .quantity(request.getQuantity())
                .amount(orderAmount)
                .build();
        orderRepository.save(order);


        // Midtrans API integration:
        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", order.getId());
        transactionDetails.put("gross_amount", orderAmount);

        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", order.getCustomer().getName());
        customerDetails.put("email", order.getCustomer().getEmail());

        Map<String, Object> itemDetails = new HashMap<>();
        itemDetails.put("id", order.getProduct().getId());
        itemDetails.put("name", order.getProduct().getName());
        itemDetails.put("price", order.getProduct().getPrice());
        itemDetails.put("quantity", order.getQuantity());

        Map<String, Object> params = new HashMap<>();
        params.put("transaction_details", transactionDetails);
        params.put("customer_details", customerDetails);
        params.put("item_details", Collections.singletonList(itemDetails));

        // Generate Snap token
        try {
            return midtransSnapApi.createTransaction(params).toMap();

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Midtrans API error: ", e);
        }

    }

}
