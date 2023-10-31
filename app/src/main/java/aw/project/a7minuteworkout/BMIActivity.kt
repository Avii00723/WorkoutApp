package aw.project.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import aw.project.a7minuteworkout.databinding.ActivityBmiBinding

import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRICS_UNIT_VIEW"
        private const val US_UNITS_VIEW="US_UNIT_VIEW"
    }
    private var currentVisibleView:String= METRIC_UNITS_VIEW
    private var binding:ActivityBmiBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if (supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        // Radio Group change listener is set to the radio group which is added in XML.
        binding?.rgUnits?.setOnCheckedChangeListener{ _,checkedId:Int ->
            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else{
                makeVisibleUsUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
        binding?.tilUsUnitWeight?.visibility=View.GONE
        binding?.tilUsUnitHeightFeet?.visibility=View.GONE
        binding?.tilUsUnitHeightInch?.visibility=View.GONE
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.llUsUnitsView?.visibility=View.VISIBLE
        binding?.tilUsUnitWeight?.visibility=View.VISIBLE
        binding?.tilUsUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilUsUnitHeightInch?.visibility=View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.etUsUnitWeight?.text!!.clear()
        binding?.etUsUnitHeightFeet?.text!!.clear()
        binding?.etUsUnitHeightInch?.text!!.clear()
        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun displayBMIResult(bmi:Float){
     val bmilable:String
     val bmiDescription:String
     if (bmi.compareTo(15f)<=0){
         bmilable="Very severly underweight"
         bmiDescription="Oops! You really need to take better care of yourself! eat more"
     }
        else if(bmi.compareTo(15f)>0&&bmi.compareTo(16f)<=0) {
            bmilable="Severly underweight"
         bmiDescription="Oops! You really need to take better care of yourself! eat more"
     }
        else if (bmi.compareTo(16f)>0&&bmi.compareTo(18.5f)<=0){
            bmilable="Underweight"
         bmiDescription="Oops! You really need to take better care of yourself! Eat more"
     }
        else if (bmi.compareTo(18.5f)>0&&bmi.compareTo(25f)<=0){
            bmilable="Normal"
         bmiDescription="Congratulations! You are in a good shape!"
     }
        else if(bmi.compareTo(25f)>0&&bmi.compareTo(30f)<=0){
            bmilable="Overweight"
         bmiDescription="Oops! You reall need to take care of yourself! Workout"
     }
        else if(bmi.compareTo(30f)>0&&bmi.compareTo(35f)<=0){
            bmilable="Overweight Class |(Moderately Overweight)"
         bmiDescription="Oops! you really need to take care of yourself! Workout"
     }
        else if (bmi.compareTo(35f)>0&&bmi.compareTo(40f)<=0){
            bmilable="Overweight class||(Severly Overweight)"
         bmiDescription="You are in very dangerous condition! Act now!"
     }
        else{
            bmilable="Overweight Class|||(Very Severly overweight"
         bmiDescription="You are in very dangerous condition! Act now!"
     }
        val BMIValue= BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.tvBMIValue?.text=BMIValue
        binding?.tvBMIType?.text=bmilable
        binding?.tvBMIDescription?.text=bmiDescription
        binding?.llDisplayBMIResult?.visibility=View.VISIBLE
    }
    private fun validateMetricUnits():Boolean{
        var isValid=true
        if (binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false

        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }
    private fun calculateUnits(){
        if (currentVisibleView==METRIC_UNITS_VIEW){
           if (validateMetricUnits()){
            val heightValue: Float =
                binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
            val weightValue: Float =
                binding?.etMetricUnitWeight?.text.toString().toFloat()
            val bmi = weightValue / (heightValue * heightValue)

            displayBMIResult(bmi)

        }else{
            Toast.makeText(this@BMIActivity,"Please enter valid value",
                Toast.LENGTH_SHORT)
                .show()
        }
        }
        else if (currentVisibleView== US_UNITS_VIEW){
            if (validateUsUnits()){
                val heightValueinFeet: String =
                    binding?.etUsUnitHeightFeet?.text.toString()
                val weightValueinlbs: Float =
                    binding?.etUsUnitWeight?.text.toString().toFloat()
                val heightValueinInches:String=
                    binding?.etUsUnitHeightInch?.text.toString()
                val heightValue=heightValueinInches.toFloat()+heightValueinFeet.toFloat()*12
                val bmi=703*(weightValueinlbs/(heightValue*heightValue))
                displayBMIResult(bmi)

            }else{
                Toast.makeText(this@BMIActivity,"Please enter valid value",
                    Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun validateUsUnits():Boolean {
        var isValid = true
        when {
            binding?.etUsUnitWeight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitHeightFeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etUsUnitHeightInch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }
}