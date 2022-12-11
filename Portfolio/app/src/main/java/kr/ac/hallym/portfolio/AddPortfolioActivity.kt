package kr.ac.hallym.portfolio

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.hallym.portfolio.databinding.ActivityAddPortfolioBinding
import java.io.ByteArrayOutputStream

class AddPortfolioActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddPortfolioBinding
    var sImg : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add_portfolio)
        binding = ActivityAddPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addPfToolbar)



        // 갤러리 연동
        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.pfimgsize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                var inputStream = contentResolver?.openInputStream(it.data!!.data!!)
                var bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let{
                    binding.addPfImage.setImageBitmap(bitmap)
                    val baos= ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos)
                    val byte = baos.toByteArray()
                    sImg = Base64.encodeToString(byte, Base64.DEFAULT)


                }?: let {
                    Log.d("bada", "bitmap null")
                }
            } catch (e : Exception) {
                Log.d("bada", "bitmap null")
            }
        }

        binding.addPfImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver?.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        if(height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth:  Int = width / 2
            while(halfHeight / inSampleSize >= reqHeight &&
                halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.menu_add_save -> {
            val inputImg = sImg
            val inputTitle = binding.addPfTilte.text.toString()
            val inputDetail = binding.addPfDetail.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into PF_TB (img, title, detail) values (?, ?, ?)",
                arrayOf<String>(inputImg!!, inputTitle, inputDetail))

            db.close()

            val prefs = getSharedPreferences("pfImg", 0)
            prefs?.edit()?.putString("pfImg", inputImg)?.apply()
            intent = intent.putExtra("addPfTitle", inputTitle)
            setResult(Activity.RESULT_OK, intent)
            intent = intent.putExtra("addPfDetail", inputDetail)
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }
        else -> true
    }

}