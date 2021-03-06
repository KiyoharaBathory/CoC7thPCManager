package cn.kiyohara.cocplayercharactermanager.frag_character_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.csz.pick.core.ImagePicker;
import com.csz.pick.core.manager.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.util.AbilityEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.AgeEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.NormalNumberEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.StatSetDialog;
import cn.kiyohara.cocplayercharactermanager.util.TextEditDialog;

public class BasicFragment extends Fragment implements View.OnClickListener {
    public SimpleDraweeView portraitIv;
    TextView nameTv, ageTv, genderTv, professionTv, addressTv, fbTv;
    TextView hpTv, mpTv, sanTv, bodyStatTv, mentalStatTv, movTv, buildTv, dbTv, armorTv;
    TextView strTv, conTv, sizTv, dexTv, appTv, eduTv, intTv, powTv, luckTv;
    DetailActivity da;
    View view;
    int maxHP, maxMP, maxSan;

    public static final int REQUEST_SELECT_IMAGES_CODE = 0x01;
    public ArrayList<String> mImagePaths;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_character_detail_basic, container, false);
        da = (DetailActivity) getActivity();
        updateData();
        initView(view);
        return view;
    }

    private void updateData() {
        SkillBean dodge = da.skillList.get(25);
        SkillBean nativeLanguage = da.skillList.get(47);
        dodge.setInitValue(da.pc.getDexterity() / 2);
        nativeLanguage.setInitValue(da.pc.getEducation());
        da.skillList.set(25, dodge);
        da.skillList.set(47, nativeLanguage);
        maxHP = (da.pc.getConstitution() + da.pc.getSize()) / 10;
        maxMP = da.pc.getWillPower() / 5;
        maxSan = 99 - da.skillList.get(14).getGrowthPoint() - da.skillList.get(14).getProfessionPoint() - da.skillList.get(14).getInterestPoint();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_basic_age_tv:
                showAgeEditDialog();
                break;
            case R.id.frag_basic_str_tv:
                showAbilityEditDialog("??????", da.pc.getStrength());
                break;
            case R.id.frag_basic_con_tv:
                showAbilityEditDialog("??????", da.pc.getConstitution());
                break;
            case R.id.frag_basic_siz_tv:
                showAbilityEditDialog("??????", da.pc.getSize());
                break;
            case R.id.frag_basic_dex_tv:
                showAbilityEditDialog("??????", da.pc.getDexterity());
                break;
            case R.id.frag_basic_app_tv:
                showAbilityEditDialog("??????", da.pc.getAppearance());
                break;
            case R.id.frag_basic_edu_tv:
                showAbilityEditDialog("??????", da.pc.getEducation());
                break;
            case R.id.frag_basic_int_tv:
                showAbilityEditDialog("??????", da.pc.getIntelligence());
                break;
            case R.id.frag_basic_pow_tv:
                showAbilityEditDialog("??????", da.pc.getWillPower());
                break;
            case R.id.frag_basic_luck_tv:
                showAbilityEditDialog("??????", da.pc.getLuck());
                break;
            case R.id.frag_basic_address_tv:
                showTextEditDialog("??????", da.story.getAddress());
                break;
            case R.id.frag_basic_family_background_tv:
                showTextEditDialog("??????", da.story.getFamilyBackground());
                break;
            case R.id.frag_basic_hp_tv:
                showNumberEditDialog("HP", da.pc.getHp(), maxHP);
                break;
            case R.id.frag_basic_mp_tv:
                showNumberEditDialog("MP", da.pc.getMp(), maxMP);
                break;
            case R.id.frag_basic_san_tv:
                showNumberEditDialog("??????", da.pc.getHp(), maxSan);
                break;
            case R.id.frag_basic_armor_tv:
                showNumberEditDialog("??????", da.pc.getHp(), 0);
                break;
            case R.id.frag_basic_stat_body_tv:
                showStatSetDialog("????????????", da.pc.getBodyStat());
                break;
            case R.id.frag_basic_stat_mental_tv:
                showStatSetDialog("????????????", da.pc.getMentalStat());
                break;
            case R.id.frag_basic_portrait_iv:
                ImagePicker.getInstance()
                        .setTitle("????????????")//????????????
                        .showCamera(true)//??????????????????????????????
                        .showImage(true)//????????????????????????
                        .showVideo(false)//????????????????????????
                        .filterGif(true)//??????????????????gif??????
                        .setMaxCount(1)//??????????????????????????????(?????????1?????????)
                        .setSingleType(true)//????????????????????????????????????
                        .setImagePaths(mImagePaths)//????????????????????????
                        .setImageLoader(new MyImageLoader2())//??????????????????????????????
                        .start((DetailActivity) getActivity(), REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE???Intent?????????requestCode
                break;
        }
    }

    public void showStatSetDialog(String typeName, int stat) {
        StatSetDialog dialog = new StatSetDialog(getContext());
        dialog.setTypeName(typeName);
        dialog.setStat(stat);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new StatSetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int stat) {
                if (typeName.equals("????????????")) {
                    da.pc.setBodyStat(stat);
                    int bs = da.pc.getBodyStat();
                    if (bs == 0) {
                        bodyStatTv.setText("??????");
                    } else if (bs == 1) {
                        bodyStatTv.setText("??????");
                    } else if (bs == 2) {
                        bodyStatTv.setText("??????");
                    } else if (bs == 3) {
                        bodyStatTv.setText("??????");
                    } else if (bs == 4) {
                        bodyStatTv.setText("??????");
                    }
                } else if (typeName.equals("????????????")) {
                    da.pc.setMentalStat(stat);
                    int ms = da.pc.getMentalStat();
                    if (ms == 0) {
                        mentalStatTv.setText("??????");
                    } else if (ms == 1) {
                        mentalStatTv.setText("???????????????");
                    } else if (ms == 2) {
                        mentalStatTv.setText("???????????????");
                    } else if (ms == 3) {
                        mentalStatTv.setText("???????????????");
                    }
                }
                DBManager.updateCharacter(da.pc);
            }
        });
    }

    private void showAgeEditDialog() {
        AgeEditDialog dialog = new AgeEditDialog(getContext());
        dialog.show();
        dialog.setDialogSize();
        dialog.setAgeEtNumber(da.pc.getAge());
        dialog.setOnEnsureListener(new AgeEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int ageNumber) {
                da.pc.setAge(ageNumber);
                DBManager.updateCharacter(da.pc);
                ageTv.setText(ageNumber + "???");
            }
        });
    }

    public void showNumberEditDialog(String typeName, int input, int maxValue) {
        NormalNumberEditDialog dialog = new NormalNumberEditDialog(getContext());
        dialog.setTypeName(typeName);
        dialog.setInput(input + "");
        dialog.setMaxValue(maxValue);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new NormalNumberEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int inputValue) {
                if (typeName.equals("HP")) {
                    da.pc.setHp(inputValue);
                    hpTv.setText(inputValue + "/" + maxHP);
                } else if (typeName.equals("MP")) {
                    da.pc.setMp(inputValue);
                    hpTv.setText(inputValue + "/" + maxMP);
                } else if (typeName.equals("??????")) {
                    if (inputValue > maxSan) {
                        inputValue = maxSan;
                    }
                    da.pc.setSanity(inputValue);
                    sanTv.setText(inputValue + "/" + maxSan);
                } else if (typeName.equals("??????")) {
                    da.story.setArmor(inputValue);
                    armorTv.setText(inputValue + "");
                }
                DBManager.updateStory(da.story);
                DBManager.updateCharacter(da.pc);
            }
        });
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
                if (typeName.equals("??????")) {
                    da.story.setAddress(describeText);
                    addressTv.setText(describeText);
                } else if (typeName.equals("??????")) {
                    da.story.setFamilyBackground(describeText);
                    fbTv.setText(describeText);
                }
                DBManager.updateStory(da.story);
            }
        });
    }

    public void showAbilityEditDialog(String typeName, int currentNumber) {
        AbilityEditDialog dialog = new AbilityEditDialog(getContext());
        dialog.setInput(currentNumber);
        dialog.setTypeName(typeName);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new AbilityEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int abilityNumber) {
                if (typeName.equals("??????")) {
                    da.pc.setStrength(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    strTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setConstitution(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    conTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setSize(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    sizTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setDexterity(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    dexTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setAppearance(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    appTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setIntelligence(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    intTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setWillPower(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    powTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setEducation(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    eduTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    da.pc.setLuck(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    luckTv.setText(temp);
                }
                DBManager.updateCharacter(da.pc);
                SkillFragment skillFrag = da.getSkillFragment();
                updateData();
                refreshView();
                skillFrag.updateData();
            }
        });

    }

    private void refreshView() {
        hpTv.setText(da.pc.getHp() + "/" + maxHP);
        mpTv.setText(da.pc.getMp() + "/" + maxMP);
        sanTv.setText(da.pc.getSanity() + "/" + maxSan);
    }

    private void initView(View v) {
        portraitIv = v.findViewById(R.id.frag_basic_portrait_iv);
        portraitIv.setImageURI(da.pc.getPortraitUri());
        portraitIv.setOnClickListener(this);
        nameTv = v.findViewById(R.id.frag_basic_name_tv);
        nameTv.setText(da.pc.getName());
        ageTv = v.findViewById(R.id.frag_basic_age_tv);
        ageTv.setText(da.pc.getAge() + "???");
        ageTv.setOnClickListener(this);
        genderTv = v.findViewById(R.id.frag_basic_gender_tv);
        if (da.pc.getGender() == 1) {
            genderTv.setText("???");
        } else {
            genderTv.setText("???");
        }
        professionTv = v.findViewById(R.id.frag_basic_profession_tv);
        professionTv.setText(da.professionBean.getProfessionName());
        addressTv = v.findViewById(R.id.frag_basic_address_tv);
        addressTv.setText(da.story.getAddress());
        addressTv.setOnClickListener(this);
        fbTv = v.findViewById(R.id.frag_basic_family_background_tv);
        fbTv.setText(da.story.getFamilyBackground());
        fbTv.setOnClickListener(this);
        hpTv = v.findViewById(R.id.frag_basic_hp_tv);
        hpTv.setText(da.pc.getHp() + "/" + maxHP);
        hpTv.setOnClickListener(this);
        mpTv = v.findViewById(R.id.frag_basic_mp_tv);
        mpTv.setText(da.pc.getMp() + "/" + maxMP);
        mpTv.setOnClickListener(this);
        sanTv = v.findViewById(R.id.frag_basic_san_tv);
        sanTv.setText(da.pc.getSanity() + "/" + maxSan);
        sanTv.setOnClickListener(this);
        bodyStatTv = v.findViewById(R.id.frag_basic_stat_body_tv);
        int bs = da.pc.getBodyStat();
        if (bs == 0) {
            bodyStatTv.setText("??????");
        } else if (bs == 1) {
            bodyStatTv.setText("??????");
        } else if (bs == 2) {
            bodyStatTv.setText("??????");
        } else if (bs == 3) {
            bodyStatTv.setText("??????");
        } else if (bs == 4) {
            bodyStatTv.setText("??????");
        }
        bodyStatTv.setOnClickListener(this);
        mentalStatTv = v.findViewById(R.id.frag_basic_stat_mental_tv);
        int ms = da.pc.getMentalStat();
        if (ms == 0) {
            mentalStatTv.setText("??????");
        } else if (ms == 1) {
            mentalStatTv.setText("???????????????");
        } else if (ms == 2) {
            mentalStatTv.setText("???????????????");
        } else if (ms == 3) {
            mentalStatTv.setText("???????????????");
        }
        mentalStatTv.setOnClickListener(this);
        movTv = v.findViewById(R.id.frag_basic_mov_tv);
        int dex = da.pc.getDexterity();
        int siz = da.pc.getSize();
        int str = da.pc.getStrength();
        int age = da.pc.getAge();
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
        movTv.setText(mov + "");
        buildTv = v.findViewById(R.id.frag_basic_build_tv);
        dbTv = v.findViewById(R.id.frag_basic_db_tv);
        int build = str + siz;
        if (build < 65) {
            buildTv.setText("-2");
            dbTv.setText("-2");
        } else if (build < 85) {
            buildTv.setText("-1");
            dbTv.setText("-1");
        } else if (build < 125) {
            buildTv.setText("0");
            dbTv.setText("0");
        } else if (build < 165) {
            buildTv.setText("1");
            dbTv.setText("+1D4");
        } else if (build < 205) {
            buildTv.setText("2");
            dbTv.setText("+1D6");
        } else if (build < 285) {
            buildTv.setText("3");
            dbTv.setText("+2D6");
        } else if (build < 365) {
            buildTv.setText("4");
            dbTv.setText("+3D6");
        } else if (build < 445) {
            buildTv.setText("5");
            dbTv.setText("+4D6");
        } else if (build < 525) {
            buildTv.setText("6");
            dbTv.setText("+5D6");
        } else {
            buildTv.setText("??????");
            dbTv.setText("??????");
        }
        armorTv = v.findViewById(R.id.frag_basic_armor_tv);
        armorTv.setText(da.story.getArmor() + "");
        armorTv.setOnClickListener(this);
        int original, half, extreme;
        strTv = v.findViewById(R.id.frag_basic_str_tv);
        original = da.pc.getStrength();
        half = original / 2;
        extreme = original / 5;
        strTv.setText(original + " / " + half + " / " + extreme);
        strTv.setOnClickListener(this);
        conTv = v.findViewById(R.id.frag_basic_con_tv);
        original = da.pc.getConstitution();
        half = original / 2;
        extreme = original / 5;
        conTv.setText(original + " / " + half + " / " + extreme);
        conTv.setOnClickListener(this);
        sizTv = v.findViewById(R.id.frag_basic_siz_tv);
        original = da.pc.getSize();
        half = original / 2;
        extreme = original / 5;
        sizTv.setText(original + " / " + half + " / " + extreme);
        sizTv.setOnClickListener(this);
        dexTv = v.findViewById(R.id.frag_basic_dex_tv);
        original = da.pc.getDexterity();
        half = original / 2;
        extreme = original / 5;
        dexTv.setText(original + " / " + half + " / " + extreme);
        dexTv.setOnClickListener(this);
        appTv = v.findViewById(R.id.frag_basic_app_tv);
        original = da.pc.getAppearance();
        half = original / 2;
        extreme = original / 5;
        appTv.setText(original + " / " + half + " / " + extreme);
        appTv.setOnClickListener(this);
        eduTv = v.findViewById(R.id.frag_basic_edu_tv);
        original = da.pc.getEducation();
        half = original / 2;
        extreme = original / 5;
        eduTv.setText(original + " / " + half + " / " + extreme);
        eduTv.setOnClickListener(this);
        intTv = v.findViewById(R.id.frag_basic_int_tv);
        original = da.pc.getIntelligence();
        half = original / 2;
        extreme = original / 5;
        intTv.setText(original + " / " + half + " / " + extreme);
        intTv.setOnClickListener(this);
        powTv = v.findViewById(R.id.frag_basic_pow_tv);
        original = da.pc.getWillPower();
        half = original / 2;
        extreme = original / 5;
        powTv.setText(original + " / " + half + " / " + extreme);
        powTv.setOnClickListener(this);
        luckTv = v.findViewById(R.id.frag_basic_luck_tv);
        original = da.pc.getLuck();
        half = original / 2;
        extreme = original / 5;
        luckTv.setText(original + " / " + half + " / " + extreme);
        luckTv.setOnClickListener(this);
    }

    public class MyImageLoader2 implements ImageLoader {

        private RequestOptions mOptions = new RequestOptions()
                .centerCrop()
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(R.drawable.ic_baseline_portrait_24);

        private RequestOptions mPreOptions = new RequestOptions()
                .skipMemoryCache(true);

        @Override
        public void loadImage(ImageView imageView, String imagePath) {
            Glide.with(imageView.getContext()).load(imagePath).apply(mOptions).into(imageView);
        }

        @Override
        public void loadPreImage(ImageView imageView, String imagePath) {
            //????????????
            Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //????????????
            Glide.get((DetailActivity) getActivity().getApplicationContext()).clearMemory();
        }
    }

}
