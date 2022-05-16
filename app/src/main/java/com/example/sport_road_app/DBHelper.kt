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
        private val COL_BEST_TIME = "Best_time"
        private val COL_LAST_TIME = "Last_time"
        private val COL_IMAGE_SRC = "image_src"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT, " +
                    "$COL_DESCRIPTION TEXT, " +
                    "$COL_LENGTH DOUBLE(6, 2), " +
                    "$COL_LOCATION TEXT, " +
                    "$COL_DIFFICULTY TEXT, " +
                    "$COL_BEST_TIME TEXT, " +
                    "$COL_LAST_TIME TEXT, " +
                    "$COL_IMAGE_SRC TEXT" +
                    ")")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun initDataBase() {
        val db = this.writableDatabase
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_NAME TEXT, " +
                    "$COL_DESCRIPTION TEXT, " +
                    "$COL_LENGTH DOUBLE(6, 2), " +
                    "$COL_LOCATION TEXT, " +
                    "$COL_DIFFICULTY TEXT, " +
                    "$COL_BEST_TIME TEXT, " +
                    "$COL_LAST_TIME TEXT, " +
                    "$COL_IMAGE_SRC TEXT" +
                    ")")
        db!!.execSQL(CREATE_TABLE_QUERY)
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
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_BEST_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_LAST_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_SRC))
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
        values.put(COL_BEST_TIME, route.best_time)
        values.put(COL_LAST_TIME, route.last_time)
        values.put(COL_IMAGE_SRC, route.image_src)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun delRoute(route: Route) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, COL_NAME + "=" + "\"" + route.name + "\"", null)
        db.close()
    }

    fun updateTime(id: Int, data: String): String? {
        val db = this.writableDatabase

        val query =
            ("UPDATE $TABLE_NAME SET $COL_LAST_TIME = \"$data\" WHERE $COL_ID = $id")
        db!!.execSQL(query)

        val selectQuery = "SELECT $COL_BEST_TIME FROM $TABLE_NAME WHERE $COL_ID = $id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor.moveToFirst()
        val last_best = cursor.getString(cursor.getColumnIndexOrThrow(COL_BEST_TIME))

        if (
            (last_best[0].code > data[0].code) or
            ((last_best[0].code == data[0].code) and (last_best[1].code > data[1].code)) or
            ((last_best[0].code == data[0].code) and (last_best[1].code == data[1].code) and (last_best[3].code > data[3].code)) or
            ((last_best[0].code == data[0].code) and (last_best[1].code == data[1].code) and (last_best[3].code == data[3].code) and (last_best[4].code > data[4].code)) or
            ((last_best[0].code == data[0].code) and (last_best[1].code == data[1].code) and (last_best[3].code == data[3].code) and (last_best[4].code == data[4].code) and (last_best[6].code > data[6].code)) or
            ((last_best[0].code == data[0].code) and (last_best[1].code == data[1].code) and (last_best[3].code == data[3].code) and (last_best[4].code == data[4].code) and (last_best[6].code == data[6].code) and (last_best[7].code == data[7].code))
                ) {
            db.execSQL("UPDATE $TABLE_NAME SET $COL_BEST_TIME = \"$data\" WHERE $COL_ID = $id")
            return data
        }
        return last_best
    }

    fun getNamesAndImageSrc(): Array<ArrayList<String>> {
        val listNames = ArrayList<String>()
        val listImageSrc = ArrayList<String>()

        val selectQuery = "SELECT $COL_NAME, $COL_IMAGE_SRC FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                listNames.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)))
                listImageSrc.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_SRC)))
            } while (cursor.moveToNext())
        }
        db.close()

        return arrayOf(listNames, listImageSrc)
    }

    fun deleteDataBase() {
        val db = this.writableDatabase

        db.execSQL("DROP TABLE $TABLE_NAME")
        db.close()
    }
}