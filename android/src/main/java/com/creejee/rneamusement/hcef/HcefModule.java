package com.creejee.rneamusement.hcef;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.NfcFCardEmulation;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.util.Map;
import java.util.HashMap;

public class HcefModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    NfcAdapter nfcAdapter = null;
    NfcFCardEmulation nfcFCardEmulation = null;
    ComponentName componentName = null;
    Boolean isHceFEnabled = false;
    Boolean isHceFSupport = false;

    public HcefModule(ReactApplicationContext context) {
        super(context);
        context.addLifecycleEventListener(this);

        // HCE-F Feature Check
        PackageManager manager = context.getPackageManager();
        if(!manager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF)){
            return ;
        }
        isHceFSupport = true;

        nfcAdapter = NfcAdapter.getDefaultAdapter(getReactApplicationContext());
        if(nfcAdapter != null){
            nfcFCardEmulation = NfcFCardEmulation.getInstance(nfcAdapter);
            componentName = new ComponentName("tk.nulldori.eamemu","tk.nulldori.eamemu.eAMEMuService");
            if(nfcFCardEmulation != null){
                nfcFCardEmulation.registerSystemCodeForService(componentName, "4000");
                isHceFEnabled = true;
            }
        }
    }

    @NonNull
    @Override
    public String getName(){
        return "EamusementHcef";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("support", isHceFSupport);
        constants.put("enabled", isHceFEnabled);
        return constants;
    }

    @ReactMethod
    void setSID(String sid, Promise promise){
        if(nfcFCardEmulation == null || componentName == null){
            promise.reject("NULL_ERROR", "nfcFCardEmulation or componentName is null");
        }

        sid = sid.toUpperCase();

        if(sid.length() != 16)
            promise.reject("LENGTH_ERROR", "The length of sid must be 16");
        if(!sid.matches("[0-9a-fA-F]+"))
            promise.reject("HEX_ERROR", "SID must be 16-digit hex number");
        if(!sid.substring(0, 4).contentEquals("02FE"))
            promise.reject("PREFIX_ERROR", "SID must be start with 02FE");


        promise.resolve(nfcFCardEmulation.setNfcid2ForService(componentName, sid));
    }

    @ReactMethod
    void enableService(Promise promise){
        if(nfcFCardEmulation == null || componentName == null){
            promise.reject("NULL_ERROR", "nfcFCardEmulation or componentName is null");
        }

        String cardId = nfcFCardEmulation.getNfcid2ForService(componentName);

        if(cardId.length() != 16)
            promise.reject("LENGTH_ERROR", "The length of sid must be 16");
        if(!cardId.matches("[0-9a-fA-F]+"))
            promise.reject("HEX_ERROR", "SID must be 16-digit hex number");
        if(!cardId.substring(0, 4).contentEquals("02FE"))
            promise.reject("PREFIX_ERROR", "SID must be start with 02FE");

        // Toast.makeText(getReactApplicationContext(), "Card Emulation Enabled!", Toast.LENGTH_SHORT).show();
        promise.resolve(nfcFCardEmulation.enableService(getCurrentActivity(), componentName));
    }

    @ReactMethod
    void disableService(Promise promise){
        if(nfcFCardEmulation == null || componentName == null){
            promise.reject("NULL_ERROR", "nfcFCardEmulation or componentName is null");
        }
        // Toast.makeText(getReactApplicationContext(), "Card Emulation Disabled...", Toast.LENGTH_SHORT).show();
        promise.resolve(nfcFCardEmulation.disableService(getCurrentActivity()));
    }

    @Override
    public void onHostResume(){
        if(nfcFCardEmulation != null && componentName != null){
            Log.d("MainActivity onResume()", "enabled!");
            nfcFCardEmulation.enableService(getCurrentActivity(), componentName);
        }
    }

    @Override
    public void onHostPause(){
        if(nfcFCardEmulation != null && componentName != null){
            Log.d("MainActivity onPause()", "disabled...");
            nfcFCardEmulation.disableService(getCurrentActivity());
        }
    }

    @Override
    public void onHostDestroy(){

    }
}