package bandfinder.models;

import java.sql.Timestamp;

public class Post {
    private int id;
    private Integer authorUser;
    private Integer authorBand;
    private String text;
    private Timestamp date;

    public Post(int id, Integer authorUser, Integer authorBand, String text, Timestamp date) {
        this.id = id;
        this.authorUser = authorUser;
        this.authorBand = authorBand;
        this.text = text;
        this.date = date;
    }

    public Post(Integer authorUser, Integer authorBand, String text, Timestamp date) {
        this.id = -1;
        this.authorUser = authorUser;
        this.authorBand = authorBand;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(Integer authorUser) {
        this.authorUser = authorUser;
    }

    public Integer getAuthorBand() {
        return authorBand;
    }

    public void setAuthorBand(Integer authorBand) {
        this.authorBand = authorBand;
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
