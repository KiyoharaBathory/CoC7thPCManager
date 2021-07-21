package cn.kiyohara.cocplayercharactermanager.frag_character_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;
import cn.kiyohara.cocplayercharactermanager.util.TextEditDialog;

public class OthersFragment extends Fragment implements View.OnClickListener {
    SkillBean creditBean;
    StoryBean storyBean;
    PlayerCharacter pc;
    TextView inventoryTv, creditTv, assetTv, pictureTv, faithTv, vipTv, memorialPlaceTv, treasureTv, peculiarityTv, traumaTv, fOMTv, extraTv, noteTv, cthulhuTv;
    ImageView inventoryIv, assetIv, pictureIv, faithIv, vipIv, memorialPlaceIv, treasureIv, peculiarityIv, traumaIv, fOMIv, extraIv, noteIv, cthulhuIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pcId = ((DetailActivity) getActivity()).getPcId();
        pc = DBManager.getCharacterById(pcId);
        creditBean = DBManager.getCreditByCharacterId(pcId);
        storyBean = DBManager.getStoryByCharacterId(pcId);
    }

    public void setCreditBean(SkillBean creditBean) {
        this.creditBean = creditBean;
        updateCreditTv();
    }

    public void setStoryBean(StoryBean storyBean) {
        this.storyBean = storyBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_character_detail_others, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_others_inventory_edit_iv:
                showTextEditDialog("物品与装备", storyBean.getInventory());
                break;
            case R.id.frag_others_all_asset_edit_iv:
                showTextEditDialog("消费水平与资产", storyBean.getAsset());
                break;
            case R.id.frag_others_picture_edit_iv:
                showTextEditDialog("形象描述", storyBean.getPicture());
                break;
            case R.id.frag_others_faith_edit_iv:
                showTextEditDialog("信仰", storyBean.getFaith());
                break;
            case R.id.frag_others_vip_edit_iv:
                showTextEditDialog("重要之人", storyBean.getVip());
                break;
            case R.id.frag_others_memorial_place_edit_iv:
                showTextEditDialog("意义非凡之地", storyBean.getMemorialPlace());
                break;
            case R.id.frag_others_treasure_edit_iv:
                showTextEditDialog("宝贵之物", storyBean.getTreasure());
                break;
            case R.id.frag_others_peculiarity_edit_iv:
                showTextEditDialog("特质", storyBean.getPeculiarity());
                break;
            case R.id.frag_others_trauma_edit_iv:
                showTextEditDialog("伤口与疤痕", storyBean.getTrauma());
                break;
            case R.id.frag_others_fear_mania_edit_iv:
                showTextEditDialog("恐惧症与躁狂症", storyBean.getFearOrMania());
                break;
            case R.id.frag_others_extra_story_edit_iv:
                showTextEditDialog("详细背景故事", storyBean.getExtraStory());
                break;
            case R.id.frag_others_note_edit_iv:
                showTextEditDialog("调查员笔记", storyBean.getNote());
                break;
            case R.id.frag_others_cthulhu_edit_iv:
                showTextEditDialog("克苏鲁神话相关", storyBean.getCthulhuMythos());
                break;
        }

    }

    public void showTextEditDialog(String typeName, String input) {
        TextEditDialog dialog = new TextEditDialog(getContext());
        dialog.setTypeName(typeName);
        dialog.setInput(input);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new TextEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String describeText) {
                if (typeName.equals("物品与装备")) {
                    storyBean.setInventory(describeText);
                    inventoryTv.setText(describeText);
                } else if (typeName.equals("消费水平与资产")) {
                    storyBean.setAsset(describeText);
                    assetTv.setText(describeText);
                } else if (typeName.equals("形象描述")) {
                    storyBean.setPicture(describeText);
                    pictureTv.setText(describeText);
                } else if (typeName.equals("信仰")) {
                    storyBean.setFaith(describeText);
                    faithTv.setText(describeText);
                } else if (typeName.equals("重要之人")) {
                    storyBean.setVip(describeText);
                    vipTv.setText(describeText);
                } else if (typeName.equals("意义非凡之地")) {
                    storyBean.setMemorialPlace(describeText);
                    memorialPlaceTv.setText(describeText);
                } else if (typeName.equals("宝贵之物")) {
                    storyBean.setTreasure(describeText);
                    treasureTv.setText(describeText);
                } else if (typeName.equals("特质")) {
                    storyBean.setPeculiarity(describeText);
                    peculiarityTv.setText(describeText);
                } else if (typeName.equals("伤口与疤痕")) {
                    storyBean.setTrauma(describeText);
                    traumaTv.setText(describeText);
                } else if (typeName.equals("恐惧症与躁狂症")) {
                    storyBean.setFearOrMania(describeText);
                    fOMTv.setText(describeText);
                } else if (typeName.equals("详细背景故事")) {
                    storyBean.setExtraStory(describeText);
                    extraTv.setText(describeText);
                } else if (typeName.equals("调查员笔记")) {
                    storyBean.setNote(describeText);
                    noteTv.setText(describeText);
                } else if (typeName.equals("克苏鲁神话相关")) {
                    storyBean.setCthulhuMythos(describeText);
                    cthulhuTv.setText(describeText);
                }
                BasicFragment frag = ((DetailActivity)getActivity()).getBasicFragment();
                frag.setStoryBean(storyBean);
                DBManager.updateStory(storyBean);
            }
        });
    }

    public void updateCreditTv(){
        int credit = creditBean.getInterestPoint() + creditBean.getGrowthPoint() + creditBean.getProfessionPoint();
        String financialStat;
        if (credit < 1) {
            financialStat = "身无分文";
        } else if (credit < 10) {
            financialStat = "贫穷";
        } else if (credit < 50) {
            financialStat = "标准";
        } else if (credit < 90) {
            financialStat = "小康";
        } else if (credit < 99) {
            financialStat = "富裕";
        } else {
            credit = 99;
            financialStat = "富豪";
        }
        String tempText = "信用评级:" + credit + "，参考经济水平:" + financialStat;
        creditTv.setText(tempText);
    }

    private void initView(View v) {
        inventoryTv = v.findViewById(R.id.frag_others_inventory_tv);
        inventoryTv.setText(storyBean.getInventory());
        inventoryIv = v.findViewById(R.id.frag_others_inventory_edit_iv);
        inventoryIv.setOnClickListener(this);
        creditTv = v.findViewById(R.id.frag_others_credit_hint_tv);
        updateCreditTv();
        assetIv = v.findViewById(R.id.frag_others_all_asset_edit_iv);
        assetIv.setOnClickListener(this);
        assetTv = v.findViewById(R.id.frag_others_asset_tv);
        assetTv.setText(storyBean.getAsset());
        pictureTv = v.findViewById(R.id.frag_others_picture_tv);
        pictureTv.setText(storyBean.getPicture());
        pictureIv = v.findViewById(R.id.frag_others_picture_edit_iv);
        pictureIv.setOnClickListener(this);
        faithTv = v.findViewById(R.id.frag_others_faith_tv);
        faithTv.setText(storyBean.getFaith());
        faithIv = v.findViewById(R.id.frag_others_faith_edit_iv);
        faithIv.setOnClickListener(this);
        vipTv = v.findViewById(R.id.frag_others_vip_tv);
        vipTv.setText(storyBean.getVip());
        vipIv = v.findViewById(R.id.frag_others_vip_edit_iv);
        vipIv.setOnClickListener(this);
        memorialPlaceTv = v.findViewById(R.id.frag_others_memorial_place_tv);
        memorialPlaceTv.setText(storyBean.getMemorialPlace());
        memorialPlaceIv = v.findViewById(R.id.frag_others_memorial_place_edit_iv);
        memorialPlaceIv.setOnClickListener(this);
        treasureTv = v.findViewById(R.id.frag_others_treasure_tv);
        treasureTv.setText(storyBean.getTreasure());
        treasureIv = v.findViewById(R.id.frag_others_treasure_edit_iv);
        treasureIv.setOnClickListener(this);
        peculiarityTv = v.findViewById(R.id.frag_others_peculiarity_tv);
        peculiarityTv.setText(storyBean.getPeculiarity());
        peculiarityIv = v.findViewById(R.id.frag_others_peculiarity_edit_iv);
        peculiarityIv.setOnClickListener(this);
        traumaTv = v.findViewById(R.id.frag_others_trauma_tv);
        traumaTv.setText(storyBean.getTrauma());
        traumaIv = v.findViewById(R.id.frag_others_trauma_edit_iv);
        traumaIv.setOnClickListener(this);
        fOMTv = v.findViewById(R.id.frag_others_fear_mania_tv);
        fOMTv.setText(storyBean.getFearOrMania());
        fOMIv = v.findViewById(R.id.frag_others_fear_mania_edit_iv);
        fOMIv.setOnClickListener(this);
        extraTv = v.findViewById(R.id.frag_others_extra_story_tv);
        extraTv.setText(storyBean.getExtraStory());
        extraIv = v.findViewById(R.id.frag_others_extra_story_edit_iv);
        extraIv.setOnClickListener(this);
        noteIv = v.findViewById(R.id.frag_others_note_edit_iv);
        noteTv = v.findViewById(R.id.frag_others_note_tv);
        noteTv.setText(storyBean.getNote());
        noteIv.setOnClickListener(this);
        cthulhuTv = v.findViewById(R.id.frag_others_cthulhu_tv);
        cthulhuTv.setText(storyBean.getCthulhuMythos());
        cthulhuIv = v.findViewById(R.id.frag_others_cthulhu_edit_iv);
        cthulhuIv.setOnClickListener(this);
    }
}
