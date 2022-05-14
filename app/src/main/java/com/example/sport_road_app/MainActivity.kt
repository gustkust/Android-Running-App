package com.example.sport_road_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        dbHelper.initDataBase()
//        loadRoutesToDataBase()
        // deleteRoutesFromDataBase()
    }

    // TODO load from JSON instead
    private fun loadRoutesToDataBase() {
        dbHelper.addRoute(
            Route(
                1,
                "Petla po Debinie",
                "Krotka trasa po sciezkach w lasku debinskim",
                4.00,
                "Forrest",
                "Easy",
                "00:10:52"
            )
        )

        dbHelper.addRoute(
            Route(
                2,
                "Wartostrada",
                "Droga po calej dlugosci wartostrady, w jedna strone",
                8.50,
                "City",
                "Medium",
                "00:25:02"
            )
        )

        dbHelper.addRoute(
            Route(
                3,
                "Kolko wokol Malty",
                "Jedna petla dookola jeziora maltanskiego",
                10.00,
                "City",
                "Medium",
                "01:01:24"
            )
        )
    }

    private fun deleteRoutesFromDataBase() {
        dbHelper.delRoute(
            Route(
                1,
                "Petla po Debinie",
                "Krotka trasa po sciezkach w lasku debinskim",
                4.00,
                "Forrest",
                "Easy",
                "00:10:52"
            )
        )

        dbHelper.delRoute(
            Route(
                2,
                "Wartostrada",
                "Droga po calej dlugosci wartostrady, w jedna strone",
                8.50,
                "City",
                "Medium",
                "00:25:02"
            )
        )

        dbHelper.delRoute(
            Route(
                3,
                "Kolko wokol Malty",
                "Jedna petla dookola jeziora maltanskiego",
                10.00,
                "City",
                "Medium",
                "01:01:24"
            )
        )
    }
}