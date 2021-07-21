package cn.kiyohara.cocplayercharactermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.csz.pick.core.ImagePicker;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.adapter.DetailPagerAdapter;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.BasicFragment;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.OthersFragment;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.SkillFragment;
import cn.kiyohara.cocplayercharactermanager.util.DiceDialog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TabLayout tabLayout;
    ViewPager vp;
    ImageView cancelIv, favouriteIv, moreIv;
    int pcId;
    PlayerCharacter pc;
    BasicFragment basicFragment;
    SkillFragment skillFragment;
    OthersFragment othersFragment;
    SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);
        initData();
        cancelIv = findViewById(R.id.detail_actionbar_iv_cancel);
        cancelIv.setOnClickListener(this);
        favouriteIv = findViewById(R.id.detail_actionbar_iv_favourite);
        favouriteIv.setOnClickListener(this);
        moreIv = findViewById(R.id.detail_actionbar_iv_more);
        moreIv.setOnClickListener(this);
        if (pc.getFavourite() == 1) {
            favouriteIv.setImageResource(R.drawable.ic_star);
        }
        tabLayout = findViewById(R.id.detail_tabs);
        vp = findViewById(R.id.detail_vp);
        initPager();
    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        basicFragment = new BasicFragment();
        skillFragment = new SkillFragment();
        othersFragment = new OthersFragment();
        fragmentList.add(basicFragment);
        fragmentList.add(skillFragment);
        fragmentList.add(othersFragment);
        DetailPagerAdapter adapter = new DetailPagerAdapter(this, getSupportFragmentManager(), fragmentList);
        vp.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == basicFragment.REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
            basicFragment.mImagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
            String portrait = "file://" + basicFragment.mImagePaths.get(0);
            basicFragment.pc.setPortraitUri(portrait);
            basicFragment.portraitIv.setImageURI(portrait);
            DBManager.updateCharacter(basicFragment.pc);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_actionbar_iv_cancel:
                finish();
                break;
            case R.id.detail_actionbar_iv_favourite:
                if (pc.getFavourite() == 0) {
                    pc.setFavourite(1);
                    DBManager.updateCharacter(pc);
                    favouriteIv.setImageResource(R.drawable.ic_star);
                    Toast.makeText(this, "已收藏", 500).show();
                } else {
                    pc.setFavourite(0);
                    DBManager.updateCharacter(pc);
                    favouriteIv.setImageResource(R.drawable.ic_star_outline);
                    Toast.makeText(this, "已取消收藏", 500).show();
                }
                break;
            case R.id.detail_actionbar_iv_more:
                PopupMenu popMenu = new PopupMenu(this, moreIv);
                popMenu.getMenuInflater().inflate(R.menu.menu_pop_detail, popMenu.getMenu());
                popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_pop_detail_dice:
                                showDiceDialog();
                                break;
                            case R.id.menu_pop_detail_new_skill:
                                break;
                            case R.id.menu_pop_detail_share:
                                break;
                        }
                        return true;
                    }
                });
                popMenu.show();
                break;
        }
    }

    private void showDiceDialog() {
        DiceDialog diceDialog = new DiceDialog(this);
        diceDialog.show();
        diceDialog.setDialogSize();
    }

    public BasicFragment getBasicFragment() {
        return basicFragment;
    }

    public SkillFragment getSkillFragment() {
        return skillFragment;
    }

    public OthersFragment getOthersFragment() {
        return othersFragment;
    }

    private void initData() {
        sp = this.getSharedPreferences("id", 0);
        pcId = sp.getInt("idSelected", 0);
        pc = DBManager.getCharacterById(pcId);
    }

    public int getPcId() {
        return pcId;
    }
}
