package com.example.addproductapplication


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.addproductapplication.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var selectedImages = mutableListOf<Uri>()
    private  val selectedColors = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Selecting color process
        binding.buttonColorPicker.setOnClickListener{
            ColorPickerDialog.Builder(this)
                .setTitle("Product Color")
                .setPositiveButton("Select", object : ColorEnvelopeListener {
                    override fun onColorSelected(envelope: ColorEnvelope, fromUser: Boolean) {
                        envelope?.let {
                            selectedColors.add(envelope.color)
                            updateColors()
                        }
                    }
                })
                .setNavigateButton("Cancel"){ colorPicker, }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveProduct) {
            val productValidation = validateInformation()
            if (!productValidation) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                saveProduct()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveProduct() {
     val name = binding.edName.text.toString().trim()
        val price = binding.edPrice.text.toString().trim()
        val category = binding.edCategory.text.toString().trim()
        val offerPercentage = binding.offerPercentage.text.toString().trim()
        val description = binding.edDescription.text.toString().trim()
        val sizes = getSizeList(binding.edSizes.text.toString().trim())
    }

    // Function to set the list of sizes, S,M,L,XL
    private fun getSizeList(sizeString: String): List<String>? {
        if (sizeString.isEmpty())
            return null

        val sizeList = sizeString.split(",")
        return sizeList
    }

    private fun validateInformation(): Boolean {
        if (binding.edPrice.text.toString().trim().isEmpty())
            return false

        if (binding.edName.text.toString().trim().isEmpty())
            return false

        if (binding.edCategory.text.toString().trim().isEmpty())
            return false

        if (selectedImages.isEmpty())
            return false

        return true
        }
}