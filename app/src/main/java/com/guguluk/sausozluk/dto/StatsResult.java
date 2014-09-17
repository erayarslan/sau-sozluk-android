package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class StatsResult {
    private Stats data;
    private String status;

    public Stats getData() {
        return data;
    }
    public void setData(Stats data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
