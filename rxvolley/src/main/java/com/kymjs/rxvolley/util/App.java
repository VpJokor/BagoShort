package com.kymjs.rxvolley.util;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

public class App {

    public static final Application INSTANCE;

    public static final Uri ENV_URI;
    public static final int ENV_RELEASE = 0;
    public static final int ENV_BETA = 1;
    public static final int ENV_ALPHA = 2;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Log.e("Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
        ENV_URI = Uri.parse(String.format("content://%s/app_info", SystemTool.getMetaData("appmanager")));
    }

    public static int getEnvCode() {
        Cursor cursor = null;
        int env;
        try {
            cursor = App.INSTANCE.getContentResolver().query(ENV_URI,
                    new String[]{"env_code"}, "package_name=?",
                    new String[]{App.INSTANCE.getPackageName()}, null);
            if (cursor == null || !cursor.moveToFirst()) {
                return ENV_RELEASE;
            }

            env = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return ENV_RELEASE;
        } finally {
            FileUtils.closeIO(cursor);
        }
        return env;
    }


    private static Activity findActivityFrom(final Context context) {
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof Application || context instanceof Service) return null;
        if (!(context instanceof ContextWrapper)) return null;
        final Context base_context = ((ContextWrapper) context).getBaseContext();
        if (base_context == context) return null;
        return findActivityFrom(base_context);
    }

    public static void startActivity(final Context context, final Intent intent) {
        final Activity activity = findActivityFrom(context);
        if (activity != null) activity.startActivity(intent);
        else context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void toast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int msgId) {
        Toast.makeText(INSTANCE, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(String msg) {
        Toast.makeText(INSTANCE, msg, Toast.LENGTH_LONG).show();
    }
}

