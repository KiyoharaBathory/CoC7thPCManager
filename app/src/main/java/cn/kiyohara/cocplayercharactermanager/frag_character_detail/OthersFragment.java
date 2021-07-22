package cn.kiyohara.cocplayercharactermanager.frag_character_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;
import cn.kiyohara.cocplayercharactermanager.util.TextEditDialog;

public class OthersFragment extends Fragment implements View.OnClickListener {
    SkillBean creditBean;
    DetailActivity da;
    TextView inventoryTv, creditTv, assetTv, pictureTv, faithTv, vipTv, memorialPlaceTv, treasureTv, peculiarityTv, traumaTv, fOMTv, extraTv, noteTv, cthulhuTv;
    ImageView inventoryIv, assetIv, pictureIv, faithIv, vipIv, memorialPlaceIv, treasureIv, peculiarityIv, traumaIv, fOMIv, extraIv, noteIv, cthulhuIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        da = (DetailActivity) getActivity();
        int pcId = da.pc.getId();
        creditBean = da.skillList.get(33);
    }

    public void setCreditBean(SkillBean creditBean) {
        this.creditBean = creditBean;
        updateCreditTv();
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
                showTextEditDialog("物品与装备", da.story.getInventory());
                break;
            case R.id.frag_others_all_asset_edit_iv:
                showTextEditDialog("消费水平与资产", da.story.getAsset());
                break;
            case R.id.frag_others_picture_edit_iv:
                showTextEditDialog("形象描述", da.story.getPicture());
                break;
            case R.id.frag_others_faith_edit_iv:
                showTextEditDialog("信仰", da.story.getFaith());
                break;
            case R.id.frag_others_vip_edit_iv:
                showTextEditDialog("重要之人", da.story.getVip());
                break;
            case R.id.frag_others_memorial_place_edit_iv:
                showTextEditDialog("意义非凡之地", da.story.getMemorialPlace());
                break;
            case R.id.frag_others_treasure_edit_iv:
                showTextEditDialog("宝贵之物", da.story.getTreasure());
                break;
            case R.id.frag_others_peculiarity_edit_iv:
                showTextEditDialog("特质", da.story.getPeculiarity());
                break;
            case R.id.frag_others_trauma_edit_iv:
                showTextEditDialog("伤口与疤痕", da.story.getTrauma());
                break;
            case R.id.frag_others_fear_mania_edit_iv:
                showTextEditDialog("恐惧症与躁狂症", da.story.getFearOrMania());
                break;
            case R.id.frag_others_extra_story_edit_iv:
                showTextEditDialog("详细背景故事", da.story.getExtraStory());
                break;
            case R.id.frag_others_note_edit_iv:
                showTextEditDialog("调查员笔记", da.story.getNote());
                break;
            case R.id.frag_others_cthulhu_edit_iv:
                showTextEditDialog("克苏鲁神话相关", da.story.getCthulhuMythos());
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
                    da.story.setInventory(describeText);
                    inventoryTv.setText(describeText);
                } else if (typeName.equals("消费水平与资产")) {
                    da.story.setAsset(describeText);
                    assetTv.setText(describeText);
                } else if (typeName.equals("形象描述")) {
                    da.story.setPicture(describeText);
                    pictureTv.setText(describeText);
                } else if (typeName.equals("信仰")) {
                    da.story.setFaith(describeText);
                    faithTv.setText(describeText);
                } else if (typeName.equals("重要之人")) {
                    da.story.setVip(describeText);
                    vipTv.setText(describeText);
                } else if (typeName.equals("意义非凡之地")) {
                    da.story.setMemorialPlace(describeText);
                    memorialPlaceTv.setText(describeText);
                } else if (typeName.equals("宝贵之物")) {
                    da.story.setTreasure(describeText);
                    treasureTv.setText(describeText);
                } else if (typeName.equals("特质")) {
                    da.story.setPeculiarity(describeText);
                    peculiarityTv.setText(describeText);
                } else if (typeName.equals("伤口与疤痕")) {
                    da.story.setTrauma(describeText);
                    traumaTv.setText(describeText);
                } else if (typeName.equals("恐惧症与躁狂症")) {
                    da.story.setFearOrMania(describeText);
                    fOMTv.setText(describeText);
                } else if (typeName.equals("详细背景故事")) {
                    da.story.setExtraStory(describeText);
                    extraTv.setText(describeText);
                } else if (typeName.equals("调查员笔记")) {
                    da.story.setNote(describeText);
                    noteTv.setText(describeText);
                } else if (typeName.equals("克苏鲁神话相关")) {
                    da.story.setCthulhuMythos(describeText);
                    cthulhuTv.setText(describeText);
                }
                DBManager.updateStory(da.story);
            }
        });
    }

    public void updateCreditTv() {
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
        inventoryTv.setText(da.story.getInventory());
        inventoryIv = v.findViewById(R.id.frag_others_inventory_edit_iv);
        inventoryIv.setOnClickListener(this);
        creditTv = v.findViewById(R.id.frag_others_credit_hint_tv);
        updateCreditTv();
        assetIv = v.findViewById(R.id.frag_others_all_asset_edit_iv);
        assetIv.setOnClickListener(this);
        assetTv = v.findViewById(R.id.frag_others_asset_tv);
        assetTv.setText(da.story.getAsset());
        pictureTv = v.findViewById(R.id.frag_others_picture_tv);
        pictureTv.setText(da.story.getPicture());
        pictureIv = v.findViewById(R.id.frag_others_picture_edit_iv);
        pictureIv.setOnClickListener(this);
        faithTv = v.findViewById(R.id.frag_others_faith_tv);
        faithTv.setText(da.story.getFaith());
        faithIv = v.findViewById(R.id.frag_others_faith_edit_iv);
        faithIv.setOnClickListener(this);
        vipTv = v.findViewById(R.id.frag_others_vip_tv);
        vipTv.setText(da.story.getVip());
        vipIv = v.findViewById(R.id.frag_others_vip_edit_iv);
        vipIv.setOnClickListener(this);
        memorialPlaceTv = v.findViewById(R.id.frag_others_memorial_place_tv);
        memorialPlaceTv.setText(da.story.getMemorialPlace());
        memorialPlaceIv = v.findViewById(R.id.frag_others_memorial_place_edit_iv);
        memorialPlaceIv.setOnClickListener(this);
        treasureTv = v.findViewById(R.id.frag_others_treasure_tv);
        treasureTv.setText(da.story.getTreasure());
        treasureIv = v.findViewById(R.id.frag_others_treasure_edit_iv);
        treasureIv.setOnClickListener(this);
        peculiarityTv = v.findViewById(R.id.frag_others_peculiarity_tv);
        peculiarityTv.setText(da.story.getPeculiarity());
        peculiarityIv = v.findViewById(R.id.frag_others_peculiarity_edit_iv);
        peculiarityIv.setOnClickListener(this);
        traumaTv = v.findViewById(R.id.frag_others_trauma_tv);
        traumaTv.setText(da.story.getTrauma());
        traumaIv = v.findViewById(R.id.frag_others_trauma_edit_iv);
        traumaIv.setOnClickListener(this);
        fOMTv = v.findViewById(R.id.frag_others_fear_mania_tv);
        fOMTv.setText(da.story.getFearOrMania());
        fOMIv = v.findViewById(R.id.frag_others_fear_mania_edit_iv);
        fOMIv.setOnClickListener(this);
        extraTv = v.findViewById(R.id.frag_others_extra_story_tv);
        extraTv.setText(da.story.getExtraStory());
        extraIv = v.findViewById(R.id.frag_others_extra_story_edit_iv);
        extraIv.setOnClickListener(this);
        noteIv = v.findViewById(R.id.frag_others_note_edit_iv);
        noteTv = v.findViewById(R.id.frag_others_note_tv);
        noteTv.setText(da.story.getNote());
        noteIv.setOnClickListener(this);
        cthulhuTv = v.findViewById(R.id.frag_others_cthulhu_tv);
        cthulhuTv.setText(da.story.getCthulhuMythos());
        cthulhuIv = v.findViewById(R.id.frag_others_cthulhu_edit_iv);
        cthulhuIv.setOnClickListener(this);
    }
}
