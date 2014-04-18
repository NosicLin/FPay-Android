package com.faeris.libpaymentunicomsingle;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

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

	private class PaycodeInfo{
		/* attribute */
		public boolean isOtherpay;
		public boolean isSms;
		public String callBackUrl;
		public String vacCode;
		public String curstomCode;
		public String props;
		public String money;
		
		/* method */
		public PaycodeInfo(JSONObject json) throws JSONException
		{
			isOtherpay=json.getBoolean("isOtherpay");
			isSms=json.getBoolean("isSms");
			callBackUrl=json.getString("callBackUrl");
			vacCode=json.getString("vacCode");
			curstomCode=json.getString("curstomCode");
			props=json.getString("props");
			money=json.getString("money");
		}
	};
	
	
	/* attribute */
	private int m_billingCount=0;
	private HashMap<String,PaycodeInfo> m_paycodes=null;
	
	
	/* method */
	public Fs_PaymentUnicomSingle()
	{
		m_paycodes=new HashMap<String,PaycodeInfo>();
	}
	
	
	@Override
	public void setConfig(Context context, String config) {
		try {
			JSONObject json = new JSONObject(config);
			String app_id=json.getString("appId");
			String cp_code=json.getString("cpCode");
			String cp_id=json.getString("cpId");
			String company=json.getString("company");
			String phone=json.getString("phone");
			String game=json.getString("game");
			String uid=json.getString("uid");
			
			HashMap<String,PaycodeInfo> paycodes=new HashMap<String,PaycodeInfo>();
			
			JSONObject json_paycodes=json.getJSONObject("paycodes");
			Iterator iter=json_paycodes.keys();
			while(iter.hasNext())
			{
				String key=(String)iter.next();
				JSONObject value=json_paycodes.getJSONObject(key);
				PaycodeInfo paycode_info=new PaycodeInfo(value);
				paycodes.put(key, paycode_info);
			}
			
			Utils.getInstances().init(context,app_id,cp_code,cp_id,company,phone,game,uid, new UnipayPayResultListener(){
				@Override
				public void PayResult(String arg0, int arg1, String arg2) {
					// TODO Auto-generated method stub
					
				}

			});
			
			this.m_paycodes=paycodes;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override 
	public boolean hasPaycode(String name)
	{
		PaycodeInfo paycode=this.m_paycodes.get(name);
		if(paycode!=null)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {
		
		try {
			
			JSONObject json = new JSONObject(msg);
			String older_id=null;
			
			try{
				older_id=json.getString("orderId");
			}
			
			catch (JSONException e)
			{
				Format f = new SimpleDateFormat("yyyyMMddHHmmss");
				m_billingCount++;
				String count=""+m_billingCount+"0000000000";
				older_id=f.format(new Date())+count.substring(0,10);
			}
			
			PaycodeInfo paycode=this.m_paycodes.get(name);
			/* paycode not find */
			if(paycode == null)
			{
				Fs_Application.runUiThread(new Runnable(){

					@Override
					public void run() {
						String msg="{\"msg\":\"计费点未找到\"}";
						listener.payResult(Fs_Payment.ERROR, msg);
						// TODO Auto-generated method stub
					}
				});
				return ;
			}
			
			
			Utils.getInstances().setBaseInfo(context,paycode.isOtherpay,paycode.isSms,paycode.callBackUrl);
			
			Utils.getInstances().pay(context,paycode.vacCode,paycode.curstomCode,paycode.props,paycode.money,older_id,
					new UnipayPayResultListener(){
								@Override
								public void PayResult(String pay_code,int flag1, String error) {
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



	

