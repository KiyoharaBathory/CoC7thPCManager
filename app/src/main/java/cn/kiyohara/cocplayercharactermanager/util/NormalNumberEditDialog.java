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

import cn.kiyohara.cocplayercharactermanager.R;

public class NormalNumberEditDialog extends Dialog implements View.OnClickListener {
    TextView titleTv, hintTv;
    EditText numberEt;
    ImageButton ensureBtn;
    String typeName, input;
    int maxValue;

    public NormalNumberEditDialog(Context context) {
        super(context);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_normal_number_edit);
        titleTv = findViewById(R.id.dialog_normal_number_edit_title_tv);
        titleTv.setText(typeName);
        hintTv = findViewById(R.id.dialog_normal_number_edit_hint);
        setHintTvByTypeName(typeName);
        numberEt = findViewById(R.id.dialog_normal_number_et);
        numberEt.setText(input);
        numberEt.requestFocus();
        ensureBtn = findViewById(R.id.dialog_normal_number_edit_btn_ensure);
        ensureBtn.setOnClickListener(this);
    }

    private void setHintTvByTypeName(String typeName) {
        if (typeName.equals("HP")) {
            hintTv.setText("此调查员的HP的自然恢复上限为" + maxValue);
        } else if (typeName.equals("MP")) {
            hintTv.setText("此调查员的MP的自然恢复上限为" + maxValue);
        } else if (typeName.equals("理智")) {
            hintTv.setText("此调查员的理智上限为" + maxValue);
        } else if (typeName.equals("护甲")) {
            hintTv.setText("如果调查员拥有护甲值，请在其他信息中的“物品与装备”一栏内注明提供护甲值的装备");
        } else if (typeName.equals("本职技能")){
            hintTv.setText("如果守秘人没有特别规定，本职技能点可以分配给信用评级与调查员的各本职技能。");
        } else if (typeName.equals("兴趣技能")){
            hintTv.setText("如果守秘人没有特别规定，兴趣技能点可以分配给除【克苏鲁神话】以外的任意技能。");
        }else if (typeName.equals("成长值")){
            hintTv.setText("技能成长：先掷1D100，若点数大于技能值则再掷1D10，将所得点数写在这里。\n技能值可以通过此方法增加至100点以上，但检定时若掷出100依然算作大失败。");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_normal_number_edit_btn_ensure) {
            if (numberEt.getText() != null) {
                input = String.valueOf(numberEt.getText());
                int inputValue = Integer.parseInt(input);
                if (onEnsureListener != null){
                    onEnsureListener.onEnsure(inputValue);
                    cancel();
                }
            }
        }
    }

    public interface OnEnsureListener {
        void onEnsure(int inputValue);
    }

    NormalNumberEditDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(NormalNumberEditDialog.OnEnsureListener onEnsureListener) {
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
