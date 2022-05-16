package com.example.sport_road_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val dbHelper = DBHelper(this)
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        dbHelper.deleteDataBase()
//        dbHelper.initDataBase()
//        loadRoutesToDataBase()
//        deleteRoutesFromDataBase()

        val recView = findViewById<RecyclerView>(R.id.recyclerView)

        layoutManager = LinearLayoutManager(this)
        recView.layoutManager = layoutManager

        adapter = RecyclerAdapter(this, dbHelper.getNamesAndImageSrc(),
            dbHelper.getAllRoutes() as ArrayList<Route>
        )
        recView.adapter = adapter
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
                "00:10:52",
                "01:01:23",
                "petla_po_debinie"
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
                "00:25:02",
                "00:55:42",
                "wartostrada"
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
                "01:01:24",
                "02:20:20",
                "kolko_wokol_malty"
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
                "00:10:52",
                "01:01:23",
                "petla_po_debinie"
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
                "00:25:02",
                "00:55:42",
                "wartostrada"
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
                "01:01:24",
                "02:20:20",
                "kolko_wokol_malty"
            )
        )
    }
}