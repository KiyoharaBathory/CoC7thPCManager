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

public class TextEditDialog extends Dialog implements View.OnClickListener {
    EditText textEt;
    TextView titleTv, hintTv;
    ImageButton ensureBtn;
    String typeName, input;

    public TextEditDialog(Context context) {
        super(context);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_text_edit);
        textEt = findViewById(R.id.dialog_text_et);
        textEt.setText(input);
        textEt.requestFocus();
        titleTv = findViewById(R.id.dialog_text_edit_title_tv);
        titleTv.setText(typeName);
        hintTv = findViewById(R.id.dialog_text_edit_hint);
        setHintTvByTypeName(typeName);
        ensureBtn = findViewById(R.id.dialog_text_edit_btn_ensure);
        ensureBtn.setOnClickListener(this);
    }

    private void setHintTvByTypeName(String typeName) {
        if (typeName.equals("居住地")) {
            hintTv.setText("请设定调查员目前居住的城市。");
        } else if (typeName.equals("出身")) {
            hintTv.setText("请设定调查员的出生成长的地方，可以与居住地一致。");
        } else if (typeName.equals("物品与装备")) {
            hintTv.setText("请在此填写调查员随身携带的现金和物品、装备(神话相关物品请填写在”克苏鲁神话相关“一栏中。)，武器需要标明使用技能种类、伤害、射程、弹药以及故障值，护甲值需要在基本信息中修改。");
        } else if (typeName.equals("消费水平与资产")) {
            hintTv.setText("请在此填写调查员的消费水平，以及除随身物品外调查员所拥有的资产。");
        } else if (typeName.equals("形象描述")) {
            hintTv.setText("请用简短的语言描述调查员的外貌。");
        } else if (typeName.equals("信仰")) {
            hintTv.setText("如宗教、意识形态、哲学思想等。");
        } else if (typeName.equals("重要之人")) {
            hintTv.setText("请如亲人、朋友、名人、其他PC或NPC等。");
        } else if (typeName.equals("意义非凡之地")) {
            hintTv.setText("如学校、工作场所等。");
        } else if (typeName.equals("宝贵之物")) {
            hintTv.setText("如职业相关物品、个人收藏品、传家宝等。");
        } else if (typeName.equals("特质")) {
            hintTv.setText("如“吃货”、“爱猫人士”、“硬核游戏玩家”等。");
        } else if (typeName.equals("伤口与疤痕")) {
            hintTv.setText("如没有，填写“无”即可。");
        } else if (typeName.equals("恐惧症与躁狂症")) {
            hintTv.setText("如没有，填写“无”即可。");
        } else if (typeName.equals("详细背景故事")) {
            hintTv.setText("首先请确定调查员的【关键背景连接】（参照七版规则书第三章第四节-关键背景连接），之后请任意发挥想象，给调查员一个背景故事。");
        } else if (typeName.equals("调查员笔记")) {
            hintTv.setText("把这里看作是游戏内调查员的随身笔记本。");
        } else if (typeName.equals("克苏鲁神话相关")) {
            hintTv.setText("请将调查员拥有的克苏鲁神话相关典籍、魔法物品以及经历过的第三类接触（目击）记录在这里。");
        } else if (typeName.equals("住址")) {
            hintTv.setText("请设定调查员当前的住址(精确到城市)");
        } else if (typeName.equals("出身")) {
            hintTv.setText("请设定调查员出生/成长的地方(精确到城市)");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_text_edit_btn_ensure) {
            if (textEt.getText() != null) {
                input = String.valueOf(textEt.getText());
            } else {
                input = "";
            }
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure(input);
                cancel();
            }
        }
    }

    public interface OnEnsureListener {
        void onEnsure(String describeText);
    }

    TextEditDialog.OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(TextEditDialog.OnEnsureListener onEnsureListener) {
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
