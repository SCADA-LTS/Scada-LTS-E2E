package org.scadalts.e2e.common.core.measure;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValueTimeUnitToPrintTest {

    @Parameterized.Parameters(name= "{index}: ms: {0}, expected: {1}")
    public static Object[] data() {
        return new Object[][]{
                {1000, "1 s"},
                {10000, "10 s"},
                {60000, "1 min"},
                {61000, "1 min 1 s"},
                {61100, "1 min 1 s 100 ms"},
                {3600000, "1 h"},
                {7200000, "2 h"},
                {71100, "1 min 11 s 100 ms"},
                {376745, "6 min 16 s 745 ms"},
                {4767845, "1 h 19 min 27 s 845 ms"},
                {12*3600000, "12 h"},
                {24*3600000, "24 h"},
                {24*3600000 + 7200000, "26 h"},
                {24*3600000 + 4767845, "25 h 19 min 27 s 845 ms"},
                {60100, "1 min 100 ms"},
        };
    }

    private final long ms;
    private final String expected;

    public ValueTimeUnitToPrintTest(long ms, String expected) {
        this.ms = ms;
        this.expected = expected;
    }

    @org.junit.Test
    public void preparingToPrintMs() {

        //when:
        String result = ValueTimeUnitToPrint.preparingToPrintMs(ms);

        //then:
        assertEquals(expected, result);
    }

}