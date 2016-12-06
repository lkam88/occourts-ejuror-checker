package com.lucaskam.occourts.ejurorChecker;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.jsoup.nodes.Document;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CheckGroupStatusJob implements Job {
    private DocumentRetriever documentRetriever;
    private DocumentParser documentParser;
    
    public CheckGroupStatusJob() {
        this.documentRetriever = new DocumentRetriever();
        this.documentParser = new DocumentParser();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            doJob();
        } catch (UnirestException e) {
            throw new JobExecutionException("Unable to download the page.", e);
        } catch (ParseException e) {
            throw new JobExecutionException("Unable to parse the html of returned page.", e);
        }
    }

    private void doJob() throws UnirestException, ParseException {
        Document document = documentRetriever.retrieveDocument();
        
        String lastUpdated = documentParser.parseLastUpdated(document);

        if (!lastUpdated.equals(Main.previousLastUpdated)) {
            Main.previousLastUpdated = lastUpdated;

            Map<Integer, String> groupStatuses = documentParser.parseGroupStatuses(document);

            System.out.println("sending notification");
            HttpResponse<String> response = Unirest.post("https://api.pushover.net/1/messages.json")
                .queryString("token", Main.pushoverToken)
                .queryString("user", Main.pushoverUser)
                .queryString("title", "Juror group statuses updated")
                .queryString("message", groupStatuses.get(5003))
                .asString();
        }
    }

}
