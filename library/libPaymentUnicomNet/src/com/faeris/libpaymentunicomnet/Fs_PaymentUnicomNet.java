package com.faeris.libpaymentunicomnet;

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
import com.unicom.dcLoader.Utils.VacMode;

public class Fs_PaymentUnicomNet implements Fs_PaymentImp{
	private int m_billingCount=1;
	
	public Fs_PaymentUnicomNet(
			Context context,  
			String app_id,	 /*应用编号*/
			String cp_code,  /*开发商编号*/
			String cp_id,    /*开发商VAC资质编号*/
			String company,  /*开发者公司名字*/
			String phone,    /*开发者客服电话号码*/
			String game)	 /*应用名称*/
	{
		Utils.getInstances().init(context,app_id,cp_code,cp_id,company,phone,game,
				new UnipayPayResultListener(){
					@Override
					public void PayResult(String paycode, int flag1,int flag2, String error) {}
						
		});
	}
	
			

	@Override
	public void setConfig(Context context, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void billing(Context context, String name, String msg,final Fs_PaymentListener listener) {
		try {
			
			JSONObject json=new JSONObject(msg);
			
			boolean is_vac=json.getBoolean("isVac");
			boolean is_otherpay=json.getBoolean("isOtherpay");
			String url=json.getString("url");
			String vac_code=json.getString("vacCode");
			String custom_code=json.getString("customCode");
			String props=json.getString("props");
			String money=json.getString("money");
			
			String uid=json.getString("uid");
			String vac_mode=json.getString("vacMode");
			
			Format f = new SimpleDateFormat("yyyyMMddHHmmss");
			m_billingCount++;
			String count=""+m_billingCount+"0000000000";
			
			String order_id=f.format(new Date())+count.substring(0,10);

			VacMode mode=VacMode.single;
			if( vac_mode == "single")
			{
				mode=VacMode.single;
			}
			else if(vac_mode=="sub")
			{
				mode=VacMode.sub;
			}
			else if (vac_mode=="unsub")
			{
				mode=VacMode.unsub;
			}
			
			Utils.getInstances().setBaseInfo(context,is_vac,is_otherpay,url);
			
			Utils.getInstances().pay(context,vac_code,custom_code,props,money,order_id,uid,mode,
					new UnipayPayResultListener()
					{
							@Override
							public void PayResult(String pay_code,int flag1, int flag2, String error) {
								Log.v("Fs_UniPurchase:bill","paycode="+pay_code+" flags1="+flag1+ " flag2="+flag2+ " error="+error);
								switch(flag1)
								{
									case Utils.SUCCESS:
										listener.payResult(Fs_Payment.SUCCESS, "{\"msg\":\"支付成功\"}");
										break;
									case Utils.FAILED:
										listener.payResult(Fs_Payment.FAILED, "{\"msg\":\"支付失败\"}");
										break;
									case Utils.CANCEL:
										listener.payResult(Fs_Payment.CANCEL, "{\"msg\":\"支付失败\"}");
										break;
									default:
										listener.payResult(Fs_Payment.ERROR, "{\"msg\":\"未知错误\"}");
										break;
								}
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













