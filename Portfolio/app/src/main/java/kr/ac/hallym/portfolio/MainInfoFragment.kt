package kr.ac.hallym.portfolio

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
        var prefs = context?.getSharedPreferences("img", 0)
        if(prefs?.getString("img", "") == "") {
            binding.mainUserImageView.setImageResource(R.drawable.user_basic)
        } else {
            val sImg = prefs?.getString("img", "")
            val encodeByte = Base64.decode(sImg, Base64.DEFAULT)
            val img = BitmapFactory.decodeByteArray(encodeByte, 0 , encodeByte.size)

            binding.mainUserImageView.setImageBitmap(img)
        }
        var name:String? = null; var birth:String? = null; var major:String? = null; var phone:String? = null; var email:String? = null
        var git:String? = null; var more:String? = null

        val prefs_name = context?.getSharedPreferences("infoName", 0)
        val prefs_birth = context?.getSharedPreferences("infoBirth", 0)
        val prefs_major = context?.getSharedPreferences("infoMajor", 0)
        val prefs_phone = context?.getSharedPreferences("infoPhone", 0)
        val prefs_email = context?.getSharedPreferences("infoEmail", 0)
        val prefs_git = context?.getSharedPreferences("infoGit", 0)
        val prefs_more = context?.getSharedPreferences("infoMore", 0)

        name = prefs_name?.getString("infoName", "")
        birth = prefs_birth?.getString("infoBirth", "")
        major = prefs_major?.getString("infoMajor", "")
        phone = prefs_phone?.getString("infoPhone", "")
        email = prefs_email?.getString("infoEmail", "")
        git = prefs_git?.getString("infoGit", "")
        more = prefs_more?.getString("infoMore", "")

        binding.mainInfoName.setText(R.string.name)
        binding.mainInfoName.append(" : ${name}")
        binding.mainInfoBirth.setText(R.string.birth)
        binding.mainInfoBirth.append(" : ${birth}")
        binding.mainInfoMajor.setText(R.string.major)
        binding.mainInfoMajor.append(" : ${major}")
        binding.mainInfoPhone.setText(R.string.phone)
        binding.mainInfoPhone.append(" : ${phone}")
        binding.mainInfoEmail.setText(R.string.email)
        binding.mainInfoEmail.append(" : ${email}")
        binding.mainInfoGit.setText(R.string.git)
        binding.mainInfoGit.append(" : ${git}")
        binding.mainInfoMore.text = "${more}"

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            name = prefs_name?.getString("infoName", "")

            birth = prefs_birth?.getString("infoBirth", "")

            major = prefs_major?.getString("infoMajor", "")

            phone = prefs_phone?.getString("infoPhone", "")

            email = prefs_email?.getString("infoEmail", "")

            git = prefs_git?.getString("infoGit", "")

            more = prefs_more?.getString("infoMore", "")

            binding.mainInfoName.setText(R.string.name)
            binding.mainInfoName.append(" : ${name}")
            binding.mainInfoBirth.setText(R.string.birth)
            binding.mainInfoBirth.append(" : ${birth}")
            binding.mainInfoMajor.setText(R.string.major)
            binding.mainInfoMajor.append(" : ${major}")
            binding.mainInfoPhone.setText(R.string.phone)
            binding.mainInfoPhone.append(" : ${phone}")
            binding.mainInfoEmail.setText(R.string.email)
            binding.mainInfoEmail.append(" : ${email}")
            binding.mainInfoGit.setText(R.string.git)
            binding.mainInfoGit.append(" : ${git}")
            binding.mainInfoMore.text = "${more}"

        }

        if(IntroActivity.mode == IntroActivity.READMODE) binding.mainInfoFab.visibility = View.GONE
        binding.mainInfoFab.setOnClickListener {
            val intent = Intent(activity, EditInfoActivity::class.java)
            requestLauncher.launch(intent)
        }

        return binding.root
    }

}