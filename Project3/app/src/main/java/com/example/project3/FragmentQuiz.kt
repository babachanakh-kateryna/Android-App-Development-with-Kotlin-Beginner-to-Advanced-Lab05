package com.example.project3

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.project3.database.FlagsDao
import com.example.project3.database.FlagsModel
import com.example.project3.databinding.FragmentQuizBinding
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper


class FragmentQuiz : Fragment() {

    lateinit var fragmentQuizBinding: FragmentQuizBinding
    var flagsarray=ArrayList<FlagsModel>()
    var correctNumber=0
    var wrongNUmber=0
    var emptyNumber=0
    var questionNumber=0
    lateinit var correctFlag:FlagsModel
    var wrongFlags=ArrayList<FlagsModel>()
    val dao= FlagsDao()
    var optionControl=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        fragmentQuizBinding=FragmentQuizBinding.inflate(inflater,container,false)


        flagsarray=dao.getRandomfiveRecords(DatabaseCopyHelper(requireActivity()))

        showData()

        fragmentQuizBinding.buttonA.setOnClickListener{
            answerControl(fragmentQuizBinding.buttonA)

        }
        fragmentQuizBinding.buttonB.setOnClickListener{
            answerControl(fragmentQuizBinding.buttonB)

        }
        fragmentQuizBinding.buttonC.setOnClickListener{
            answerControl(fragmentQuizBinding.buttonC)

        }
        fragmentQuizBinding.buttonD.setOnClickListener{
            answerControl(fragmentQuizBinding.buttonD)

        }
        fragmentQuizBinding.buttonnext.setOnClickListener{

            questionNumber++

            if(questionNumber>4){

                if(!optionControl){
                    emptyNumber++

                }
                val direction=FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply{
                    correct=correctNumber
                    empty=emptyNumber
                    wrong=wrongNUmber
                }
                //Toast.makeText(requireActivity(),"The Quiz is Finished",Toast.LENGTH_SHORT).show()

                this.findNavController().apply {
                    navigate(direction)
                    popBackStack(R.id.fragmentResult,false)
                }

            }else{
                showData()

                if(!optionControl){
                    emptyNumber++
                    fragmentQuizBinding.textViewEmpty.text=emptyNumber.toString()
                }else{

                    setButtonToInitialProperties()
                }
            }
            optionControl=false

        }

        return fragmentQuizBinding.root
    }

    private fun showData(){

        fragmentQuizBinding.textViewQuestion.text=resources.getString(R.string.question_number).plus(" ").plus(questionNumber+1)
        correctFlag=flagsarray[questionNumber]
        fragmentQuizBinding.imageviewflag.setImageResource(resources.getIdentifier(correctFlag.flagname,"drawable",requireActivity().packageName))
        wrongFlags=dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()),correctFlag.id)

        val mixOptions=HashSet<FlagsModel>()
        mixOptions.clear()

        mixOptions.add(correctFlag)
        mixOptions.add(wrongFlags[0])
        mixOptions.add(wrongFlags[1])
        mixOptions.add(wrongFlags[2])


        val options=ArrayList<FlagsModel>()

        options.clear()


        for(eachSet in mixOptions){
            options.add(eachSet)
        }
        fragmentQuizBinding.buttonA.text=options[0].countryname
        fragmentQuizBinding.buttonB.text=options[1].countryname
        fragmentQuizBinding.buttonC.text=options[2].countryname
        fragmentQuizBinding.buttonD.text=options[3].countryname
    }


    private fun answerControl(button: Button){
        var clickedOption=button.text.toString()
        var correctOption=correctFlag.countryname


        if(clickedOption==correctOption){
            correctNumber++
            fragmentQuizBinding.textViewCorrect.text=correctNumber.toString()
            button.setBackgroundColor(Color.GREEN)

        }else{

            wrongNUmber++
            fragmentQuizBinding.textViewWrong.text=wrongNUmber.toString()
            button.setBackgroundColor(Color.RED)
            when(correctOption){
                fragmentQuizBinding.buttonA.text -> fragmentQuizBinding.buttonA.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonB.text -> fragmentQuizBinding.buttonB.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonC.text -> fragmentQuizBinding.buttonC.setBackgroundColor(Color.GREEN)
                fragmentQuizBinding.buttonD.text -> fragmentQuizBinding.buttonD.setBackgroundColor(Color.GREEN)
            }

        }

        fragmentQuizBinding.buttonA.isClickable=false
        fragmentQuizBinding.buttonB.isClickable=false
        fragmentQuizBinding.buttonC.isClickable=false
        fragmentQuizBinding.buttonD.isClickable=false


        optionControl=true
    }

    private fun setButtonToInitialProperties(){
        fragmentQuizBinding.buttonA.apply{
            setBackgroundColor(resources.getColor(R.color.option,requireActivity().theme))
            isClickable=true

        }
        fragmentQuizBinding.buttonB.apply{
            setBackgroundColor(resources.getColor(R.color.option,requireActivity().theme))
            isClickable=true

        }
        fragmentQuizBinding.buttonC.apply{
            setBackgroundColor(resources.getColor(R.color.option,requireActivity().theme))
            isClickable=true

        }
        fragmentQuizBinding.buttonD.apply{
            setBackgroundColor(resources.getColor(R.color.option,requireActivity().theme))
            isClickable=true

        }
    }

}