package com.katiacompany.clonetelegram.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import com.katiacompany.clonetelegram.R
import com.katiacompany.clonetelegram.activities.RegisterActivity
import com.katiacompany.clonetelegram.databinding.FragmentSettingsBinding
import com.katiacompany.clonetelegram.utilities.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        binding.settingsFullName.text = USER.fullname
        binding.settingsPhoneNumber.text = USER.phone
        binding.settingsChangeInfo.text = USER.bio
        binding.settingsStatus.text = USER.status
        binding.settingsChangeUsername.text = USER.username

        binding.settingsBtnChangeUsername.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        binding.settingsBtnChangeInfo.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        binding.settingsChangePhoto.setOnClickListener { changePhotoUser() }
    }

    private fun changePhotoUser() {
        APP_ACTIVITY.cropActivityResultLauncher.launch(null)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }

}