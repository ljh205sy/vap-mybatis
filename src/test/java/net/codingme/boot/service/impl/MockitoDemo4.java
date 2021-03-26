package net.codingme.boot.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;


/**
 * @author: liujinhui
 * @date: 2020/7/23 13:27
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoDemo4 {
    @Mock
    private List mockList;

    @Test
    public void shorthand() {
        mockList.add(1);
        verify(mockList).add(1);
    }
}
