package me.kenux.travelog.zstudy.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        log.info("Create ItemService <- {} ", itemRepository.getClass());
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void saveAll(Item item1, Item item2) {
        itemRepository.save(item1);
        itemRepository.save(item2);
    }
}
