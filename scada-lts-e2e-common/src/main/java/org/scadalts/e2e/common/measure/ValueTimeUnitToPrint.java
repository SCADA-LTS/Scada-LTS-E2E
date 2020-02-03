package org.scadalts.e2e.common.measure;

import java.text.MessageFormat;

public enum ValueTimeUnitToPrint {

    MILLISECONDS {
        @Override
        public String toPrint(double ms) {
            return MessageFormat.format("{0} ms", ms);
        }
    },
    SECONDS {
        @Override
        public String toPrint(double ms) {
            return MessageFormat.format("{0} s", ms / 1000.0);
        }
    },
    MINUTES {
        @Override
        public String toPrint(double ms) {
            return MessageFormat.format("{0} min", (ms / (1000.0 * 60.0)));
        }
    };

    public abstract String toPrint(double ms);

    public static String preparingToPrintMs(double ms) {
        if(ms < 9999) {
            return ValueTimeUnitToPrint.MILLISECONDS.toPrint(ms);
        }
        if(ms < 99999) {
            return ValueTimeUnitToPrint.SECONDS.toPrint(ms);
        }
        return ValueTimeUnitToPrint.MINUTES.toPrint(ms);
    }

    public static String preparingToPrintNano(double nano) {
        return preparingToPrintMs(nano / 1000000.0);
    }
}
