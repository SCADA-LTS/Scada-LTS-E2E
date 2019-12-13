package org.scadalts.e2e.pages.page.users;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class UsersPage extends PageObjectAbstract<UsersPage> {

    public UsersPage(SelenideElement source) {
        super(source);
    }

    @Override
    public UsersPage getPage() {
        return this;
    }
}