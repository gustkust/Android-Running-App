package com.example.sport_road_app

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class RouteDetailsActivity : AppCompatActivity(), StopWatchFragment.OnDataPass {
    private val dbHelper = DBHelper(this)
    private lateinit var selectedRoute: Route
    private lateinit var routeBestTimeTextView: TextView
    private lateinit var routeLastTimeTextView: TextView
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)

        selectedRoute = intent.extras?.get("selectedRoute") as Route

        val routeNameTextView = findViewById<TextView>(R.id.routeNameTextView)
        val routeDescriptionTextView = findViewById<TextView>(R.id.routeDescriptionTextView)
        val routeLengthTextView = findViewById<TextView>(R.id.routeLengthTextView)
        val routeLocationTextView = findViewById<TextView>(R.id.routeLocationTextView)
        val routeDifficultyTextView = findViewById<TextView>(R.id.routeDifficultyTextView)
        routeBestTimeTextView = findViewById<TextView>(R.id.routeBestTimeTextView)
        routeLastTimeTextView = findViewById<TextView>(R.id.routeLastTimeTextView)
        val itemImage = findViewById<ImageView>(R.id.routeImage)

        val bestTimeTextView = findViewById<TextView>(R.id.bestTimeTextView)
        val lastTimeTextView = findViewById<TextView>(R.id.lastTimeTextView)

        routeNameTextView.text = selectedRoute.name
        routeDescriptionTextView.text = selectedRoute.description
        routeLengthTextView.text = selectedRoute.length.toString()
        routeLocationTextView.text = selectedRoute.location
        routeDifficultyTextView.text = selectedRoute.difficulty
        routeBestTimeTextView.text = selectedRoute.best_time
        routeLastTimeTextView.text = selectedRoute.last_time
        itemImage.setImageResource(resources.getIdentifier(selectedRoute.image_src, "drawable", packageName))

        routeBestTimeTextView.visibility = View.INVISIBLE
        bestTimeTextView.visibility = View.INVISIBLE
        routeLastTimeTextView.visibility = View.INVISIBLE
        lastTimeTextView.visibility = View.INVISIBLE

        val showTimeData = findViewById<Button>(R.id.showTimeDataButton)

        val timerFragment = supportFragmentManager.findFragmentById(R.id.stopWatchFragmentContainerView)

        supportFragmentManager.beginTransaction().apply {
            if (timerFragment != null) {
                hide(timerFragment)
            }
        }.commit()

        supportActionBar?.apply {
            title = selectedRoute.name
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(resources.getIdentifier(selectedRoute.image_src, "drawable", packageName))
        }

        showTimeData.setOnClickListener {
            routeBestTimeTextView.visibility = View.VISIBLE
            bestTimeTextView.visibility = View.VISIBLE
            routeLastTimeTextView.visibility = View.VISIBLE
            lastTimeTextView.visibility = View.VISIBLE

            supportFragmentManager.beginTransaction().apply {
                if (timerFragment != null) {
                    show(timerFragment)
                }
            }.commit()

        }
    }

    override fun onDataPass(data: String) {
        val best_time = dbHelper.updateTime(selectedRoute.id, data)
        routeBestTimeTextView.text = best_time
        routeLastTimeTextView.text = data
    }
}