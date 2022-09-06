package bandfinder.models;

import java.sql.Timestamp;

public class Notification {

    private int id;
    private Integer userId;
    private Integer bandId;
    private boolean isRead;
    private String message;
    private Timestamp date;


    public Notification(int id, Integer userId, Integer bandId, boolean isRead, String message, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.isRead = isRead;
        this.message = message;
        this.date = date;
    }

    public Notification(Integer userId, Integer bandId, boolean isRead, String message, Timestamp date) {
        this(-1, userId, bandId, isRead, message, date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setReadStatus(boolean isRead) {
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) { this.message = message; }

    public Timestamp getDate() {
        return this.date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        return this.id == ((Notification) obj).id;
    }
}
