package com.example.project3

import android.graphics.Color
import com.example.project3.R
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project3.databinding.FragmentResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class FragmentResult : Fragment() {
    lateinit var fragmentResultBinding: FragmentResultBinding
    var correctNumber=0F
    var emptyNumber=0F
    var wrongNumber=0F


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentResultBinding=FragmentResultBinding.inflate(inflater,container,true)

        val args=arguments?.let {
            FragmentResultArgs.fromBundle(it)
        }

        args?.let {
            correctNumber = it.correct.toFloat()
            emptyNumber=it.empty.toFloat()
            wrongNumber=it.wrong.toFloat()
        }


        var barEntriesArrayListCorrect=ArrayList<BarEntry>()
        var barEntriesArrayListEmpty=ArrayList<BarEntry>()
        var barEntriesArrayListWrong=ArrayList<BarEntry>()


        barEntriesArrayListCorrect.add(BarEntry(0F,correctNumber))
        barEntriesArrayListEmpty.add(BarEntry(0F,emptyNumber))
        barEntriesArrayListWrong.add(BarEntry(0F,wrongNumber))


        var barDataSetCorrect= BarDataSet(barEntriesArrayListCorrect,"Correct Number").apply {
            color= Color.GREEN
            valueTextSize=24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }
        var barDataSetEmpty=BarDataSet(barEntriesArrayListCorrect,"Empty Number").apply {
            color = Color.BLUE
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }
        var barDataSetWrong=BarDataSet(barEntriesArrayListCorrect,"Wrong Number").apply {
            color = Color.RED
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }


        val barData= BarData(barDataSetCorrect,barDataSetEmpty,barDataSetWrong)

        fragmentResultBinding.resultchart.data=barData

        fragmentResultBinding.newquiz.setOnClickListener{

            this.findNavController().popBackStack(R.id.fragmentHome,false)

        }
        fragmentResultBinding.exit.setOnClickListener{

            requireActivity().finish()

        }
        return fragmentResultBinding.root
    }
}