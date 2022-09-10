package bandfinder.models;

import java.util.Date;

public class Comment {

    private int id;

    private Date date;

    private String text;

    private int authorId;

    private int postId;

    private int likes;

    public Comment(Date date, String text, int authorId, int postId, int likes){
        this(-1, date, text, authorId, postId, likes);
    }

    public Comment(int id, Date date, String text, int authorId, int postId, int likes) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.authorId = authorId;
        this.postId = postId;
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public static class CommentDisplay{
        private String username;
        private String date;
        private String text;

        public String getUsername() {
            return username;
        }

        public String getDate() {
            return date;
        }

        public String getText() {
            return text;
        }

        public int getLikes() {
            return likes;
        }

        private int likes;

        public CommentDisplay(String username, String date, String text, int likes) {
            this.username = username;
            this.date = date;
            this.text = text;
            this.likes = likes;
        }

    }
}
