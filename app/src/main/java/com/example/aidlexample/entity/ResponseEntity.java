package com.example.aidlexample.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseEntity implements Parcelable {
    int resultCode;
    String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public ResponseEntity() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resultCode);
        dest.writeString(this.resultMsg);
    }

    protected ResponseEntity(Parcel in) {
        this.resultCode = in.readInt();
        this.resultMsg = in.readString();
    }

    public static final Creator<ResponseEntity> CREATOR = new Creator<ResponseEntity>() {
        @Override
        public ResponseEntity createFromParcel(Parcel source) {
            return new ResponseEntity(source);
        }

        @Override
        public ResponseEntity[] newArray(int size) {
            return new ResponseEntity[size];
        }
    };


    public void readFromParcel(Parcel reply) {

    }
}
