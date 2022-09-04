package bandfinder.models;

import java.sql.Timestamp;

public class Post {
    private int id;
    private Integer userId;
    private Integer bandId;
    private String text;
    private Timestamp date;

    public Post(int id, Integer userId, Integer bandId, String text, Timestamp date) {
        this.id = id;
        this.userId = userId;
        this.bandId = bandId;
        this.text = text;
        this.date = date;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        return this.id == ((Post) obj).id;
    }
}
