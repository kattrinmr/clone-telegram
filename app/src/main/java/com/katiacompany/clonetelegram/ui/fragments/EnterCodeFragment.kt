package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.databinding.FragmentEnterCodeBinding
import com.katiacompany.clonetelegram.utilities.AppTextWatcher
import com.katiacompany.clonetelegram.utilities.showToast

class EnterCodeFragment : Fragment(R.layout.fragment_enter_code) {

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
        binding.registerInputCode.addTextChangedListener(AppTextWatcher {
            val s = binding.registerInputCode.text.toString()
            if (s.length == 6) {
                verifiedCode()
            }
        })
    }

    fun verifiedCode() {
        showToast("OK")
    }

}