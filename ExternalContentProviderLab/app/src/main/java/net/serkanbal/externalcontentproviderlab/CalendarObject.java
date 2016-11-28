package net.serkanbal.externalcontentproviderlab;

/**
 * Created by Serkan on 28/11/16.
 */

public class CalendarObject {
    private long id;
    private String title;
    private long date;

    public CalendarObject(long id, String title, long date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
