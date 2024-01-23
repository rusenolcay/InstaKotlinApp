package com.rusen.instagramcloneapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rusen.instagramcloneapp.Post.PostActivity
import com.rusen.instagramcloneapp.Post.ReelsActivity
import com.rusen.instagramcloneapp.databinding.FragmentAddBinding

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), PostActivity::class.java))
        }
        binding.reell.setOnClickListener {
            activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))

        }
        return binding.root
    }
}