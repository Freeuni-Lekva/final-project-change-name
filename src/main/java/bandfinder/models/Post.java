package bandfinder.models;

public class Post {
    private int id;
    private String contentText;
    private int likes;
    private String postDate;

    public Post(int id, String contentText, int likes, String postDate) {
        this.id = id;
        this.contentText = contentText;
        this.likes = likes;
        this.postDate = postDate;
    }

    public Post(String contentText){
        this(-1,contentText,0,"test_date");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
