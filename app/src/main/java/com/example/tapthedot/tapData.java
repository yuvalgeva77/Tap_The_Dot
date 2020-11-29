package com.example.tapthedot;


import android.os.Parcel;
import android.os.Parcelable;

public class tapData implements Parcelable {
    int location;
    int time;
    boolean success;

    public tapData(int location, int time, boolean success) {
        this.location = location;
        this.time = time;
        this.success = success;
    }

    protected tapData(Parcel in) {
        location = in.readInt();
        time = in.readInt();
        success = in.readByte() != 0;
    }

    public static final Creator<tapData> CREATOR = new Creator<tapData>() {
        @Override
        public tapData createFromParcel(Parcel in) {
            return new tapData(in);
        }

        @Override
        public tapData[] newArray(int size) {
            return new tapData[size];
        }
    };

    @Override
    public String toString() {
        return
                "location=" + location +
                ", time=" + time +
                ", success=" + success
               ;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(location);
        dest.writeInt(time);
        dest.writeByte((byte) (success ? 1 : 0));
    }

}
