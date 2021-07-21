package cn.kiyohara.cocplayercharactermanager.util;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cn.kiyohara.cocplayercharactermanager.R;
import cn.kiyohara.cocplayercharactermanager.adapter.ProfessionListAdapter;
import cn.kiyohara.cocplayercharactermanager.db.character.ProfessionBean;

public class ProfessionSelectActivity extends AppCompatActivity implements View.OnClickListener {
    ListView pList;
    ImageView cancelIv, randomIv;
    List<ProfessionBean> mDatas = new ArrayList<>();
    ProfessionListAdapter adapter;
    int pId, major1, major2, major3, major4, major5, major6, major7, major8;
    String pName;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_select);
        pList = findViewById(R.id.profession_select_lv);
        sp = this.getSharedPreferences("professionSelect", 0);
        editor = sp.edit();
        cancelIv = findViewById(R.id.profession_actionbar_iv_cancel);
        randomIv = findViewById(R.id.profession_actionbar_iv_random);
        try {
            mDatas = professionXMLLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new ProfessionListAdapter(this, mDatas);
        pList.setAdapter(adapter);
        cancelIv.setOnClickListener(this);
        randomIv.setOnClickListener(this);
        setLVListener();
    }

    private void setLVListener() {
        pList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfessionBean profession = mDatas.get(position);
                pName = profession.getProfessionName();
                pId = profession.getpId();
                editor.putInt("pId", pId);
                editor.putString("pName", pName);
                editor.apply();
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profession_actionbar_iv_cancel:
                finish();
                break;
            case R.id.profession_actionbar_iv_random:
                Random random = new Random();
                pId = random.nextInt(114) + 1;
                pName = mDatas.get(pId - 1).getProfessionName();
                editor.putInt("pId", pId);
                editor.putString("pName", pName);
                editor.apply();
                finish();
                break;
        }

    }

    public List<ProfessionBean> professionXMLLoader() throws Exception {//读取coc_professions.xml文件内容

        List<ProfessionBean> list = new ArrayList<>();
        SAXReader reader = new SAXReader();
        InputStream is = this.getResources().openRawResource(R.raw.coc_professions);
        Document doc = reader.read(is);
        Element root = doc.getRootElement();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            ProfessionBean profession = new ProfessionBean();
            Element e = iterator.next();
            Element professionId = e.element("pId");
            profession.setpId(Integer.parseInt(professionId.getStringValue()));
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
            list.add(profession);
        }
        return list;
    }
}
