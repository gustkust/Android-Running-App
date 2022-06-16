package com.example.sport_road_app

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton


class RouteDetailsActivity : AppCompatActivity(), StopWatchFragment.OnDataPass {

    private  val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private  val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private  val fromButtom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_buttom_anim)}
    private  val toButtom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_buttom_anim)}

    private var clicked = false;

    private lateinit var timer: FloatingActionButton
    private lateinit var menu: FloatingActionButton
    private lateinit var back: FloatingActionButton

    private val dbHelper = DBHelper(this)
    private lateinit var selectedRoute: Route
    private lateinit var routeBestTimeTextView: TextView
    private lateinit var routeLastTimeTextView: TextView
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)

        menu = findViewById<FloatingActionButton>(R.id.floatingActionButtonMenu)
        timer = findViewById<FloatingActionButton>(R.id.floatingActionButtonTimer)
        back = findViewById<FloatingActionButton>(R.id.floatingActionButtonBack)

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



        menu.setOnClickListener(){
            onMenuButtonClicked()
        }
        timer.setOnClickListener(){


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
        back.setOnClickListener(){

            finish()
        }
    }

    override fun onDataPass(data: String) {
        val best_time = dbHelper.updateTime(selectedRoute.id, data)
        routeBestTimeTextView.text = best_time
        routeLastTimeTextView.text = data
    }

    private fun onMenuButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        if(!clicked) clicked = true else clicked = false

    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            timer.visibility = View.VISIBLE
            back.visibility = View.VISIBLE
        } else {
            timer.visibility = View.INVISIBLE
            back.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            timer.startAnimation(fromButtom)
            back.startAnimation(fromButtom)
            menu.startAnimation(rotateOpen)
        } else {
            timer.startAnimation(toButtom)
            back.startAnimation(toButtom)
            menu.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){
        if(!clicked){
            timer.isClickable = true
            back.isClickable = true
        } else {
            timer.isClickable = false
            back.isClickable = false
        }
    }
}