package org.example.lab6;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class SpyTest {

    @Test
    void shouldVerifyAddMethod() {
        List<String> list = new ArrayList<>();

        List<String> spyList = spy(list);

        spyList.add("Spring");

        verify(spyList).add("Spring");

        assertEquals(1, spyList.size());
    }
}

