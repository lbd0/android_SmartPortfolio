package kr.ac.hallym.portfolio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.hallym.portfolio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(IntroActivity.mode === IntroActivity.WRITEMODE)  binding.mainToolbar.title = "Main_WriteMode"
        setSupportActionBar(binding.mainToolbar)


        val adapter = MyFragmentPagerAdapter(this)
        binding.mainViewpager.adapter = adapter
        TabLayoutMediator(binding.mainTabs, binding.mainViewpager) {tab, position ->
            when(position) {
                0 -> tab.text = "Infomation"
                1 -> tab.text = "Resume"
                2 -> tab.text = "Portfolio"
            }
        }.attach()

        // 네비게이션 나오는 햄버거버튼
        toggle = ActionBarDrawerToggle(this, binding.mainDrawer, R.string.drawer_opened, R.string.drawer_closed)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 네비게이션 메뉴 설정
        val menuItem = binding.mainDrawerView.menu
        if(IntroActivity.mode === IntroActivity.READMODE) {
            // 읽기 모드 일 경우 읽기 모드로 바꾸는 메뉴 안 보이게
            menuItem.getItem(2).isVisible = false
        } else {
            // 쓰기 모드 일 경우 쓰기 모드로 바꾸는 메뉴 안 보이게
            menuItem.getItem(1).isVisible = false
        }

        // 네비게이션
        binding.mainDrawerView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.logout -> {
                    val intent = Intent(this, IntroActivity::class.java)
                    IntroActivity.mode = IntroActivity.READMODE
                    startActivity(intent)
                }

                R.id.wm -> {
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
                        if (password.toString().equals("1234")) {
                            wrongTxt.setText("")
                            alertDialog.dismiss()
                            IntroActivity.mode = IntroActivity.WRITEMODE
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {  // 비밀번호 틀리면 틀렸다는 텍스트 뜨고 비밀번호 창 안 꺼짐
                            dialogView.findViewById<EditText>(R.id.password).setText("")
                            wrongTxt.setText("Wrong!")
                        }
                    }
                    // cancle 버튼 눌렀을 때 쓰기모드로 변경 X
                    cancleBtn.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    alertDialog.show()
                }

                R.id.rm -> {
                    IntroActivity.mode = IntroActivity.READMODE
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments : List<Fragment>
    init {
        fragments = listOf(MainInfoFragment(), MainResumeFragment(), MainPortfolioFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment = fragments[position]
}