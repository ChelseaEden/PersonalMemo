package com.example.g.personalmemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g.personalmemo.R;
import com.example.g.personalmemo.util.Dbservice;
import com.example.g.personalmemo.util.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPasswordActivity extends AppCompatActivity {

    private User quser;
    private Toast toast = null;
    private static Dbservice dbservice = null;
    @BindView(R.id.question) TextView Qs;
    @BindView(R.id.account1) EditText accountEdit;
    @BindView(R.id.answer)  EditText As;
    @BindView(R.id.findquestion) Button findQs;
    @BindView(R.id.findpassword) Button findPs;

    @OnClick(R.id.findquestion)
    public void FindQs(){
        String account = accountEdit.getText().toString();
        dbservice = new Dbservice(getApplicationContext());
        quser = dbservice.find(account);
        if(TextUtils.isEmpty(quser.username)){
            showToast("用户名不存在");
        }else{
            Qs.setText(quser.question);
        }
    }
    @OnClick(R.id.findpassword)
    public void FindPs(){
        String TQ = Qs.getText().toString();
        if(TextUtils.isEmpty(TQ)){
            showToast("请先查找答案");
        }else{
            String as = As.getText().toString();
            if (quser.answer.equals(as))
            showToast("您的密码为:"+ quser.password);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        ButterKnife.bind(this);
        ImageView unameClear = (ImageView) findViewById(R.id.iv_pwdClear);
        ImageView pwdClear = (ImageView) findViewById(R.id.iv_pwdClear1);
        FindPasswordActivity.EditTextClearTools.addClearListener(accountEdit,unameClear);
        FindPasswordActivity.EditTextClearTools.addClearListener(As,pwdClear);
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
    public static class EditTextClearTools {
        public static void addClearListener(final EditText et, final ImageView iv) {
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
                    String str = s + "";
                    if (s.length() > 0) {
                        iv.setVisibility(View.VISIBLE);
                    } else {
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
}
