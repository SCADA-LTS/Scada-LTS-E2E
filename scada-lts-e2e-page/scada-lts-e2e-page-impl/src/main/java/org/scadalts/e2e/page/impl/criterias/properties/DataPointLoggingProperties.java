package org.scadalts.e2e.page.impl.criterias.properties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scadalts.e2e.page.impl.dicts.IntervalLoggingPeriodType;
import org.scadalts.e2e.page.impl.dicts.IntervalLoggingType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.dicts.PurgeType;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class DataPointLoggingProperties {

    private final LoggingType loggingType;
    private final IntervalLoggingPeriodType intervalLoggingPeriodType;
    private final IntervalLoggingType intervalLoggingType;
    private final PurgeType purgeType;
    private final int defaultCacheSize;
    private final boolean discardExtremeValues;
    private final String discardHighLimit;
    private final String discardLowLimit;
    private final int intervalLoggingPeriod;
    private final int purgePeriod;
    private final String tolerance;

    private static final DataPointLoggingProperties EMPTY = noChange();

    protected DataPointLoggingProperties(LoggingType loggingType, IntervalLoggingPeriodType intervalLoggingPeriodType,
                                       IntervalLoggingType intervalLoggingType, PurgeType purgeType,
                                       int defaultCacheSize, boolean discardExtremeValues, String discardHighLimit,
                                       String discardLowLimit, int intervalLoggingPeriod, int purgePeriod, String tolerance) {
        this.loggingType = loggingType == null ? LoggingType.NONE : loggingType;
        this.intervalLoggingPeriodType = intervalLoggingPeriodType == null ? IntervalLoggingPeriodType.MINUTES : intervalLoggingPeriodType;
        this.intervalLoggingType = intervalLoggingType == null ? IntervalLoggingType.INSTANT : intervalLoggingType;
        this.purgeType = purgeType == null ? PurgeType.YEARS : purgeType;
        this.defaultCacheSize = defaultCacheSize == 0 ? 1 : defaultCacheSize;
        this.discardExtremeValues = discardExtremeValues;
        this.discardHighLimit = discardHighLimit == null ? "1.7976931348623157E308" : discardHighLimit;
        this.discardLowLimit = discardLowLimit == null ? "-1.7976931348623157E308" : discardLowLimit;
        this.intervalLoggingPeriod = intervalLoggingPeriod == 0 ? 15 : intervalLoggingPeriod;
        this.purgePeriod = purgePeriod == 0 ? 1 : purgePeriod;
        this.tolerance = tolerance == null ? "0.0" : tolerance;
    }

    public static DataPointLoggingProperties empty() {
        return EMPTY;
    }

    public static DataPointLoggingProperties noChange() {
        return DataPointLoggingProperties.builder()
                .loggingType(LoggingType.NONE)
                .intervalLoggingPeriod(0)
                .defaultCacheSize(0)
                .discardExtremeValues(false)
                .discardHighLimit("0.0")
                .discardLowLimit("0.0")
                .intervalLoggingPeriodType(IntervalLoggingPeriodType.YEARS)
                .intervalLoggingType(IntervalLoggingType.INSTANT)
                .purgePeriod(0)
                .purgeType(PurgeType.YEARS)
                .tolerance("0.0")
                .build();
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }
}
