package com.common;

import java.io.Serializable;

public final class Response implements Serializable {
    private RequestStatus requestStatus;
    private String textMessage;
    private Object result;

    public Response(RequestStatus requestStatus, String textMessage, Object result) {
        this.requestStatus = requestStatus;
        this.textMessage = textMessage;
        this.result = result;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
