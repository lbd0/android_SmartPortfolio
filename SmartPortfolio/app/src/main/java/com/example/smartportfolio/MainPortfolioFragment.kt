package com.example.smartportfolio

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartportfolio.databinding.FragmentMainPortfolioBinding
import com.example.smartportfolio.databinding.MainPortfolioRecyclerviewBinding
import java.util.zip.GZIPOutputStream

// 포트폴리오 화면
class MainPortfolioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_portfolio, container, false)

        val binding = FragmentMainPortfolioBinding.inflate(inflater, container, false)

        val contents1 = mutableListOf<Int>(R.drawable.user_basic, R.drawable.resume_pic2)
        val contents3 = mutableListOf<String>("동화고등학교 졸업", "한림대학교 입학")
        val contents2 = mutableListOf<String>("2020.1.26", "2020.3.1")

        binding.mainPfRecyclerview.layoutManager = LinearLayoutManager(activity)
        binding.mainPfRecyclerview.adapter = MyAdapterPf(contents1, contents2, contents3)
        binding.mainPfRecyclerview.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )

        if(IntroActivity.mode === IntroActivity.READMODE) binding.mainPfFab.visibility = View.GONE
        binding.mainPfFab.setOnClickListener {
            Log.d("bada", "1")
        }
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