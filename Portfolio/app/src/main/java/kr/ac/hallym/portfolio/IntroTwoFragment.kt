package kr.ac.hallym.portfolio

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.ac.hallym.portfolio.databinding.FragmentIntroTwoBinding

// 인트로 읽기모드 프래그먼트
class IntroTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_intro_two, container, false)
        val binding = FragmentIntroTwoBinding.inflate(inflater, container, false)

        var prefs = context?.getSharedPreferences("name", 0)
        val name = prefs?.getString("name", "")

        prefs = context?.getSharedPreferences("img", 0)
        val sImg = prefs?.getString("img", "")
        val encodeByte = Base64.decode(sImg, Base64.DEFAULT)
        val img = BitmapFactory.decodeByteArray(encodeByte, 0 , encodeByte.size)

        binding.userName.setText(name)
        binding.introUserImageView.setImageBitmap(img)

        return binding.root
    }

}