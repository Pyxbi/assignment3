package org.example.backend.model;
public class Response<T> {
    private T data;
    private String responseMsg;
    private int statusCode;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isOk() {
        return statusCode >= 200 && statusCode <= 299;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseMsg='" + responseMsg + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }


    public void setMessage(String message) {
        this.responseMsg = message;
    }

    // Set status for the response
    public void setStatus(int status) {
        this.statusCode = status;
    }
}