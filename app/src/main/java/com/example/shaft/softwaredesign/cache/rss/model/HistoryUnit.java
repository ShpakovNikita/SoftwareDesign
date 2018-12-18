package com.example.shaft.softwaredesign.cache.rss.model;

import java.util.Date;

public class HistoryUnit{
    public Date visitDate;
    public String link;

    public HistoryUnit(Date visitDate, String link) {
        this.visitDate = visitDate;
        this.link = link;
    }

    public HistoryUnit(){}

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
