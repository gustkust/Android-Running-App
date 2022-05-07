package com.example.sport_road_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // loadRoutesToDataBase()
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
                "Easy"
            )
        )

        dbHelper.addRoute(
            Route(
                2,
                "Wartostrada",
                "Droga po calej dlugosci wartostrady, w jedna strone",
                8.50,
                "City",
                "Medium"
            )
        )

        dbHelper.addRoute(
            Route(
                3,
                "Kolko wokol Malty",
                "Jedna petla dookola jeziora maltanskiego",
                10.00,
                "City",
                "Medium"
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
                "Easy"
            )
        )

        dbHelper.delRoute(
            Route(
                2,
                "Wartostrada",
                "Droga po calej dlugosci wartostrady, w jedna strone",
                8.50,
                "City",
                "Medium"
            )
        )

        dbHelper.delRoute(
            Route(
                3,
                "Kolko wokol Malty",
                "Jedna petla dookola jeziora maltanskiego",
                10.00,
                "City",
                "Medium"
            )
        )
    }
}