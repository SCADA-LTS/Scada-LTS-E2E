package org.scadalts.e2e.common.core.measure;

import java.text.MessageFormat;

public enum ValueTimeUnitToPrint {

    MILLISECONDS {
        @Override
        public String toPrint(double ms) {
            return MessageFormat.format("{0} ms", String.valueOf((long)ms));
        }
    },
    SECONDS {
        @Override
        public String toPrint(double ms) {
            double diff = 1000.0;
            if(ms < diff) {
                return MessageFormat.format("{0}", MILLISECONDS.toPrint(ms));
            }
            double rest = ms % diff;
            if(rest == 0) {
                return MessageFormat.format("{0} s", String.valueOf((long)(ms / diff)));
            }
            return MessageFormat.format("{0} s {1}", String.valueOf((long)((ms - rest) / diff)), MILLISECONDS.toPrint(rest));
        }
    },
    MINUTES {
        @Override
        public String toPrint(double ms) {
            double diff = 1000.0 * 60.0;
            if(ms < diff) {
                return MessageFormat.format("{0}", SECONDS.toPrint(ms));
            }
            double rest = ms % diff;
            if(rest == 0) {
                return MessageFormat.format("{0} min", (long)(ms / diff));
            }
            return MessageFormat.format("{0} min {1}", String.valueOf((long)((ms - rest) / diff)), SECONDS.toPrint(rest));
        }
    },
    HOURS {
        @Override
        public String toPrint(double ms) {
            double diff = 1000.0 * 60.0 * 60.0;
            if(ms < diff) {
                return MessageFormat.format("{0}", MINUTES.toPrint(ms));
            }
            double rest = ms % diff;
            if(rest == 0) {
                return MessageFormat.format("{0} h", (long)(ms / diff));
            }
            return MessageFormat.format("{0} h {1}", String.valueOf((long)((ms - rest) / diff)), MINUTES.toPrint(rest));
        }
    };

    public abstract String toPrint(double ms);

    public static String preparingToPrintMs(double ms) {
        if(ms < 1000) {
            return ValueTimeUnitToPrint.MILLISECONDS.toPrint(ms);
        }
        if(ms < 60000) {
            return ValueTimeUnitToPrint.SECONDS.toPrint(ms);
        }
        if(ms < 3600000) {
            return ValueTimeUnitToPrint.MINUTES.toPrint(ms);
        }
        return ValueTimeUnitToPrint.HOURS.toPrint(ms);
    }

    public static String preparingToPrintNanoToMs(double nano) {
        double diff = 1000000.0;
        double rest = nano % diff;
        return preparingToPrintMs((nano - rest)/1000000.0);
    }

    public static String preparingToPrintNano(double nano) {
        double diff = 1000000.0;
        if(nano < diff) {
            return MessageFormat.format("{0} ns", String.valueOf((long)nano));
        }
        double rest = nano % diff;
        if(rest == 0) {
            return preparingToPrintMs(nano/1000000.0);
        }
        return MessageFormat.format("{0} {1} ns", preparingToPrintMs((nano - rest)/1000000.0), String.valueOf((long)rest));
    }
}
