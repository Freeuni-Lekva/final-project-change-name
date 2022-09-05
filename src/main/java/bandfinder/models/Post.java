package bandfinder.models;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int authorUser;
    private int authorBand;
    private String text;
    private Timestamp date;

    public Post(int id, int authorUser, int authorBand, String text, Timestamp date) {
        this.id = id;
        this.authorUser = authorUser;
        this.authorBand = authorBand;
        this.text = text;
        this.date = date;
    }

    public Post(int authorUser, int authorBand, String text, Timestamp date) {
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

    public int getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(int authorUser) {
        this.authorUser = authorUser;
    }

    public int getAuthorBand() {
        return authorBand;
    }

    public void setAuthorBand(int authorBand) {
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
