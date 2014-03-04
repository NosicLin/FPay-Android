package com.faeris.libpaymm;
import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import android.content.Context;
import com.faeris.lib.Fs_Application;

public class Fs_Purchase {
	
	private static Fs_onPurchaseListener m_listener;
	private static Purchase m_purchase;
	private static Context m_context;
	
	public static void init(final String appid,final String appkey)	{
		
		Fs_Application.runUiThread(new Runnable(){
			public void run(){
				m_purchase=Purchase.getInstance();
				m_context=Fs_Application.getContext();
				m_listener=new Fs_onPurchaseListener(m_context);
				
				// set app info 
				try {
					
					m_purchase.setAppInfo(appid, appkey);
		
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
				
				// init purchase 
				try {
					
					m_purchase.init(m_context, m_listener);
		
				} catch (Exception e) {
					
					e.printStackTrace();
					return;
				}
			}
		});
	}
	
	public static String order(final String paycode)
	{
		Fs_Application.runUiThread(new Runnable(){
			public void run(){
				String order_id="";
				
				try{
					order_id=m_purchase.order(m_context, paycode, 1, m_listener);
				}catch (Exception e) {
					e.printStackTrace();
				}
				//return order_id;
			}
		
		});
		return "";
	}
}
