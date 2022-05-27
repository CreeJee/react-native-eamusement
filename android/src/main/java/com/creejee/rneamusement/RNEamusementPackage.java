package com.creejee.rneamusement;

import androidx.annotation.NonNull;

import com.creejee.rneamusement.cardConvert.CardConvertModule;
import com.creejee.rneamusement.hcef.HcefModule;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNEamusementPackage implements ReactPackage {

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    @NonNull
    @Override
    public List<NativeModule> createNativeModules(
            @NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new CardConvertModule(reactContext));
        modules.add(new HcefModule(reactContext));

        return modules;
    }

}
