package me.kenux.travelog.zstudy;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.zstudy.core.Item;
import me.kenux.travelog.zstudy.core.ItemRepository;
import me.kenux.travelog.zstudy.core.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TransactionalTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

//    @BeforeEach
//    void beforeEach() {
//        log.info("start beforeEach");
//        itemService = new ItemService(itemRepository);
//        log.info("end beforeEach");
//    }

    @Test
//    @Transactional
    void save_item_on_service_propagation_transaction() {
        log.info("ItemService = {}", itemService.getClass());
        log.info("create data");
        final Item item1 = createItem("item1");
        final Item item2 = createItem("item2");
        log.info("will save...");
        itemService.saveAll(item1, item2);
    }

    private Item createItem(String name) {
        return new Item(name);
    }
}
