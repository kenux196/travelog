package me.kenux.travelog.zstudy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SampleSpyTest {

    private Runner runnerWithMock;
    private Runner runnerWithSpy;
    private SomeService mockService;
    private SomeService spyService;

    @BeforeEach
    void setup() {
        mockService = mock(SomeService.class);
        runnerWithMock = new Runner(mockService);
        spyService = spy(new SomeService());
        runnerWithSpy = new Runner(spyService);
    }

    @Test
    void callRunTestMethod() {
        System.out.println("call mock method");
        runnerWithMock.run();
        verify(mockService, times(1)).runRealMethod();
        System.out.println("call spy method");
        runnerWithSpy.run();
        verify(spyService, times(1)).runRealMethod();
    }

    @Test
    void getSomeMessageNoStub() {
        final String message = spyService.getSomeMessage();
        assertThat(message).isEqualTo("Hello");
    }

    @Test
    void getSomeMessageStub() {
        when(spyService.getSomeMessage()).thenReturn("안녕하세요");
        final String message = spyService.getSomeMessage();
        assertThat(message).isEqualTo("안녕하세요");
    }


    public static class Runner {

        protected SomeService someService;

        public Runner(SomeService someService) {
            this.someService = someService;
        }

        public void run() {
            someService.runRealMethod();
        }
    }

    public static class SomeService {
        public void runRealMethod() {
            System.out.println("Run~~~ runRealMethod");
        }

        public String getSomeMessage() {
            return "Hello";
        }
    }
}
