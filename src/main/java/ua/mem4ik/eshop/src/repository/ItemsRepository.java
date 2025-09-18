package ua.mem4ik.eshop.src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mem4ik.eshop.src.domain.Items;

public interface ItemsRepository extends JpaRepository<Items, Long> {

}
