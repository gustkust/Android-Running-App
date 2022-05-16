package com.example.sport_road_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context, private val data: Array<ArrayList<String>>, private var routes: ArrayList<Route>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = data[0]
    private var images = data[1]

    fun getDrawable(context: Context, name: String?): Int {
        return context.resources.getIdentifier(
            name,
            "drawable", context.packageName
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemImage.setImageResource(context.resources.getIdentifier(images[position], "drawable", context.packageName))
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView

        init {
            itemImage = itemView.findViewById(R.id.routeImage)
            itemTitle = itemView.findViewById(R.id.item_title)

            itemView.setOnClickListener{
                val selectedRouteName = itemTitle.text
                var selectedRoute = Route(
                    0,
                    "tmp",
                    "tmp",
                    0.0,
                    "tmp",
                    "tmp",
                    "tmp",
                    "tmp",
                    "tmp"
                )

                for (route in routes) {
                    if (route.name == selectedRouteName) {
                        selectedRoute = route
                    }
                }

                val intent = Intent (context, RouteDetailsActivity::class.java)
                intent.putExtra("selectedRoute", selectedRoute)
                context.startActivity(intent)
            }
        }
    }
}