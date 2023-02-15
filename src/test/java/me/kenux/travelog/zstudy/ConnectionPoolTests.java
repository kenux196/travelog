package me.kenux.travelog.zstudy;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.config.QueryDslConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Import(QueryDslConfig.class)
//@SpringBootTest
public class ConnectionPoolTests {

    @Autowired
    MemberRepository memberRepository;

//    @Test
    @Transactional
    void basic() throws InterruptedException {
        System.out.println("Start test");
        Thread.sleep(100000);
        System.out.println("------- sleep end ---------");
        final List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
    }

//    @Test
//    @Transactional
    void thread() throws InterruptedException {
        int threadCount = 10;
        String threadBaseName = "findMemberThread-";
        for (int i = 0; i < threadCount; i++) {
            runThread(threadBaseName + i);
        }
        Thread.sleep(300000);
    }

    @Transactional
    public void runThread(String threadName) {
        new Thread(() -> {
            try {
                Thread.sleep(30000);
                final List<Member> all = memberRepository.findAll();
                System.out.println("threadName = " + threadName + "result = " + all);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, threadName).run();
    }

}
