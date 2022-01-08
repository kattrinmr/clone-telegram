package com.katiacompany.clonetelegram

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.widget.Toolbar
import com.google.firebase.storage.StorageReference
import com.katiacompany.clonetelegram.activities.RegisterActivity
import com.katiacompany.clonetelegram.databinding.ActivityMainBinding
import com.katiacompany.clonetelegram.models.User
import com.katiacompany.clonetelegram.ui.fragments.ChatsFragment
import com.katiacompany.clonetelegram.ui.objects.AppDrawer
import com.katiacompany.clonetelegram.utilities.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }

        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) { it ->
            it?.let { uri ->
                val path: StorageReference = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                    .child(UID)
                path.putFile(uri).addOnCompleteListener { it2 ->
                    if (it2.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                    } else showToast(it2.exception?.message.toString())
                }
            }
        }

    }

    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java) ?: User()
            })
    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1,1)
                .setRequestedSize(600, 600)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(APP_ACTIVITY)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}