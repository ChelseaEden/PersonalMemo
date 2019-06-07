package com.example.g.personalmemo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g.personalmemo.R;
import com.example.g.personalmemo.adapter.MemoAdapter;
import com.example.g.personalmemo.util.Memo;
import com.example.g.personalmemo.util.MemoDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MemoActivity extends AppCompatActivity {

    @BindView(R.id.tv_memo)
    TextView tvMemo;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.float_button)
    FloatingActionButton floatButton;

    private static MemoDb memoDb = null;
    private List<Memo> MemoList;
    private String username;
    private int isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("user");
        init();
    }


    private void init() {
        //初始化控件
        memoDb = new MemoDb(getApplicationContext());
        MemoList = memoDb.find(username);
        MemoAdapter adapter = new MemoAdapter(this,MemoList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (view.getId() == R.id.tv_delete) {
                    Memo ex = MemoList.get(postion);
                    memoDb.del(ex.getId());
                    MemoList.remove(postion);
                    adapter.notifyDataSetChanged();
                }else {
                    isAdd = 0;
                    Memo ex = MemoList.get(postion);
                    Intent intent = new Intent(MemoActivity.this, EditActivity.class);
                    intent.putExtra("user", username);
                    intent.putExtra("isAdd", isAdd);
                    intent.putExtra("id", ex.getId());
                    intent.putExtra("title", ex.getTitle());
                    intent.putExtra("info", ex.getInfo());
                    intent.putExtra("kindid", ex.getKindId());
                    startActivity(intent);
                }
            }
        });

        floatButton = (FloatingActionButton) findViewById(R.id.float_button);
        floatButton.setImageResource(R.drawable.add_img);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAdd = 1;
                Intent intent = new Intent(MemoActivity.this, EditActivity.class);
                intent.putExtra("isAdd", isAdd);
                intent.putExtra("user", username);
                startActivity(intent);
            }
        });
     }


}
