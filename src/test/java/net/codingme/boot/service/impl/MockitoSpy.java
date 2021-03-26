package net.codingme.boot.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


/**
 * @author: liujinhui
 * @date: 2020/7/23 13:27
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoSpy {
    @Mock
    private List mockList;

    @Test(expected = IndexOutOfBoundsException.class)
    public void mockTest() {
        List list = Mockito.spy(new LinkedList());
        //下面预设的list.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
        when(list.get(0)).thenReturn(3);
        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        doReturn(999).when(list).get(999);
        //预设size()期望值
        when(list.size()).thenReturn(100);
        //调用真实对象的api
        list.add(1);
        list.add(2);
        verify(list).add(1);
        verify(list).add(2);
    }
}
