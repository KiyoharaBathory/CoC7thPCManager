package cn.kiyohara.cocplayercharactermanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.BasicFragment;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.OthersFragment;
import cn.kiyohara.cocplayercharactermanager.frag_character_detail.SkillFragment;
import cn.kiyohara.cocplayercharactermanager.util.DiceDialog;
import cn.kiyohara.cocplayercharactermanager.util.NewSkillDialog;
import cn.kiyohara.cocplayercharactermanager.util.SkillCustomizeDialog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TabLayout tabLayout;
    ViewPager vp;
    ImageView cancelIv, favouriteIv, moreIv;
    int pcId;
    public PlayerCharacter pc;
    public List<SkillBean> skillList;
    public StoryBean story;
    public ProfessionBean professionBean;
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
            pc.setPortraitUri(portrait);
            basicFragment.portraitIv.setImageURI(portrait);
            DBManager.updateCharacter(pc);
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
                    Toast.makeText(this, "?????????", 500).show();
                } else {
                    pc.setFavourite(0);
                    DBManager.updateCharacter(pc);
                    favouriteIv.setImageResource(R.drawable.ic_star_outline);
                    Toast.makeText(this, "???????????????", 500).show();
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
                                showNewSkillDialog();
                                break;
                            case R.id.menu_pop_detail_share:
                                getPCInTextForm();
                                break;
                        }
                        return true;
                    }
                });
                popMenu.show();
                break;
        }
    }

    private void showNewSkillDialog() {
        NewSkillDialog nsDialog = new NewSkillDialog(this);
        nsDialog.show();
        nsDialog.setDialogSize();
        nsDialog.setOnEnsureListener(new NewSkillDialog.OnEnsureListener() {
            @Override
            public void onEnsure(SkillBean bean) {
                bean.setPlayerId(pc.getId());
                bean.setsId(skillList.size() + 1);
                DBManager.insertSkill(bean);
                skillList.add(bean);
                skillFragment.adapter.notifyDataSetChanged();
            }
        });
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
        skillList = DBManager.getAllSkillByCharacterId(pcId);
        story = DBManager.getStoryByCharacterId(pcId);
        ProfessionBean bean = new ProfessionBean();
        try {
            professionBean = bean.baseProfessionLoader(this, pc.getpId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPCInTextForm() {
        StringBuffer sb = new StringBuffer();
        String line;
        String gender;
        if (pc.getGender() == 1) {
            gender = "???";
        } else {
            gender = "???";
        }
        int maxHp = (pc.getSize() + pc.getConstitution()) / 10;
        int maxMp = pc.getWillPower() / 5;
        int build = pc.getStrength() + pc.getSize();
        String bui, db;
        if (build < 65) {
            bui = "-2";
            db = "-2";
        } else if (build < 85) {
            bui = "-1";
            db = "-1";
        } else if (build < 125) {
            bui = "0";
            db = "0";
        } else if (build < 165) {
            bui = "1";
            db = "+1D4";
        } else if (build < 205) {
            bui = "2";
            db = "+1D6";
        } else if (build < 285) {
            bui = "3";
            db = "+2D6";
        } else if (build < 365) {
            bui = "4";
            db = "+3D6";
        } else if (build < 445) {
            bui = "5";
            db = "+4D6";
        } else if (build < 525) {
            bui = "6";
            db = "+5D6";
        } else {
            bui = "??????";
            db = "??????";
        }
        int dex = pc.getDexterity();
        int siz = pc.getSize();
        int str = pc.getStrength();
        int age = pc.getAge();
        int mov;
        if (dex < siz && str < siz) {
            mov = 7;
        } else if (dex > siz && str > siz) {
            mov = 9;
        } else {
            mov = 8;
        }
        if (age >= 40 && age <= 49) {
            mov = mov - 1;
        } else if (age >= 50 && age <= 59) {
            mov = mov - 2;
        } else if (age >= 60 && age <= 69) {
            mov = mov - 3;
        } else if (age >= 70 && age <= 79) {
            mov = mov - 4;
        } else if (age >= 80 && age <= 89) {
            mov = mov - 5;
        }
        int maxSan = 99 - skillList.get(14).getGrowthPoint() - skillList.get(14).getProfessionPoint() - skillList.get(14).getInterestPoint();


        line = "=====????????????=====";
        sb.append(line);
        line = "\n" + pc.getName() + "," + gender + "," + pc.getAge() + "???,??????:" + professionBean.getProfessionName();
        sb.append(line);
        line = "\n??????:" + story.getAddress() + ",??????:" + story.getFamilyBackground();
        sb.append(line);
        line = "\n??????STR:" + pc.getStrength() + " ??????CON:" + pc.getConstitution() + " ??????SIZ:" + pc.getSize();
        sb.append(line);
        line = "\n??????DEX:" + pc.getDexterity() + " ??????APP:" + pc.getAppearance() + " ??????EDU:" + pc.getEducation();
        sb.append(line);
        line = "\n??????INT:" + pc.getIntelligence() + " ??????POW:" + pc.getWillPower() + " ??????LUCK:" + pc.getLuck();
        sb.append(line);
        line = "\n????????????:" + db + " ??????:" + bui + " ?????????:" + mov;
        sb.append(line);
        line = "\nHP:" + pc.getHp() + "/" + maxHp + " MP:" + pc.getMp() + "/" + maxMp + " SAN:" + pc.getSanity() + "/" + maxSan;
        sb.append(line);
        line = "\n=====????????????=====";
        sb.append(line);
        line = "\n(???????????????????????????????????????????????????)";
        sb.append(line);
        for (int i = 0; i < skillList.size(); i++) {
            SkillBean bean = skillList.get(i);
            int total = bean.getInitValue() + bean.getGrowthPoint() + bean.getProfessionPoint() + bean.getInterestPoint();
            int half = total / 2;
            int extreme = total / 5;
            if (bean.getInterestPoint() != 0 || bean.getProfessionPoint() != 0 || bean.getGrowthPoint() != 0) {
                if (bean.getCustomize() != null) {
                    line = "\n" + bean.getsName() + ":" + bean.getCustomize() + " " + total + "% (" + half + "/" + extreme + ")";
                    sb.append(line);
                } else {
                    line = "\n" + bean.getsName() + " " + total + "% (" + half + "/" + extreme + ")";
                    sb.append(line);
                }
            } else if (bean.getsId() > 59) {
                if (bean.getCustomize() != null) {
                    line = "\n" + bean.getsName() + ":" + bean.getCustomize() + " " + total + "% (" + half + "/" + extreme + ")";
                    sb.append(line);
                } else {
                    line = "\n" + bean.getsName() + " " + total + "% (" + half + "/" + extreme + ")";
                    sb.append(line);
                }
            }
        }
        line = "\n=====???????????????=====";
        sb.append(line);
        line = "\n" + story.getInventory();
        sb.append(line);
        line = "\n=====??????=====";
        sb.append(line);
        line = "\n" + story.getAsset();
        sb.append(line);
        line = "\n=====????????????=====";
        sb.append(line);
        line = "\n????????????:" + story.getPicture();
        sb.append(line);
        line = "\n??????:" + story.getFaith();
        sb.append(line);
        line = "\n????????????:" + story.getVip();
        sb.append(line);
        line = "\n??????????????????:" + story.getMemorialPlace();
        sb.append(line);
        line = "\n????????????:" + story.getTreasure();
        sb.append(line);
        line = "\n??????:" + story.getPeculiarity();
        sb.append(line);
        line = "\n???????????????:" + story.getTrauma();
        sb.append(line);
        line = "\n?????????????????????:" + story.getFearOrMania();
        sb.append(line);
        line = "\n????????????:" + story.getExtraStory();
        sb.append(line);
        line = "\n?????????????????????:" + story.getCthulhuMythos();
        sb.append(line);
        ClipData cd = ClipData.newPlainText("PC", sb);
        try {
            ClipboardManager clip = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setPrimaryClip(cd);
        } catch (Exception e) {
            Toast.makeText(this, "??????????????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_SHORT).show();
    }
}
