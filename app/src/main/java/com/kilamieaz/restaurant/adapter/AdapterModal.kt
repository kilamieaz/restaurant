package com.kilamieaz.restaurant.adapter

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.kilamieaz.restaurant.R
import com.kilamieaz.restaurant.model.ModelDetail
import com.kilamieaz.restaurant.utils.ApiClient
import com.kilamieaz.restaurant.utils.ApiInterface
import com.kilamieaz.restaurant.utils.PrefsHelper
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterModal : BaseAdapter {

    var details : ArrayList<ModelDetail>
    var context : Context
    var inflater : LayoutInflater
    var prefsHelper : PrefsHelper

    constructor(details: ArrayList<ModelDetail>, context: Context) {
        this.details = details
        this.context = context
        inflater = LayoutInflater.from(context)
        prefsHelper = PrefsHelper(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val detail = details.get(position)
        val v = inflater.inflate(R.layout.item_modal, null)
        val imgModal = v.findViewById<ImageView>(R.id.img_list)
        val nama_menu = v.findViewById<TextView>(R.id.nama_menu)
        val qty = v.findViewById<TextView>(R.id.qty)
        val subTotal = v.findViewById<TextView>(R.id.sub_total)
        val checkBox = v.findViewById<CheckBox>(R.id.checkbox)
        val iconList = v.findViewById<ImageView>(R.id.iconList)

        nama_menu.setText("${detail.table!!.name}")
        qty.setText("${detail.menu!!.name} [ ${detail.quantity} ]")
        subTotal.setText("Rp. " + String.format("%,d", detail.sub_total))
        if (prefsHelper.getValue("role_id").equals("3")) {
            if (detail.status == 2) {
                checkBox.isChecked = true
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    val update = ApiClient().getClient().create(ApiInterface::class.java)
//                    detail.status = 2
//                    detail.chef_id = prefsHelper.getValue("id_user")!!.toInt()
                    val map = HashMap<String, Int>()
                    map.put("chef_id", prefsHelper.getValue("id_user")!!.toInt())
                    update.updateOrder(detail.id!!, 2, map).enqueue(object : Callback<ModelDetail> {
                        override fun onResponse(call: Call<ModelDetail>, response: Response<ModelDetail>) {
                            if (response.code() == 200) {
                                details.set(position, response.body()!!)
                                e("body", response.body()!!.status.toString())
                            }else{
                                detail.status = 1
                                detail.chef_id = null
                                checkBox.isChecked = false
                                e("msg", response.message())
                            }
                        }

                        override fun onFailure(call: Call<ModelDetail>, t: Throwable) {
                            checkBox.isChecked = false
                            e("msg", t.message)
                        }

                    })
                } else {
                    if (detail.status == 2) {
                        checkBox.isChecked = true
                    }
                }
            }
        } else if (prefsHelper.getValue("role_id").equals("4")) {
            if (detail.status == 3) {
                checkBox.isChecked = true
            }
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    val update = ApiClient().getClient().create(ApiInterface::class.java)
//                    detail.status = 3
//                    detail.chef_id = prefsHelper.getValue("id_user")!!.toInt()
                    val map = HashMap<String, Int>()
                    map.put("waiter_id", prefsHelper.getValue("id_user")!!.toInt())
                    update.updateOrder(detail.id!!, 3, map).enqueue(object : Callback<ModelDetail> {
                        override fun onResponse(call: Call<ModelDetail>, response: Response<ModelDetail>) {
                            if (response.code() == 200) {
                                details.set(position, response.body()!!)
                            }else{
                                detail.status = 1
                                detail.waiter_id = null
                                checkBox.isChecked = false
                                checkBox.isChecked = false
                            }
                        }

                        override fun onFailure(call: Call<ModelDetail>, t: Throwable) {
                            checkBox.isChecked = false
                            e("msg", t.message)
                        }

                    })
                } else {
                    if (detail.status == 3) {
                        checkBox.isChecked = true
                    }
                }
            }
        } else if (prefsHelper.getValue("role_id").equals("1")) {
            checkBox.visibility = GONE
            iconList.visibility = VISIBLE
            if (detail.status == 1) {
                iconList.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_ordered_24dp))
            } else if (detail.status == 2) {
                iconList.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_cooked_24dp))
            } else if (detail.status == 3){
                iconList.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sended_24dp))
            }
        }

        Picasso.with(context)
            .load("${ApiClient.storageUrl}/${detail.menu!!.photo}")
            .error(R.drawable.ic_launcher_background)
            .into(imgModal)

        return v
    }

    override fun getItem(position: Int): Any {
        return details.get(position)
    }

    override fun getItemId(position: Int): Long {
        return details.get(position).id!!.toLong()
    }

    override fun getCount(): Int {
        return details.size
    }

}