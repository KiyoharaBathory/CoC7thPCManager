package cn.kiyohara.cocplayercharactermanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(@Nullable Context context) {
        super(context, "kiyohara.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table pclist(id integer primary key autoincrement," +
                "favourite integer," +
                "portrait text," +
                "name text," +
                "age integer," +
                "gender integer," +
                "str integer," +
                "con integer," +
                "siz integer," +
                "dex integer," +
                "app integer," +
                "edu integer," +
                "int integer," +
                "pow integer," +
                "luck integer," +
                "hp integer," +
                "mp integer," +
                "bodystat integer," +
                "sanity integer," +
                "mentalstat integer," +
                "pid integer)";
        db.execSQL(sql);
        sql = "create table slist(sid integer," +
                "sname text," +
                "playerid integer," +
                "initvalue integer," +
                "interestpoint integer," +
                "growthpoint integer," +
                "professionpoint integer," +
                "customize text)";
        db.execSQL(sql);
        sql = "create table story(playerid integer primary key," +
                "address text," +
                "family text," +
                "inventory text," +
                "armor integer," +
                "asset text," +
                "picture text," +
                "faith text," +
                "vip text," +
                "memorialplace text," +
                "treasure text," +
                "peculiarity text," +
                "trauma text," +
                "fearormania text," +
                "extrastory text," +
                "note text," +
                "cthulhumythos text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
