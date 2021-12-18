package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.databinding.FragmentEnterPhoneNumberBinding
import com.katiacompany.clonetelegram.utilities.replaceFragment
import com.katiacompany.clonetelegram.utilities.showToast

class EnterPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentEnterPhoneNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.registerBtnNext.setOnClickListener {
            sendCode()
        }

    }

    private fun sendCode() {
        if (binding.registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast("Введите номер телефона")
        } else {
            replaceFragment(EnterCodeFragment())
        }
    }
}