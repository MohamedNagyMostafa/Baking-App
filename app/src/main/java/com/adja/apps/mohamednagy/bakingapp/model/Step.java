package com.adja.apps.mohamednagy.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class Step {
    @SerializedName("id")
    private int mId;
    @SerializedName("shortDescription")
    private String mShortDescription;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("videoURL")
    private String mVideoLink;
    @SerializedName("thumbnailURL")
    private String mThumbnailURL;

    public Step(){}
    public Step(int id, String shortDescription, String description, String videoLink, String thumbnailURL){
        mId               = id;
        mShortDescription = shortDescription;
        mDescription      = description;
        mVideoLink        = videoLink;
        mThumbnailURL     = thumbnailURL;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setVideoLink(String mVideoLink) {
        this.mVideoLink = mVideoLink;
    }

    public void setThumbnailURL(String mThumbnailURL) {
        this.mThumbnailURL = mThumbnailURL;
    }

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoLink() {
        return mVideoLink;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }
}
