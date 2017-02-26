package com.project.classistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * A class to Change from one activity to another.
 */

public class ActivityChanger {
    private static Intent intent;

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param context: The current Activity context.
     * @param targetActivity: The activity where you want to go.
     */
    public static void changeActivity(Context context,String targetActivity){
        if(targetActivity.equalsIgnoreCase("addSubjectStudentActivity")){
            intent=new Intent(context,AddSubjectStudentActivity.class);
            context.startActivity(intent);
        }
        else if (targetActivity.equalsIgnoreCase("addSubjectTeacherActivity")){
            intent=new Intent(context,AddSubjectTeacherActivity.class);
            context.startActivity(intent);
        }
        else if(targetActivity.equalsIgnoreCase("RoutineActivity")){
            intent=new Intent(context,RoutineActivity.class);
            context.startActivity(intent);
        }
        else if (targetActivity.equalsIgnoreCase("StudentRegisterActivity")){
            intent=new Intent(context,StudentRegisterActivity.class);
            context.startActivity(intent);
        }
        else if (targetActivity.equalsIgnoreCase("TeacherRegisterActivity")){
            intent=new Intent(context,TeacherRegisterActivity.class);
            context.startActivity(intent);
        }
        else if(targetActivity.equalsIgnoreCase("StudentMainActivity")){
            intent=new Intent(context,StudentMainActivity.class);
            context.startActivity(intent);
        }
        else if(targetActivity.equalsIgnoreCase("TeacherMainActivity")){
            intent=new Intent(context,TeacherMainActivity.class);
            context.startActivity(intent);
        }
    }

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param context: The current activity context from where you want to change.
     * @param targetActivity: The activity where you want to go.
     * @param bundle: The data which is to be transfered to another Activity.
     */
    public static void changeActivity(Context context, String targetActivity, Bundle bundle){
        intent.putExtras(bundle);
    }

    /**
     * NOTE: This is the method to change from one activity to another without any bundle data.
     * @param context: The current activity context from where you want to change.
     * @param targetActivity: The activity where you want to go.
     * @param key: The string key for the String data.
     * @param value: The string data which is to be transferred to the target activity.
     */
    public static void changeActivity(Context context, String targetActivity, String key, String value){
        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        intent.putExtras(bundle);
    }
}
