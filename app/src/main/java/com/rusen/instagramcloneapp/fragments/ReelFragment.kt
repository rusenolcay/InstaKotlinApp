package com.rusen.instagramcloneapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.rusen.instagramcloneapp.Models.Reel
import com.rusen.instagramcloneapp.R
import com.rusen.instagramcloneapp.adapers.ReelAdapter
import com.rusen.instagramcloneapp.databinding.FragmentReelBinding
import com.rusen.instagramcloneapp.utils.REEL

class ReelFragment : Fragment() {
    private lateinit var binding: FragmentReelBinding
    lateinit var adapter: ReelAdapter
    var reelList = ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelBinding.inflate(inflater, container, false)
        adapter = ReelAdapter(requireContext(), reelList)
        binding.viewPager.adapter = adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {

            var tempList = ArrayList<Reel>()
            reelList.clear()

            for (i in it.documents) {
                var reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
        }
        return binding.root
    }

}