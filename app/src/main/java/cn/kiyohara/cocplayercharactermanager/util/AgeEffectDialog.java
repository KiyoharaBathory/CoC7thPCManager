package cn.kiyohara.cocplayercharactermanager.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Random;

import cn.kiyohara.cocplayercharactermanager.R;

public class AgeEffectDialog extends Dialog implements View.OnClickListener {
    ImageButton ensureBtn;
    TextView titleTv, effectTv, pleaseTv;
    int age = 15;
    int edu = 0;

    public AgeEffectDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_age_effect_check);
        ensureBtn = findViewById(R.id.dialog_profession_select_btn_ensure);
        ensureBtn.setOnClickListener(this::onClick);
        titleTv = findViewById(R.id.dialog_age_effect_age_tv);
        effectTv = findViewById(R.id.dialog_age_effect_effect_tv);
        pleaseTv = findViewById(R.id.dialog_age_effect_hint_please);
        textSet(age, edu);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEdu(int edu) {
        this.edu = edu;
    }

    public void textSet(int age, int edu) {
        Random random = new Random();
        String temp, s;
        int dice, eduAdd;
        if ((15 <= age) && (age <= 19)) {
            titleTv.setText("15~19岁");
            effectTv.setText("力量与体型合计减少 5 点\n教育减少 5 点\n决定幸运值时可以掷骰 2 次取较好的一次");
            pleaseTv.setText("请按照提示自行修改属性值");
        } else if ((20 <= age) && (age <= 39)) {
            titleTv.setText("20~39岁");
            dice = random.nextInt(100) + 1;
            if (dice > edu) {
                s = "成功";
                eduAdd = random.nextInt(10) + 1;
                edu = edu + eduAdd;
            } else {
                s = "失败";
                eduAdd = 0;
            }
            temp = "进行 1 次教育增强检定：\n第 1 次： " + s + " ，教育值增加 " + eduAdd + " 点\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else if ((40 <= age) && (age <= 49)) {
            titleTv.setText("40~49岁");
            temp = "力量、体质、敏捷合计减少 5 点\n外貌减少 5 点\n进行 2 次教育增强检定";
            for (int i = 1; i < 3; i++) {
                dice = random.nextInt(100) + 1;
                if (dice > edu) {
                    s = "成功";
                    eduAdd = random.nextInt(10) + 1;
                    edu = edu + eduAdd;
                    if (edu > 99) {
                        edu = 99;
                    }
                } else {
                    s = "失败";
                    eduAdd = 0;
                }
                temp = temp + "\n第 " + i + " 次： " + s + " ，教育值增加 " + eduAdd + " 点";
            }
            temp = temp + "\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else if ((50 <= age) && (age <= 59)) {
            titleTv.setText("50~59岁");
            temp = "力量、体质、敏捷合计减少 10 点\n外貌减少 10 点\n进行 3 次教育增强检定";
            for (int i = 1; i < 4; i++) {
                dice = random.nextInt(100) + 1;
                if (dice > edu) {
                    s = "成功";
                    eduAdd = random.nextInt(10) + 1;
                    edu = edu + eduAdd;
                    if (edu > 99) {
                        edu = 99;
                    }
                } else {
                    s = "失败";
                    eduAdd = 0;
                }
                temp = temp + "\n第 " + i + " 次： " + s + " ，教育值增加 " + eduAdd + " 点";
            }
            temp = temp + "\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else if ((60 <= age) && (age <= 69)) {
            titleTv.setText("60~69岁");
            temp = "力量、体质、敏捷合计减少 20 点\n外貌减少 15 点\n进行 4 次教育增强检定";
            for (int i = 1; i < 5; i++) {
                dice = random.nextInt(100) + 1;
                if (dice > edu) {
                    s = "成功";
                    eduAdd = random.nextInt(10) + 1;
                    edu = edu + eduAdd;
                    if (edu > 99) {
                        edu = 99;
                    }
                } else {
                    s = "失败";
                    eduAdd = 0;
                }
                temp = temp + "\n第 " + i + " 次： " + s + " ，教育值增加 " + eduAdd + " 点";
            }
            temp = temp + "\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else if ((70 <= age) && (age <= 79)) {
            titleTv.setText("70~79岁");
            temp = "力量、体质、敏捷合计减少 40 点\n外貌减少 20 点\n进行 4 次教育增强检定";
            for (int i = 1; i < 5; i++) {
                dice = random.nextInt(100) + 1;
                if (dice > edu) {
                    s = "成功";
                    eduAdd = random.nextInt(10) + 1;
                    edu = edu + eduAdd;
                    if (edu > 99) {
                        edu = 99;
                    }
                } else {
                    s = "失败";
                    eduAdd = 0;
                }
                temp = temp + "\n第 " + i + " 次： " + s + " ，教育值增加 " + eduAdd + " 点";
            }
            temp = temp + "\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else if ((80 <= age) && (age <= 89)) {
            titleTv.setText("80~89岁");
            temp = "力量、体质、敏捷合计减少 80 点\n外貌减少 25 点\n进行 4 次教育增强检定";
            for (int i = 1; i < 5; i++) {
                dice = random.nextInt(100) + 1;
                if (dice > edu) {
                    s = "成功";
                    eduAdd = random.nextInt(10) + 1;
                    edu = edu + eduAdd;
                    if (edu > 99) {
                        edu = 99;
                    }
                } else {
                    s = "失败";
                    eduAdd = 0;
                }
                temp = temp + "\n第 " + i + " 次： " + s + " ，教育值增加 " + eduAdd + " 点";
            }
            temp = temp + "\n最终教育值应为： " + edu + " 点";
            effectTv.setText(temp);
        } else {
            titleTv.setText("设定年龄超出通常范围");
            effectTv.setText("请征求守秘人的意见来进行属性调整");
            pleaseTv.setText(" ");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_profession_select_btn_ensure) {
            cancel();
        }
    }

    public void setDialogSize() {
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setAttributes(wlp);
    }

}
