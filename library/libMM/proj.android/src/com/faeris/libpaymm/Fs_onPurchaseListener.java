package com.faeris.libpaymm;
import com.faeris.lib.Fs_Application;
import java.util.HashMap;

import android.content.Context;
import android.os.Message;
import android.util.Log;


import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import mm.purchasesdk.PurchaseCode;

public class Fs_onPurchaseListener  implements OnPurchaseListener {
	public Fs_onPurchaseListener(Context context) {
		
	}

	@Override
	public void onAfterApply() {

	}

	@Override
	public void onAfterDownload() {

	}

	@Override
	public void onBeforeApply() {

	}

	@Override
	public void onBeforeDownload() {

	}

	@Override
	public void onInitFinish(int code) {
		
		final int code_id=code;
		Fs_Application.runOnEngineThread(new Runnable(){
			public void run(){
				Fs_libpayMMJni.onInitFinish(code_id);
			}
		});
	}

	@Override
	public void onBillingFinish(int code, HashMap arg) {
		int result_code=0;
		String trace_key= "";
		Integer older_id=0;
		
		if (arg!=null)
		{
			
			trace_key = (String) arg.get(OnPurchaseListener.TRADEID);
			older_id=Fs_Purchase.GetID(trace_key);
			Fs_Purchase.unMapID(trace_key);
		}
		
		if (code == PurchaseCode.ORDER_OK || (code == PurchaseCode.AUTH_OK)) {
			result_code=1;
		}
	
		
		final String f_older_id=older_id.toString();
		final int f_code=result_code;
		
		Fs_Application.runOnEngineThread(new Runnable(){
			public void run(){
				Fs_libpayMMJni.onBillingFinish(f_code,  f_older_id);
			}
		});
	}

	
	@Override
	public void onQueryFinish(int code, HashMap arg) {
		
	}

	@Override
	public void onUnsubscribeFinish(int code) {
	
	}

}
