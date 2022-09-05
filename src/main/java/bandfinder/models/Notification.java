package bandfinder.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Notification {

    private int id;
    private int userId;
    private boolean isRead;
    private String message;
    private Timestamp date;


    public Notification(int id, int userId, boolean isRead, String message, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.isRead = isRead;
        this.message = message;
        this.date = date;
    }

    public Notification(int userId, boolean isRead, String message, Timestamp date) {
        this.id = -1;
        this.userId = userId;
        this.isRead = isRead;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
