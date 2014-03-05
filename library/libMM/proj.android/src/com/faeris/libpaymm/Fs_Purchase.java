package com.faeris.libpaymm;
import java.util.HashMap;

import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import android.content.Context;
import android.util.Log;

import com.faeris.lib.Fs_Application;

public class Fs_Purchase {
	
	private static Fs_onPurchaseListener m_listener;
	private static Purchase m_purchase;
	private static Context m_context;
	private static int m_orderId=0;
	
	private static HashMap<String,Integer> m_olderSet=new HashMap<String,Integer>();
	
	
	public static void MapID(String key,Integer value)
	{
		m_olderSet.put(key,value);
	}
	public static Integer GetID(String key)
	{
		return m_olderSet.get(key);
	}
	public static void unMapID(String key)
	{

		m_olderSet.remove(key);
	}
	
	
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
		m_orderId=m_orderId+1;
		final int older_id=m_orderId;
		
		Fs_Application.runUiThread(new Runnable(){
			public void run(){
				String trace_id="";
				
				try{
					
					trace_id=m_purchase.order(m_context, paycode, 1, m_listener);
					Fs_Purchase.MapID(trace_id,older_id);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		});
		
		Integer ret=older_id;
		return ret.toString();
	}
}
