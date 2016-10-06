package org.eclipse.paho.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TaskerBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_INTENT = "com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING";
    public static final String CONDITION_INTENT = "com.twofortyfouram.local.Intent.ACTION_QUERY_CONDITION";
    public static final String TASKER_DATA_BUNDLE = "com.twofortyfouram.locale.Intent.EXTRA_BUNDLE";

    private ITaskerActionRunner actionRunner;
    private ITaskerConditionChecker conditionChecker;

    // Default constructor allows instantiation directly from AndroidManifest
    // (used here to start the MqttService since it won't have its own broadcast receiver listening)
    public TaskerBroadcastReceiver(){
        MqttServiceStarter serviceStarter = new MqttServiceStarter();
        actionRunner = (ITaskerActionRunner)serviceStarter;
        conditionChecker = null;
    }

    public TaskerBroadcastReceiver(ITaskerActionRunner runner, ITaskerConditionChecker checker) {
        this.actionRunner = runner;
        this.conditionChecker = checker;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getBundleExtra(TASKER_DATA_BUNDLE);

        if(ACTION_INTENT.equals(intent.getAction()) && this.actionRunner != null)
        {
            // Tasker action triggered
            this.actionRunner.runAction(context, bundle);
        }
        else if(CONDITION_INTENT.equals(intent.getAction()) && this.conditionChecker != null)
        {
            // Tasker condition check triggered
            this.conditionChecker.checkCondition(context, bundle);
        }
        else
        {
            // TODO: Received invalid intent
        }



    }
}