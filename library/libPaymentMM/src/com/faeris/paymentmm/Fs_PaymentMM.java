package com.faeris.paymentmm;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import mm.purchasesdk.Purchase;
import android.content.Context;

import com.faeris.lib.Fs_Application;
import com.faeris.libext.Fs_Payment;
import com.faeris.libext.Fs_PaymentImp;
import com.faeris.libext.Fs_PaymentListener;

public class Fs_PaymentMM implements Fs_PaymentImp{
	
	private class PaycodeInfo
	{
		/* atrribute */
		public String code;
		public String extdata;
		public int value;
		
		/* method */
		public PaycodeInfo(JSONObject json) throws JSONException
		{
			code=json.getString("code");
			
			
			try {
				extdata=json.getString("extdata");
			}
			catch(JSONException e)
			{
				extdata="";
			}
			
			value=json.getInt("value");
		}
	}
	
	/* attribute */
	HashMap<String,PaycodeInfo> m_paycodes;
	

	public Fs_PaymentMM(Context context,String appid,String appkey)
	{
		m_paycodes=new HashMap<String,PaycodeInfo>();
	}
	
	@Override
	public void setConfig(Context context, String config) {
		
	
		try {
			
			JSONObject json=new JSONObject(config);
			String appid=json.getString("appid");
			String appkey=json.getString("appkey");
			
			JSONObject json_paycodes=json.getJSONObject("paycodes");
			
			Iterator iter=json_paycodes.keys();
			
			HashMap<String,PaycodeInfo> paycodes=new HashMap<String,PaycodeInfo>();
			while(iter.hasNext())
			{
				String key=(String)iter.next();
				JSONObject value=json_paycodes.getJSONObject(key);
				PaycodeInfo paycode=new PaycodeInfo(value);
				
				paycodes.put(key, paycode);
			}
			
				
			Purchase.getInstance().setAppInfo(appid, appkey);
			Purchase.getInstance().init(context, new Fs_PaymentMMListener());
			m_paycodes=paycodes;	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {	
		
		PaycodeInfo paycode=m_paycodes.get(name);
		if(paycode==null)
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
		
		try {
			JSONObject json=new JSONObject(msg);
			
			String extdata=null;
			try{
				extdata=json.getString("extdata");
			}
			catch(JSONException e)
			{
				extdata=paycode.extdata;
			}
			
			Purchase.getInstance().order(context, paycode.code, paycode.value, extdata, true,new Fs_PaymentMMListener(listener));
			
		} catch (JSONException e) {
			Fs_Application.runUiThread(new Runnable(){
				@Override
				public void run() {
					String msg="{\"msg\":\"参数解析错误\"}";
					listener.payResult(Fs_Payment.ERROR, msg);
				}
			});
		}
	}

	@Override
	public boolean hasPaycode(String name) {
		PaycodeInfo paycode=m_paycodes.get(name);
		if(paycode==null)
		{
			return false;
		}
		return true;
	}

}
