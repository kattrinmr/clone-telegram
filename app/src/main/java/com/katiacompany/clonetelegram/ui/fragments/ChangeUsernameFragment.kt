package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import android.view.*
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.databinding.FragmentChangeUsernameBinding
import com.katiacompany.clonetelegram.utilities.*
import java.util.*

class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    private lateinit var mNewUsername: String
    private lateinit var binding: FragmentChangeUsernameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.settingsInputUsername.setText(USER.username)
    }

    override fun change() {
        mNewUsername = binding.settingsInputUsername.text.toString().lowercase(Locale.getDefault())
        if (mNewUsername.isEmpty()) showToast("Поле пустое!")
        else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) showToast("Такой пользователь уже существует!")
                    else changeUsername()
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful) updateCurrentUsername()
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    deletePrevUsername()
                }
                else showToast(it.exception?.message.toString())
            }
    }

    private fun deletePrevUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    hideKeyboard()
                    parentFragmentManager.popBackStack()
                    USER.username = mNewUsername
                }
                else showToast(it.exception?.message.toString())
            }
    }

}