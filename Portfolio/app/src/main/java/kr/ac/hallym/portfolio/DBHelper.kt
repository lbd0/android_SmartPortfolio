package kr.ac.hallym.portfolio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper (context: Context) : SQLiteOpenHelper(context, "smartpfdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table if not exists RESUME_TB(" +
                "_id integer primary key autoincrement," +
                "title not null," +
                "date not null)" )

        db?.execSQL("create table if not exists PF_TB(" +
                "_id integer primary key autoincrement," +
                "img not null," +
                "title not null," +
                "detail not null)" )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
