package cn.kiyohara.cocplayercharactermanager.frag_character_detail;

import android.content.Intent;
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
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.CreateActivity;
import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;
import cn.kiyohara.cocplayercharactermanager.util.AbilityEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.NormalNumberEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.StatSetDialog;
import cn.kiyohara.cocplayercharactermanager.util.TextEditDialog;

public class BasicFragment extends Fragment implements View.OnClickListener {
    public SimpleDraweeView portraitIv;
    TextView nameTv, ageTv, genderTv, professionTv, addressTv, fbTv;
    TextView hpTv, mpTv, sanTv, bodyStatTv, mentalStatTv, movTv, buildTv, dbTv, armorTv;
    TextView strTv, conTv, sizTv, dexTv, appTv, eduTv, intTv, powTv, luckTv;

    public PlayerCharacter pc;
    StoryBean storyBean;
    ProfessionBean professionBean;
    List<SkillBean> skillList;

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
        View view = inflater.inflate(R.layout.frag_character_detail_basic, container, false);
        int pcId = ((DetailActivity) getActivity()).getPcId();
        pc = DBManager.getCharacterById(pcId);
        storyBean = DBManager.getStoryByCharacterId(pcId);
        ProfessionBean bean = new ProfessionBean();
        try {
            professionBean = bean.baseProfessionLoader(getContext(), pc.getpId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        skillList = DBManager.getAllSkillByCharacterId(pcId);
        maxHP = (pc.getConstitution() + pc.getSize()) / 10;
        maxMP = pc.getWillPower() / 5;
        maxSan = 99 - skillList.get(14).getGrowthPoint();
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_basic_str_tv:
                showAbilityEditDialog("力量", pc.getStrength());
                break;
            case R.id.frag_basic_con_tv:
                showAbilityEditDialog("体质", pc.getConstitution());
                break;
            case R.id.frag_basic_siz_tv:
                showAbilityEditDialog("体型", pc.getSize());
                break;
            case R.id.frag_basic_dex_tv:
                showAbilityEditDialog("敏捷", pc.getDexterity());
                break;
            case R.id.frag_basic_app_tv:
                showAbilityEditDialog("外貌", pc.getAppearance());
                break;
            case R.id.frag_basic_edu_tv:
                showAbilityEditDialog("教育", pc.getEducation());
                break;
            case R.id.frag_basic_int_tv:
                showAbilityEditDialog("智力", pc.getIntelligence());
                break;
            case R.id.frag_basic_pow_tv:
                showAbilityEditDialog("意志", pc.getWillPower());
                break;
            case R.id.frag_basic_luck_tv:
                showAbilityEditDialog("幸运", pc.getLuck());
                break;
            case R.id.frag_basic_address_tv:
                showTextEditDialog("住址", storyBean.getAddress());
                break;
            case R.id.frag_basic_family_background_tv:
                showTextEditDialog("出身", storyBean.getFamilyBackground());
                break;
            case R.id.frag_basic_hp_tv:
                showNumberEditDialog("HP", pc.getHp(), maxHP);
                break;
            case R.id.frag_basic_mp_tv:
                showNumberEditDialog("MP", pc.getMp(), maxMP);
                break;
            case R.id.frag_basic_san_tv:
                showNumberEditDialog("理智", pc.getHp(), maxSan);
                break;
            case R.id.frag_basic_armor_tv:
                showNumberEditDialog("护甲", pc.getHp(), 0);
                break;
            case R.id.frag_basic_stat_body_tv:
                showStatSetDialog("健康状态", pc.getBodyStat());
                break;
            case R.id.frag_basic_stat_mental_tv:
                showStatSetDialog("理智状态", pc.getMentalStat());
                break;
            case R.id.frag_basic_portrait_iv:
                ImagePicker.getInstance()
                        .setTitle("选择头像")//设置标题
                        .showCamera(true)//设置是否显示拍照按钮
                        .showImage(true)//设置是否展示图片
                        .showVideo(false)//设置是否展示视频
                        .filterGif(true)//设置是否过滤gif图片
                        .setMaxCount(1)//设置最大选择图片数目(默认为1，单选)
                        .setSingleType(true)//设置图片视频不能同时选择
                        .setImagePaths(mImagePaths)//设置历史选择记录
                        .setImageLoader(new MyImageLoader2())//设置自定义图片加载器
                        .start((DetailActivity)getActivity(), REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode
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
                if (typeName.equals("健康状态")) {
                    pc.setBodyStat(stat);
                    int bs = pc.getBodyStat();
                    if (bs == 0) {
                        bodyStatTv.setText("健康");
                    } else if (bs == 1) {
                        bodyStatTv.setText("昏迷");
                    } else if (bs == 2) {
                        bodyStatTv.setText("重伤");
                    } else if (bs == 3) {
                        bodyStatTv.setText("濒死");
                    } else if (bs == 4) {
                        bodyStatTv.setText("死亡");
                    }
                } else if (typeName.equals("理智状态")) {
                    pc.setMentalStat(stat);
                    int ms = pc.getMentalStat();
                    if (ms == 0) {
                        mentalStatTv.setText("正常");
                    } else if (ms == 1) {
                        mentalStatTv.setText("临时性疯狂");
                    } else if (ms == 2) {
                        mentalStatTv.setText("不定性疯狂");
                    } else if (ms == 3) {
                        mentalStatTv.setText("永久性疯狂");
                    }
                }
                DBManager.updateCharacter(pc);
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
                    pc.setHp(inputValue);
                    hpTv.setText(inputValue + "/" + maxHP);
                } else if (typeName.equals("MP")) {
                    pc.setMp(inputValue);
                    hpTv.setText(inputValue + "/" + maxMP);
                } else if (typeName.equals("理智")) {
                    pc.setSanity(inputValue);
                    sanTv.setText(inputValue + "/" + maxSan);
                } else if (typeName.equals("护甲")) {
                    storyBean.setArmor(inputValue);
                    armorTv.setText(inputValue + "");
                }
                DBManager.updateStory(storyBean);
                DBManager.updateCharacter(pc);
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
                if (typeName.equals("住址")) {
                    storyBean.setAddress(describeText);
                    addressTv.setText(describeText);
                } else if (typeName.equals("出身")) {
                    storyBean.setFamilyBackground(describeText);
                    fbTv.setText(describeText);
                }
                OthersFragment frag = ((DetailActivity)getActivity()).getOthersFragment();
                frag.setStoryBean(storyBean);
                DBManager.updateStory(storyBean);
            }
        });
    }

    public void setStoryBean(StoryBean storyBean) {
        this.storyBean = storyBean;
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
                if (typeName.equals("力量")) {
                    pc.setStrength(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    strTv.setText(temp);
                } else if (typeName.equals("体质")) {
                    pc.setConstitution(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    conTv.setText(temp);
                } else if (typeName.equals("体型")) {
                    pc.setSize(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    sizTv.setText(temp);
                } else if (typeName.equals("敏捷")) {
                    pc.setDexterity(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    dexTv.setText(temp);
                } else if (typeName.equals("外貌")) {
                    pc.setAppearance(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    appTv.setText(temp);
                } else if (typeName.equals("智力")) {
                    pc.setIntelligence(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    intTv.setText(temp);
                } else if (typeName.equals("意志")) {
                    pc.setWillPower(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    powTv.setText(temp);
                } else if (typeName.equals("教育")) {
                    pc.setEducation(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    eduTv.setText(temp);
                } else if (typeName.equals("幸运")) {
                    pc.setLuck(abilityNumber);
                    int half = abilityNumber / 2;
                    int extreme = abilityNumber / 5;
                    String temp = abilityNumber + " / " + half + " / " + extreme;
                    luckTv.setText(temp);
                }
                DBManager.updateCharacter(pc);
                DetailActivity activity = (DetailActivity) getActivity();
                SkillFragment skillFrag = activity.getSkillFragment();
                skillFrag.updateData();
            }
        });

    }

    private void initView(View v) {
        portraitIv = v.findViewById(R.id.frag_basic_portrait_iv);
        portraitIv.setImageURI(pc.getPortraitUri());
        portraitIv.setOnClickListener(this);
        nameTv = v.findViewById(R.id.frag_basic_name_tv);
        nameTv.setText(pc.getName());
        ageTv = v.findViewById(R.id.frag_basic_age_tv);
        ageTv.setText(pc.getAge() + "");
        genderTv = v.findViewById(R.id.frag_basic_gender_tv);
        if (pc.getGender() == 1) {
            genderTv.setText("男");
        } else {
            genderTv.setText("女");
        }
        professionTv = v.findViewById(R.id.frag_basic_profession_tv);
        professionTv.setText(professionBean.getProfessionName());
        addressTv = v.findViewById(R.id.frag_basic_address_tv);
        addressTv.setText(storyBean.getAddress());
        addressTv.setOnClickListener(this);
        fbTv = v.findViewById(R.id.frag_basic_family_background_tv);
        fbTv.setText(storyBean.getFamilyBackground());
        fbTv.setOnClickListener(this);
        hpTv = v.findViewById(R.id.frag_basic_hp_tv);
        hpTv.setText(pc.getHp() + "/" + maxHP);
        hpTv.setOnClickListener(this);
        mpTv = v.findViewById(R.id.frag_basic_mp_tv);
        mpTv.setText(pc.getMp() + "/" + maxMP);
        mpTv.setOnClickListener(this);
        sanTv = v.findViewById(R.id.frag_basic_san_tv);
        sanTv.setText(pc.getSanity() + "/" + maxSan);
        sanTv.setOnClickListener(this);
        bodyStatTv = v.findViewById(R.id.frag_basic_stat_body_tv);
        int bs = pc.getBodyStat();
        if (bs == 0) {
            bodyStatTv.setText("健康");
        } else if (bs == 1) {
            bodyStatTv.setText("昏迷");
        } else if (bs == 2) {
            bodyStatTv.setText("重伤");
        } else if (bs == 3) {
            bodyStatTv.setText("濒死");
        } else if (bs == 4) {
            bodyStatTv.setText("死亡");
        }
        bodyStatTv.setOnClickListener(this);
        mentalStatTv = v.findViewById(R.id.frag_basic_stat_mental_tv);
        int ms = pc.getMentalStat();
        if (ms == 0) {
            mentalStatTv.setText("正常");
        } else if (ms == 1) {
            mentalStatTv.setText("临时性疯狂");
        } else if (ms == 2) {
            mentalStatTv.setText("不定性疯狂");
        } else if (ms == 3) {
            mentalStatTv.setText("永久性疯狂");
        }
        mentalStatTv.setOnClickListener(this);
        movTv = v.findViewById(R.id.frag_basic_mov_tv);
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
            buildTv.setText("破格");
            dbTv.setText("破格");
        }
        armorTv = v.findViewById(R.id.frag_basic_armor_tv);
        armorTv.setText(storyBean.getArmor() + "");
        armorTv.setOnClickListener(this);
        int original, half, extreme;
        strTv = v.findViewById(R.id.frag_basic_str_tv);
        original = pc.getStrength();
        half = original / 2;
        extreme = original / 5;
        strTv.setText(original + " / " + half + " / " + extreme);
        strTv.setOnClickListener(this);
        conTv = v.findViewById(R.id.frag_basic_con_tv);
        original = pc.getConstitution();
        half = original / 2;
        extreme = original / 5;
        conTv.setText(original + " / " + half + " / " + extreme);
        conTv.setOnClickListener(this);
        sizTv = v.findViewById(R.id.frag_basic_siz_tv);
        original = pc.getSize();
        half = original / 2;
        extreme = original / 5;
        sizTv.setText(original + " / " + half + " / " + extreme);
        sizTv.setOnClickListener(this);
        dexTv = v.findViewById(R.id.frag_basic_dex_tv);
        original = pc.getDexterity();
        half = original / 2;
        extreme = original / 5;
        dexTv.setText(original + " / " + half + " / " + extreme);
        dexTv.setOnClickListener(this);
        appTv = v.findViewById(R.id.frag_basic_app_tv);
        original = pc.getAppearance();
        half = original / 2;
        extreme = original / 5;
        appTv.setText(original + " / " + half + " / " + extreme);
        appTv.setOnClickListener(this);
        eduTv = v.findViewById(R.id.frag_basic_edu_tv);
        original = pc.getEducation();
        half = original / 2;
        extreme = original / 5;
        eduTv.setText(original + " / " + half + " / " + extreme);
        eduTv.setOnClickListener(this);
        intTv = v.findViewById(R.id.frag_basic_int_tv);
        original = pc.getIntelligence();
        half = original / 2;
        extreme = original / 5;
        intTv.setText(original + " / " + half + " / " + extreme);
        intTv.setOnClickListener(this);
        powTv = v.findViewById(R.id.frag_basic_pow_tv);
        original = pc.getWillPower();
        half = original / 2;
        extreme = original / 5;
        powTv.setText(original + " / " + half + " / " + extreme);
        powTv.setOnClickListener(this);
        luckTv = v.findViewById(R.id.frag_basic_luck_tv);
        original = pc.getLuck();
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
            //大图加载
            Glide.with(imageView.getContext()).load(imagePath).apply(mPreOptions).into(imageView);
        }

        @Override
        public void clearMemoryCache() {
            //清理缓存
            Glide.get((DetailActivity)getActivity().getApplicationContext()).clearMemory();
        }
    }

}
