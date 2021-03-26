package net.codingme.boot.service.impl;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

// 静态导入会使代码更简洁

/**
 * http://www.testclass.net/mockito/mockito-doc-zh-05
 */
public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * 验证某些行为
     */
    @Test
    public void verify_behaviour() {
        // mock creation 创建mock对象
        List mockedList = mock(List.class);

        //using mock object 使用mock对象
        mockedList.add("one");
        mockedList.clear();

        //verification 验证，
        //验证add(1)和clear()行为是否发生
        verify(mockedList).add("two");
        verify(mockedList).clear();
    }

    /**
     * 模拟我们所期望的结果
     */
    @Test
    public void when_thenReturn_value() {
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        assertEquals("hello world world", result);
    }


    /**
     * 预设当流关闭时抛出异常
     *
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        //预设当流关闭时抛出异常
        doThrow(new IOException()).when(outputStream).close();
        outputStream.close();
    }

    /**
     * 何做一些测试桩 (Stub)
     */
    @Test(expected = RuntimeException.class)
    public void when_thenReturn() {
        //You can mock concrete classes, not only interfaces
        // 你可以mock具体的类型,不仅只是接口
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        // 测试桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
//        when(mockedList.get(anyInt())).thenReturn("element");    // 参数匹配器，下面会讲

        //following prints "first"
        // 输出“first”
        System.out.println(mockedList.get(0));

        //following throws runtime exception
        // 抛出异常
        System.out.println(mockedList.get(1));

        //following prints "null" because get(999) was not stubbed
        // 因为get(999) 没有打桩，因此输出null
        System.out.println(mockedList.get(999));

        //Although it is possible to verify a stubbed invocation, usually it's just redundant
        //If your code cares what get(0) returns then something else breaks (often before even verify() gets executed).
        //If your code doesn't care what get(0) returns then it should not be stubbed. Not convinced? See here.
        // 验证get(0)被调用的次数
        verify(mockedList).get(0);
    }

    /**
     * 参数匹配器 (matchers)
     */

    @Test
    public void with_arguments() {

        LinkedList mockedList = mock(LinkedList.class);

        //stubbing using built-in anyInt() argument matcher
        // 使用内置的anyInt()参数匹配器
        when(mockedList.get(anyInt())).thenReturn("element");

        //following prints "element"
        // 输出element
        System.out.println(mockedList.get(1));

        //you can also verify using an argument matcher
        // 你也可以验证参数匹配器
        verify(mockedList).get(anyInt());
    }


    /**
     * 验证函数调用次数
     * verify函数默认验证的是执行了times(1)，也就是某个测试函数是否执行了1次.因此，times(1)通常被省略了。
     * <p>
     * atLeastOnce() //验证至少调用一次
     * atLeast(2) //验证至少调用2次
     * atMost(5) //验证至多调用3次
     */
    @Test
    public void verifying_number_of_invocations() {
        LinkedList mockedList = mock(LinkedList.class);
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        // 下面的两个验证函数效果一样,因为verify默认验证的就是times(1)
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        // 验证具体的执行次数
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        // 使用never()进行验证,never相当于times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        // 使用atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("five times");
        verify(mockedList, atMost(5)).add("three times");
    }

    /**
     * doReturn()、doThrow()、doAnswer()、doNothing()、doCallRealMethod()系列方法的运用
     * 通过when(Object)为无返回值的函数打桩有不同的方法,因为编译器不喜欢void函数在括号内...
     * <p>
     * 使用doThrow(Throwable) 替换stubVoid(Object)来为void函数打桩是为了与doAnswer()等函数族保持一致性。
     * <p>
     * 当你想为void函数打桩时使用含有一个exception 参数的doAnswer() :
     *
     * @throws RuntimeException
     */
    @Test(expected = RuntimeException.class)
    public void when_doThrow() throws RuntimeException {

        LinkedList mockedList = mock(LinkedList.class);
        doThrow(new RuntimeException()).when(mockedList).clear();

        //following throws RuntimeException:
        // 下面的代码会抛出异常
        mockedList.clear();
    }


}
