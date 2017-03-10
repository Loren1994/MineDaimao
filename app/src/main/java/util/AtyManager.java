package util;


import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by victor on 16-6-14.
 */
public final class AtyManager {
    private static ArrayList<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static Activity currentActivity() {
        return activityList.get(activityList.size() - 1);
    }

    public static Activity findActivity(Class<?> clazz) {
        Activity activity = null;
        for (Activity aty : activityList) {
            if (aty.getClass() == clazz) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    private static void finishActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
        }
    }

    public static void finishActivity(Class<?> clazz) {
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity aty = activityList.get(i);
            if (aty.getClass() == clazz) {
                finishActivity(aty);
            }
        }
    }

    public static void finishAllActivitiesExcept(Class<?>... clazz) {
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity activity = activityList.get(i);
            boolean shouldFinish = true;
            for (Class<?> c : clazz) {
                if (activity.getClass() == c) {
                    shouldFinish = false;
                    break;
                }
            }
            if (shouldFinish) {
                finishActivity(activity);
            }
        }
    }

    public static void finishAllActivities() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }
}