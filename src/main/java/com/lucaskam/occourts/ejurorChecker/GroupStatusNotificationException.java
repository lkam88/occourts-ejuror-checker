package com.lucaskam.occourts.ejurorChecker;

public class GroupStatusNotificationException extends Exception {
    public GroupStatusNotificationException(Exception e) {
        super(e);
    }

    public GroupStatusNotificationException(String message) {
        super(message);
    }
}
