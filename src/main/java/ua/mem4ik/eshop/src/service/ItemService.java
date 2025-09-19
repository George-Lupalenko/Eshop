package ua.mem4ik.eshop.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mem4ik.eshop.src.domain.Category;
import ua.mem4ik.eshop.src.domain.Item;
import ua.mem4ik.eshop.src.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findByCategory(String name) {
        return itemRepository.findByCategory(Category.valueOf(name));
    }
}

