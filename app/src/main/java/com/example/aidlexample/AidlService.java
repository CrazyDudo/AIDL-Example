package com.example.aidlexample;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import com.example.aidlexample.entity.RequestEntity;
import com.example.aidlexample.entity.ResponseEntity;
import com.example.aidlexample.listener.IResultListener;

public class AidlService extends Service {

    private static final String TAG = "AidlService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind====");
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind====");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate====");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy====");
        super.onDestroy();
    }

    private class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "basicTypes: server received :" + aString);

        }

        @Override
        public void objectTypes(RequestEntity requestEntity) throws RemoteException {

            Log.d(TAG, "objectTypes:requestEntity.getRequestMsg=== " + requestEntity.getRequestMsg());
        }

        @Override
        public void callbackTypes(IResultListener listener) throws RemoteException {
            ResponseEntity entity = new ResponseEntity();
            entity.setResultCode(0);
            entity.setResultMsg("Result OK");
            listener.onResult(entity);
        }

    }

}


