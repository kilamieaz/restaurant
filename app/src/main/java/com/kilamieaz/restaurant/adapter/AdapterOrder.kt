package com.kilamieaz.restaurant.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.kilamieaz.restaurant.R
import com.kilamieaz.restaurant.activity.MainAdmin
import com.kilamieaz.restaurant.activity.MainChef
import com.kilamieaz.restaurant.activity.MainWaiters
import com.kilamieaz.restaurant.model.ModelDetail
import com.kilamieaz.restaurant.model.ModelOrder

class AdapterOrder : RecyclerView.Adapter<AdapterOrder.OrderHolder> {

    var statusNeed: Int
    var context: Context
    var orders: ArrayList<ModelOrder>
    var onItemClickListerner : OnItemClickListerner

    constructor(statusNeed: Int, context: Context, orders: ArrayList<ModelOrder>, cls: Int) {
        this.statusNeed = statusNeed
        this.context = context
        this.orders = orders
        if (cls == 1)
            onItemClickListerner = context as MainChef
        else if (cls == 2)
            onItemClickListerner = context as MainWaiters
        else
            onItemClickListerner = context as MainAdmin
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.item_main, p0, false)
        return OrderHolder(v)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(h: OrderHolder, p1: Int) {
        val order = orders.get(p1)
//        h.no_order.text = "Transaction : ${order.order_code}"
        if (order.member != null) {
            h.no_order.text = order.member!!.name
        } else {
            h.no_order.text = "Guest"
        }
        h.created_date.text = order.created_at
        h.linearOrder.setOnClickListener {
            onItemClickListerner.click(order)
        }
    }

    class OrderHolder(v: View) : RecyclerView.ViewHolder(v) {
        var no_order: TextView
        var created_date: TextView
        var linearOrder: LinearLayout

        init {
            no_order = v.findViewById(R.id.no_order)
            created_date = v.findViewById(R.id.created_date)
            linearOrder = v.findViewById(R.id.linearOrder)
        }

    }

    interface OnItemClickListerner{
        fun click(order : ModelOrder)
    }
}