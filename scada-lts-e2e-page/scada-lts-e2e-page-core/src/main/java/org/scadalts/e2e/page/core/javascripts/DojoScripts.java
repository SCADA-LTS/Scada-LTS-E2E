package org.scadalts.e2e.page.core.javascripts;

import java.text.MessageFormat;

public enum DojoScripts {

    SET_COLOR("textRendererEditor.handlerRangeColour(\"{0}\");");

    private String script;

    DojoScripts(String script) {
        this.script = script;
    }

    public String getScript(String colorCode) {
        return MessageFormat.format(script, colorCode);
    }
}
