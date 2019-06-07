package com.example.g.personalmemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.example.g.personalmemo.R;
import com.example.g.personalmemo.util.Dbservice;
import com.example.g.personalmemo.util.FaceDB;
import com.example.g.personalmemo.util.User;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private static Dbservice dbservice = null;
    private SharedPreferences pref;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private Button reg;
    private Button findpassword;
    private Button facelogin;
    private CheckBox rememberPass;
    private Toast toast = null;
    private SharedPreferences.Editor editor;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEdit = (EditText)findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        rememberPass = (CheckBox)findViewById(R.id.remember);
        facelogin = (Button)findViewById(R.id.facelogin);
        login = (Button)findViewById(R.id.log);
        reg = (Button)findViewById(R.id.reg);
        findpassword = (Button) findViewById(R.id.getpassword);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        init();
    }
    public void activeEngine() {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(LoginActivity.this, FaceDB.APP_ID, FaceDB.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            showToast(getString(R.string.already_activated));
                        } else {
                            showToast(getString(R.string.active_failed, activeCode));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine();
            } else {
                showToast(getString(R.string.permission_denied));
            }
        }
    }
    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }
    public static class EditTextClearTools {
        public static void addClearListener(final EditText et , final ImageView iv){
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //如果有输入内容长度大于0那么显示clear按钮
                    String str = s + "" ;
                    if (s.length() > 0){
                        iv.setVisibility(View.VISIBLE);
                    }else{
                        iv.setVisibility(View.INVISIBLE);
                    }
                }
            });
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    et.setText("");
                }
            });
        }
    }
    private void init(){
        ImageView unameClear = (ImageView) findViewById(R.id.iv_pwdClear);
        ImageView pwdClear = (ImageView) findViewById(R.id.iv_pwdClear2);

        EditTextClearTools.addClearListener(accountEdit,unameClear);
        EditTextClearTools.addClearListener(passwordEdit,pwdClear);

        boolean isRemember = pref.getBoolean("remember_password",false);
        //验证是否点击过记住密码
        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        //设置各个按钮监听点击
        findpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class);
                startActivity(intent);
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Loadlogindata();
            }
        });
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            activeEngine();
        }
        facelogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FaceLoginActivity.class);
                startActivity(intent);
            }
        });
    }
    //验证登录用户密码是否匹配
    private void Loadlogindata() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        dbservice = new Dbservice(getApplicationContext());
        if (dbservice.checkbyname(account) != 0) {
            User quser = dbservice.find(account);
            if (quser.username == null) {
                showToast("用户名不存在");
                return;
            }
            if (account.equals(quser.username) && password.equals(quser.password)) {
                editor = pref.edit();
                if (rememberPass.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MemoActivity.class);
                intent.putExtra("user", quser.username);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            showToast("用户名不存在");
        }
    }
}
