package com.lucaskam.occourts.ejurorChecker;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class PushOverNotificationSender implements GroupStatusNotificationSender {
    public static final String PUSHOVER_API_URL = "https://api.pushover.net/1/messages.json";
    private String pushoverToken;
    private String pushoverUser;

    public PushOverNotificationSender(String pushoverToken, String pushoverUser) {
        this.pushoverToken = pushoverToken;
        this.pushoverUser = pushoverUser;
    }

    @Override
    public void sendNotification(String groupStatus) throws GroupStatusNotificationException {
        HttpResponse<String> response;
        try {
            response = Unirest.post(PUSHOVER_API_URL)
                              .queryString("token", pushoverToken)
                              .queryString("user", pushoverUser)
                              .queryString("title", String.format("Juror group status updated"))
                              .queryString("message", groupStatus)
                              .asString();
        } catch (Exception e) {
            throw new GroupStatusNotificationException(e);
        }

        int responseStatusCode = response.getStatus();
        if (responseStatusCode < 200 || responseStatusCode > 300) {
            throw new GroupStatusNotificationException(String.format("Pushover returned an unexpected status code: %s", responseStatusCode));
        }
    }
}
