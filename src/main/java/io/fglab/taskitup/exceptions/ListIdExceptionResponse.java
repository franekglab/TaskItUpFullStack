package io.fglab.taskitup.exceptions;


public class ListIdExceptionResponse {

    private String listIdentifier;

    public ListIdExceptionResponse(String listIdentifier) {
        this.listIdentifier = listIdentifier;
    }

    public String getListIdentifier() {
        return listIdentifier;
    }

    public void setListIdentifier(String listIdentifier) {
        this.listIdentifier = listIdentifier;
    }
}
