package com.faeris.paymentmm;

import java.util.HashMap;

import com.faeris.libext.Fs_Payment;
import com.faeris.libext.Fs_PaymentListener;

import mm.purchasesdk.OnPurchaseListener;
import mm.purchasesdk.Purchase;
import mm.purchasesdk.PurchaseCode;

public class Fs_PaymentMMListener implements OnPurchaseListener{
	
	private Fs_PaymentListener m_listener;
	
	public Fs_PaymentMMListener(Fs_PaymentListener listener)
	{
		m_listener=listener;
	}
	
	
	public Fs_PaymentMMListener() {
		m_listener=null;
	}


	@Override
	public void onAfterApply() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterDownload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeApply() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeDownload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBillingFinish(int code, HashMap arg1) {

		// 此次订购的orderID
		String orderID = null;
		// 商品的paycode
		String paycode = null;
		
		// 商品的有效期(仅租赁类型商品有效)
		String leftday = null;
		
		// 商品的交易 ID，用户可以根据这个交易ID，查询商品是否已经交易
		String tradeID = null;
		
		String ordertype = null;
		
		
		if (code == PurchaseCode.ORDER_OK || (code == PurchaseCode.AUTH_OK)) {
			m_listener.payResult(Fs_Payment.SUCCESS, "{\"msg\":\"购买成功\"}");
		}
		else if(code==PurchaseCode.BILL_CANCEL_FAIL)
		{
			m_listener.payResult(Fs_Payment.CANCEL, "{\"msg\":\"支付取消\"}");
		}
		else 
		{
			m_listener.payResult(Fs_Payment.FAILED, "{\"msg\":\""+code+"\"}");
		}
	}
	

	@Override
	public void onInitFinish(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onQueryFinish(int arg0, HashMap arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribeFinish(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
