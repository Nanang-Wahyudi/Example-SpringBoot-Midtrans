package midtrans.integrasi.Midtrans_ServerApp.service;

import midtrans.integrasi.Midtrans_ServerApp.model.request.OrderRequest;

import java.util.Map;

public interface OrderService {

    Map<String, Object> createOrder(OrderRequest request);

}
