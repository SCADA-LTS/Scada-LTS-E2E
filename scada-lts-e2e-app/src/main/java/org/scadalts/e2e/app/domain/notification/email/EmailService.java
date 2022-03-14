package org.scadalts.e2e.app.domain.notification.email;


public interface EmailService {
    boolean sendEmailFail(EmailData emailData);
    boolean sendEmailSuccess(EmailData emailData);
}
