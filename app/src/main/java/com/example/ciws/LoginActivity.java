package com.example.ciws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ciws.service.MyIntentService;
import com.example.ciws.util.HttpUtil;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends Activity implements OnClickListener, MyIntentService.UpdateUI{
    // 声明控件对象

    private EditText et_name, et_pass;
    private Button mLoginButton;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;//编辑框监听器
    private TextWatcher password_watcher;

    private String userName,password, userID;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
//        Intent intent1 = new Intent(getApplicationContext(), MyService.class);
//        bindService(intent1, connection, BIND_AUTO_CREATE);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);

        findViewById(R.id.login_all).setOnClickListener(this);


        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
//                switch (msg.what){
//                    case StatusTable.RESUTL_LOGIN_OK:
//                        User user=(User)msg.obj;
//                        Intent intent=new Intent(getBaseContext(),MyIntentService.class);
//                        intent.setAction(MyIntentService.ACTION_WRITELOG);
//                        OpDiary row= new OpDiary();
//                        SimpleDateFormat format=new SimpleDateFormat("yyyMMdd HH:mm:ss");
//                        row.setOpTime(format.format(new Date()));
//                        row.setUsername(user.getId());
//                        row.setOpType(1);
//                        row.setOpObject("");
//                        intent.putExtra(MyIntentService.EXTRA_PARAM_LOGROW,row);
//                        startService(intent);
//                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                        Intent intentAct = new Intent(getApplicationContext(), HomeActivity.class);
//                        intentAct.putExtra("USER",user);
//                        startActivity(intentAct);
//                        break;
//                    case StatusTable.RESUTL_LOGIN_FAIL:
//                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                        break;
//                    case StatusTable.WORKNET_ERROR:
//                        Toast.makeText(LoginActivity.this, "工作网络错误", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
                return false;
            }
        });
    }

    //edittext监听
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if (s.toString().length() > 0) {
                    bt_username_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };

        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_all:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;

            case R.id.login_button:
                try {
                    login();
                } catch (NoSuchAlgorithmException e) {
//                            e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"加密出错",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;

            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;

            case R.id.bt_pwd_eye:
                if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    bt_pwd_eye.setBackgroundResource(R.drawable.login_button_eye_s);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    bt_pwd_eye.setBackgroundResource(R.drawable.login_button_eye_n);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() throws NoSuchAlgorithmException{
        userName = et_name.getText().toString();
        password = et_pass.getText().toString();
        if (!userName.equals("") && !password.equals("")) {
            new Thread(new Runnable() {
                String r=null;
                String result=getResources().getString(R.string.login_result);
                @Override
                public void run() {
                    try {
                        r=HttpUtil.postGeneralUrl(String.format("http://172.16.10.89:8080/ciws/ajaxLogin?userid=%s&password=%s",userName,password),"application/json","","UTF-8");
                        if(!r.contains(result)){
                            Intent intent=new Intent(getBaseContext(),MainActivity.class);
                            intent.putExtra(MyIntentService.EXTRA_PARAM_USERID,userName);
                            intent.putExtra(MyIntentService.EXTRA_PARAM_USERNAME,password);
                            startActivity(intent);
                        }else{
                            r="用户名或密码错误";
                        }
                    } catch (Exception e) {
//                        r=e.getMessage();
                        r="网络错误";
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!r.equals("{}")){
                                Toast.makeText(LoginActivity.this, r, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).start();

        } else {
            Toast.makeText(LoginActivity.this, "用户名或密码为空！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
//        stopService(new Intent(this,MyService.class));
        super.onPause();
    }

    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
        }
    }


    @Override
    public void updateUi(Message message) {
        handler.sendMessage(message);
    }
}

