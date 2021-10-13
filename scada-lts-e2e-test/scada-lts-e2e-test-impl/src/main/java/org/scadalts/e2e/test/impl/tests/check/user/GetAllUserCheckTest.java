package org.scadalts.e2e.test.impl.tests.check.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.identifiers.UserIdentifier;
import org.scadalts.e2e.page.impl.pages.users.UsersPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;

@RunWith(Parameterized.class)
public class GetAllUserCheckTest {

    @Parameterized.Parameters(name = "{index}: user: {0}")
    public static List<UserIdentifier> data() {
        usersPage = TestWithPageUtil.openNavigationPage()
                .openUsers();
        return usersPage.getUsers();
    }

    private static UsersPage usersPage;
    private UserIdentifier identifier;

    public GetAllUserCheckTest(UserIdentifier identifier) {
        this.identifier = identifier;
    }

    @Test
    public void get_user() {
        //when:
        usersPage.openUserEditor(identifier)
                .waitForObject(identifier)
                .printLoadingMeasure();
    }
}
