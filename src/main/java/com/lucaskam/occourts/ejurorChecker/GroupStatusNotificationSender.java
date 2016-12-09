package com.lucaskam.occourts.ejurorChecker;

public interface GroupStatusNotificationSender {
    void sendNotification(String groupStatus) throws GroupStatusNotificationException;
}
