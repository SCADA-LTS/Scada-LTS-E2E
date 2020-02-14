package org.scadalts.e2e.app.domain.notification.email;

import java.io.File;

interface MessageTransformator {
    String transform(EmailData emailData, File inline);
    boolean isHtml();
}
