package com.example.apptourdulich.Domains;

public class Photo {
    private int resourceID;

    public Photo(int resourceID)
    {
        this.resourceID=resourceID;

    }
    public Photo () {

    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }
}
