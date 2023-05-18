package org.scadalts.e2e.app.domain.notification.reaction;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendMsgReaction {
}
