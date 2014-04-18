package com.faeris.libpaymentunicomsingle;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.faeris.lib.Fs_Application;
import com.faeris.libext.Fs_Payment;
import com.faeris.libext.Fs_PaymentImp;
import com.faeris.libext.Fs_PaymentListener;

import com.unicom.dcLoader.Utils;
import com.unicom.dcLoader.Utils.UnipayPayResultListener;

public class Fs_PaymentUnicomSingle implements Fs_PaymentImp{

	private int m_billingCount=0;
	public Fs_PaymentUnicomSingle(
			Context context,
			String app_id,
			String cp_code,
			String cp_id,
			String company,
			String phone,
			String game,
			String uid)
	{
		Utils.getInstances().init(context,app_id,cp_code,cp_id,company,phone,game,uid, new UnipayPayResultListener(){
			@Override
			public void PayResult(String arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				
			}

		});
	}
	
	
	@Override
	public void setConfig(Context context, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {
		
		try {
			JSONObject json = new JSONObject(msg);
		
			boolean is_otherpay=json.getBoolean("isOtherpay");
			boolean is_sms=json.getBoolean("isSms");
			String url=json.getString("url");
			String vac_code=json.getString("vacCode");
			String custom_code=json.getString("customCode");
			String props=json.getString("props");
			String money=json.getString("money");
	
			Format f = new SimpleDateFormat("yyyyMMddHHmmss");
			m_billingCount++;
			String count=""+m_billingCount+"0000000000";
			
			String order_id=f.format(new Date())+count.substring(0,10);
			
			Utils.getInstances().setBaseInfo(Fs_Application.getContext(),is_otherpay,is_sms,url);
			
			Utils.getInstances().pay(Fs_Application.getContext(),vac_code,custom_code,props,money,order_id,
					new UnipayPayResultListener(){
								@Override
								public void PayResult(String pay_code,
										int flag1, String error) {
									Log.v("Fs_UniPurchase:bill","paycode="+pay_code+" flags1="+flag1+  " error="+error);
									switch(flag1)
									{
										case Utils.SUCCESS_SMS:
										case Utils.SUCCESS_3RDPAY:
											listener.payResult(Fs_Payment.SUCCESS,"{\"msg\":\"支付成功\"}");
											break;
										case Utils.FAILED:
											listener.payResult(Fs_Payment.FAILED, "{\"msg\":\"支付失败\"}");
											break;
										case Utils.CANCEL:
											listener.payResult(Fs_Payment.CANCEL, "{\"msg\":\"支付取消\"}");
											break;
										default:
											listener.payResult(Fs_Payment.ERROR, "{\"msg\":\""+error+"\"}");
											break;
									}

								}
					});
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
	

