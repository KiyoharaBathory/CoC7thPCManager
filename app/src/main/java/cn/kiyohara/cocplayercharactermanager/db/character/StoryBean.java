package cn.kiyohara.cocplayercharactermanager.db.character;

public class StoryBean {
    int playerId;//从属的调查员的id
    String address;//住址
    String familyBackground;//家庭背景
    String inventory;//携带物品，包括武器和防具
    int armor;//护甲值
    String asset;//其他资产
    String picture, faith, vip, memorialPlace, treasure, peculiarity, trauma, fearOrMania, extraStory;//个人背景故事
    String note;//调查员笔记/备注
    String cthulhuMythos;//克苏鲁神话相关备注，如典籍、法术、第三类接触、神话造物持有

    public StoryBean() {
    }

    public StoryBean(int playerId) {
        this.playerId = playerId;
        this.address = "";
        this.familyBackground = "";
        this.inventory = "";
        this.armor = 0;
        this.asset = "";
        this.picture = "";
        this.faith = "";
        this.vip = "";
        this.memorialPlace = "";
        this.treasure = "";
        this.peculiarity = "";
        this.trauma = "";
        this.fearOrMania = "";
        this.extraStory = "";
        this.note = "";
        this.cthulhuMythos = "";
    }


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFamilyBackground() {
        return familyBackground;
    }

    public void setFamilyBackground(String familyBackground) {
        this.familyBackground = familyBackground;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFaith() {
        return faith;
    }

    public void setFaith(String faith) {
        this.faith = faith;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getMemorialPlace() {
        return memorialPlace;
    }

    public void setMemorialPlace(String memorialPlace) {
        this.memorialPlace = memorialPlace;
    }

    public String getTreasure() {
        return treasure;
    }

    public void setTreasure(String treasure) {
        this.treasure = treasure;
    }

    public String getPeculiarity() {
        return peculiarity;
    }

    public void setPeculiarity(String peculiarity) {
        this.peculiarity = peculiarity;
    }

    public String getTrauma() {
        return trauma;
    }

    public void setTrauma(String trauma) {
        this.trauma = trauma;
    }

    public String getFearOrMania() {
        return fearOrMania;
    }

    public void setFearOrMania(String fearOrMania) {
        this.fearOrMania = fearOrMania;
    }

    public String getExtraStory() {
        return extraStory;
    }

    public void setExtraStory(String extraStory) {
        this.extraStory = extraStory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCthulhuMythos() {
        return cthulhuMythos;
    }

    public void setCthulhuMythos(String cthulhuMythos) {
        this.cthulhuMythos = cthulhuMythos;
    }
}
