package ua.mem4ik.eshop.src.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mem4ik.eshop.src.domain.enums.Category;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.domain.User;

import java.util.List;

@Repository

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByAuthor(User author);

    List<Item> findByCategory(Category category);
}
