package me.kenux.travelog.zstudy;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.config.QueryDslConfig;
import me.kenux.travelog.zstudy.core.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
@Slf4j
class EntityManagerTest {

    @Autowired
    EntityManager em;

    @Test
    void persist() {
        Item item = new Item("testItem");
        em.persist(item);
        em.clear();

        final Item findItem = em.find(Item.class, item.getId());
        assertThat(findItem.getId()).isNotNull();
    }

    @Test
    void update() {
        Item item = new Item("item1");
        em.persist(item);

        item.changeItemName("item2");
        em.flush(); // 여기서 update query 나간다.
        em.clear();

        final Item findItem = em.find(Item.class, item.getId());
        assertThat(findItem.getName()).isEqualTo("item2");
    }

    @Test
    void clear_detached() {
        Item item = new Item("item1");
        em.persist(item);
        em.clear(); // item 영속 -> 비영속 상태

        item.changeItemName("item2");
        em.flush(); // 비영속 상태인 item 의 변경으로 dirty checking 안된다. 따라서, update query 미발생.

        final Item findItem = em.find(Item.class, item.getId());
        assertThat(findItem.getName()).isNotEqualTo("item2");
    }

    @Test
    void is_not_contains_persist_context() {
        Item item = new Item("item1");
        em.persist(item);
        log.info("after persist contains = {} item= {}", em.contains(item), item);
        item.changeItemName("item2");
        em.detach(item);
        em.flush();
        log.info("after detach contains = {} item= {}", em.contains(item), item);
        item.changeItemName("item3");
        em.merge(item);
        em.flush();
        log.info("after merge contains = {} item= {}", em.contains(item), item);
        item.changeItemName("item4");
        em.clear();
        log.info("after clear contains = {} item= {}", em.contains(item), item);
        em.merge(item);
        log.info("after merge contains = {} item= {}", em.contains(item), item);
    }

    private void prepareTestData(int count) {
        for (int i = 0; i < count; i++) {
            Item item = new Item("item" + i);
            em.persist(item);
        }
    }
}