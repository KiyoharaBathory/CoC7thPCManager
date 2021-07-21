package cn.kiyohara.cocplayercharactermanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;

public class SkillListAdapter extends BaseAdapter {
    Context context;
    List<SkillBean> mDatas;
    PlayerCharacter pc;
    LayoutInflater inflater;

    public SkillListAdapter(Context context, List<SkillBean> mDatas, PlayerCharacter pc) {
        this.context = context;
        this.mDatas = mDatas;
        this.pc = pc;
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
        return mDatas.get(position).getsId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SkillListViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_detail_list_skill, parent, false);
            holder = new SkillListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SkillListViewHolder) convertView.getTag();
        }
        SkillBean bean = mDatas.get(position);
        if (bean.getCustomize() == null){
            holder.skillNameTv.setText(mDatas.get(position).getsName());
        }else{
        holder.skillNameTv.setText(bean.getsName()+":"+bean.getCustomize());}
        int tempValue = 0;
        int totalValue = 0;
        int sId = bean.getsId();
        if (sId == 26) {
            tempValue = pc.getDexterity() / 2;
        } else if (sId == 48) {
            tempValue = pc.getEducation();
        } else {
            tempValue = mDatas.get(position).getInitValue();
        }
        totalValue = totalValue + tempValue;
        holder.initValueTv.setText("基础:" + tempValue);
        tempValue = bean.getProfessionPoint();
        totalValue = totalValue + tempValue;
        holder.pPTv.setText("职业:" + tempValue);
        tempValue = bean.getInterestPoint();
        totalValue = totalValue + tempValue;
        holder.iPTv.setText("兴趣:" + tempValue);
        tempValue = bean.getGrowthPoint();
        totalValue = totalValue + tempValue;
        holder.growthTv.setText("成长:" + tempValue);
        int halfValue = totalValue / 2;
        int extremeValue = totalValue / 5;
        holder.finalPointTv.setText(totalValue + "/" + halfValue + "/" + extremeValue);

        return convertView;
    }

    class SkillListViewHolder {
        TextView skillNameTv, initValueTv, pPTv, iPTv, growthTv, finalPointTv;
        ConstraintLayout background;

        public SkillListViewHolder(View view) {
            skillNameTv = view.findViewById(R.id.item_detail_list_skill_name);
            initValueTv = view.findViewById(R.id.item_detail_list_skill_basic_point);
            pPTv = view.findViewById(R.id.item_detail_list_skill_profession_point);
            iPTv = view.findViewById(R.id.item_detail_list_skill_interest_point);
            growthTv = view.findViewById(R.id.item_detail_list_growth_point);
            finalPointTv = view.findViewById(R.id.item_detail_list_skill_final_point);
            background = view.findViewById(R.id.item_detail_list_background);
        }
    }
}
