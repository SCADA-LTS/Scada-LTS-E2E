package org.scadalts.e2e.page.core.javascripts;

public enum JavascriptWindow implements JavascriptProvider {

    DISMISS_ALERT {
        @Override
        public String getScriptToExecute() {
            return "window.confirm = function(){return false;}";
        }
    },

    ACCEPT_ALERT {
        @Override
        public String getScriptToExecute() {
            return "window.confirm = function(){return true;}";
        }
    },
    ACCEPT_ON_BEFORE_UNLOAD {
        @Override
        public String getScriptToExecute() {
            return "window.onbeforeunload = function() {};";
        }
    };

    @Override
    public abstract String getScriptToExecute();

    public static String cleanConfirm() {
        return "window.confirm = function(){}";
    }
}
