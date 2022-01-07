package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.databinding.FragmentChangeBioBinding
import com.katiacompany.clonetelegram.utilities.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    private lateinit var binding: FragmentChangeBioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.settingsInputBio.setText(USER.bio)

    }

    override fun change() {
        super.change()
        val newBio = binding.settingsInputBio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO).setValue(newBio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.bio = newBio
                    parentFragmentManager.popBackStack()
                } else showToast(it.exception?.message.toString())
            }
    }

}