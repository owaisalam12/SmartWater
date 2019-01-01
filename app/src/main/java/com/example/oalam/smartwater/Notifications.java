package com.example.oalam.smartwater;


public class Notifications {

    private String mNotificationTitle;
    private String mNotificationText;
    private String mNotificationTime;
//    private int mImageResourceID=imageCheck;
//  //  private int mAudioResourceID;
//    private static final int imageCheck=-1;

    public Notifications(String mNotificationTitle, String mNotificationText, String mNotificationTime) {
        this.mNotificationTitle = mNotificationTitle;
        this.mNotificationText = mNotificationText;
        this.mNotificationTime = mNotificationTime;
    }

//    public Notifications(String mNotificationTitle, String mNotificationText, int mImageResourceID) {
//        this.mNotificationTitle = mNotificationTitle;
//        this.mNotificationText = mNotificationText;
//        this.mImageResourceID = mImageResourceID;
//    }

    public String getmNotificationTitle() {
        return mNotificationTitle;
    }

    public String getmNotificationTime() {
        return mNotificationTime;
    }

    public String getmNotificationText() {
        return mNotificationText;
    }

//    public int getmImageResourceID() {
//        return mImageResourceID;
//    }
//    public boolean hasImage(){
//        return mImageResourceID !=imageCheck;
//    }


}
