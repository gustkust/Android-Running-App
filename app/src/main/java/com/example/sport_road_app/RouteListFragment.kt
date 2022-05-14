package com.example.sport_road_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment


class RouteListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val routeListView = view.findViewById<ListView>(R.id.routeListView)

        val dbHelper = context?.let { DBHelper(it.applicationContext) }
        val routes = dbHelper?.getAllRoutes()

        val entries = ArrayList<String>()

        if (routes != null) {
            for (route in routes) {
                entries.add(route.name)
            }
        }


        val adapter =
            ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1, entries)
        routeListView.adapter = adapter

        routeListView.onItemClickListener = OnItemClickListener { _, view, position, id ->
            val thread = Thread() {
                val selectedRouteName = routeListView.getItemAtPosition(position).toString()
                var selectedRoute = Route(
                    0,
                    "tmp",
                    "tmp",
                    0.0,
                    "tmp",
                    "tmp",
                    "tmp"
                )

                if (routes != null) {
                    for (route in routes) {
                        if (route.name == selectedRouteName) {
                            selectedRoute = route
                        }
                    }
                }

                val intent = Intent (activity, RouteDetailsActivity::class.java)
                intent.putExtra("selectedRoute", selectedRoute)
                startActivity(intent)
            }
            thread.start()
            onPause()
        }
    }
}