package com.example.tapthedot;


import android.os.Parcel;
import android.os.Parcelable;

public class tapData implements Parcelable {
    float x;
    float y;
    boolean success;

    public tapData(float x, float y, boolean success) {
        this.x = x;
        this.y = y;
        this.success = success;
    }

    protected tapData(Parcel in) {
        x = in.readInt();
        y = in.readInt();
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
                "x=" + x +
                ", y=" + y +
                ", success=" + success
               ;
    }

    public float getLocation() {
        return x;
    }

    public void setLocation(int location) {
        this.x = location;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeByte((byte) (success ? 1 : 0));
    }

}
