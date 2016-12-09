package com.lucaskam.occourts.ejurorChecker;

import org.jsoup.nodes.Document;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.ParseException;
import java.util.Map;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CheckGroupStatusJob implements Job {
    private final DocumentRetriever documentRetriever;
    private final DocumentParser documentParser;
    private final GroupStatusNotificationSender groupStatusNotificationSender;
    private final int groupNumber;
    private String previousLastUpdated;
    private JusticeCenter justiceCenter;

    public CheckGroupStatusJob(DocumentRetriever documentRetriever, DocumentParser documentParser, GroupStatusNotificationSender
        groupStatusNotificationSender, int groupNumber, String previousLastUpdated, JusticeCenter justiceCenter) {
        this.documentRetriever = documentRetriever;
        this.documentParser = documentParser;
        this.groupStatusNotificationSender = groupStatusNotificationSender;
        this.groupNumber = groupNumber;
        this.previousLastUpdated = previousLastUpdated;
        this.justiceCenter = justiceCenter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            doJob();
        } catch (ParseException e) {
            throwException("Unable to parse the html of returned page.", e);
        } catch (GroupStatusNotificationException e) {
            throwException("Unable to send notification.", e);
        } catch (DocumentRetrieverException e) {
            throwException("Download web page to send notification.", e);
        } catch (Exception e) {
            throwException("Something bad happened.", e);
        }
    }

    public void throwException(String message, Exception causedException) throws JobExecutionException {
        JobExecutionException jobExecutionException = new JobExecutionException(message, causedException);
        jobExecutionException.printStackTrace();
        throw jobExecutionException;
    }

    private void doJob() throws GroupStatusNotificationException, DocumentRetrieverException, ParseException {
        Document document = documentRetriever.retrieveDocument(justiceCenter);

        String lastUpdated = documentParser.parseLastUpdated(document);

        if (!lastUpdated.equals(previousLastUpdated)) {
            previousLastUpdated = lastUpdated;

            Map<Integer, String> groupStatuses = documentParser.parseGroupStatuses(document);

            groupStatusNotificationSender.sendNotification(groupStatuses.get(groupNumber));
        }
    }


}
