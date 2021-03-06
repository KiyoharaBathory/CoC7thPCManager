package cn.kiyohara.cocplayercharactermanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.csz.pick.core.ImagePicker;
import com.csz.pick.core.manager.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.db.DBManager;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;
import cn.kiyohara.cocplayercharactermanager.util.AbilityEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.AgeEditDialog;
import cn.kiyohara.cocplayercharactermanager.util.AgeEffectDialog;
import cn.kiyohara.cocplayercharactermanager.util.ProfessionSelectActivity;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    SimpleDraweeView portraitIv;
    ImageView cancelIv;
    RadioGroup genderRadioGroup;
    EditText nameEt;
    TextView ageTv, strTv, conTv, sizTv, dexTv, appTv, eduTv, intTv, powTv, luckTv, professionTv, totalAbilityTv;
    Button autoBtn, ageEffectBtn, professionBtn, finishBtn;
    PlayerCharacter pc;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private static final int REQUEST_SELECT_IMAGES_CODE = 0x01;
    private ArrayList<String> mImagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        pc = new PlayerCharacter();
        sp = this.getSharedPreferences("professionSelect", 0);
        editor = sp.edit();
        editor.putInt("pId", -1);
        editor.putString("pName", "???????????????");
        editor.commit();
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_actionbar_iv_cancel:
                finish();
                break;
            case R.id.create_iv_portrait:
                ImagePicker.getInstance()
                        .setTitle("????????????")//????????????
                        .showCamera(true)//??????????????????????????????
                        .showImage(true)//????????????????????????
                        .showVideo(false)//????????????????????????
                        .filterGif(true)//??????????????????gif??????
                        .setMaxCount(1)//??????????????????????????????(?????????1?????????)
                        .setSingleType(true)//????????????????????????????????????
                        .setImagePaths(mImagePaths)//????????????????????????
                        .setImageLoader(new MyImageLoader())//??????????????????????????????
                        .start(CreateActivity.this, REQUEST_SELECT_IMAGES_CODE);//REQEST_SELECT_IMAGES_CODE???Intent?????????requestCode
                break;
            case R.id.create_button_finish:
                if (pc.getName() == null) {
                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                } else if (pc.getpId() == -1) {
                    Toast.makeText(this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    int hp = (pc.getConstitution() + pc.getSize()) / 10;
                    int mp = pc.getWillPower() / 5;
                    int san = pc.getWillPower();
                    pc.setHp(hp);
                    pc.setMp(mp);
                    pc.setSanity(san);
                    DBManager.insertCharacter(pc);
                    int tempId = DBManager.getCharacterIdByName(pc.getName());
                    SkillBean skillBean = new SkillBean();
                    List<SkillBean> newSkillList = new ArrayList<>();
                    try {
                        newSkillList = skillBean.getNewSkillList(this, tempId);
                        SkillBean dodge = newSkillList.get(25);
                        SkillBean nativeLanguage = newSkillList.get(47);
                        dodge.setInitValue(pc.getDexterity()/2);
                        nativeLanguage.setInitValue(pc.getEducation());
                        newSkillList.set(25,dodge);
                        newSkillList.set(47,nativeLanguage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    StoryBean story = new StoryBean(tempId);
                    DBManager.insertSkillList(newSkillList);
                    DBManager.insertStory(story);
                    finish();
                }
                break;
            case R.id.create_button_ability_auto:
                autoSetAllAbility();
                break;
            case R.id.create_edit_age:
                showAgeEditDialog();
                break;
            case R.id.create_edit_ability_str:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_con:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_siz:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_dex:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_app:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_edu:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_int:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_pow:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_edit_ability_luck:
                showAbilityEditDialog("??????");
                break;
            case R.id.create_button_profession_select:
                Intent intent = new Intent(this, ProfessionSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.create_button_ability_check_age_effect:
                showAgeEffectDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGES_CODE && resultCode == RESULT_OK) {
            mImagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
            String portrait = "file://"+mImagePaths.get(0);
            pc.setPortraitUri(portrait);
            portraitIv.setImageURI(portrait);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        professionTv.setText(sp.getString("pName", "???????????????"));
        pc.setpId(sp.getInt("pId", -1));
    }

    public void showAgeEditDialog() {
        AgeEditDialog dialog = new AgeEditDialog(CreateActivity.this);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new AgeEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int ageNumber) {
                pc.setAge(ageNumber);
                String temp = "" + ageNumber;
                ageTv.setText(temp);
                nameEt.clearFocus();
            }
        });
    }

    public void showAbilityEditDialog(String typeName) {
        AbilityEditDialog dialog = new AbilityEditDialog(CreateActivity.this);
        dialog.setTypeName(typeName);
        dialog.show();
        dialog.setDialogSize();
        dialog.setOnEnsureListener(new AbilityEditDialog.OnEnsureListener() {
            @Override
            public void onEnsure(int abilityNumber) {
                if (typeName.equals("??????")) {
                    pc.setStrength(abilityNumber);
                    String temp = "" + abilityNumber;
                    strTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setConstitution(abilityNumber);
                    String temp = "" + abilityNumber;
                    conTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setSize(abilityNumber);
                    String temp = "" + abilityNumber;
                    sizTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setDexterity(abilityNumber);
                    String temp = "" + abilityNumber;
                    dexTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setAppearance(abilityNumber);
                    String temp = "" + abilityNumber;
                    appTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setIntelligence(abilityNumber);
                    String temp = "" + abilityNumber;
                    intTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setWillPower(abilityNumber);
                    String temp = "" + abilityNumber;
                    powTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setEducation(abilityNumber);
                    String temp = "" + abilityNumber;
                    eduTv.setText(temp);
                } else if (typeName.equals("??????")) {
                    pc.setLuck(abilityNumber);
                    String temp = "" + abilityNumber;
                    luckTv.setText(temp);
                }
                int temp = pc.getStrength() + pc.getConstitution() + pc.getSize() + pc.getDexterity() + pc.getAppearance() + pc.getIntelligence() + pc.getWillPower() + pc.getEducation();
                totalAbilityTv.setText("" + temp);
            }
        });

    }

    public void autoSetAllAbility() {
        int tempInt;
        String tempString;
        AbilityEditDialog dialog = new AbilityEditDialog(CreateActivity.this);
        tempInt = dialog.randomAbility("??????");
        pc.setStrength(tempInt);
        tempString = "" + tempInt;
        strTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setConstitution(tempInt);
        tempString = "" + tempInt;
        conTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setSize(tempInt);
        tempString = "" + tempInt;
        sizTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setDexterity(tempInt);
        tempString = "" + tempInt;
        dexTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setAppearance(tempInt);
        tempString = "" + tempInt;
        appTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setEducation(tempInt);
        tempString = "" + tempInt;
        eduTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setIntelligence(tempInt);
        tempString = "" + tempInt;
        intTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setWillPower(tempInt);
        tempString = "" + tempInt;
        powTv.setText(tempString);
        tempInt = dialog.randomAbility("??????");
        pc.setLuck(tempInt);
        tempString = "" + tempInt;
        luckTv.setText(tempString);
        int temp = pc.getStrength() + pc.getConstitution() + pc.getSize() + pc.getDexterity() + pc.getAppearance() + pc.getIntelligence() + pc.getWillPower() + pc.getEducation();
        totalAbilityTv.setText("" + temp);
    }

    public void showAgeEffectDialog() {
        AgeEffectDialog dialog = new AgeEffectDialog(CreateActivity.this);
        dialog.setAge(pc.getAge());
        dialog.setEdu(pc.getEducation());
        dialog.show();
        dialog.setDialogSize();
    }

    private void initView() {
        portraitIv = findViewById(R.id.create_iv_portrait);
        portraitIv.setOnClickListener(this);
        cancelIv = findViewById(R.id.create_actionbar_iv_cancel);
        ageTv = findViewById(R.id.create_edit_age);
        genderRadioGroup = findViewById(R.id.create_radio_group_gender);
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.create_radio_gender_btn_male:
                        pc.setGender(1);
                        break;
                    case R.id.create_radio_gender_btn_female:
                        pc.setGender(0);
                        break;
                }
            }
        });
        nameEt = findViewById(R.id.create_edit_name);
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                pc.setName(temp);
            }
        });
        strTv = findViewById(R.id.create_edit_ability_str);
        conTv = findViewById(R.id.create_edit_ability_con);
        sizTv = findViewById(R.id.create_edit_ability_siz);
        dexTv = findViewById(R.id.create_edit_ability_dex);
        appTv = findViewById(R.id.create_edit_ability_app);
        eduTv = findViewById(R.id.create_edit_ability_edu);
        intTv = findViewById(R.id.create_edit_ability_int);
        powTv = findViewById(R.id.create_edit_ability_pow);
        luckTv = findViewById(R.id.create_edit_ability_luck);
        totalAbilityTv = findViewById(R.id.create_hint_tv_total_ability_number);
        professionTv = findViewById(R.id.create_tv_selected_profession);
        professionTv.setText("???????????????");
        autoBtn = findViewById(R.id.create_button_ability_auto);
        ageEffectBtn = findViewById(R.id.create_button_ability_check_age_effect);
        professionBtn = findViewById(R.id.create_button_profession_select);
        finishBtn = findViewById(R.id.create_button_finish);
    }

    public class MyImageLoader implements ImageLoader {

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
            Glide.get(CreateActivity.this.getApplicationContext()).clearMemory();
        }
    }
}
