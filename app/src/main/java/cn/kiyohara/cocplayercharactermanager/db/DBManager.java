package cn.kiyohara.cocplayercharactermanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.kiyohara.cocplayercharactermanager.db.character.PlayerCharacter;
import cn.kiyohara.cocplayercharactermanager.db.character.SkillBean;
import cn.kiyohara.cocplayercharactermanager.db.character.StoryBean;

public class DBManager {
    public static SQLiteDatabase db;

    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public static StoryBean getStoryByCharacterId(int playerId) {
        StoryBean bean = new StoryBean();
        bean.setPlayerId(playerId);
        Cursor cursor = db.query("story", null, "playerid=?", new String[]{playerId + ""}, null, null, null);
        while (cursor.moveToNext()) {
            bean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            bean.setFamilyBackground(cursor.getString(cursor.getColumnIndex("family")));
            bean.setInventory(cursor.getString(cursor.getColumnIndex("inventory")));
            bean.setArmor(cursor.getInt(cursor.getColumnIndex("armor")));
            bean.setAsset(cursor.getString(cursor.getColumnIndex("asset")));
            bean.setPicture(cursor.getString(cursor.getColumnIndex("picture")));
            bean.setFaith(cursor.getString(cursor.getColumnIndex("faith")));
            bean.setVip(cursor.getString(cursor.getColumnIndex("vip")));
            bean.setMemorialPlace(cursor.getString(cursor.getColumnIndex("memorialplace")));
            bean.setTreasure(cursor.getString(cursor.getColumnIndex("treasure")));
            bean.setPeculiarity(cursor.getString(cursor.getColumnIndex("peculiarity")));
            bean.setTrauma(cursor.getString(cursor.getColumnIndex("trauma")));
            bean.setFearOrMania(cursor.getString(cursor.getColumnIndex("fearormania")));
            bean.setExtraStory(cursor.getString(cursor.getColumnIndex("extrastory")));
            bean.setNote(cursor.getString(cursor.getColumnIndex("note")));
            bean.setCthulhuMythos(cursor.getString(cursor.getColumnIndex("cthulhumythos")));
        }
        cursor.close();
        return bean;
    }

    public static void updateStory(StoryBean bean) {
        int playerId = bean.getPlayerId();
        ContentValues values = new ContentValues();
        values.put("address", bean.getAddress());
        values.put("family", bean.getFamilyBackground());
        values.put("inventory", bean.getInventory());
        values.put("armor", bean.getArmor());
        values.put("asset", bean.getAsset());
        values.put("picture", bean.getPicture());
        values.put("faith", bean.getFaith());
        values.put("vip", bean.getVip());
        values.put("memorialplace", bean.getMemorialPlace());
        values.put("treasure", bean.getTreasure());
        values.put("peculiarity", bean.getPeculiarity());
        values.put("trauma", bean.getTrauma());
        values.put("fearormania", bean.getFearOrMania());
        values.put("extrastory", bean.getExtraStory());
        values.put("note", bean.getNote());
        values.put("cthulhumythos", bean.getCthulhuMythos());
        db.update("story", values, "playerid = ?", new String[]{playerId + ""});
    }

    public static void insertStory(StoryBean bean) {
        ContentValues values = new ContentValues();
        values.put("playerid", bean.getPlayerId());
        values.put("address", bean.getAddress());
        values.put("family", bean.getFamilyBackground());
        values.put("inventory", bean.getInventory());
        values.put("armor", bean.getArmor());
        values.put("asset", bean.getAsset());
        values.put("picture", bean.getPicture());
        values.put("faith", bean.getAddress());
        values.put("vip", bean.getAddress());
        values.put("memorialplace", bean.getMemorialPlace());
        values.put("treasure", bean.getTreasure());
        values.put("peculiarity", bean.getPeculiarity());
        values.put("trauma", bean.getTrauma());
        values.put("fearormania", bean.getFearOrMania());
        values.put("extrastory", bean.getExtraStory());
        values.put("note", bean.getNote());
        values.put("cthulhumythos", bean.getCthulhuMythos());
        db.insert("story", null, values);
    }

    public static List<SkillBean> getAllSkillByCharacterId(int playerId) {
        List<SkillBean> list = new ArrayList<>();
        String sql = "select * from slist where playerid = " + playerId + " order by sid asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            SkillBean bean = new SkillBean();
            bean.setPlayerId(playerId);
            bean.setsId(cursor.getInt(cursor.getColumnIndex("sid")));
            bean.setInitValue(cursor.getInt(cursor.getColumnIndex("initvalue")));
            bean.setsName(cursor.getString(cursor.getColumnIndex("sname")));
            bean.setCustomize(cursor.getString(cursor.getColumnIndex("customize")));
            bean.setInterestPoint(cursor.getInt(cursor.getColumnIndex("interestpoint")));
            bean.setGrowthPoint(cursor.getInt(cursor.getColumnIndex("growthpoint")));
            bean.setProfessionPoint(cursor.getInt(cursor.getColumnIndex("professionpoint")));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

    public static SkillBean getCreditByCharacterId(int playerId) {
        String sql = "select * from slist where playerid=" + playerId + " and sid=34";
        Cursor cursor = db.rawQuery(sql, null);
        SkillBean bean = new SkillBean();
        if (cursor.moveToFirst()) {
            bean.setPlayerId(playerId);
            bean.setsId(cursor.getInt(cursor.getColumnIndex("sid")));
            bean.setInitValue(cursor.getInt(cursor.getColumnIndex("initvalue")));
            bean.setsName(cursor.getString(cursor.getColumnIndex("sname")));
            bean.setCustomize(cursor.getString(cursor.getColumnIndex("customize")));
            bean.setInterestPoint(cursor.getInt(cursor.getColumnIndex("interestpoint")));
            bean.setGrowthPoint(cursor.getInt(cursor.getColumnIndex("growthpoint")));
            bean.setProfessionPoint(cursor.getInt(cursor.getColumnIndex("professionpoint")));
        }
        cursor.close();
        return bean;
    }

    public static void updateSkill(SkillBean bean) {
        int playerId = bean.getPlayerId();
        int sId = bean.getsId();
        ContentValues values = new ContentValues();
        values.put("initvalue", bean.getInitValue());
        values.put("sname", bean.getsName());
        values.put("customize", bean.getCustomize());
        values.put("interestpoint", bean.getInterestPoint());
        values.put("growthpoint", bean.getInterestPoint());
        values.put("professionpoint", bean.getProfessionPoint());
        db.update("slist", values, "playerid=? and sid =?", new String[]{playerId + "", sId + ""});
    }

    public static int deleteSkill(int sId, int playerId) {
        int i = db.delete("slist", "sid=? and playerid=?", new String[]{sId + "", playerId + ""});
        return i;
    }

    public static void insertSkill(SkillBean bean) {
        ContentValues values = new ContentValues();
        values.put("sid", bean.getsId());
        values.put("playerid", bean.getPlayerId());
        values.put("initvalue", bean.getInitValue());
        values.put("sname", bean.getsName());
        values.put("customize", bean.getCustomize());
        values.put("interestpoint", bean.getInterestPoint());
        values.put("growthpoint", bean.getInterestPoint());
        values.put("professionpoint", bean.getProfessionPoint());
        db.insert("slist", null, values);
    }

    public static void insertSkillList(List<SkillBean> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            SkillBean bean = list.get(i);
            insertSkill(bean);
        }
    }

    public static int getCharacterIdByName(String name) {
        int id = 0;
        Cursor cursor = db.query("pclist", new String[]{"id"}, "name=?", new String[]{name + ""}, null, null, "id desc");
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return id;
    }

    public static PlayerCharacter getCharacterById(int id) {
        Cursor cursor = db.query("pclist", null, "id=?", new String[]{id + ""}, null, null, null);
        PlayerCharacter pc = new PlayerCharacter();
        pc.setId(id);
        if (cursor.moveToFirst()) {
            pc.setFavourite(cursor.getInt(cursor.getColumnIndex("favourite")));
            pc.setPortraitUri(cursor.getString(cursor.getColumnIndex("portrait")));
            pc.setName(cursor.getString(cursor.getColumnIndex("name")));
            pc.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            pc.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
            pc.setStrength(cursor.getInt(cursor.getColumnIndex("str")));
            pc.setConstitution(cursor.getInt(cursor.getColumnIndex("con")));
            pc.setSize(cursor.getInt(cursor.getColumnIndex("siz")));
            pc.setDexterity(cursor.getInt(cursor.getColumnIndex("dex")));
            pc.setAppearance(cursor.getInt(cursor.getColumnIndex("app")));
            pc.setEducation(cursor.getInt(cursor.getColumnIndex("edu")));
            pc.setIntelligence(cursor.getInt(cursor.getColumnIndex("int")));
            pc.setWillPower(cursor.getInt(cursor.getColumnIndex("pow")));
            pc.setLuck(cursor.getInt(cursor.getColumnIndex("luck")));
            pc.setHp(cursor.getInt(cursor.getColumnIndex("hp")));
            pc.setMp(cursor.getInt(cursor.getColumnIndex("mp")));
            pc.setSanity(cursor.getInt(cursor.getColumnIndex("sanity")));
            pc.setBodyStat(cursor.getInt(cursor.getColumnIndex("bodystat")));
            pc.setMentalStat(cursor.getInt(cursor.getColumnIndex("mentalstat")));
            pc.setpId(cursor.getInt(cursor.getColumnIndex("pid")));
        }
        cursor.close();
        return pc;
    }

    public static List<PlayerCharacter> getAllCharacter() {
        List<PlayerCharacter> list = new ArrayList<>();
        String sql = "select * from pclist order by id asc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            PlayerCharacter pc = new PlayerCharacter();
            pc.setId(cursor.getInt(cursor.getColumnIndex("id")));
            pc.setFavourite(cursor.getInt(cursor.getColumnIndex("favourite")));
            pc.setPortraitUri(cursor.getString(cursor.getColumnIndex("portrait")));
            pc.setName(cursor.getString(cursor.getColumnIndex("name")));
            pc.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            pc.setGender(cursor.getInt(cursor.getColumnIndex("gender")));
            pc.setStrength(cursor.getInt(cursor.getColumnIndex("str")));
            pc.setConstitution(cursor.getInt(cursor.getColumnIndex("con")));
            pc.setSize(cursor.getInt(cursor.getColumnIndex("siz")));
            pc.setDexterity(cursor.getInt(cursor.getColumnIndex("dex")));
            pc.setAppearance(cursor.getInt(cursor.getColumnIndex("app")));
            pc.setEducation(cursor.getInt(cursor.getColumnIndex("edu")));
            pc.setIntelligence(cursor.getInt(cursor.getColumnIndex("int")));
            pc.setWillPower(cursor.getInt(cursor.getColumnIndex("pow")));
            pc.setLuck(cursor.getInt(cursor.getColumnIndex("luck")));
            pc.setHp(cursor.getInt(cursor.getColumnIndex("hp")));
            pc.setMp(cursor.getInt(cursor.getColumnIndex("mp")));
            pc.setSanity(cursor.getInt(cursor.getColumnIndex("sanity")));
            pc.setBodyStat(cursor.getInt(cursor.getColumnIndex("bodystat")));
            pc.setMentalStat(cursor.getInt(cursor.getColumnIndex("mentalstat")));
            pc.setpId(cursor.getInt(cursor.getColumnIndex("pid")));
            list.add(pc);
        }
        cursor.close();
        return list;
    }

    public static void insertCharacter(PlayerCharacter pc) {
        ContentValues values = new ContentValues();
        values.put("favourite", pc.getFavourite());
        values.put("portrait", pc.getPortraitUri());
        values.put("name", pc.getName());
        values.put("age", pc.getAge());
        values.put("gender", pc.getGender());
        values.put("str", pc.getStrength());
        values.put("con", pc.getConstitution());
        values.put("siz", pc.getSize());
        values.put("dex", pc.getDexterity());
        values.put("app", pc.getAppearance());
        values.put("edu", pc.getEducation());
        values.put("int", pc.getIntelligence());
        values.put("pow", pc.getWillPower());
        values.put("luck", pc.getLuck());
        values.put("hp", pc.getHp());
        values.put("mp", pc.getMp());
        values.put("bodystat", pc.getBodyStat());
        values.put("mentalstat", pc.getMentalStat());
        values.put("sanity", pc.getSanity());
        values.put("pid", pc.getpId());
        db.insert("pclist", null, values);
    }

    public static void updateCharacter(PlayerCharacter pc) {
        int id = pc.getId();
        ContentValues values = new ContentValues();
        values.put("favourite", pc.getFavourite());
        values.put("portrait", pc.getPortraitUri());
        values.put("name", pc.getName());
        values.put("age", pc.getAge());
        values.put("gender", pc.getGender());
        values.put("str", pc.getStrength());
        values.put("con", pc.getConstitution());
        values.put("siz", pc.getSize());
        values.put("dex", pc.getDexterity());
        values.put("app", pc.getAppearance());
        values.put("edu", pc.getEducation());
        values.put("int", pc.getIntelligence());
        values.put("pow", pc.getWillPower());
        values.put("luck", pc.getLuck());
        values.put("hp", pc.getHp());
        values.put("mp", pc.getMp());
        values.put("bodystat", pc.getBodyStat());
        values.put("mentalstat", pc.getMentalStat());
        values.put("sanity", pc.getSanity());
        values.put("pid", pc.getpId());
        db.update("pclist", values, "id=?", new String[]{id + ""});
    }

    public static int deleteCharacter(int id) {
        int i = db.delete("pclist", "id=?", new String[]{id + ""});
        db.delete("slist", "playerid=?", new String[]{id + ""});
        db.delete("story", "playerid=?", new String[]{id + ""});
        return i;
    }
}
