// IMyAidlInterface.aidl
package com.example.aidlexample;

import com.example.aidlexample.entity.RequestEntity;
import com.example.aidlexample.entity.ResponseEntity;
import com.example.aidlexample.listener.IResultListener;

// Declare any non-default types here with import statements

interface IMyAidlInterface {

     //basic types 基础型参数
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    //object 实体对象
    void objectTypes(in RequestEntity requestEntity);
    //callback 回调
    void callbackTypes(IResultListener listener);
}
