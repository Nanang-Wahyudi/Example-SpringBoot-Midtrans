package midtrans.integrasi.Midtrans_ServerApp.repository;

import midtrans.integrasi.Midtrans_ServerApp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
