package com.huol.sswcrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Crack implements IXposedHookLoadPackage {
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.qq.ssw1")) {
            ClassLoader classLoader = lpparam.classLoader;
            XposedBridge.log("Loaded app: " + lpparam.packageName);
            Class clazz = XposedHelpers.findClass("com.qq.ssw.v2.splash.SplashActivityV2", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(clazz, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Class mainActivity = XposedHelpers.findClass("com.qq.ssw.v2.main.MainActivityV2", lpparam.classLoader);
                    Activity activity = (Activity) param.thisObject;
                    Intent mIntent = new Intent(activity, mainActivity);
                    activity.startActivity(mIntent);
                }
            }); // 去开屏广告和不必要权限要求
            XposedHelpers.findAndHookMethod("tencent.io.b", classLoader, "i", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(true);
                }
            }); //破解会员
        }
    }
}