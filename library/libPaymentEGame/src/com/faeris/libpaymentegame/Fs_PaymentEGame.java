package com.faeris.libpaymentegame;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import cn.egame.terminal.paysdk.EgamePay;
import cn.egame.terminal.paysdk.EgamePayListener;

import com.faeris.lib.Fs_Application;
import com.faeris.libext.Fs_Payment;
import com.faeris.libext.Fs_PaymentImp;
import com.faeris.libext.Fs_PaymentListener;

public class Fs_PaymentEGame implements Fs_PaymentImp{

	@Override
	public void setConfig(Context context, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {
		try {
			JSONObject json = new JSONObject(msg);
			String pay_alias=json.getString("alias");
			
			EgamePay.pay(context, pay_alias, new EgamePayListener() {
	
				@Override
				public void payCancel(String arg0) {
					listener.payResult(Fs_Payment.CANCEL, "{\"msg\":\"支付取消\"}");
				}
	
				@Override
				public void payFailed(String arg0, int arg1) {
					listener.payResult(Fs_Payment.FAILED, "{\"msg\":\"支付失败\"}");
				}
	
				@Override
				public void paySuccess(String arg0) {
					listener.payResult(Fs_Payment.SUCCESS, "{\"msg\":\"支付成功\"");
				}
				
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
