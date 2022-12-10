package kr.ac.hallym.portfolio

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kr.ac.hallym.portfolio.databinding.ActivityAddPortfolioBinding

class AddPortfolioActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddPortfolioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_portfolio)
        binding = ActivityAddPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addPfToolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add_save -> {
            val inputTitle = binding.addPfTilte.text.toString()
            val inputDetail = binding.addPfDetail.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into PF_TB (title, date) values (?, ?)",
                arrayOf<String>(inputTitle, inputDetail))
            db.close()

            var intent = intent.putExtra("addPfTitle", inputTitle)
            setResult(Activity.RESULT_OK, intent)
            intent = intent.putExtra("addPfDetail", inputDetail)
            setResult(Activity.RESULT_OK, intent)
            finish()
            true
        }
        else -> true
    }

}