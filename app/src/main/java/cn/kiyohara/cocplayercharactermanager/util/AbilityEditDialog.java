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

import androidx.annotation.NonNull;

import java.util.Random;

import cn.kiyohara.cocplayercharactermanager.R;

public class AbilityEditDialog extends Dialog implements View.OnClickListener {
    EditText abilityEt;
    TextView titleTv, hintTv;
    ImageButton ensureBtn, randomBtn;
    int input;
    String typeName;

    public AbilityEditDialog(@NonNull Context context) {
        super(context);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setInput(int input) {
        this.input = input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_edit);
        abilityEt = findViewById(R.id.dialog_number_et);
        abilityEt.setText(input+"");
        abilityEt.requestFocus();
        titleTv = findViewById(R.id.dialog_number_edit_title_tv);
        titleTv.setText(typeName + "：");
        hintTv = findViewById(R.id.dialog_number_edit_hint);
        if (typeName.equals("体型") | typeName.equals("智力") | typeName.equals("教育")) {
            hintTv.setText(typeName + "计算方式：(2D6+6)×5");
        } else {
            hintTv.setText(typeName + "计算方式：3D6×5");
        }
        ensureBtn = findViewById(R.id.dialog_profession_select_btn_ensure);
        ensureBtn.setOnClickListener(this);
        randomBtn = findViewById(R.id.dialog_profession_select_btn_random);
        randomBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_profession_select_btn_ensure:
                if (abilityEt.getText() != null) {
                    String temp = String.valueOf(abilityEt.getText());
                    input = Integer.parseInt(temp);
                    if (onEnsureListener != null) {
                        onEnsureListener.onEnsure(input);
                        cancel();
                    }
                }
                break;
            case R.id.dialog_profession_select_btn_random:
                input = randomAbility(typeName);
                String temp = "" + input;
                abilityEt.setText(temp);
                break;
        }
    }

    public interface OnEnsureListener {
        public void onEnsure(int abilityNumber);
    }

    AbilityEditDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(AbilityEditDialog.OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public int randomAbility(String typeName) {
        Random random = new Random();
        int tempNumber;
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        if (typeName.equals("体型") | typeName.equals("智力") | typeName.equals("教育")) {
            tempNumber = dice1 * 5 + dice2 * 5 + 30;
        } else {
            tempNumber = (dice1 + dice2 + dice3) * 5;
        }
        return tempNumber;
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
