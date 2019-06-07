package com.example.g.personalmemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.g.personalmemo.R;
import com.example.g.personalmemo.util.MemoDb;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.textvi)
    TextView textvi;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.edit_pwdClear)
    ImageView editPwdClear;
    @BindView(R.id.edit_info)
    EditText editInfo;
    @BindView(R.id.edit_pwdClear1)
    ImageView editPwdClear1;
    @BindView(R.id.edit_kind)
    Spinner editKind;
    @BindView(R.id.edit_save)
    Button editSave;
    @BindView(R.id.edit_reset)
    Button editReset;
    @BindView(R.id.iv_kind)
    ImageView ivKind;

    @OnClick(R.id.edit_save)
    public void setEditSave() {
        if (editTitle.getText().toString().isEmpty() || editInfo.getText().toString().isEmpty()) {
            showToast("请填写完整再保存");
        } else {
            memoDb = new MemoDb(getApplicationContext());
            if (isAdd == 0) {
                memoDb.update(editTitle.getText().toString(), editInfo.getText().toString(),kindid,memoid);
                Intent gintent = new Intent(EditActivity.this, MemoActivity.class);
                gintent.putExtra("user", username);
                startActivity(gintent);
            } else {
                memoDb.save(editTitle.getText().toString(), editInfo.getText().toString(), username, kindid);
                Intent gintent = new Intent(EditActivity.this, MemoActivity.class);
                gintent.putExtra("user", username);
                startActivity(gintent);
            }
        }
    }

    @OnClick(R.id.edit_reset)
    public void setEditReset() {
        if (isAdd == 0) {
            editTitle.setText(title);
            editInfo.setText(info);
        }
    }

    private static MemoDb memoDb = null;
    private int isAdd;
    private int memoid;
    private int kindid;
    private String kind;
    private String title;
    private String info;
    private Toast toast = null;
    private String username;
    final String[] spinnerItems = {"个人密码", "事件", "日记", "生日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        EditTextClearTools.addClearListener(editTitle, editPwdClear);
        EditTextClearTools.addClearListener(editInfo, editPwdClear1);
        Intent intent = getIntent();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        editKind.setAdapter(spinnerAdapter);
        editKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //拿到被选择项的值
                kind = (String) editKind.getSelectedItem();
                switch (kind) {
                    case "个人密码":
                        kindid = 0;
                        ivKind.setImageResource(R.mipmap.ic_password);
                        break;
                    case "事件":
                        kindid = 1;
                        ivKind.setImageResource(R.mipmap.ic_event);
                        break;
                    case "日记":
                        kindid = 2;
                        ivKind.setImageResource(R.mipmap.ic_date);
                        break;
                    default:
                        kindid = 3;
                        ivKind.setImageResource(R.mipmap.ic_birthday);
                        break;
                }
                //把该值传给 TextView

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                kindid = 0;
            }
        });
        username = intent.getStringExtra("user");
        if (isAdd == 0) {
            isAdd = intent.getIntExtra("isAdd", 0);
            memoid = intent.getIntExtra("id", 0);
            title = intent.getStringExtra("title");
            info = intent.getStringExtra("info");
            kindid = intent.getIntExtra("kindid", 0);

            editTitle.setText(title);
            editInfo.setText(info);
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

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }
}
