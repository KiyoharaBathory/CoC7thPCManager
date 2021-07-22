package cn.kiyohara.cocplayercharactermanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.adapter.PCAdapter;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<PlayerCharacter> mDatas;
    PCAdapter adapter;
    ListView characterLv;
    ImageView moreIv;
    ImageButton createBtn;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatas = new ArrayList<>();
        characterLv = findViewById(R.id.main_character_list);
        moreIv = findViewById(R.id.main_actionBar_iv_about);
        createBtn = findViewById(R.id.main_btn_new);
        adapter = new PCAdapter(this, mDatas);
        characterLv.setAdapter(adapter);
        setListViewListener();
        setListViewLongClickListener();
        sp = this.getSharedPreferences("id", 0);
        editor = sp.edit();
        Fresco.initialize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<PlayerCharacter> list = DBManager.getAllCharacter();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_actionBar_iv_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.main_btn_new:
                Intent intentCreate = new Intent(this, CreateActivity.class);
                startActivity(intentCreate);
                break;
        }
    }


    private void setListViewLongClickListener() {
        characterLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PlayerCharacter clickedPc = mDatas.get(position);
                showDeleteDialog(clickedPc);
                return true;
            }
        });
    }

    private void showDeleteDialog(final PlayerCharacter pc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("您确定要删除这个调查员的信息么？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int clickedId = pc.getId();
                        int favourite = pc.getFavourite();
                        if (favourite == 1) {
                            Toast.makeText(MainActivity.this, "删除前请先解除收藏！", Toast.LENGTH_SHORT).show();
                        } else {
                            DBManager.deleteCharacter(clickedId);
                            mDatas.remove(pc);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        builder.create().show();
    }

    private void setListViewListener() {
        characterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = mDatas.get(position).getId();
                editor.putInt("idSelected", selectedId);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
}