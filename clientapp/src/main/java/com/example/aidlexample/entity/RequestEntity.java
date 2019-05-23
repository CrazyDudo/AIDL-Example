package com.example.aidlexample.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestEntity implements Parcelable {
    String requestMsg;
    String requestCode;

    public String getRequestMsg() {
        return requestMsg;
    }

    public RequestEntity() {
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    protected RequestEntity(Parcel in) {
        requestMsg = in.readString();
        requestCode = in.readString();
    }

    public static final Creator<RequestEntity> CREATOR = new Creator<RequestEntity>() {
        @Override
        public RequestEntity createFromParcel(Parcel in) {
            return new RequestEntity(in);
        }

        @Override
        public RequestEntity[] newArray(int size) {
            return new RequestEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(requestMsg);
        dest.writeString(requestCode);
    }

}
