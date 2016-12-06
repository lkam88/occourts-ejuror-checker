package com.lucaskam.occourts.ejurorChecker;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class DocumentParser {
    public String parseLastUpdated(Document document) throws ParseException {
        String lastUpdatedText = document.select("center font[color='#FF0000']").text();

        return lastUpdatedText.split("Last Update:")[1];
    }

    public Map<Integer, String> parseGroupStatuses(Document document) {
        Element tableBody = document.select("table.simple-table").get(0).children().get(0);

        Map<Integer, String> groupStatuses = new HashMap<>();
        for (Element tableRow : tableBody.children()) {
            Element groups = tableRow.child(0);
            Element instructions = tableRow.child(1);
            String instructionText = instructions.text();

            if (!groups.tagName().equals("td")) {
                continue;
            }

            String[] split = groups.text().split(" through ");
            Integer startGroup = Integer.parseInt(split[0]);
            Integer endGroup = Integer.parseInt(split[1]);

            for (int groupNumber = startGroup; groupNumber < endGroup + 1; groupNumber++) {
                groupStatuses.put(groupNumber, instructionText);
            }
        }
        return groupStatuses;
    }
}
