package org.scadalts.e2e.page.core.javascripts;

import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;

@Log4j2
public enum JQueryScripts {

    SET_VAL("jQuery(\"{0}:visible\").val(\"{1}\")"),
    SELECT("jQuery(\"{0}:visible option\").filter(function () '{ return jQuery(this).html() == \"'{1}'\"; }').attr(\"selected\", \"selected\")"),
    GET_VAL("jQuery(\"{0}:visible\").val()"),
    IS_CHECKED("jQuery(\"{0}:visible\").prop(\"checked\")"),
    CLICK("jQuery(\"{0}:visible\").click()");

    private final String script;

    JQueryScripts(String script) {
        this.script = script;
    }

    public static String val(String cssSelector, String arg) {
        String scriptToExecute = MessageFormat.format(JQueryScripts.SET_VAL.script, cssSelector, arg);
        logger.info("script jquery: {}", scriptToExecute);
        return scriptToExecute;
    }

    public static String attrSelected(String selectSelector, String arg) {
        String scriptToExecute = MessageFormat.format(JQueryScripts.SELECT.script, selectSelector, arg);
        logger.info("script jquery: {}", scriptToExecute);
        return scriptToExecute;
    }

    public static String val(String cssSelector) {
        String scriptToExecute = MessageFormat.format(JQueryScripts.GET_VAL.script, cssSelector);
        logger.info("script jquery: {}", scriptToExecute);
        return scriptToExecute;
    }

    public static String prop(String cssSelector) {
        String scriptToExecute = MessageFormat.format(JQueryScripts.IS_CHECKED.script, cssSelector);
        logger.info("script jquery: {}", scriptToExecute);
        return scriptToExecute;
    }

    public static String click(String cssSelector) {
        String scriptToExecute = MessageFormat.format(JQueryScripts.CLICK.script, cssSelector);
        logger.info("script jquery: {}", scriptToExecute);
        return scriptToExecute;
    }

}
