package kr.ac.hallym.portfolio

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kr.ac.hallym.portfolio.databinding.ActivityAddResumeBinding

class AddResumeActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddResumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_resume)
        binding = ActivityAddResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addResumeToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add_save -> {
            val inputTitle = binding.addResumeTilte.text.toString()
            val inputDate = binding.addResumeDate.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into RESUME_TB (title, date) values (?, ?)",
                arrayOf<String>(inputTitle, inputDate))
            db.close()

            var intent = intent.putExtra("addResumeTitle", inputTitle)
            setResult(Activity.RESULT_OK, intent)
            intent = intent.putExtra("addResumeDate", inputDate)
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }
}