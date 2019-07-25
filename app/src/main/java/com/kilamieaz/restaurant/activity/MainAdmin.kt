package com.kilamieaz.restaurant.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ListView
import android.widget.TextView
import com.kilamieaz.restaurant.R
import com.kilamieaz.restaurant.adapter.AdapterModal
import com.kilamieaz.restaurant.adapter.AdapterOrder
import com.kilamieaz.restaurant.model.ModelOrder
import com.kilamieaz.restaurant.utils.ApiClient
import com.kilamieaz.restaurant.utils.ApiInterface
import com.kilamieaz.restaurant.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainAdmin : AppCompatActivity(), AdapterOrder.OnItemClickListerner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
        rc_main.layoutManager = LinearLayoutManager(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "${PrefsHelper(this).getValue("name")}"
        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private fun getData() {
        shimmer_view_container.startShimmerAnimation()
        shimmer_view_container.visibility = VISIBLE
        val order = ApiClient().getClient().create(ApiInterface::class.java)
        order.orders().enqueue(object : Callback<ArrayList<ModelOrder>> {

            override fun onResponse(call: Call<ArrayList<ModelOrder>>, response: Response<ArrayList<ModelOrder>>) {
                if (response.code() == 200){
                    val orders = response.body()
                    rc_main.adapter = AdapterOrder(0, this@MainAdmin, orders!!, 0)
                } else {
                    Snackbar.make(rc_main, "Kesalahan Server code : ${response.code()}", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", object : View.OnClickListener{
                            override fun onClick(v: View?) {
                                getData()
                            }

                        }).show()
                    Log.e("${response.code()}", response.message())
                }
                shimmer_view_container.stopShimmerAnimation()
                shimmer_view_container.visibility = GONE
                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<ArrayList<ModelOrder>>, t: Throwable) {
                Snackbar.make(rc_main, "Kesalahan Server karena : ${t.message}", Snackbar.LENGTH_INDEFINITE).setAction("Try Again", object : View.OnClickListener{
                    override fun onClick(v: View?) {
                        getData()
                    }

                }).show()
                Log.e("fail", t.message)
                shimmer_view_container.stopShimmerAnimation()
                shimmer_view_container.visibility = GONE
                swipeRefresh.isRefreshing = false
            }
        })
    }

    override fun click(order: ModelOrder) {
        val builder = AlertDialog.Builder(this@MainAdmin)
        val v : View = layoutInflater.inflate(R.layout.modal_order, null)
        builder.setView(v)
        builder.setCancelable(true)
        val nama_pemesan = v.findViewById<TextView>(R.id.nama_pemesan)
        val no_meja = v.findViewById<TextView>(R.id.no_meja)
        val listView = v.findViewById<ListView>(R.id.lv_modal)

        listView.adapter = AdapterModal(order.detail_orders!!, this@MainAdmin)
        no_meja.setText(order.order_code)
        nama_pemesan.setText(order.created_at)

        val dialog = builder.create()
        dialog.window.getAttributes().windowAnimations = R.style.DialogTheme
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.logout -> {
                PrefsHelper(this@MainAdmin).setLogin(false)
                startActivity(Intent(this@MainAdmin, LoginActivity::class.java))
                finish()
            }
        }
        return true
    }
}