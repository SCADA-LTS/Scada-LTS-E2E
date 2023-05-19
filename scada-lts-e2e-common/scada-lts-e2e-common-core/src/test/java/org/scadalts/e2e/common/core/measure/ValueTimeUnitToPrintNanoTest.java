package org.scadalts.e2e.common.core.measure;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValueTimeUnitToPrintNanoTest {

    @Parameterized.Parameters(name= "{index}: ns: {0}, expected: {1}, expected [ms]: {2}")
    public static Object[] data() {
        return new Object[][]{
                {1000*1000000.0, "1 s", "1 s"},
                {10000*1000000.0, "10 s", "10 s"},
                {60000*1000000.0, "1 min", "1 min"},
                {61000*1000000.0, "1 min 1 s", "1 min 1 s"},
                {61100*1000000.0, "1 min 1 s 100 ms", "1 min 1 s 100 ms"},
                {3600000*1000000.0, "1 h", "1 h"},
                {7200000*1000000.0, "2 h", "2 h"},
                {71100*1000000.0, "1 min 11 s 100 ms", "1 min 11 s 100 ms"},
                {376745*1000000.0, "6 min 16 s 745 ms", "6 min 16 s 745 ms"},
                {4767845*1000000.0, "1 h 19 min 27 s 845 ms", "1 h 19 min 27 s 845 ms"},
                {12*3600000*1000000.0, "12 h", "12 h"},
                {24*3600000*1000000.0, "24 h", "24 h"},
                {(24*3600000 + 7200000)*1000000.0, "26 h", "26 h"},
                {(24*3600000 + 4767845)*1000000.0, "25 h 19 min 27 s 845 ms", "25 h 19 min 27 s 845 ms"},
                {1000, "1000 ns", "0 ms"},
                {10000, "10000 ns", "0 ms"},
                {60000, "60000 ns", "0 ms"},
                {61000, "61000 ns", "0 ms"},
                {61100, "61100 ns", "0 ms"},
                {3600000, "3 ms 600000 ns", "3 ms"},
                {7200000, "7 ms 200000 ns", "7 ms"},
                {71100, "71100 ns", "0 ms"},
                {376745, "376745 ns", "0 ms"},
                {4767845, "4 ms 767845 ns", "4 ms"},
                {12*3600000.0, "43 ms 200000 ns", "43 ms"},
                {24*3600000.0, "86 ms 400000 ns", "86 ms"},
                {24*3600000 + 7200000.0, "93 ms 600000 ns", "93 ms"},
                {24*3600000 + 4767845.0, "91 ms 167845 ns", "91 ms"},
                {(24*3600000 + 4767845)*1000000.0 + 312, "25 h 19 min 27 s 845 ms 312 ns", "25 h 19 min 27 s 845 ms"},
                {60100*1000000.0, "1 min 100 ms", "1 min 100 ms"},
                {(24*3600000 + 4767000)*1000000.0 + 312, "25 h 19 min 27 s 312 ns", "25 h 19 min 27 s"},
        };
    }

    private final double ns;
    private final String expected;
    private final String msExpected;

    public ValueTimeUnitToPrintNanoTest(double ns, String expected, String msExpected) {
        this.ns = ns;
        this.expected = expected;
        this.msExpected = msExpected;
    }

    @org.junit.Test
    public void preparingToPrintNano() {

        //when:
        String result = ValueTimeUnitToPrint.preparingToPrintNano(ns);

        //then:
        assertEquals(expected, result);
    }

    @org.junit.Test
    public void preparingToPrintNanoToMs() {

        //when:
        String result = ValueTimeUnitToPrint.preparingToPrintNanoToMs(ns);

        //then:
        assertEquals(msExpected, result);
    }

}