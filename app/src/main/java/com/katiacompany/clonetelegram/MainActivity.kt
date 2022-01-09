package com.katiacompany.clonetelegram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.katiacompany.clonetelegram.activities.RegisterActivity
import com.katiacompany.clonetelegram.databinding.ActivityMainBinding
import com.katiacompany.clonetelegram.models.User
import com.katiacompany.clonetelegram.ui.fragments.ChatsFragment
import com.katiacompany.clonetelegram.ui.objects.AppDrawer
import com.katiacompany.clonetelegram.utilities.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        APP_ACTIVITY = this

        initFirebase()
        initUser {
            initFields()
            initFunc()
        }
    }

    override fun onStart() {
        super.onStart()
        AppStatus.updateStatus(AppStatus.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        AppStatus.updateStatus(AppStatus.OFFLINE)
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
    }
}