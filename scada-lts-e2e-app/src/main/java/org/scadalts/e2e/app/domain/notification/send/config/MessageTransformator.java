package org.scadalts.e2e.app.domain.notification.send.config;

import org.scadalts.e2e.app.domain.notification.send.MsgData;

import java.io.File;

public interface MessageTransformator {
    String transform(MsgData msgData, File inline);
    String transform(MsgData msgData);
    boolean isHtml();
}
