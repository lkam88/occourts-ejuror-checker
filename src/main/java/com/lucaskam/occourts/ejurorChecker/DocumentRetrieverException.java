package com.lucaskam.occourts.ejurorChecker;

public class DocumentRetrieverException extends Exception {
    public DocumentRetrieverException(Exception e) {
        super(e);
    }

    public DocumentRetrieverException(String message) {
        super(message);
    }
}
