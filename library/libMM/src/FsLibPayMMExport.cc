/*
** Lua binding: FsPayMM
** Generated automatically by tolua++-1.0.92 on 03/04/14 23:45:24.
*/

#ifndef __cplusplus
#include "stdlib.h"
#endif
#include "string.h"

#include "tolua++.h"

/* Exported function */
TOLUA_API int  tolua_FsPayMM_open (lua_State* tolua_S);

#include "FsLibPayMMExport.h"

/* function to register type */
static void tolua_reg_types (lua_State* tolua_S)
{
 toluaext_usertype(tolua_S,"PayMM");
 tolua_usertype(tolua_S,"FsObject");
}

/* method: getInstance of class  PayMM */
#ifndef TOLUA_DISABLE_tolua_FsPayMM_PayMM_getInstance00
static int tolua_FsPayMM_PayMM_getInstance00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isusertable(tolua_S,1,"PayMM",0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,2,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   PayMM* tolua_ret = (PayMM*)  PayMM::getInstance();
    toluaext_pushfsobject2(tolua_S,(void*)tolua_ret,"PayMM");
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'getInstance'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* method: purgeInstance of class  PayMM */
#ifndef TOLUA_DISABLE_tolua_FsPayMM_PayMM_purgeInstance00
static int tolua_FsPayMM_PayMM_purgeInstance00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isusertable(tolua_S,1,"PayMM",0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,2,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  {
   PayMM::purgeInstance();
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'purgeInstance'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* method: init of class  PayMM */
#ifndef TOLUA_DISABLE_tolua_FsPayMM_PayMM_init00
static int tolua_FsPayMM_PayMM_init00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isusertype(tolua_S,1,"PayMM",0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isstring(tolua_S,3,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,4,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  PayMM* self = (PayMM*)  tolua_tousertype(tolua_S,1,0);
  const char* appid = ((const char*)  tolua_tostring(tolua_S,2,0));
  const char* appkey = ((const char*)  tolua_tostring(tolua_S,3,0));
#ifndef TOLUA_RELEASE
  if (!self) tolua_error(tolua_S,"invalid 'self' in function 'init'", NULL);
#endif
  {
   self->init(appid,appkey);
  }
 }
 return 0;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'init'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* method: order of class  PayMM */
#ifndef TOLUA_DISABLE_tolua_FsPayMM_PayMM_order00
static int tolua_FsPayMM_PayMM_order00(lua_State* tolua_S)
{
#ifndef TOLUA_RELEASE
 tolua_Error tolua_err;
 if (
     !tolua_isusertype(tolua_S,1,"PayMM",0,&tolua_err) ||
     !tolua_isstring(tolua_S,2,0,&tolua_err) ||
     !tolua_isnoobj(tolua_S,3,&tolua_err)
 )
  goto tolua_lerror;
 else
#endif
 {
  PayMM* self = (PayMM*)  tolua_tousertype(tolua_S,1,0);
  const char* paycode = ((const char*)  tolua_tostring(tolua_S,2,0));
#ifndef TOLUA_RELEASE
  if (!self) tolua_error(tolua_S,"invalid 'self' in function 'order'", NULL);
#endif
  {
   std::string tolua_ret = (std::string)  self->order(paycode);
   tolua_pushcppstring(tolua_S,(const char*)tolua_ret);
  }
 }
 return 1;
#ifndef TOLUA_RELEASE
 tolua_lerror:
 tolua_error(tolua_S,"#ferror in function 'order'.",&tolua_err);
 return 0;
#endif
}
#endif //#ifndef TOLUA_DISABLE

/* Open function */
TOLUA_API int tolua_FsPayMM_open (lua_State* tolua_S)
{
 tolua_open(tolua_S);
 tolua_reg_types(tolua_S);
 tolua_module(tolua_S,NULL,0);
 tolua_beginmodule(tolua_S,NULL);
  tolua_cclass(tolua_S,"PayMM","PayMM","FsObject",toluaext_fscollector);
  tolua_beginmodule(tolua_S,"PayMM");
   tolua_function(tolua_S,"getInstance",tolua_FsPayMM_PayMM_getInstance00);
   tolua_function(tolua_S,"purgeInstance",tolua_FsPayMM_PayMM_purgeInstance00);
   tolua_function(tolua_S,"init",tolua_FsPayMM_PayMM_init00);
   tolua_function(tolua_S,"order",tolua_FsPayMM_PayMM_order00);
  tolua_endmodule(tolua_S);
 tolua_endmodule(tolua_S);
 return 1;
}


#if defined(LUA_VERSION_NUM) && LUA_VERSION_NUM >= 501
 TOLUA_API int luaopen_FsPayMM (lua_State* tolua_S) {
 return tolua_FsPayMM_open(tolua_S);
};
#endif

