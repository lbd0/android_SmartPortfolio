package kr.ac.hallym.portfolio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) : SQLiteOpenHelper(context, "smartpfdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table RESUME_TB(" + "title not null primary key)" )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}