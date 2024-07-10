package midtrans.integrasi.Midtrans_ServerApp.repository;

import midtrans.integrasi.Midtrans_ServerApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByEmail(String email);

    Customer findByEmail(String email);

}
