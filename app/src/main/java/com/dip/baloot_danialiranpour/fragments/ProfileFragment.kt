package com.dip.baloot_danialiranpour.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import com.dip.baloot_danialiranpour.R
import com.dip.baloot_danialiranpour.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {


    lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.githubLink.movementMethod = LinkMovementMethod.getInstance()

        binding.aboutMeBtn.setOnClickListener{
            val bottomSheet: BottomSheetFragment = BottomSheetFragment().newInstance()
            bottomSheet.show(
                childFragmentManager,
                "bottomSheet"
            )
        }



    }
}