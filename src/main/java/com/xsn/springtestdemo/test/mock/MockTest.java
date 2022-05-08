package com.xsn.springtestdemo.test.mock;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

// @SpringBootTest
public class MockTest {

    @Test
    public void mockDemo() {

        List mock = mock(List.class);

        mock.add("test");
        mock.add("test1");
        mock.add("test1");
        mock.get(0);

        // verify：是否调用目标方法
        verify(mock).add("test");
        then(mock).should().add("test");

        verify(mock, times(1)).add("test");
        then(mock).should(times(1)).add("test");

        verify(mock, atLeast(2)).add("test1");
        then(mock).should(atLeast(2)).add("test1");

        verify(mock, atMost(2)).add("test1");
        then(mock).should(atMost(2)).add("test1");

        verify(mock).get(0);
        then(mock).should().get(0);

        verify(mock, never()).add("test2");
        then(mock).should(never()).add("test2");

        // error
        verify(mock).add("test2");
        verify(mock).get(1);
    }

    @Test
    public void stubDemo() {

        ArrayList mock = mock(ArrayList.class);

        when(mock.get(0)).thenReturn("a");
        given(mock.get(0)).willReturn("a");

        when(mock.get(1)).thenThrow(new RuntimeException("test"));
        // given(mock.get(1)).willThrow(new RuntimeException("test"));

        doThrow(new RuntimeException("void")).when(mock).clear();
        // willThrow(new RuntimeException("void")).given(mock).clear();

        System.out.println(mock.get(0));
        try {
            mock.get(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(mock.get(2));
    }

    @Test
    public void matcherDemo() {

        ArrayList mock = mock(ArrayList.class);

        when(mock.get(anyInt())).thenReturn("meta");
        given(mock.get(anyInt())).willReturn("meta");

        when(mock.subList(anyInt(), eq(1))).thenReturn(new ArrayList() {{
            add("meta2");
        }});
        given(mock.subList(anyInt(), eq(1))).willReturn(new ArrayList() {{
            add("meta2");
        }});

        System.out.println(mock.get(12));
        System.out.println(mock.subList(3, 1));

        verify(mock).get(anyInt());
        then(mock).should().get(anyInt());
    }

    @Test
    public void orderDemo() {

        ArrayList mock = mock(ArrayList.class);
        LinkedList mock2 = mock(LinkedList.class);

        mock.add(1);
        mock.add(2);
        mock2.add(1);
        mock2.add(2);

        InOrder inOrder = inOrder(mock, mock2);

        inOrder.verify(mock).add(1);
        inOrder.verify(mock).add(2);
        inOrder.verify(mock2).add(1);

        // then(mock2).should(inOrder).add(1);  error
        then(mock2).should(inOrder).add(2);

        // error
        inOrder.verify(mock).add(1);
    }

    // @SpringBootTest
    @Mock HelloService helloService;

    @Test
    public void annoDemo() {
        when(helloService.hello()).thenReturn("hello world");
        System.out.println(helloService.hello());
    }

    @Test
    public void consecutiveDemo() {
        ArrayList mock = mock(ArrayList.class);

        when(mock.get(anyInt()))
                .thenReturn(0, 1, 2);

        System.out.println(mock.get(0));
        System.out.println(mock.get(1));
        System.out.println(mock.get(2));
        System.out.println(mock.get(3));

        // 清空
        reset(mock);
        System.out.println(mock.get(0));
    }

    @Test
    public void answerDemo() {
        ArrayList mock = mock(ArrayList.class);

        when(mock.get(anyInt()))
                .then(invocation -> {
                    Object argument = invocation.getArgument(0);
                    return argument + "r";
                });

        System.out.println(mock.get(0));
        System.out.println(mock.get(1));
    }

    @Test
    public void spyDemo() {
        List list = new LinkedList();
        List spy = spy(list);

        spy.add("1");

        when(spy.size()).thenReturn(10);

        System.out.println(spy.get(0));
        System.out.println(spy.size());

        // spy 只是一个 copy，所以 list 上的操作不影响 spy
        list.add("2");
        spy.forEach(System.out::println);
    }

    // @TODO timeout 的正确用法
    @Test
    public void timeoutDemo() throws InterruptedException {
        HelloService mock = mock(HelloService.class);

        when(mock.hello())
                .then(invocation -> {
                    TimeUnit.SECONDS.sleep(1);
                    return "hello world";
                });

        System.out.println(mock.hello());
        System.out.println(mock.hello());

        verify(mock, timeout(100).times(2))
                .hello();
    }

    @Test
    public void oneLinerStubsDemo() {
        HelloService helloService = when(mock(HelloService.class).hello())
                .thenReturn("hello world")
                .getMock();
        System.out.println(helloService.hello());
    }

}
