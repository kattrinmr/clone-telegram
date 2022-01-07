package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.katiacompany.clonetelegram.MainActivity
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.databinding.FragmentChangeNameBinding
import com.katiacompany.clonetelegram.utilities.*

class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    private lateinit var binding: FragmentChangeNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size > 1) {
            binding.settingsInputName.setText(fullnameList[0])
            binding.settingsInputSurname.setText(fullnameList[1])
        } else binding.settingsInputName.setText(fullnameList[0])
    }

    override fun change() {
        val name = binding.settingsInputName.text.toString()
        val surname = binding.settingsInputSurname.text.toString()

        if (name.isEmpty()) showToast(getString(R.string.settings_toast_name_is_empty))
        else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        parentFragmentManager.popBackStack()
                    }
                }
        }
    }

}