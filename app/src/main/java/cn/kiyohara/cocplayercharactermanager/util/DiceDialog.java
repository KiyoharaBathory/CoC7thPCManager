package cn.kiyohara.cocplayercharactermanager.util;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.Random;

import cn.kiyohara.cocplayercharactermanager.R;

public class DiceDialog extends Dialog implements View.OnClickListener {
    TextView resultHint, errorTv;
    TickerView ticker, his1, his2, his3, his4, his5;
    EditText diceEt;
    ImageButton rollBtn;
    int h1, h2, h3, h4, h5, timeOfSame;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    MediaPlayer mp;

    public DiceDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dice);
        resultHint = findViewById(R.id.dialog_dice_result_hint);
        ticker = findViewById(R.id.dialog_dice_ticker);
        ticker.setCharacterLists(TickerUtils.provideNumberList());
        diceEt = findViewById(R.id.dialog_dice_et);
        rollBtn = findViewById(R.id.dialog_dice_roll);
        rollBtn.setOnClickListener(this);
        errorTv = findViewById(R.id.dialog_dice_error);
        errorTv.setVisibility(8);
        sp = getContext().getSharedPreferences("diceHistory", 0);
        editor = sp.edit();
        timeOfSame = 1;
        h1 = sp.getInt("h1", 0);
        h2 = sp.getInt("h2", 0);
        h3 = sp.getInt("h3", 0);
        h4 = sp.getInt("h4", 0);
        h5 = sp.getInt("h5", 0);
        his1 = findViewById(R.id.dialog_dice_history1);
        his1.setCharacterLists(TickerUtils.provideNumberList());
        his2 = findViewById(R.id.dialog_dice_history2);
        his2.setCharacterLists(TickerUtils.provideNumberList());
        his3 = findViewById(R.id.dialog_dice_history3);
        his3.setCharacterLists(TickerUtils.provideNumberList());
        his4 = findViewById(R.id.dialog_dice_history4);
        his4.setCharacterLists(TickerUtils.provideNumberList());
        his5 = findViewById(R.id.dialog_dice_history5);
        his5.setCharacterLists(TickerUtils.provideNumberList());
        setHistory();
        mp = MediaPlayer.create(getContext(),R.raw.ylmsn);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_dice_roll) {
            if (mp.isPlaying()){
                mp.stop();
            }
            String temp = String.valueOf(diceEt.getText());
            if (temp.equals("")) {
                errorTv.setText("你朝桌面掷出了一团空气，这套假动作差点把骰娘都骗到了。");
                errorTv.setVisibility(0);
            } else {
                int dice = Integer.parseInt(temp);
                if (dice < 1) {
                    errorTv.setText("你朝桌面掷出了一个点，它直接穿过桌面消失了，没人知道它去了哪里。");
                    errorTv.setVisibility(0);
                } else if (dice == 1) {
                    ticker.setText("1");
                    errorTv.setText("你朝桌面掷出了一根棍子，骰娘也不知道你到底想干什么。");
                    errorTv.setVisibility(0);
                } else if (dice == 114514) {
                    ticker.setText("114514");
                    errorTv.setText("你居然往桌面上扔了一坨**......\n骰娘要被熏晕了。");
                    errorTv.setVisibility(0);
                    mp.start();
                } else if (dice > 1000000) {
                    errorTv.setText("你朝桌面掷出了一个写满数字的球，它骨碌骨碌滚动着，根本没有停下的意思。");
                    errorTv.setVisibility(0);
                } else {
                    errorTv.setVisibility(8);
                    Random random = new Random();
                    int result = random.nextInt(dice) + 1;
                    if (h1 == result) {
                        h5 = h4;
                        h4 = h3;
                        h3 = h2;
                        h2 = h1;
                        h1 = result;
                        saveDiceHistory();
                        setHistory();
                        errorTv.setText("你掷出了跟上次一模一样的结果！这是连续的第" + timeOfSame + "次。");
                        timeOfSame = timeOfSame + 1;
                        errorTv.setVisibility(0);
                    } else {
                        h5 = h4;
                        h4 = h3;
                        h3 = h2;
                        h2 = h1;
                        h1 = result;
                        saveDiceHistory();
                        setHistory();
                        timeOfSame = 1;
                    }
                    ticker.setText(result + "");

                }
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        mp.stop();
        super.onDetachedFromWindow();
    }

    private void saveDiceHistory() {
        editor.putInt("h1", h1);
        editor.putInt("h2", h2);
        editor.putInt("h3", h3);
        editor.putInt("h4", h4);
        editor.putInt("h5", h5);
        editor.apply();
    }

    private void setHistory() {
        his1.setText(h1 + "");
        his2.setText(h2 + "");
        his3.setText(h3 + "");
        his4.setText(h4 + "");
        his5.setText(h5 + "");
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
