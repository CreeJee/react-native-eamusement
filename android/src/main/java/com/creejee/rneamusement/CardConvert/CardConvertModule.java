package com.creejee.rneamusement.CardConvert;


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
    void convertSID(String sid, Promise promise){
        if(sid.length() != 16 || !sid.startsWith("02FE")){
            promise.reject("SID_FORMAT_ERROR", "SID must be 16-digit hex string.");
        }

        String cardID = converter.toKonamiID(sid);

        promise.resolve(cardID);
    }
}