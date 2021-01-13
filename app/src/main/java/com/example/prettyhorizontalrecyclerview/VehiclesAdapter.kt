package com.example.prettyhorizontalrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.ceil

class VehiclesAdapter(
    private val vehicleList: List<VehicleItem>,
    private val onVehicleItemClickCallback: VehicleItemClickCallback
) : RecyclerView.Adapter<VehiclesAdapter.VehiclesViewHolder>() {

    private var focusedItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item, parent, false)
        return VehiclesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {

        if (position == focusedItem) {
            holder.setActiveAlpha()
        } else {
            holder.setInActiveAlpha()
        }

        val vehicleItem = vehicleList[position]
        holder.vehicleIcon.setImageResource(vehicleItem.vehicleDrawableId)
        holder.vehicleType.text = vehicleItem.vehicleType
        holder.vehiclePrice.text = vehicleItem.vehiclePrice.toString() + vehicleItem.currency

        holder.itemView.setOnClickListener { view ->
            val xPositionForActiveView = view.x + view.width / 2

            val xPositionForScrollView =

                // Если первый или последний элемент требуется скрол именно к этому элемент
                if (position == 0 || position == itemCount - 1) {
                    xPositionForActiveView
                }

                // Иначе, требуется делать скрол к предыдущему элементу
                else {
                    xPositionForActiveView - view.width
                }

            onVehicleItemClickCallback.onItemClick(xPositionForActiveView, xPositionForScrollView)

            holder.setActiveAlpha()
            notifyItemChanged(focusedItem)
            focusedItem = position
        }
    }

    inner class VehiclesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleIcon: ImageView = itemView.findViewById(R.id.vehicle_icon)
        val vehicleType: TextView = itemView.findViewById(R.id.vehicle_type)
        val vehiclePrice: TextView = itemView.findViewById(R.id.vehicle_price)

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