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
		String orderID = "";
		if (code == PurchaseCode.ORDER_OK || (code == PurchaseCode.AUTH_OK)) {
			if (arg!=null)
			{
				result_code=1;
				orderID = (String) arg.get(OnPurchaseListener.ORDERID);
			}
		}
		
		final String order_id=orderID;
		final int f_code=result_code;
		
		Fs_Application.runOnEngineThread(new Runnable(){
			public void run(){
				Fs_libpayMMJni.onBillingFinish(f_code,  order_id);
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
