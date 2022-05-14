package com.example.sport_road_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RouteDetailsActivity : AppCompatActivity(), StopWatchFragment.OnDataPass {
    private val dbHelper = DBHelper(this)
    private lateinit var selectedRoute: Route
    private lateinit var routeTimeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)

        selectedRoute = intent.extras?.get("selectedRoute") as Route

        val routeIdTextView = findViewById<TextView>(R.id.routeIdTextView)
        val routeNameTextView = findViewById<TextView>(R.id.routeNameTextView)
        val routeDescriptionTextView = findViewById<TextView>(R.id.routeDescriptionTextView)
        val routeLengthTextView = findViewById<TextView>(R.id.routeLengthTextView)
        val routeLocationTextView = findViewById<TextView>(R.id.routeLocationTextView)
        val routeDifficultyTextView = findViewById<TextView>(R.id.routeDifficultyTextView)
        routeTimeTextView = findViewById<TextView>(R.id.routeTimeTextView)

        routeIdTextView.text = selectedRoute.id.toString()
        routeNameTextView.text = selectedRoute.name
        routeDescriptionTextView.text = selectedRoute.description
        routeLengthTextView.text = selectedRoute.length.toString()
        routeLocationTextView.text = selectedRoute.location
        routeDifficultyTextView.text = selectedRoute.difficulty
        routeTimeTextView.text = selectedRoute.time
    }

    override fun onDataPass(data: String) {
        dbHelper.updateTime(selectedRoute.id, data)
        routeTimeTextView.text = data
    }
}