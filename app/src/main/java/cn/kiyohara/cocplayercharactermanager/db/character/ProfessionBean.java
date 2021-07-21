package cn.kiyohara.cocplayercharactermanager.db.character;

import android.content.Context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

import cn.kiyohara.cocplayercharactermanager.R;

public class ProfessionBean {
    int pId;
    String professionName;
    int minCredit, maxCredit;
    String skillPointAlgorithm, majorSkills;
    int majorSkill1, majorSkill2, majorSkill3, majorSkill4, majorSkill5, majorSkill6, majorSkill7, majorSkill8;

    public ProfessionBean baseProfessionLoader(Context context,int pId) throws Exception {
        ProfessionBean profession = new ProfessionBean();
        profession.setpId(pId);
        SAXReader reader = new SAXReader();
        InputStream is = context.getResources().openRawResource(R.raw.coc_professions);
        Document doc = reader.read(is);
        Element root = doc.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element e = it.next();
            Element professionId = e.element("pId");
            int id = Integer.parseInt(professionId.getStringValue());
            if (id == pId) {
                Element pName = e.element("pName");
                Element skillPointAlgorithm = e.element("skillPointAlgorithm");
                Element majorSkills = e.element("majorSkills");
                Element minCredit = e.element("minCredit");
                Element maxCredit = e.element("maxCredit");
                Element majorSkill1 = e.element("majorSkill1");
                Element majorSkill2 = e.element("majorSkill2");
                Element majorSkill3 = e.element("majorSkill3");
                Element majorSkill4 = e.element("majorSkill4");
                Element majorSkill5 = e.element("majorSkill5");
                Element majorSkill6 = e.element("majorSkill6");
                Element majorSkill7 = e.element("majorSkill7");
                Element majorSkill8 = e.element("majorSkill8");

                profession.setProfessionName(pName.getStringValue());
                profession.setSkillPointAlgorithm(skillPointAlgorithm.getStringValue());
                profession.setMajorSkills(majorSkills.getStringValue());
                profession.setMinCredit(Integer.parseInt(minCredit.getStringValue()));
                profession.setMaxCredit(Integer.parseInt(maxCredit.getStringValue()));
                profession.setMajorSkill1(Integer.parseInt(majorSkill1.getStringValue()));
                profession.setMajorSkill2(Integer.parseInt(majorSkill2.getStringValue()));
                profession.setMajorSkill3(Integer.parseInt(majorSkill3.getStringValue()));
                profession.setMajorSkill4(Integer.parseInt(majorSkill4.getStringValue()));
                profession.setMajorSkill5(Integer.parseInt(majorSkill5.getStringValue()));
                profession.setMajorSkill6(Integer.parseInt(majorSkill6.getStringValue()));
                profession.setMajorSkill7(Integer.parseInt(majorSkill7.getStringValue()));
                profession.setMajorSkill8(Integer.parseInt(majorSkill8.getStringValue()));
            }
        }
        return profession;
    }

    public ProfessionBean() {
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public int getMinCredit() {
        return minCredit;
    }

    public void setMinCredit(int minCredit) {
        this.minCredit = minCredit;
    }

    public int getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(int maxCredit) {
        this.maxCredit = maxCredit;
    }

    public String getSkillPointAlgorithm() {
        return skillPointAlgorithm;
    }

    public void setSkillPointAlgorithm(String skillPointAlgorithm) {
        this.skillPointAlgorithm = skillPointAlgorithm;
    }

    public String getMajorSkills() {
        return majorSkills;
    }

    public void setMajorSkills(String majorSkills) {
        this.majorSkills = majorSkills;
    }

    public int getMajorSkill1() {
        return majorSkill1;
    }

    public void setMajorSkill1(int majorSkill1) {
        this.majorSkill1 = majorSkill1;
    }

    public int getMajorSkill2() {
        return majorSkill2;
    }

    public void setMajorSkill2(int majorSkill2) {
        this.majorSkill2 = majorSkill2;
    }

    public int getMajorSkill3() {
        return majorSkill3;
    }

    public void setMajorSkill3(int majorSkill3) {
        this.majorSkill3 = majorSkill3;
    }

    public int getMajorSkill4() {
        return majorSkill4;
    }

    public void setMajorSkill4(int majorSkill4) {
        this.majorSkill4 = majorSkill4;
    }

    public int getMajorSkill5() {
        return majorSkill5;
    }

    public void setMajorSkill5(int majorSkill5) {
        this.majorSkill5 = majorSkill5;
    }

    public int getMajorSkill6() {
        return majorSkill6;
    }

    public void setMajorSkill6(int majorSkill6) {
        this.majorSkill6 = majorSkill6;
    }

    public int getMajorSkill7() {
        return majorSkill7;
    }

    public void setMajorSkill7(int majorSkill7) {
        this.majorSkill7 = majorSkill7;
    }

    public int getMajorSkill8() {
        return majorSkill8;
    }

    public void setMajorSkill8(int majorSkill8) {
        this.majorSkill8 = majorSkill8;
    }
}
