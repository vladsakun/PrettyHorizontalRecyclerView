package com.example.prettyhorizontalrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vehiclesRecyclerView = findViewById<RecyclerView>(R.id.vehicle_recyclerview)
        val focusedBackgroundView = findViewById<FrameLayout>(R.id.focused_item_background)
        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.horizontal_scrollview)

        val dip = resources.getDimension(R.dimen.itemOffset)
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            resources.displayMetrics
        )

        val vehiclesAdapter = VehiclesAdapter(populateList(), object : VehicleItemClickCallback {
            override fun onItemClick(
                xPositionForFocusedBackgroundView: Float,
                xPositionForHorizontalScrollView: Float
            ) {

                focusedBackgroundView.animate()
                    .translationX(xPositionForFocusedBackgroundView - focusedBackgroundView.width / 2)

                horizontalScrollView.smoothScrollTo(
                    (xPositionForHorizontalScrollView - focusedBackgroundView.width / 2 - px).toInt(),
                    0
                )

            }

        })

        vehiclesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        vehiclesRecyclerView.adapter = vehiclesAdapter

        vehiclesRecyclerView.addItemDecoration(ItemOffsetDecoration(this, R.dimen.itemOffset))

    }

    private fun populateList(): List<VehicleItem> {
        return listOf(
            VehicleItem(
                R.drawable.ic_car,
                "Sedan",
                350
            ),
            VehicleItem(
                R.drawable.ic_car_cabriolet,
                "Cabriolet",
                500
            ),
            VehicleItem(
                R.drawable.ic_jeep,
                "Jeep",
                450
            ),
            VehicleItem(
                R.drawable.ic_pickup,
                "Pickup",
                400
            ),
            VehicleItem(
                R.drawable.ic_van,
                "Van",
                300
            ),
            VehicleItem(
                R.drawable.ic_minivan,
                "Minivan",
                350
            ),
            VehicleItem(
                R.drawable.ic_truck,
                "Truck",
                300
            )
        )
    }
}