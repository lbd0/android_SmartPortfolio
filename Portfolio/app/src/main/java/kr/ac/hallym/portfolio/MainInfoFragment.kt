package kr.ac.hallym.portfolio

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
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

        // 인트로 이미지 불러오기
        val prefs = context?.getSharedPreferences("img", 0)
        if(prefs == null) {
            binding.mainUserImageView.setImageResource(R.drawable.user_basic)
        } else {
            val sImg = prefs?.getString("img", "")
            val encodeByte = Base64.decode(sImg, Base64.DEFAULT)
            val img = BitmapFactory.decodeByteArray(encodeByte, 0 , encodeByte.size)

            binding.mainUserImageView.setImageBitmap(img)
        }

        if(IntroActivity.mode == IntroActivity.READMODE) binding.mainInfoFab.visibility = View.GONE
        binding.mainInfoFab.setOnClickListener {

        }

        return binding.root
    }

}