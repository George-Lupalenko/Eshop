package ua.mem4ik.eshop.src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mem4ik.eshop.src.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
