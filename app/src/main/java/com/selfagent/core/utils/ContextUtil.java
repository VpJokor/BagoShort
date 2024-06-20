package com.selfagent.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContextUtil {

    public static Activity getActivityFromContextWrapper(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return getActivityFromContextWrapper(((ContextWrapper) context).getBaseContext());
        }
        throw new IllegalArgumentException();//一般不执行这句，直接抛出异常，会导致应用崩溃
    }

    public static void finishActivity(@NonNull final Class<? extends Activity> clz, final boolean isLoadAnim) {
        List<Activity> activities = new ContextUtil().getActivitiesByReflect();
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    private List<Activity> getActivitiesByReflect() {
        LinkedList<Activity> list = new LinkedList<>();
        Activity topActivity = null;
        try {
            Object activityThread = getActivityThread();
            if (activityThread == null) return list;
            Field mActivitiesField = activityThread.getClass().getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(activityThread);
            if (!(mActivities instanceof Map)) {
                return list;
            }
            Map<Object, Object> binder_activityClientRecord_map = (Map<Object, Object>) mActivities;
            for (Object activityRecord : binder_activityClientRecord_map.values()) {
                Class activityClientRecordClass = activityRecord.getClass();
                Field activityField = activityClientRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                if (topActivity == null) {
                    Field pausedField = activityClientRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        topActivity = activity;
                    } else {
                        list.addFirst(activity);
                    }
                } else {
                    list.addFirst(activity);
                }
            }
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e.getMessage());
        }
        if (topActivity != null) {
            list.addFirst(topActivity);
        }
        return list;
    }

    private Object getActivityThread() {
        Object activityThread = getActivityThreadInActivityThreadStaticField();
        if (activityThread != null) return activityThread;
        return getActivityThreadInActivityThreadStaticMethod();
    }

    private Object getActivityThreadInActivityThreadStaticField() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            return sCurrentActivityThreadField.get(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e.getMessage());
            return null;
        }
    }

    private Object getActivityThreadInActivityThreadStaticMethod() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            return activityThreadClass.getMethod("currentActivityThread").invoke(null);
        } catch (Exception e) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e.getMessage());
            return null;
        }
    }

}
