package org.scadalts.e2e.app.domain.notification.email;


public interface MsgService {
    boolean sendFail(EmailData emailData);
    boolean sendSuccess(EmailData emailData);
}
