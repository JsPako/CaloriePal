package com.jspako.caloriepal.ui.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jspako.caloriepal.databinding.DialogSingleIntBinding
import com.jspako.caloriepal.databinding.DialogNameBinding
import com.jspako.caloriepal.databinding.DialogActivityBinding
import com.jspako.caloriepal.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var profilePreferences: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profilePreferences = requireContext().getSharedPreferences("profilePrefs", Context.MODE_PRIVATE)

        val profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.init(profilePreferences)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val nameBtn: Button = binding.nameBtn
        nameBtn.setOnClickListener {
            dialogName()
        }
        val nameBtnValue: Button = binding.nameValue
        nameBtnValue.setOnClickListener {
            dialogName()
        }

        val ageBtn: Button = binding.ageBtn
        ageBtn.setOnClickListener{
            dialogAge()
        }
        val ageBtnValue: Button = binding.ageValue
        ageBtnValue.setOnClickListener{
            dialogAge()
        }

        val activityBtn: Button = binding.activityBtn
        activityBtn.setOnClickListener {
            dialogActivity()
        }
        val activityBtnValue: Button = binding.activityValue
        activityBtnValue.setOnClickListener {
            dialogActivity()
        }

        val caloriesBtn: Button = binding.calorieBtn
        caloriesBtn.setOnClickListener {
            dialogInt("Daily Calories Intake", "kCal", "calories")
        }
        val caloriesBtnValue: Button = binding.calorieValue
        caloriesBtnValue.setOnClickListener {
            dialogInt("Daily Calories Intake", "kCal", "calories")
        }

        val proteinBtn: Button = binding.proteinBtn
        proteinBtn.setOnClickListener {
            dialogInt("Daily Protein Intake", "grams", "protein")
        }
        val proteinBtnValue: Button = binding.proteinValue
        proteinBtnValue.setOnClickListener {
            dialogInt("Daily Protein Intake", "grams", "protein")
        }

        val fatsBtn: Button = binding.fatsBtn
        fatsBtn.setOnClickListener {
            dialogInt("Daily Fats Intake", "grams", "fats")
        }
        val fatsBtnValue: Button = binding.fatsValue
        fatsBtnValue.setOnClickListener {
            dialogInt("Daily Fats Intake", "grams", "fats")
        }

        binding.nameValue.text = profilePreferences.getString("name", "")
        if (profilePreferences.getInt("age", 0) == 0){
            binding.ageValue.text
        }
        else {
            binding.ageValue.text = (profilePreferences.getInt("age", 0).toString() + " years old")
        }

        binding.activityValue.text = profilePreferences.getString("activity", "")
        if (profilePreferences.getInt("calories", 0) == 0){
            binding.ageValue.text
        }
        else {
            binding.calorieValue.text = (profilePreferences.getInt("calories", 0).toString() + " kCal")
        }
        if (profilePreferences.getInt("protein", 0) == 0){
            binding.ageValue.text
        }
        else {
            binding.proteinValue.text = (profilePreferences.getInt("protein", 0).toString() + " grams")
        }
        if (profilePreferences.getInt("fats", 0) == 0){
            binding.ageValue.text
        }
        else {
            binding.fatsValue.text = (profilePreferences.getInt("fats", 0).toString() + " grams")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun dialogName(){
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogNameBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.titleView.text = "Name"
        dialogBinding.editTextName.setText(binding.nameValue.text)

        dialogBinding.submitBtn.setOnClickListener {
            val name = dialogBinding.editTextName.text.toString()
            if (name.isNotEmpty()) {
                val name = dialogBinding.editTextName.text.toString()
                binding.textProfile.text = name
                binding.nameValue.text = name

                with(profilePreferences.edit()) {
                    putString("name", name)
                    apply()
                }
                dialog.dismiss()
            }
            else{
                Toast.makeText(requireContext(), "The name cannot be empty. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun dialogAge(){
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogSingleIntBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.titleView.text = "Age"
        dialogBinding.suffixView.text = "years old"
        dialogBinding.editTextNumber.setText(binding.ageValue.text.filter { it.isDigit() })

        dialogBinding.submitBtn.setOnClickListener{
            val age = dialogBinding.editTextNumber.text.toString()
            if (age.isNotEmpty()) {
                val age = age.toInt()
                if(age in 1..99) {
                    binding.ageValue.text = "$age years old"

                    with(profilePreferences.edit()) {
                        putInt("age", age)
                        apply()
                    }
                    dialog.dismiss()
                }
                else{
                    Toast.makeText(requireContext(), "The age is unrealistic. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(), "The age cannot be empty. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun dialogActivity(){
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogActivityBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.titleView.text = "Activity Level"

        var index = 0
        val value = binding.activityValue.text
        if (value.equals("Low Activity")){
            index = 0
        }
        if (value.equals("Moderate Activity")){
            index = 1
        }
        if(value.equals("High Activity")){
            index = 2
        }
        dialogBinding.spinner.setSelection(index)

        dialogBinding.submitBtn.setOnClickListener{
            val selection = dialogBinding.spinner.selectedItem.toString()
            if(selection.isNotEmpty()){
                binding.activityValue.text = selection

                with(profilePreferences.edit()) {
                    putString("activity", selection)
                    apply()
                }
                dialog.dismiss()
            }
            else{
                Toast.makeText(requireContext(), "The selection cannot be empty. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun dialogInt(title: String, suffix: String, type: String){
        val dialog = Dialog(requireContext())

        val dialogBinding = DialogSingleIntBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.titleView.text = title
        dialogBinding.suffixView.text = suffix
        var text = ""
        if(type == "calories"){
            text = binding.calorieValue.text.filter { it.isDigit() }.toString()
        }
        if(type == "protein"){
            text = binding.proteinValue.text.filter { it.isDigit() }.toString()
        }
        if(type == "fats"){
            text = binding.fatsValue.text.filter { it.isDigit() }.toString()
        }
        dialogBinding.editTextNumber.setText(text)

        dialogBinding.submitBtn.setOnClickListener {
            val value = dialogBinding.editTextNumber.text.toString()
            if (value.isNotEmpty()) {
                val value = value.toInt()
                if(type == "calories"){
                    binding.calorieValue.text = "$value kCal"
                }
                if(type == "protein"){
                    binding.proteinValue.text = "$value grams"
                }
                if(type == "fats"){
                    binding.fatsValue.text = "$value grams"
                }

                with(profilePreferences.edit()) {
                    putInt(type, value)
                    apply()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "The value cannot be empty. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}