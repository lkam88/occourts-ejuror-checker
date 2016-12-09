package com.lucaskam.occourts.ejurorChecker;

public class Main {
    public static void main(String[] args) throws Exception {
        String pushoverToken = System.getenv("PUSHOVER_TOKEN");
        String pushoverUser = System.getenv("PUSHOVER_USER");
        String justiceCenterCode = System.getenv("JUSTICE_CENTER_CODE");
        Integer groupNumber = Integer.valueOf(System.getenv("GROUP_NUMBER"));
        Integer intervalInMinutes = Integer.valueOf(System.getenv("INTERVAL_IN_MINUTES"));

        JusticeCenter justiceCenter = JusticeCenter.parseCode(justiceCenterCode);
        DocumentRetriever documentRetriever = new DocumentRetriever();
        DocumentParser documentParser = new DocumentParser();
        PushOverNotificationSender groupStatusNotificationSender = new PushOverNotificationSender(pushoverToken, pushoverUser);


        CheckGroupStatusJobRunner checkGroupStatusJobRunner = new CheckGroupStatusJobRunner(documentRetriever, documentParser,
                                                                                            groupStatusNotificationSender, groupNumber, justiceCenter, 
                                                                                            intervalInMinutes);
        checkGroupStatusJobRunner.run();
    }
}
