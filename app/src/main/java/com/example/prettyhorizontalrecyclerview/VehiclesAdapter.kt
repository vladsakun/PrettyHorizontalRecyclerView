package com.example.prettyhorizontalrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VehiclesAdapter(

    // Список транспорта
    private val vehicleList: List<VehicleItem>,

    // Callback, который вызывается на нажатие элемента
    private val onVehicleItemClickCallback: VehicleItemClickCallback

) : RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder>() {

    // Позиция элемента, который имеет фокус (по умолчанию == 0 (первый элемент))
    private var focusedItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item, parent, false)
        return VehiclesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {

        // Позиция текущего элемента == элемента, который имеет фокус
        if (position == focusedItem) {

            // Установить прозрачнось на 1
            holder.setActiveAlpha()
        } else {

            // Установить прозрачность на 0.5
            holder.setInActiveAlpha()
        }

        // Текущий єлемент
        val vehicleItem = vehicleList[position]

        // Инициализируем изображение транспорта
        holder.vehicleIcon.setImageResource(vehicleItem.vehicleDrawableId)

        // Инициализируем тип транспорта
        holder.vehicleType.text = vehicleItem.vehicleType

        // Инициализируем цену транспорта
        holder.vehiclePrice.text = vehicleItem.vehiclePrice.toString() + vehicleItem.currency
    }

    inner class VehiclesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val vehicleIcon: ImageView = itemView.findViewById(R.id.vehicle_icon)
        val vehicleType: TextView = itemView.findViewById(R.id.vehicle_type)
        val vehiclePrice: TextView = itemView.findViewById(R.id.vehicle_price)

        init {
            // Устанавливаем слушатель нажатия для View
            itemView.setOnClickListener { view ->

                // Х координата центра нажатого элемента
                val xPositionForActiveView = view.x + view.width / 2

                // Х координата для скрола
                val xPositionForScrollView =

                    // Если первый или последний элемент требуется скрол именно к этому элемент
                    if (layoutPosition == 0 || layoutPosition == itemCount - 1) {
                        xPositionForActiveView
                    }

                    // Иначе, требуется делать скрол к предыдущему элементу
                    else {
                        xPositionForActiveView - view.width
                    }

                // Вызываем callback, чтобы в MainActivity изменить позицию бєкграунда фокуса и выполнить скрол
                onVehicleItemClickCallback.onItemClick(
                    xPositionForActiveView,
                    xPositionForScrollView
                )

                // Устанавливаем значение прозрачности 1 для нажатого элемента так как он теперь имеет фокус
                setActiveAlpha()

                // Обновляем старый и новый элементы
                notifyItemChanged(focusedItem, Object())
                focusedItem = layoutPosition
                notifyItemChanged(focusedItem, Object())
            }
        }

        // Установить значение прозрачность 1 для всех View (активный элемент)
        fun setActiveAlpha() {
            vehicleIcon.alpha = 1f
            vehicleType.alpha = 1f
            vehiclePrice.alpha = 1f
        }

        // Установить значение прозрачность 0.5 для всех View (второстепенные элементы)
        fun setInActiveAlpha() {
            vehicleIcon.alpha = 0.5f
            vehicleType.alpha = 0.5f
            vehiclePrice.alpha = 0.5f
        }
    }
}