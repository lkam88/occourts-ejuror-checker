package com.lucaskam.occourts.ejurorChecker;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentRetriever {
    public Document retrieveDocument() throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://ocscefm1.occourts.org/directory/jury-services/serving-as-juror/njc-callin-information.cfm")
                                               .asString();
        String rawHtml = response.getBody();
        return Jsoup.parse(rawHtml);
    }
}
