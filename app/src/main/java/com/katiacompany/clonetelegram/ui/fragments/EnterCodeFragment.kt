package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.katiacompany.clonetelegram.MainActivity
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.activities.RegisterActivity
import com.katiacompany.clonetelegram.databinding.FragmentEnterCodeBinding
import com.katiacompany.clonetelegram.utilities.AUTH
import com.katiacompany.clonetelegram.utilities.AppTextWatcher
import com.katiacompany.clonetelegram.utilities.replaceActivity
import com.katiacompany.clonetelegram.utilities.showToast

class EnterCodeFragment(val PhoneNumber: String, val id: String) : Fragment(R.layout.fragment_enter_code) {

    private lateinit var binding: FragmentEnterCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //(activity as RegisterActivity).title = getString(R.string.register_title_your_phone)
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
        AUTH.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Добро пожаловать!")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else showToast(it.exception?.message.toString())
        }
    }

}