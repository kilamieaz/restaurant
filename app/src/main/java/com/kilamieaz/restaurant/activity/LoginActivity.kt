package com.kilamieaz.restaurant.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.util.Log.e
import android.view.View.GONE
import android.view.View.VISIBLE
import com.kilamieaz.restaurant.R
import com.kilamieaz.restaurant.model.ModelLogin
import com.kilamieaz.restaurant.model.Success
import com.kilamieaz.restaurant.utils.ApiClient
import com.kilamieaz.restaurant.utils.ApiInterface
import com.kilamieaz.restaurant.utils.PrefsHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class LoginActivity : AppCompatActivity() {

    var prefsHelper : PrefsHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefsHelper = PrefsHelper(this@LoginActivity)

        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            if (prefsHelper!!.getLogin()) {
                if (prefsHelper!!.getValue("role_id").equals("3")) {
                    startActivity(Intent(this@LoginActivity, MainChef::class.java))
                    finish()
                } else if (prefsHelper!!.getValue("role_id").equals("4")) {
                    startActivity(Intent(this@LoginActivity, MainWaiters::class.java))
                    finish()
                }
                else if (prefsHelper!!.getValue("role_id").equals("1")) {
                    startActivity(Intent(this@LoginActivity, MainAdmin::class.java))
                    finish()
                }
            }
            progressLogin.visibility = GONE
            expandable_layout.expand()

        }, 3000)

        btnLogin.setOnClickListener {
            var err = false
            if (etEmail.editText!!.text.toString().isEmpty()) {
                err = true
                etEmail.error = "Email tidak boleh kosong"
            } else {
                etEmail.error = null
            }
            if (etPassword.editText!!.text.toString().isEmpty()) {
                err = true
                etPassword.error = "Password tidak boleh kosong"
            }  else {
                etPassword.error = null
            }
            if (!err){
                goLogin()
                btnLogin.isEnabled = false
                progressLogin.visibility = VISIBLE
            }
        }

    }

    fun goLogin() {
        val login = ApiClient().getClient().create(ApiInterface::class.java)
        login.login(etEmail.editText!!.text.toString(), etPassword.editText!!.text.toString())
            .enqueue(object : Callback<ModelLogin> {
                override fun onResponse(call: Call<ModelLogin>, response: Response<ModelLogin>) {
                    if (response.code() == 200) {
                        goDetails(response.body()!!.success!!.token!!)
                    } else {
                        Snackbar.make(etEmail, "Email/Password Salah", Snackbar.LENGTH_SHORT).show()
                        e("Salah", response.message())
                    }
                    progressLogin.visibility = GONE
                    btnLogin.isEnabled = true
                }

                override fun onFailure(call: Call<ModelLogin>, t: Throwable) {
                    e("Kesalahan", t.message)
                    progressLogin.visibility = GONE
                    btnLogin.isEnabled = true
                }
            })
    }

    fun goDetails(token: String) {
        val login = ApiClient().getClient().create(ApiInterface::class.java)
        login.detailsLogin(
            "Bearer ${token}",
            "application/json",
            "XMLHttpRequest"
        )
            .enqueue(object : Callback<Success> {
                override fun onResponse(call: Call<Success>, response: Response<Success>) {
                    if (response.code() == 200) {
                        prefsHelper!!.setLogin(true)
                        prefsHelper!!.setValue("id_user", "${response.body()!!.success!!.id}")
                        prefsHelper!!.setValue("email", "${response.body()!!.success!!.email}")
                        prefsHelper!!.setValue("name", "${response.body()!!.success!!.name}")
                        prefsHelper!!.setValue("handphone", "${response.body()!!.success!!.handphone}")
                        prefsHelper!!.setValue("role_id", "${response.body()!!.success!!.role_id}")
                        prefsHelper!!.setValue("role", "${response.body()!!.success!!.role!!.name}")
                        if (response.body()!!.success!!.role_id == 3) {
                            startActivity(Intent(this@LoginActivity, MainChef::class.java))
                            finish()
                        } else if (response.body()!!.success!!.role_id == 4) {
                            startActivity(Intent(this@LoginActivity, MainWaiters::class.java))
                            finish()
                        } else if (response.body()!!.success!!.role_id == 1) {
                            startActivity(Intent(this@LoginActivity, MainAdmin::class.java))
                            finish()
                        }
                    } else {
                        e("Kesalahan", response.message())
                    }
                    progressLogin.visibility = GONE
                    btnLogin.isEnabled = true
                }

                override fun onFailure(call: Call<Success>, t: Throwable) {
                    e("Kesalahan", t.message)
                    progressLogin.visibility = GONE
                    btnLogin.isEnabled = true
                }
            })
    }
}
