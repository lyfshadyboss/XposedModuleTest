package com.example.xposedmoduletest;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.endsWith("com.gtp.nextlauncher")) {
            return;
        }

        XposedBridge.log("Loaded app : " + lpparam.packageName);

        // public void setDefaultViewportFrustum(int w, int h)
        XposedHelpers.findAndHookMethod("com.go.gl.graphics.GLCanvas", lpparam.classLoader,
                "setDefaultViewportFrustum", "int", "int", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("setDefaultViewportFrustum -- ");

                        XposedBridge.log("w = " + (Integer) param.args[0] + ", h = " + (Integer) param.args[1]);
                    }
                });

        // public void setProjection(float arg8, float arg9, float arg10, float
        // arg11)
        XposedHelpers.findAndHookMethod("com.go.gl.graphics.GLCanvas", lpparam.classLoader, "setProjection", "float",
                "float", "float", "float", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("setProjection -- ");

                        XposedBridge.log("" + (Float) param.args[0] + ", " + (Float) param.args[1] + ", "
                                + (Float) param.args[2] + ", " + (Float) param.args[3]);
                    }
                });

        // public void setProjection(float arg9, float arg10, float arg11, float
        // arg12, float arg13, float arg14)

        XposedHelpers.findAndHookMethod("com.go.gl.graphics.GLCanvas", lpparam.classLoader, "setProjection", "float",
                "float", "float", "float", "float", "float", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("setProjection -- ");

                        Object[] args = param.args;
                        for (int i = 0; i < args.length; ++i) {
                            XposedBridge.log("arg[" + i + "] = " + (Float) args[i]);
                        }
                    }
                });
    }

}
