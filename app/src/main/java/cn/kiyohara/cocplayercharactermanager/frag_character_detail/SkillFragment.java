package cn.kiyohara.cocplayercharactermanager.frag_character_detail;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.adapter.SkillListAdapter;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.util.NormalNumberEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.SkillCustomizeDialog;

public class SkillFragment extends Fragment implements View.OnClickListener {
    TextView titleTv, majorsHintTv, selectedTv, professionPoint, interestPoint, growthPoint;
    Button allocatePP, allocateIP, growBtn, customizeBtn;
    LinearLayout titleLayout;
    ImageView showHideTitle;
    ListView skillLv;
    DetailActivity da;
    SkillListAdapter adapter;
    int pcId;
    int selectedPos;
    int maxCredit, minCredit;
    ProfessionBean profession;
    SkillBean selected;
    boolean hideTitle = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_character_detail_skills, container, false);
        da = (DetailActivity) getActivity();
        pcId = da.pc.getId();
        profession = da.professionBean;
        maxCredit = profession.getMaxCredit();
        minCredit = profession.getMinCredit();
        da.skillList = DBManager.getAllSkillByCharacterId(pcId);
        selected = da.skillList.get(0);
        selectedPos = 0;
        initView(view);
        setLVListener();
        return view;
    }

    public void setLVListener() {
        skillLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = da.skillList.get(position);
                selectedPos = position;
                refreshTitleView();
                if (hideTitle) {
                    setTitleShow();
                }
                if (selected.getCustomize() != null) {
                    customizeBtn.setVisibility(0);
                } else {
                    customizeBtn.setVisibility(8);
                }
            }
        });
    }

    public void updateData() {
        refreshTitleView();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_skills_hide_title:
                if (hideTitle) {
                    setTitleShow();
                } else {
                    setTitleHide();
                }
                break;
            case R.id.frag_skills_skill_point_allocate:
                showSPEditDialog("本职技能", selected.getProfessionPoint());
                break;
            case R.id.frag_skills_interest_point_allocate:
                showSPEditDialog("兴趣技能", selected.getInterestPoint());
                break;
            case R.id.frag_skills_grow_btn:
                showSPEditDialog("成长值", 0);
                break;
            case R.id.frag_skills_customize_btn:
                String sName = selected.getsName();
                String custom = selected.getCustomize();
                int initValue = selected.getInitValue();
                showSkillCustomizeDialog(sName, custom, initValue);
                break;
        }
    }

    public void initView(View v) {
        titleLayout = v.findViewById(R.id.frag_skills_title_layout);
        titleTv = v.findViewById(R.id.frag_skills_title_tv);
        titleTv.setText(profession.getProfessionName());
        majorsHintTv = v.findViewById(R.id.frag_skills_majors_hint_tv);
        majorsHintTv.setText("此职业的信用评级范围：" + minCredit + "~" + maxCredit + "\n本职技能：" + profession.getMajorSkills() + "\n如未经守秘人允许，请勿将技能点分配给克苏鲁神话技能。");
        selectedTv = v.findViewById(R.id.frag_skills_selected_skill_tv);
        professionPoint = v.findViewById(R.id.frag_skills_skill_point_hint);
        interestPoint = v.findViewById(R.id.frag_skills_interest_point_hint);
        allocatePP = v.findViewById(R.id.frag_skills_skill_point_allocate);
        allocatePP.setOnClickListener(this);
        allocateIP = v.findViewById(R.id.frag_skills_interest_point_allocate);
        allocateIP.setOnClickListener(this);
        growthPoint = v.findViewById(R.id.frag_skills_growth_point_hint);
        growBtn = v.findViewById(R.id.frag_skills_grow_btn);
        growBtn.setOnClickListener(this);
        customizeBtn = v.findViewById(R.id.frag_skills_customize_btn);
        customizeBtn.setOnClickListener(this);
        showHideTitle = v.findViewById(R.id.frag_skills_hide_title);
        showHideTitle.setOnClickListener(this);
        skillLv = v.findViewById(R.id.frag_skills_list);
        adapter = new SkillListAdapter(getContext(), da.skillList, da.pc);
        skillLv.setAdapter(adapter);
        refreshTitleView();
    }

    public void refreshTitleView() {
        if (selected.getCustomize() == null) {
            selectedTv.setText("当前选中：" + selected.getsName());
        } else {
            selectedTv.setText("当前选中：" + selected.getsName() + ":" + selected.getCustomize());
        }
        String algo = profession.getSkillPointAlgorithm();
        int initPP = 0;
        if (algo.equals("EDU×4")) {
            initPP = da.pc.getEducation() * 4;
        } else if (algo.equals("EDU×2+DEX×2")) {
            initPP = (da.pc.getEducation() * 2) + (da.pc.getDexterity() * 2);
        } else if (algo.equals("EDU×2+APP×2")) {
            initPP = (da.pc.getEducation() * 2) + (da.pc.getAppearance() * 2);
        } else if (algo.equals("EDU×2+STR/DEX×2")) {
            if (da.pc.getStrength() > da.pc.getDexterity()) {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getStrength() * 2);
            } else {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getDexterity() * 2);
            }
        } else if (algo.equals("EDU×2+APP/POW×2")) {
            if (da.pc.getAppearance() > da.pc.getWillPower()) {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getAppearance() * 2);
            } else {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getWillPower() * 2);
            }
        } else if (algo.equals("EDU×2+DEX/POW×2")) {
            if (da.pc.getDexterity() > da.pc.getWillPower()) {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getDexterity() * 2);
            } else {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getWillPower() * 2);
            }
        } else if (algo.equals("EDU×2+STR×2")) {
            initPP = (da.pc.getEducation() * 2) + (da.pc.getStrength() * 2);
        } else if (algo.equals("EDU×2+DEX/APP×2")) {
            if (da.pc.getDexterity() > da.pc.getAppearance()) {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getDexterity() * 2);
            } else {
                initPP = (da.pc.getEducation() * 2) + (da.pc.getAppearance() * 2);
            }
        } else if (algo.equals("EDU×2+APP/DEX/STR×2")) {
            int pp1 = (da.pc.getEducation() * 2) + (da.pc.getAppearance() * 2);
            int pp2 = (da.pc.getEducation() * 2) + (da.pc.getDexterity() * 2);
            int pp3 = (da.pc.getEducation() * 2) + (da.pc.getStrength() * 2);
            int pp4 = Math.max(pp1, pp2);
            initPP = Math.max(pp3, pp4);
        }
        int currentPP;
        int usedPP = 0;
        for (int i = 0; i < da.skillList.size(); i++) {
            usedPP = usedPP + da.skillList.get(i).getProfessionPoint();
        }
        currentPP = initPP - usedPP;
        professionPoint.setText("剩余本职点:" + currentPP + "，此技能:" + selected.getProfessionPoint());
        int initIP = da.pc.getIntelligence() * 2;
        int usedIP = 0;
        for (int i = 0; i < da.skillList.size(); i++) {
            usedIP = usedIP + da.skillList.get(i).getInterestPoint();
        }
        int currentIP = initIP - usedIP;
        interestPoint.setText("剩余兴趣点:" + currentIP + "，此技能:" + selected.getInterestPoint());
        int gp = selected.getGrowthPoint();
        int totalSP = gp + selected.getInterestPoint() + selected.getProfessionPoint() + selected.getInitValue();
        growthPoint.setText("当前成长值:" + gp + "，总值:" + totalSP);
    }

    private void showSkillCustomizeDialog(String sName, String custom, int initValue) {
        SkillCustomizeDialog dialog = new SkillCustomizeDialog(getContext());
        dialog.setsName(sName);
        dialog.setCustom(custom);
        dialog.setInitValue(initValue);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new SkillCustomizeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String custom, int initValue) {
                selected.setCustomize(custom);
                selected.setInitValue(initValue);
                refreshTitleView();
                DBManager.updateSkill(selected);
                da.skillList.set(selectedPos, selected);
            }
        });
    }

    private void showSPEditDialog(String typeName, int input) {
        NormalNumberEditDialog dialog = new NormalNumberEditDialog(getContext());
        dialog.setTypeName(typeName);
        dialog.setInput(input + "");
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new NormalNumberEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int inputValue) {
                if (typeName.equals("本职技能")) {
                    selected.setProfessionPoint(inputValue);
                } else if (typeName.equals("兴趣技能")) {
                    selected.setInterestPoint(inputValue);
                } else if (typeName.equals("成长值")) {
                    int grow = selected.getGrowthPoint() + inputValue;
                    selected.setGrowthPoint(grow);
                }
                DBManager.updateSkill(selected);
                da.skillList.set(selectedPos, selected);
                refreshTitleView();
                adapter.notifyDataSetChanged();
                if (selected.getsId() == 34) {
                    OthersFragment frag = da.getOthersFragment();
                    frag.setCreditBean(selected);
                }
            }
        });
    }

    private void setTitleShow() {
        titleLayout.setVisibility(0);
        hideTitle = false;
        showHideTitle.setImageResource(R.drawable.ic_arrow_up);
    }

    private void setTitleHide() {
        titleLayout.setVisibility(8);
        hideTitle = true;
        showHideTitle.setImageResource(R.drawable.ic_arrow_down);
    }
}
