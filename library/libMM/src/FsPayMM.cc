#include "FsPayMM.h"
#include "tolua++.h"
#include "FsLuaEngine.h"
#include "FsGlobal.h"
#include "sys/platform/android/FsJniUtil.h"



static const char* JNI_PAY_MM_CLASS_NAME = "com/faeris/libpaymm/Fs_Purchase";



/* Exported function */
TOLUA_API int  tolua_FsPayMM_open (lua_State* tolua_S);

NS_FS_BEGIN

static PayMM* S_payMM=NULL;

const char* PayMM::className()
{
	return "PayMM";
}


PayMM* PayMM::getInstance()
{
	if(S_payMM==NULL)
	{
		S_payMM=new PayMM();
		FS_NO_REF_DESTROY(S_payMM);
	}

	return S_payMM;
}


void PayMM::purgeInstance()
{
	if(S_payMM)
	{
		FS_DESTROY(S_payMM);
		S_payMM=NULL;
	}
}

void PayMM::init(const char* appid,const char* appkey)
{
	JNIEnv* env=JniUtil::getEnv();

	jstring jappid=env->NewStringUTF(appid);
	jstring jappkey=env->NewStringUTF(appkey);

	FS_JNI_CALL_VOID_STATIC_METHOD(JNI_PAY_MM_CLASS_NAME,"init",
				"(Ljava/lang/String;Ljava/lang/String;)V",
				jappid,jappkey);

	env->DeleteLocalRef(jappid);
	env->DeleteLocalRef(jappkey);
}

std::string PayMM::order(const char* paycode)
{
	JNIEnv* env=JniUtil::getEnv();

	jstring jpaycode=env->NewStringUTF(paycode);
	jobject ob_ret;

	FS_JNI_CALL_STATIC_METHOD(JNI_PAY_MM_CLASS_NAME,"order",
				"(Ljava/lang/String;)Ljava/lang/String;",
				Object,
				ob_ret,
				jpaycode
				);

	jstring j_str=(jstring) ob_ret;

	const char* retcode=env->GetStringUTFChars(j_str,NULL);
	std::string s_str(retcode);

	env->ReleaseStringUTFChars(j_str,retcode);
	env->DeleteLocalRef(jpaycode);

	return s_str;

}

void PayMM::billingFinish(int status,const std::string& id)
{

}


void PayMM::initFinish(int status)
{

}

PayMM::PayMM()
{
}

PayMM::~PayMM()
{

}




void Fs_PayMM_Moduel_Init()
{
	LuaEngine* engine=(LuaEngine*)Global::scriptEngine();
	struct lua_State* st=engine->getLuaState();
	tolua_FsPayMM_open(st);
}


void Fs_PayMM_Moduel_Exit()
{

}




NS_FS_END

