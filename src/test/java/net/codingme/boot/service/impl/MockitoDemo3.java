package net.codingme.boot.service.impl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * @author: liujinhui
 * @date: 2020/7/23 13:26
 */
public class MockitoDemo3 {

    @Mock
    private List mockList;

    public MockitoDemo3() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shorthand() {
        mockList.add(1);
        verify(mockList).add(1);
    }


}
