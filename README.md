# react-native-eamusement

## 서문

우선 좋은 오픈소스를 개발해준 @juchan1220 님에게 감사드립니다.
그리고 이 페키지는 아래의 링크를 최신버전으로 업데이트 및 누락된 소스를 추가시킨 버전입니다.
> https://github.com/juchan1220/eAMEMu_RN

A,B,E class및 convertSID관련 참조 문서
> https://github.com/skogaby/butterfly/blob/469b79a9858f2153ab9e51b8b2754b2d3d8ff94e/butterflycore/src/main/java/com/buttongames/butterflycore/cardconv/

## Getting started

`$ npm install react-native-eamusement --save`

## Postinstall process
### xml/aid_list.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<host-nfcf-service xmlns:android="http://schemas.android.com/apk/res/android">
    <system-code-filter android:name="4000" />
    <nfcid2-filter android:name="null" />
</host-nfcf-service>

```
### manifest.xml
```xml
    <uses-feature android:name="android.hardware.nfc.hcef" android:required="true"/>
    <uses-permission android:name="android.permission.NFC"/>
    <application ...>
        <service android:name=".com.creejee.rneamusement.RNEamusementService"
        android:exported="true"
        android:enabled="false"
        android:permission="android.permission.BIND_NFC_SERVICE">
            <intent-filter>
                <action android:name="android.nfc.cardemulation.action.HOST_NFCF_SERVICE"/>
            </intent-filter>
            <meta-data
                android:name="android.nfc.cardemulation.host_nfcf_service"
                android:resource="@xml/aid_list" />
        </service>
    </application>
```

### Mostly automatic installation

`$ react-native link react-native-eamusement`

## Usage
```javascript
import {EamusementHcef, EamusementCardConvert} from 'react-native-eamusement';

```
