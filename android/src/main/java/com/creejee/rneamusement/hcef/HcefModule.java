package com.creejee.rneamusement.hcef;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.NfcFCardEmulation;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.util.Map;
import java.util.HashMap;

import com.creejee.rneamusement.RNEamusementService;
import com.creejee.rneamusement.util.Logging;

public class HcefModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    NfcAdapter nfcAdapter = null;
    NfcFCardEmulation nfcFCardEmulation = null;
    ComponentName componentName = null;

    Boolean isHceFEnabled = false;
    Boolean isHceFSupport = false;


    private boolean safeServiceEnable() {
        Activity activity = getCurrentActivity();
        if(nfcFCardEmulation != null && componentName != null){
            return nfcFCardEmulation.enableService(activity, componentName);
        }
        return false;
    }

    private boolean safeServiceDisable() {
        Activity activity = getCurrentActivity();
        if(nfcFCardEmulation != null && componentName != null){
            return nfcFCardEmulation.disableService(activity);
        }
        return false;
    }

    public HcefModule(ReactApplicationContext context) {
        super(context);
        context.addLifecycleEventListener(this);

        // HCE-F Feature Check
        PackageManager packageManager = context.getPackageManager();
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF)){
            return ;
        }
        isHceFSupport = true;

        // HCE-F Enable
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if(nfcAdapter != null){
            nfcFCardEmulation = NfcFCardEmulation.getInstance(nfcAdapter);
            componentName = new ComponentName(context, RNEamusementService.class);
            if(nfcFCardEmulation != null){
                nfcFCardEmulation.registerSystemCodeForService(componentName, "4000");
                isHceFEnabled = true;
            }
        }
        // Flag Service

        if(packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
            packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER ) {
            Logging.d("Service disabled, so we will force flag service");
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            );
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
        constants.put("HCEF_FATAL_ERROR", HcefErrorCode.FATAL_ERROR);
        constants.put("HCEF_UID_HEX_ERROR", HcefErrorCode.UID_HEX_ERROR);
        constants.put("HCEF_UID_LENGTH_ERROR", HcefErrorCode.UID_LENGTH_ERROR);
        constants.put("HCEF_UID_PREFIX_ERROR", HcefErrorCode.UID_PREFIX_ERROR);
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
            promise.reject(HcefErrorCode.FATAL_ERROR, "nfcFCardEmulation or componentName is null");
        }

        String cardId = nfcFCardEmulation.getNfcid2ForService(componentName);
        if(cardId.length() != 16)
            promise.reject(HcefErrorCode.UID_LENGTH_ERROR, "The length of sid must be 16");
        if(!cardId.matches("[0-9a-fA-F]+"))
            promise.reject(HcefErrorCode.UID_HEX_ERROR, "SID must be 16-digit hex number");
        if(!cardId.substring(0, 4).contentEquals("02FE"))
            promise.reject(HcefErrorCode.UID_PREFIX_ERROR, "SID must be start with 02FE");

        promise.resolve(safeServiceEnable());
    }

    @ReactMethod
    void disableService(Promise promise){
        if(nfcFCardEmulation == null || componentName == null){
            promise.reject(HcefErrorCode.FATAL_ERROR, "nfcFCardEmulation or componentName is null");
        }
        promise.resolve(safeServiceDisable());
    }

    @Override
    public void onHostResume(){


        Logging.d("MainActivity onResume()");
        safeServiceEnable();
    }

    @Override
    public void onHostPause(){
        Logging.d("MainActivity onPause()");
        safeServiceDisable();
    }

    @Override
    public void onHostDestroy(){
        Logging.d("MainActivity onHostDestroy()");
        safeServiceDisable();
    }
}