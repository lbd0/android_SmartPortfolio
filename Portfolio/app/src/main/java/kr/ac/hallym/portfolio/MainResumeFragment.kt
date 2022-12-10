package kr.ac.hallym.portfolio

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import kr.ac.hallym.portfolio.databinding.FragmentMainResumeBinding
import kr.ac.hallym.portfolio.databinding.MainResumeRecyclerviewBinding

// 이력 화면
class MainResumeFragment : Fragment() {
    lateinit var adapter : MyAdapterResume

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_resume, container, false)

        val binding = FragmentMainResumeBinding.inflate(inflater, container, false)

        val contents = mutableListOf<String>()
        val contents_sub = mutableListOf<String>()

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            contents.add(it.data?.getStringExtra("addResumeTitle").toString())
            contents_sub.add(it.data?.getStringExtra("addResumeDate").toString())
            adapter.notifyDataSetChanged()
        }

        if(IntroActivity.mode == IntroActivity.READMODE) binding.mainResumeFab.visibility = View.GONE
        binding.mainResumeFab.setOnClickListener {
            val intent = Intent(activity, AddResumeActivity::class.java)
            requestLauncher.launch(intent)
        }

        val db = DBHelper(activity!!).readableDatabase
        val cursor = db.rawQuery("select * from RESUME_TB", null)
        cursor.run {
            while(moveToNext()) {
                contents.add(cursor.getString(1))
                contents_sub.add(cursor.getString(2))
            }
        }
        db.close()

        binding.mainResumeRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = MyAdapterResume(contents, contents_sub)
        binding.mainResumeRecyclerview.adapter = adapter

        return binding.root
    }
}

class MyViewHolderResume(val binding: MainResumeRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapterResume(val contents: MutableList<String>?, val contents_sub: MutableList<String>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolderResume(MainResumeRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolderResume).binding

        binding.mainResumeData.text = contents!![position]
        binding.mainResumeSubdata.text = contents_sub!![position]

    }

    override fun getItemCount(): Int {
        return contents?.size ?:0
    }
}

