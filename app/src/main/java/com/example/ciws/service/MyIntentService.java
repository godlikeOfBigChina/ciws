package com.example.ciws.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static UpdateUI updateUI;

    public static String EXTRA_PARAM_USERID="USERID";
    public static String EXTRA_PARAM_USERNAME="USERNAME";
    public static String EXTRA_PARAM_PASSWORDS="PASSWORD";

    public static String ACTION_LOGIN;

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void setUpdateUI(UpdateUI updateUi) {
        updateUI = updateUi;
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionLogin(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
//        intent.setAction(ACTION_LOGIN);
//        intent.putExtra(EXTRA_PARAM_USERNAME, param1);
//        intent.putExtra(EXTRA_PARAM_PASSWORDS, param2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
//            if (ACTION_LOGIN.equals(action)) {
//                String param1 = intent.getStringExtra(EXTRA_PARAM_USERNAME);
//                String param2 = intent.getStringExtra(EXTRA_PARAM_PASSWORDS);
//                handleActionLogin(param1, param2);
//            }
        }
    }

    public interface UpdateUI {
        void updateUi(Message message);
    }
}
