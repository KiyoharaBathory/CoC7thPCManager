package cn.kiyohara.cocplayercharactermanager.db.character;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerCharacter {
    int id;
    int favourite;//是否被收藏
    String portraitUri;
    String name;
    int age;
    int gender;//0为女，1为男
    int strength, constitution, size, dexterity, appearance, education, intelligence, willPower, luck;//八围和幸运
    int hp, mp, sanity, bodyStat, mentalStat;//表示hp、mp的当前值，以及当前状态（1表示健康，0表示重伤，-1表示濒死）
    int pId; //调查员职业id

    public PlayerCharacter() {
        favourite = 0;
        age = 15;
        gender = 1;
        strength = 0;
        constitution = 0;
        size = 0;
        dexterity = 0;
        appearance = 0;
        education = 0;
        intelligence = 0;
        willPower = 0;
        luck = 0;
        hp = 0;
        mp = 0;
        sanity = 0;
        bodyStat = 0;
        mentalStat = 0;
        pId = -1;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public int getBodyStat() {
        return bodyStat;
    }

    public void setBodyStat(int bodyStat) {
        this.bodyStat = bodyStat;
    }

    public int getMentalStat() {
        return mentalStat;
    }

    public void setMentalStat(int mentalStat) {
        this.mentalStat = mentalStat;
    }

    public int getSanity() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity = sanity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getAppearance() {
        return appearance;
    }

    public void setAppearance(int appearance) {
        this.appearance = appearance;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWillPower() {
        return willPower;
    }

    public void setWillPower(int willPower) {
        this.willPower = willPower;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

}