package com.lucaskam.occourts.ejurorChecker;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentRetriever {
    public Document retrieveDocument(JusticeCenter justiceCenter) throws DocumentRetrieverException {
        try {
            HttpResponse<String> response = Unirest.get(justiceCenter.getUrl())
                                                   .asString();
            String rawHtml = response.getBody();

            int responseStatusCode = response.getStatus();
            if (responseStatusCode < 200 || responseStatusCode > 300) {
                throw new DocumentRetrieverException(String.format("%s website returned unexpected response code: %s", justiceCenter.getName(),
                                                                   responseStatusCode));
            }

            return Jsoup.parse(rawHtml);
        } catch (DocumentRetrieverException e) {
            throw e;

        } catch (Exception e) {
            throw new DocumentRetrieverException(e);
        }
    }
}
