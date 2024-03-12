package com.example.productapp.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.productapp.Model.ProductRequest
import com.example.productapp.R
import com.example.productapp.ViewModelPack.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddProductBottomSheet : BottomSheetDialogFragment() {

    private val productViewModel: ProductViewModel by viewModel()
    private val REQUEST_IMAGE_CAPTURE = 1

    private lateinit var productNameEditText :EditText
    private lateinit var priceEditText :EditText
    private lateinit var taxEditText :EditText
    private lateinit var submitButton :Button
    private lateinit var productTypeSpinner :Spinner
    private lateinit var uploadButton :Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_product_bottom_sheet, container, false)


        productNameEditText = view.findViewById(R.id.productNameEditText)
       priceEditText = view.findViewById(R.id.priceEditText)
        taxEditText = view.findViewById(R.id.taxEditText)
       submitButton = view.findViewById(R.id.submitButton)
       productTypeSpinner = view.findViewById(R.id.productTypeSpinner)
        uploadButton = view.findViewById(R.id.uploadButton)

    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {

        uploadButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
        val productTypes = listOf("Product 1", "Product 2", "Product 3")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, productTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productTypeSpinner.adapter = adapter


        submitButton.setOnClickListener {
            val productName = productNameEditText.text.toString().trim()
            val productType = productTypeSpinner.selectedItem.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()
            val tax = taxEditText.text.toString().toDoubleOrNull()

            if (productName.isNotEmpty() && price != null && tax != null) {
                val product = ProductRequest(productName, productType, price, tax, listOf()) // Assuming no files added for simplicity
                addProduct(product)
            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val width = imageBitmap.width
            val height = imageBitmap.height
            Toast.makeText(requireContext(), "Image captured: $width x $height", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(requireContext(), "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addProduct(product: ProductRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                productViewModel.addProduct(product)
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Product added successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


