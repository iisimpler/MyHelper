package io.github.iisimpler.helper.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.MessageType;

public class NotifyUtil {

    private static final Logger LOG = Logger.getInstance(NotifyUtil.class);

    public static void notify(String text, MessageType messageType) {
        NotificationGroup notificationGroup = NotificationGroup.findRegisteredGroup("MyHelper.Notify");
        LOG.assertTrue(notificationGroup != null, "MyHelper.Notify not find.");
        Notification notification = notificationGroup.createNotification("[MyHelper]" + text, messageType);
        Notifications.Bus.notify(notification);
    }

    public static void info(String text) {
        notify(text, MessageType.INFO);
    }

    public static void warn(String text) {
        notify(text, MessageType.WARNING);
    }

    public static void error(String text) {
        notify(text, MessageType.ERROR);
    }

}
