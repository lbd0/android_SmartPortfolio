package com.example.smartportfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.smartportfolio.databinding.ActivityIntroBinding
import com.example.smartportfolio.databinding.FragmentIntroOneBinding
import org.w3c.dom.Text

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_intro)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 화면은 토글 꺼짐
        if(binding.introToggle.isChecked) binding.introToggle.toggle()

        // 읽기모드 /  쓰기모드 변경 토글 이벤트
        binding.introToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            // 쓰기모드로 변경 시
            if(isChecked) {
                // 비밀번호 입력 창 뜨게 함
                val dialogView = layoutInflater.inflate(R.layout.login, null)
                val alertDialog = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .create()

                val wrongTxt = dialogView.findViewById<TextView>(R.id.wrongTxt)
                val onBtn = dialogView.findViewById<Button>(R.id.ok_btn)
                val cancleBtn = dialogView.findViewById<Button>(R.id.cancle_btn)

                // ok 버튼 눌렸을 때
                onBtn.setOnClickListener {
                    // 비밀번호 맞으면 쓰기모드로 변경 (비밀번호는 1234)
                    val password = dialogView.findViewById<EditText>(R.id.password).text
                    Log.d("bada", "$password")
                    if(password.toString().equals("1234")) {
                        wrongTxt.setText("")
                        alertDialog.dismiss()
                        changeFragment(IntroOneFragment())
                    }
                    else {  // 비밀번호 틀리면 틀렸다는 텍스트 뜨고 비밀번호 창 안 꺼짐
                        dialogView.findViewById<EditText>(R.id.password).setText("")
                        wrongTxt.setText("Wrong!")
                    }
                }

                // cancle 버튼 눌렀을 때 토글은 읽기모드 토글로 변경, 쓰기모드로 변경 X
                cancleBtn.setOnClickListener {
                    binding.introToggle.toggle()
                    alertDialog.dismiss()
                }

                alertDialog.show()
            } else {
                // 읽기모드로 변경
                changeFragment(IntroTwoFragment())
            }
        }
        // 첫 화면은 읽기모드
        changeFragment(IntroTwoFragment())



        // main버튼 누르면 splash 화면으로
         binding.introBtn.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }



    }

    // 읽기모드와 쓰기모드 fragment 변경 함수
    private fun changeFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.intro_fragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
    }
}