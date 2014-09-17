package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class Stats {
    private String goodCount;
    private String badCount;
    private String favCount;
    private String ticket;

    public String getGoodCount() {
        return goodCount;
    }
    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }
    public String getBadCount() {
        return badCount;
    }
    public void setBadCount(String badCount) {
        this.badCount = badCount;
    }
    public String getFavCount() {
        return favCount;
    }
    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }
    public String getTicket() {
        return ticket;
    }
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
