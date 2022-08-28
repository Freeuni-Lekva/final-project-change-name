package bandfinder.models;

public class Notification {

    private int id;
    private int userId;
    private boolean isRead;
    private String message;

    public Notification(int id, int userId, boolean isRead, String message) {
        this.id = id;
        this.userId = userId;
        this.isRead = isRead;
        this.message = message;
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

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        return this.id == ((Notification) obj).id;
    }
}
