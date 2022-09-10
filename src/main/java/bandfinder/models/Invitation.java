package bandfinder.models;

import bandfinder.infrastructure.Constants;

public class Invitation {
    //When user sends request to join band
    private int id;
    private int userId;
    private int bandId;
    private boolean isProcessed;

    public Invitation(int id, int userId, int bandId, boolean isProcessed) {
        this.id = id;
        this.userId = userId;
        this.bandId = bandId;
        this.isProcessed = isProcessed;
    }

    public Invitation(int userId, int bandId){
        this(Constants.NO_ID,userId,bandId,false);
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

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
}
