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

public class AgeEditDialog extends Dialog implements View.OnClickListener {
    EditText ageEt;
    TextView titleTv,hintTv;
    ImageButton ensureBtn, randomBtn;
    int ageInputted;

    public AgeEditDialog(@NonNull Context context) {
        super(context);
    }

    public void setAgeEtNumber(int age){
        ageEt.setText(age+"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number_edit);
        ageEt = findViewById(R.id.dialog_number_et);
        ageEt.requestFocus();
        titleTv = findViewById(R.id.dialog_number_edit_title_tv);
        titleTv.setText("年龄：");
        hintTv = findViewById(R.id.dialog_number_edit_hint);
        hintTv.setText("调查员年龄通常会设定在15-89岁\n如果要超出这个范围，请先征求守秘人意见");
        ensureBtn = findViewById(R.id.dialog_profession_select_btn_ensure);
        ensureBtn.setOnClickListener(this::onClick);
        randomBtn = findViewById(R.id.dialog_profession_select_btn_random);
        randomBtn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_profession_select_btn_ensure:
                if (ageEt.getText() != null) {
                    String inputTemp = String.valueOf(ageEt.getText());
                    ageInputted = Integer.parseInt(inputTemp);
                    if (onEnsureListener != null) {
                        onEnsureListener.onEnsure(ageInputted);
                        cancel();
                    }
                }
                break;
            case R.id.dialog_profession_select_btn_random:
                Random random = new Random();
                ageInputted = random.nextInt(76) + 15;
                String eTemp = "" + ageInputted;
                ageEt.setText(eTemp);
        }
    }

    public interface OnEnsureListener {
        public void onEnsure(int ageNumber);
    }

    AgeEditDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(AgeEditDialog.OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
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
