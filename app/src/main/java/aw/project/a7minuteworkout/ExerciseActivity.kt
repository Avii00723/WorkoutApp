@file:Suppress("UNUSED_EXPRESSION")

package aw.project.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import aw.project.a7minuteworkout.FinishActivity
import androidx.recyclerview.widget.LinearLayoutManager
import aw.project.a7minuteworkout.databinding.ActivityExerciseBinding
import aw.project.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? =null
    private var restProgress = 0
    private var restTimerDuration:Long=1
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var exerciseTimerDuration:Long=1
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1

    private var tts: TextToSpeech?=null
    private var player:MediaPlayer?=null

    private var exerciseAdapter:Exercisestatusadapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExeercise)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExeercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        exerciseList=Constants.defaultExerciseList()

        tts=TextToSpeech(this,this)

        binding?.toolbarExeercise?.setNavigationOnClickListener{
            onBackPressed()
        }
        setupRestView()
        setupExerciseStatusRecycleView()
    }
    override fun onBackPressed(){
        super.onBackPressed()
        customDialogForBackButton()
            this@ExerciseActivity
    }
    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()

    }

    private fun setupExerciseStatusRecycleView(){
        binding?.rvexercisestatus?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter=Exercisestatusadapter(exerciseList!!)
        binding?.rvexercisestatus?.adapter=exerciseAdapter



    }

    private fun setupRestView() {

        try{
            val soundURI=Uri.parse(
                "android.resource://aw.project.a7minutesworkout/"+R.raw.press_start)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false
            player?.start()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        binding?.flRestView?.visibility=View.VISIBLE
        binding?.tvtitle?.visibility=View.VISIBLE
        binding?.tvExerciseName?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.videoView?.visibility=View.INVISIBLE
        binding?.tvupcomingLabel?.visibility=View.VISIBLE
        binding?.tvupcomingExerciseName?.visibility=View.VISIBLE
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvupcomingExerciseName?.text=exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility=View.INVISIBLE
        binding?.tvtitle?.visibility=View.INVISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.videoView?.visibility=View.VISIBLE
        binding?.tvupcomingLabel?.visibility=View.INVISIBLE
        binding?.tvupcomingExerciseName?.visibility=View.INVISIBLE
        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.videoView?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text=exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){

        binding?.ProgressBar?.progress=restProgress
        restTimer=object:CountDownTimer(restTimerDuration*1000,1000){

            override fun onTick(p0: Long) {
                restProgress++
                binding?.ProgressBar?.progress=10-restProgress
                binding?.tvtimer?.text=(10-restProgress).toString()

            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
               setupExerciseView()
            }
        }.start()
    }
    private fun setExerciseProgressBar(){

        binding?.ProgressBarExercise?.progress=exerciseProgress
        exerciseTimer=object:CountDownTimer(exerciseTimerDuration*1000,1000){

            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.ProgressBarExercise?.progress=30-exerciseProgress
                binding?.tvtimerExercise?.text=(30-exerciseProgress).toString()

            }

            override fun onFinish() {

            if (currentExercisePosition<exerciseList?.size!!-1){
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupRestView()
            }
                else{
                    finish()
                val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                startActivity(intent)
            }
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer!=null){
            restTimer?.cancel()
            restProgress=0
        }
        if (exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        //Shutting down the text to speech feature when activity is destroyed
        //START
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player!=null){
            player!!.stop()
        }
        binding=null
    }

    override fun onInit(status: Int) {
       if (status==TextToSpeech.SUCCESS){
           //set US Emglish as language for tts
           val result=tts?.setLanguage(Locale.ENGLISH)

           if (result==TextToSpeech.LANG_MISSING_DATA||
               result==TextToSpeech.LANG_NOT_SUPPORTED){
               Log.e("TTS",
                   "The Language specified is not supported!")
           }
       }
        else{
            Log.e("TTS","Initialization Failed!")
       }
    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }
}