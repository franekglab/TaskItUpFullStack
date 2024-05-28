package io.fglab.taskitup.exceptions;

public class ListNotFoundExceptionResponse {

    private String ListNotFound;

    public ListNotFoundExceptionResponse(String listNotFound) {
        ListNotFound = listNotFound;
    }

    public String getListNotFound() {
        return ListNotFound;
    }

    public void setListNotFound(String listNotFound) {
        ListNotFound = listNotFound;
    }
}
