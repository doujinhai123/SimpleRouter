package com.dixon.simple.router.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.dixon.simple.router.base.core.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dalvik.system.DexFile;

public class ClassUtil {

    /**
     * 找到指定字符串开头的所有class
     */
    public static Set<String> getAllClassNameByStartWith(Application context, final String classStartString)
            throws PackageManager.NameNotFoundException {
        final Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        for (final String path : paths) {
            DexFile dexFile = null;
            try {
                //加载 apk中的dex 并遍历 获得所有包名为 {packageName} 的类
                dexFile = new DexFile(path);
                Enumeration<String> dexEntries = dexFile.entries();
                while (dexEntries.hasMoreElements()) {
                    String className = dexEntries.nextElement();
                    if (!TextUtils.isEmpty(className) && className.startsWith(classStartString)) {
                        classNames.add(className);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != dexFile) {
                    try {
                        dexFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.i(Constant.ROUTER_LOG_TAG, classStartString + " class: " + classNames);
        return classNames;
    }

    /**
     * 获得本程序所有的apk路径
     */
    public static List<String> getSourcePaths(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        //instant run
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitSourceDirs) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.splitSourceDirs));
            }
        }
        Log.i(Constant.ROUTER_LOG_TAG, "SourceDir: " + applicationInfo.sourceDir);
        return sourcePaths;
    }
}
