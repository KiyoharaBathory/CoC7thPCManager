package cn.kiyohara.cocplayercharactermanager.db.character;

import android.content.ContentValues;
import android.content.Context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.R;

public class SkillBean {
    int sId, playerId, initValue, interestPoint, growthPoint, professionPoint;
    String customize, sName;

    public SkillBean() {
    }

    public List<SkillBean> getNewSkillList(Context context, int playerId) throws Exception {
        List<SkillBean> list = new ArrayList<>();
        for (int i = 1; i < 59; i++) {
            SkillBean bean = new SkillBean();
            bean = skillLoader(context, i);
            bean.setPlayerId(playerId);
            bean.setGrowthPoint(0);
            bean.setInterestPoint(0);
            bean.setProfessionPoint(0);
            list.add(bean);
        }
        return list;
    }

    public SkillBean skillLoader(Context context, int sId) throws Exception {
        SkillBean bean = new SkillBean();
        bean.setsId(sId);
        SAXReader reader = new SAXReader();
        InputStream is = context.getResources().openRawResource(R.raw.coc_skills);
        Document doc = reader.read(is);
        Element root = doc.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element e = it.next();
            Element skillId = e.element("sId");
            int id = Integer.parseInt(skillId.getStringValue());
            if (id == sId) {
                Element name = e.element("skillName");
                Element initValue = e.element("initValue");
                bean.setsName(name.getStringValue());
                bean.setInitValue(Integer.parseInt(initValue.getStringValue()));
                if (e.element("customize") != null) {
                    Element customize = e.element("customize");
                    bean.setCustomize(customize.getStringValue());
                } else {
                    bean.setCustomize(null);
                }
            }
        }
        return bean;
    }

    public int getProfessionPoint() {
        return professionPoint;
    }

    public void setProfessionPoint(int professionPoint) {
        this.professionPoint = professionPoint;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getInitValue() {
        return initValue;
    }

    public void setInitValue(int initValue) {
        this.initValue = initValue;
    }

    public int getInterestPoint() {
        return interestPoint;
    }

    public void setInterestPoint(int interestPoint) {
        this.interestPoint = interestPoint;
    }

    public int getGrowthPoint() {
        return growthPoint;
    }

    public void setGrowthPoint(int growthPoint) {
        this.growthPoint = growthPoint;
    }

    public String getCustomize() {
        return customize;
    }

    public void setCustomize(String customize) {
        this.customize = customize;
    }
}
