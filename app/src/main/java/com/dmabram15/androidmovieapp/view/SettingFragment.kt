package com.dmabram15.androidmovieapp.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.edit
import com.dmabram15.androidmovieapp.viewmodel.SettingViewModel
import com.dmabram15.androidmoviesapp.R
import com.dmabram15.androidmoviesapp.databinding.SettingFragmentBinding

class SettingFragment : Fragment() {

    private var isShowAdult : Boolean? = null

    companion object {
        fun newInstance() = SettingFragment()
    }
    private lateinit var binding: SettingFragmentBinding
    private val viewModel: SettingViewModel by lazy {
        ViewModelProvider(this).get(SettingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPreferences()
        setListeners()
    }

    private fun setListeners() {
        binding.includeAdultCheckBox.setOnClickListener { view ->
            activity?.let {
                it.getPreferences(Context.MODE_PRIVATE).edit(){
                    putBoolean(
                        ADULT_PREFER_KEY,
                        (view as CheckBox).isChecked
                    )
                    apply()
                }
            }
        }
    }

    private fun getPreferences() {
        activity?.let {
            isShowAdult = it.getPreferences(Context.MODE_PRIVATE).getBoolean(ADULT_PREFER_KEY, false)
            binding.includeAdultCheckBox.isChecked = isShowAdult as Boolean
        }
    }

}