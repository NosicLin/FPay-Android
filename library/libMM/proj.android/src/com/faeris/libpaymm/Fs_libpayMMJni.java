package com.faeris.libpaymm;

public class Fs_libpayMMJni {
	
	public static native void  onBillingFinish(int code, String order_id);
	public static native void  onInitFinish(int code);
	public static native void  onMuduleInit();
}


