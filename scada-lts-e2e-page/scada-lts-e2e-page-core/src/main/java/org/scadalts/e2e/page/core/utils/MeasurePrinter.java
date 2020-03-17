package org.scadalts.e2e.page.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.pages.PageObject;

import java.text.MessageFormat;

import static org.scadalts.e2e.common.measure.ValueTimeUnitToPrint.preparingToPrintMs;

@Log4j2
public class MeasurePrinter {
    public static <T extends PageObject<T>> void print(PageObject<T> page) {
        String msgWithMeasure = _getMessageToPrint(page.getClass().getSimpleName(),
                page.backendPerformanceMs(), page.frontendPerformanceMs());
        logger.info(msgWithMeasure);
    }

    public static <T extends PageObject<T>> void print(PageObject<T> page, String object) {
        String msgWithMeasure = _getMessageToPrint(page.getClass().getSimpleName(), object, page.backendPerformanceMs(),
                page.frontendPerformanceMs());
        logger.info(msgWithMeasure);
    }

    public static void print(String pageName, long backend, long frontend) {
        String msgWithMeasure = _getMessageToPrint(pageName, backend, frontend);
        logger.info(msgWithMeasure);
    }

    private static <T extends PageObject<T>> String _getMessageToPrint(String pageName, long backendMs, long frontendMs) {
        String backend = preparingToPrintMs(backendMs);
        String frontend = preparingToPrintMs(frontendMs);
        return MessageFormat.format("page: {0}, backend: {1}, frontend: {2}\n", pageName, backend, frontend);
    }

    private static <T extends PageObject<T>> String _getMessageToPrint(String pageName, String object, long backendMs,
                                                                       long frontendMs) {
        String backend = preparingToPrintMs(backendMs);
        String frontend = preparingToPrintMs(frontendMs);
        return MessageFormat.format("\n\npage: {0}, object: {1}, backend: {2}, frontend: {3}\n",
                pageName, object, backend, frontend);
    }
}
