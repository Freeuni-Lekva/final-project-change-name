package bandfinder.models;

import bandfinder.infrastructure.Constants;

public class Follow {

    private int id;
    private int followerID;
    private int followeeID;

    public Follow(int id, int followerID, int followeeID) {
        this.id = id;
        this.followerID = followerID;
        this.followeeID = followeeID;
    }

    public Follow(int followerID, int followeeID){
        this(Constants.NO_ID, followerID, followeeID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowerID() {
        return followerID;
    }

    public void setFollowerID(int followerID) {
        this.followerID = followerID;
    }

    public int getFolloweeID() {
        return followeeID;
    }

    public void setFolloweeID(int followeeID) {
        this.followeeID = followeeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follow follow = (Follow) o;

        return id == follow.id || (followerID == follow.followerID && followeeID == follow.followeeID);
    }
}
