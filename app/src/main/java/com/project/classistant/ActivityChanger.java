package com.project.classistant;

import android.content.Intent;
import android.os.Bundle;

/**
 * A class to Change from one activity to another.
 */

public class ActivityChanger {
    private static Intent intent;

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param currentActivity: The current activity from where you want to change.
     * @param targetActivity: The activity where you want to go.
     */
    public static void changeActivity(String currentActivity, String targetActivity){
    }

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param currentActivity: The current activity from where you want to change.
     * @param targetActivity: The activity where you want to go.
     * @param bundle: The data which is to be transfered to another Activity.
     */
    public static void changeActivity(String currentActivity, String targetActivity, Bundle bundle){
        intent.putExtras(bundle);
    }

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param currentActivity: The current activity from where you want to change.
     * @param targetActivity: The activity where you want to go.
     * @param key: The string key for the String data.
     * @param value: The string data which is to be transferred to the target activity.
     */
    public static void changeActivity(String currentActivity, String targetActivity, String key, String value){
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        intent.putExtras(bundle);
    }
}
