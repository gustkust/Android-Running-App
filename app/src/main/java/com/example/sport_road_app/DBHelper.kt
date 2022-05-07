package com.example.sport_road_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDB.db"

        //Table
        private val COL_ID = "Id"
        private val TABLE_NAME = "Route"
        private val COL_NAME = "Name"
        private val COL_DESCRIPTION = "Description"
        private val COL_LENGTH = "Length"
        private val COL_LOCATION = "Location"
        private val COL_DIFFICULTY = "Difficulty"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT, " +
                    "$COL_DESCRIPTION TEXT, " +
                    "$COL_LENGTH DOUBLE(6, 2), " +
                    "$COL_LOCATION TEXT, " +
                    "$COL_DIFFICULTY TEXT" +
                    ")")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllRoutes(): List<Route> {
        val listRoutes = ArrayList<Route>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val route = Route(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COL_LENGTH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_LOCATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY))
                )
                listRoutes.add(route)
            } while (cursor.moveToNext())
        }
        db.close()
        return listRoutes
    }

    fun addRoute(route: Route) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, route.name)
        values.put(COL_DESCRIPTION, route.description)
        values.put(COL_LENGTH, route.length)
        values.put(COL_LOCATION, route.location)
        values.put(COL_DIFFICULTY, route.difficulty)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun delRoute(route: Route) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_NAME + "=" + "\""  + route.name + "\"", null)
        db.close()
    }
}