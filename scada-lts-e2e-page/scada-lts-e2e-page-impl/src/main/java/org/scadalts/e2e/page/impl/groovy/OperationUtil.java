package org.scadalts.e2e.page.impl.groovy;

import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.javascripts.JavascriptProvider;
import org.scadalts.e2e.page.core.javascripts.JavascriptsTiming;
import org.scadalts.e2e.page.core.pages.PageObject;
import org.scadalts.e2e.page.core.utils.E2eCookie;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OperationUtil {

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }

    public static NavigationPage maximize() {
        return navigationPage.maximize();
    }

    public static NavigationPage getPage() {
        return navigationPage.getPage();
    }

    public static String getPageName() {
        return navigationPage.getPageName();
    }

    public static NavigationPage waitOnPage(long wait) {
        return navigationPage.waitOnPage(wait);
    }

    public static NavigationPage waitForObject(NodeCriteria nodeCriteria) {
        return navigationPage.waitForObject(nodeCriteria);
    }

    public static NavigationPage waitForObject(IdentifierObject identifier) {
        return navigationPage.waitForObject(identifier);
    }

    public static NavigationPage waitForObject(String id) {
        return navigationPage.waitForObject(id);
    }

    public static NavigationPage refreshPage() {
        return navigationPage.refreshPage();
    }

    public static NavigationPage printLoadingMeasure() {
        return navigationPage.printLoadingMeasure();
    }

    public static NavigationPage printLoadingMeasure(String msg) {
        return navigationPage.printLoadingMeasure(msg);
    }

    public static long frontendPerformanceMs() {
        return navigationPage.frontendPerformanceMs();
    }

    public static long backendPerformanceMs() {
        return navigationPage.backendPerformanceMs();
    }

    public static long getResponseStartMs() {
        return navigationPage.getResponseStartMs();
    }

    public static long getDomCompleteMs() {
        return navigationPage.getDomCompleteMs();
    }

    public static long getNavigationStartMs() {
        return navigationPage.getNavigationStartMs();
    }

    public static long getTimingMs(JavascriptsTiming script) {
        return navigationPage.getTimingMs(script);
    }

    public static Optional<Object> executeJs(String script) {
        return navigationPage.executeJs(script);
    }

    public static <T> Optional<T> executeJs(JavascriptProvider script, Class<T> returnType) {
        return navigationPage.executeJs(script, returnType);
    }

    public static long executeJsLong(JavascriptProvider script) {
        return navigationPage.executeJsLong(script);
    }

    public static int executeJsInt(JavascriptProvider script) {
        return navigationPage.executeJsInt(script);
    }

    public static double executeJsDouble(JavascriptProvider script) {
        return navigationPage.executeJsDouble(script);
    }

    public static boolean executeJsBoolean(JavascriptProvider script) {
        return navigationPage.executeJsBoolean(script);
    }

    public static Optional<Boolean> executeJsOptBoolean(JavascriptProvider script) {
        return navigationPage.executeJsOptBoolean(script);
    }

    public static Optional<String> executeJsOptString(JavascriptProvider script) {
        return navigationPage.executeJsOptString(script);
    }

    public static void executeJs(JavascriptProvider script) {
        navigationPage.executeJs(script);
    }

    public static String getHeadHtml() {
        return navigationPage.getHeadHtml();
    }

    public static String getBodyText() {
        return navigationPage.getBodyText();
    }

    public static String getBodyHtml() {
        return navigationPage.getBodyHtml();
    }

    public static String getTitle() {
        return navigationPage.getTitle();
    }

    public static boolean containsObject(IdentifierObject identifier) {
        return navigationPage.containsObject(identifier);
    }

    public static boolean containsObject(CriteriaObject criteria) {
        return navigationPage.containsObject(criteria);
    }

    public static boolean containsText(String text) {
        return navigationPage.containsText(text);
    }

    public static NavigationPage acceptAlertOnPage() {
        return navigationPage.acceptAlertOnPage();
    }

    public static NavigationPage dismissAlertOnPage() {
        return navigationPage.dismissAlertOnPage();
    }

    public static boolean isAlertOnPage() {
        return navigationPage.isAlertOnPage();
    }

    public static String getAlertContent() {
        return navigationPage.getAlertContent();
    }

    public static NavigationPage screenshot(String fileName) {
        return navigationPage.screenshot(fileName);
    }

    public static NavigationPage screenshot() {
        return navigationPage.screenshot();
    }

    public static NavigationPage delay() {
        return navigationPage.delay();
    }

    public static NavigationPage waitForCompleteLoad() {
        return navigationPage.waitForCompleteLoad();
    }

    public static Optional<String> getSessionId() {
        return navigationPage.getSessionId();
    }

    public static List<E2eCookie> getCookies() {
        return navigationPage.getCookies();
    }

    public static String getCurrentUrl() {
        return navigationPage.getCurrentUrl();
    }

    public static void printCurrentUrl() {
        navigationPage.printCurrentUrl();
    }

    public static Object executeJQuery(String jQueryScript) {
        return navigationPage.executeJQuery(jQueryScript);
    }

    public static String executeJQueryString(String jQueryScript) {
        return navigationPage.executeJQueryString(jQueryScript);
    }

    public static void waitLoadedJQuery() {
        navigationPage.waitLoadedJQuery();
    }

    public static void closeWindows() {
        navigationPage.closeWindows();
    }

    public static void close() {
        navigationPage.close();
    }

    public static <T extends PageObject<T>> void screenshot(T page) {
        page.screenshot();
    }

    public static void logout() {
        navigationPage.logout();
    }

    public static String getUserName() {
        return navigationPage.getUserName();
    }

    public static Set<String> tabsOpened() {
        return NavigationPage.tabsOpened();
    }

    public static String closeAllButOnePage() {
        return NavigationPage.closeAllButOnePage();
    }

    public static void kill() {
        NavigationPage.kill();
    }


}
