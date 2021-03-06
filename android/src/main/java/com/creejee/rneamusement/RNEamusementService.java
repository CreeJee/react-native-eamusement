package com.creejee.rneamusement;

import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.widget.Toast;
public class RNEamusementService extends HostNfcFService {
    @Override
    public byte[] processNfcFPacket(byte[] commandPacket, Bundle extras){
        if(commandPacket.length < 1 + 1 + 8){
            Toast.makeText(this.getApplicationContext(),  "ProcessPacket: " + "too short packet!", Toast.LENGTH_SHORT).show();
            return null;
        }

        byte[] nfcId = new byte[8];
        System.arraycopy(commandPacket, 2, nfcId, 0, 8);
        StringBuilder res = new StringBuilder();

        for(int i= 0;i<8;i++){
            res.append(Integer.toHexString(nfcId[i] & 0xFF));
        }

        Toast.makeText(this.getApplicationContext(),  "ProcessPacket: " + res, Toast.LENGTH_SHORT).show();

        if(commandPacket[1] == (byte)0x04){
            byte[] resp = new byte[1 + 1 + 8 + 1];
            resp[0] = (byte)11;
            resp[1] = (byte)0x05;
            System.arraycopy(nfcId, 0, resp, 2, 8);
            resp[10] = (byte)0;
            return resp;
        }
        else{
            return null;
        }
    }

    @Override
    public void onDeactivated(int reason)
    {

    }
}
