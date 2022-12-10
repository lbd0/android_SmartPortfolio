package kr.ac.hallym.portfolio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kr.ac.hallym.portfolio.databinding.FragmentMainInfoBinding

// 인적사항 화면
class MainInfoFragment : Fragment() {
    lateinit var binding : FragmentMainInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_info, container, false)
        binding = FragmentMainInfoBinding.inflate(inflater, container, false)

        if(IntroActivity.mode == IntroActivity.READMODE) binding.mainInfoFab.visibility = View.GONE
        binding.mainInfoFab.setOnClickListener {

        }

        return binding.root
    }

}