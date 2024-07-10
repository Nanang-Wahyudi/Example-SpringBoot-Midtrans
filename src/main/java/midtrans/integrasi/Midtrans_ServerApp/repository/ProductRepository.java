package midtrans.integrasi.Midtrans_ServerApp.repository;

import midtrans.integrasi.Midtrans_ServerApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
