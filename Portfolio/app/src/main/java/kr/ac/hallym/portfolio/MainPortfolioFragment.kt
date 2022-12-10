package kr.ac.hallym.portfolio

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.hallym.portfolio.databinding.FragmentMainPortfolioBinding
import kr.ac.hallym.portfolio.databinding.MainPortfolioRecyclerviewBinding
import java.util.zip.GZIPOutputStream

// 포트폴리오 화면
class MainPortfolioFragment : Fragment() {
    lateinit var adapter : MyAdapterPf

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_portfolio, container, false)

        val binding = FragmentMainPortfolioBinding.inflate(inflater, container, false)

        val contents1 = mutableListOf<Int>(R.drawable.user_basic,R.drawable.user_basic,R.drawable.user_basic)
        val contents2 = mutableListOf<String>()
        val contents3 = mutableListOf<String>()

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            contents2.add(it.data?.getStringExtra("addPfTitle").toString())
            contents3.add(it.data?.getStringExtra("addPfDetail").toString())
            adapter.notifyDataSetChanged()
        }

        if(IntroActivity.mode === IntroActivity.READMODE) binding.mainPfFab.visibility = View.GONE
        binding.mainPfFab.setOnClickListener {
            val intent = Intent(activity, AddPortfolioActivity::class.java)
            requestLauncher.launch(intent)
        }

        val db = DBHelper(activity!!).readableDatabase
        val cursor = db.rawQuery("select * from PF_TB", null)
        cursor.run {
            while(moveToNext()) {
                contents2.add(cursor.getString(1))
                contents3.add(cursor.getString(2))
            }
        }
        db.close()


        binding.mainPfRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = MyAdapterPf(contents1, contents2, contents3)
        binding.mainPfRecyclerview.adapter = adapter
        binding.mainPfRecyclerview.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )


        return binding.root
    }

}

class MyViewHolderPf(val binding : MainPortfolioRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapterPf (val contents1 : MutableList<Int>?, val contents2: MutableList<String>?, val contents3: MutableList<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolderPf(MainPortfolioRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolderPf).binding
        binding.mainPfImageview.setImageResource(contents1!![position])
        binding.mainPfTitletxt.text = contents2!![position]
        binding.mainPfDetailtxt.text = contents3!![position]
    }

    override fun getItemCount(): Int {
        return contents1?.size ?:0
    }
}