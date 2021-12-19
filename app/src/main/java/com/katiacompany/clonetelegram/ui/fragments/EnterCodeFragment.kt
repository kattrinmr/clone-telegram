package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.PhoneAuthProvider
import com.katiacompany.clonetelegram.MainActivity
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.activities.RegisterActivity
import com.katiacompany.clonetelegram.databinding.FragmentEnterCodeBinding
import com.katiacompany.clonetelegram.utilities.*

class EnterCodeFragment(private val phoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    private lateinit var binding: FragmentEnterCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = getString(R.string.check_your_phone)
        binding.registerInputCode.addTextChangedListener(AppTextWatcher {
            val s = binding.registerInputCode.text.toString()
            if (s.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = binding.registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dataMap = mutableMapOf<String, Any>()
                dataMap[CHILD_ID] = uid
                dataMap[CHILD_PHONE] = phoneNumber
                dataMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dataMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            showToast("Добро пожаловать!")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else showToast(task2.exception?.message.toString())
                    }
            } else showToast(task.exception?.message.toString())
        }
    }
}