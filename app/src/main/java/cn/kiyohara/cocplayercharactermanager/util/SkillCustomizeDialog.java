package cn.kiyohara.cocplayercharactermanager.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiyohara.cocplayercharactermanager.R;

public class SkillCustomizeDialog extends Dialog implements View.OnClickListener {
    EditText customEt, initEt;
    TextView titleTv;
    ImageButton ensureBtn;
    String sName, custom;
    int initValue;

    public SkillCustomizeDialog(Context context) {
        super(context);
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public void setInitValue(int initValue) {
        this.initValue = initValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_skill_customize);
        titleTv = findViewById(R.id.dialog_skill_customize_title_tv);
        titleTv.setText(sName);
        ensureBtn = findViewById(R.id.dialog_skill_customize_btn_ensure);
        ensureBtn.setOnClickListener(this);
        customEt = findViewById(R.id.dialog_customize_et);
        customEt.setText(custom);
        initEt = findViewById(R.id.dialog_skill_customize_init_value_et);
        initEt.setText(initValue + "");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_skill_customize_btn_ensure) {
            if (customEt.getText() != null && initEt.getText() != null) {
                custom = String.valueOf(customEt.getText());
                String tempValue = String.valueOf(initEt.getText());
                initValue = Integer.parseInt(tempValue);
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure(custom, initValue);
                }
                cancel();
            }
        }
    }

    public interface OnEnsureListener {
        void onEnsure(String custom, int initValue);
    }

    SkillCustomizeDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(SkillCustomizeDialog.OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setAttributes(wlp);
    }
}
