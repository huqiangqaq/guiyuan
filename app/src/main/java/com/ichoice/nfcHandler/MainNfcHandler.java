package com.ichoice.nfcHandler;

import android.os.Handler;
import android.os.Message;

public class MainNfcHandler extends Handler {
	@Override
	public void handleMessage(Message msg) {
		
		super.handleMessage(msg);
		if (msg.what == 2) {
			//return (String)msg.obj;
		}
	}
}
