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
import android.widget.Toast;

import androidx.annotation.NonNull;

import cn.kiyohara.cocplayercharactermanager.DetailActivity;
import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;

public class NewSkillDialog extends Dialog implements View.OnClickListener {
    EditText sNameEt, customEt, initEt;
    ImageButton ensureBtn;
    String sName, custom;
    int initValue;
    SkillBean bean = new SkillBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_new_skill);
        sNameEt = findViewById(R.id.dialog_new_skill_sName_et);
        customEt = findViewById(R.id.dialog_new_skill_custom_et);
        initEt = findViewById(R.id.dialog_new_skill_init_et);
        initEt.setText("0");
        ensureBtn = findViewById(R.id.dialog_new_skill_ensure);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_new_skill_ensure) {
            if (sNameEt.getText() != null && initEt.getText() != null) {
                sName = String.valueOf(sNameEt.getText());
                bean.setsName(sName);
                initValue = Integer.parseInt(String.valueOf(initEt.getText()));
                bean.setInitValue(initValue);
                custom = String.valueOf(customEt.getText());
                if (!custom.equals("")) {
                    bean.setCustomize(custom);
                } else {
                    bean.setCustomize(null);
                }
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure(bean);
                }
                cancel();
            } else {
                Toast.makeText(getContext(), "信息不完整！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface OnEnsureListener {
        void onEnsure(SkillBean bean);
    }

    NewSkillDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(NewSkillDialog.OnEnsureListener onEnsureListener) {
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

    public NewSkillDialog(@NonNull Context context) {
        super(context);
    }
}
