// IResultListener.aidl
package com.example.aidlexample.listener;
import com.example.aidlexample.entity.ResponseEntity;
// Declare any non-default types here with import statements

interface IResultListener {
    void onResult(inout ResponseEntity responseEntity );
}