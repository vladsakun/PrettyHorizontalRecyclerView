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

        // Устанавливаем горизонтальный LinearLayoutManager списку
        vehiclesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Указываем расстояние между элементами списка
        vehiclesRecyclerView.addItemDecoration(ItemSpacingDecoration(this, R.dimen.spacingBetweenListItems))

        // Отключаем NestedScrollView у списка
        vehiclesRecyclerView.isNestedScrollingEnabled = false

        // Устанавливаем размер кэширования 10,
        // чтобы при первом скролле уже были прогружены элементы всего списка
        vehiclesRecyclerView.setItemViewCacheSize(10)

        // Конвертируем расстояние между элементами из dp в px
        val dip = resources.getDimension(R.dimen.spacingBetweenListItems)
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            resources.displayMetrics
        )

        // Инициализируем адаптер
        val vehiclesAdapter = VehiclesAdapter(populateList(), object : VehicleItemClickCallback {

            override fun onItemClick(
                xPositionForFocusedBackgroundView: Float, // Х координата для бэкграунда элемента в фокусе (центр элемента)
                xPositionForHorizontalScrollView: Float // Х координата для скрола (центр предыдущего или нажатого элемента)
            ) {

                // Перемещаем бэкграунд по оси х к центру нажатого элемента - центр бэкграунда
                focusedBackgroundView.animate()
                    .translationX(xPositionForFocusedBackgroundView - focusedBackgroundView.width / 2)

                // Скролим по оси х к центру предыдущего или нажатого элемента - центр бэкграунда - расстояние между элементами
                horizontalScrollView.smoothScrollTo(
                    (xPositionForHorizontalScrollView - focusedBackgroundView.width / 2 - px).toInt(),
                    0
                )

            }
        })

        // Указываем списку адаптер
        vehiclesRecyclerView.adapter = vehiclesAdapter

    }

    // Инициализируем данные для списка
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