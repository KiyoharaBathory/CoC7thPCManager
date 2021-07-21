package cn.kiyohara.cocplayercharactermanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;

public class ProfessionListAdapter extends BaseAdapter {
    Context context;
    List<ProfessionBean> mDatas;
    LayoutInflater inflater;


    public ProfessionListAdapter(Context context, List<ProfessionBean> mDatas) {
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
        ProfessionListViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_profession_select, parent, false);
            holder = new ProfessionListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ProfessionListViewHolder) convertView.getTag();
        }

        holder.nameTv.setText(mDatas.get(position).getProfessionName());
        String temp = mDatas.get(position).getSkillPointAlgorithm();
        holder.algorithmTv.setText(temp);
        temp = "本职技能：" + mDatas.get(position).getMajorSkills();
        holder.majorTv.setText(temp);
        temp = mDatas.get(position).getMinCredit() + "~" + mDatas.get(position).getMaxCredit();
        holder.creditTv.setText(temp);
        return convertView;
    }


    class ProfessionListViewHolder {
        TextView nameTv, majorTv, creditTv, algorithmTv;

        public ProfessionListViewHolder(View view) {
            nameTv = view.findViewById(R.id.item_profession_name);
            majorTv = view.findViewById(R.id.item_profession_majors);
            creditTv = view.findViewById(R.id.item_profession_credit_range);
            algorithmTv = view.findViewById(R.id.item_profession_skillpoint_measure);
        }
    }
}
