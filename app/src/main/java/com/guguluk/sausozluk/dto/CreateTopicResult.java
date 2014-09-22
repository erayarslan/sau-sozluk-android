package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class CreateTopicResult {
    private Topic data;
    private String status;

    public Topic getData() {
        return data;
    }

    public void setData(Topic data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
