package org.scadalts.e2e.page.core.pages;

public abstract class MainPageObjectAbstract<T extends MainPageObject<T>> extends PageObjectAbstract<T>
        implements MainPageObject<T> {

    private final String refUrl;
    private final String title;

    public MainPageObjectAbstract(String title, String refUrl) {
        super();
        this.refUrl = refUrl;
        this.title = title;
    }

    @Override
    public String getRefUrl() {
        return refUrl;
    }

    @Override
    public String getTitle() {
        //return waitWhileNotVisible($(".smallTitle")).getText();
        return title;
    }
}
