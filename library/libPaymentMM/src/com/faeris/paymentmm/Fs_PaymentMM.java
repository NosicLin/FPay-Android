package com.faeris.paymentmm;

import org.json.JSONException;
import org.json.JSONObject;

import mm.purchasesdk.Purchase;
import android.content.Context;

import com.faeris.lib.Fs_Application;
import com.faeris.libext.Fs_Payment;
import com.faeris.libext.Fs_PaymentImp;
import com.faeris.libext.Fs_PaymentListener;

public class Fs_PaymentMM implements Fs_PaymentImp{

	public Fs_PaymentMM(Context context,String appid,String appkey)
	{
		Purchase.getInstance().setAppInfo(appid, appkey);
		Purchase.getInstance().init(context, new Fs_PaymentMMListener());
	}
	
	@Override
	public void setConfig(Context context, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {	
		try {
			JSONObject json=new JSONObject(msg);
			String paycode=json.getString("code");
			int value=json.getInt("value");
			String extdata=json.getString("extdata");
			Purchase.getInstance().order(context, paycode, value, extdata, true,new Fs_PaymentMMListener(listener));
			
		} catch (JSONException e) {
			Fs_Application.runUiThread(new Runnable(){
				@Override
				public void run() {
					String msg="{\"msg\":\"参数解析错误\"}";
					listener.payResult(Fs_Payment.ERROR, msg);
					// TODO Auto-generated method stub
				}
			});
		}
	
	}

}
