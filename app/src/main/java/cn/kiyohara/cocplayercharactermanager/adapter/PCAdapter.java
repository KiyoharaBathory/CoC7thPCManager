package cn.kiyohara.cocplayercharactermanager.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;

public class PCAdapter extends BaseAdapter {
    Context context;
    List<PlayerCharacter> mDatas;
    LayoutInflater inflater;

    public PCAdapter(Context context, List<PlayerCharacter> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PCListViewHolder holder = null;
        PlayerCharacter pc = mDatas.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_main_list_character, parent, false);
            holder = new PCListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PCListViewHolder) convertView.getTag();
        }

        if (pc.getPortraitUri() != null) {
            holder.portraitIv.setImageURI(pc.getPortraitUri());
        }
        holder.nameTv.setText(pc.getName());
        int age = pc.getAge();
        String gender;
        if (pc.getGender() == 1) {
            gender = "男";
        } else {
            gender = "女";
        }
        String temp = age + "岁，" + gender;
        holder.ageNGenderTv.setText(temp);
        ProfessionBean bean = new ProfessionBean();
        try {
            bean = bean.baseProfessionLoader(context, pc.getpId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.professionTv.setText(bean.getProfessionName());
        return convertView;
    }

    class PCListViewHolder {
        TextView nameTv, ageNGenderTv, professionTv;
        SimpleDraweeView portraitIv;

        public PCListViewHolder(View view) {
            nameTv = view.findViewById(R.id.item_main_list_character_tv_name);
            ageNGenderTv = view.findViewById(R.id.item_main_list_character_tv_age);
            professionTv = view.findViewById(R.id.item_main_list_character_tv_profession);
            portraitIv = view.findViewById(R.id.item_main_list_character_iv_portrait);
        }
    }
}
