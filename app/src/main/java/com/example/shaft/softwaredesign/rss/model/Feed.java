package com.example.shaft.softwaredesign.rss.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "feed", strict = false)
public class Feed implements Serializable {
    @Element(name = "title")
    private String title;

    @Element(name = "icon")
    private String icon;

    @Element(name = "subtitle")
    private String subtitle;

    @Element(name = "logo")
    private String logo;

    @ElementList(inline = true, name = "entry")
    private List<Feed> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Feed> getItems() {
        return items;
    }

    public void setItems(List<Feed> items) {
        this.items = items;
    }
}
