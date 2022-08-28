package com.creejee.rneamusement.cardConvert;


import androidx.annotation.NonNull;

import com.creejee.rneamusement.konamiCardX.A;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;


public class CardConvertModule extends ReactContextBaseJavaModule {
    private static final A converter = new A();


    public CardConvertModule(ReactApplicationContext context){
        super(context);
    }

    @NonNull
    @Override
    public String getName(){
        return "EamusementCardConvert";
    }

    @ReactMethod
    void convertKonamiID(String sid, Promise promise){
        try {
            promise.resolve(converter.toKonamiID(sid));
        } catch(RuntimeException e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    void convertNfcID(String sid, Promise promise){
        try {
            promise.resolve(converter.toUID(sid));
        } catch(RuntimeException e) {
            promise.reject(e);
        }
    }
}