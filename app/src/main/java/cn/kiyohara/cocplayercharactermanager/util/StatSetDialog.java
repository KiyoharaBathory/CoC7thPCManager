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

import com.weigan.loopview.LoopView;

import java.util.ArrayList;

import cn.kiyohara.cocplayercharactermanager.R;

public class StatSetDialog extends Dialog implements View.OnClickListener {
    LoopView statLoop;
    TextView titleTv, hintTv;
    ImageButton ensureBtn;
    int stat;
    String typeName;

    public StatSetDialog(Context context) {
        super(context);
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_stat_edit);
        titleTv = findViewById(R.id.dialog_stat_title);
        titleTv.setText(typeName);
        hintTv = findViewById(R.id.dialog_stat_hint);
        setHintByTypeName(typeName);
        statLoop = findViewById(R.id.dialog_stat_loop);
        setLoopByTypeName(typeName);
        statLoop.setCurrentPosition(stat);
        ensureBtn = findViewById(R.id.dialog_stat_btn_ensure);
        ensureBtn.setOnClickListener(this);
    }

    private void setLoopByTypeName(String typeName) {
        ArrayList<String> statList = new ArrayList<>();
        if (typeName.equals("健康状态")) {
            statList.add("健康");
            statList.add("昏迷");
            statList.add("重伤");
            statList.add("濒死");
            statList.add("死亡");
        } else if (typeName.equals("理智状态")) {
            statList.add("正常");
            statList.add("临时性疯狂");
            statList.add("不定性疯狂");
            statList.add("永久性疯狂");
        }
        statLoop.setItems(statList);
    }

    private void setHintByTypeName(String typeName) {
        if (typeName.equals("健康状态")) {
            hintTv.setText("如果角色只因轻伤而HP归零，该角色会陷入昏迷。\n" +
                    "当角色一次性受到大于等于其一半HP值的伤害时，该角色会进入重伤状态。\n" +
                    "在重伤状态下角色的HP归零时，该角色会陷入濒死状态。\n" +
                    "当角色一次性受到大于其最大HP值的伤害时，该角色会立即死亡。");
        } else if (typeName.equals("理智状态")) {
            hintTv.setText("当调查员一次性失去5点理智值时，需要进行一次智力检定，如果通过检定，调查员会进入临时性疯狂状态。\n" +
                    "当调查员在一天内失去了五分之一或更多的理智值时，调查员将陷入不定性疯狂。\n" +
                    "当调查员的理智值归零时，调查员会陷入无药可救的永久性疯狂，其行为不再受玩家的操纵。");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_stat_btn_ensure) {
            stat = statLoop.getSelectedItem();
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure(stat);
                cancel();
            }
        }
    }

    public interface OnEnsureListener {
        void onEnsure(int stat);
    }

    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
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
