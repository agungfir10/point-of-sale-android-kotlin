package com.agungfir.pointofsale.ui.checkout

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface.OnShowListener
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.agungfir.pointofsale.R
import com.agungfir.pointofsale.databinding.FragmentCheckoutBinding
import com.agungfir.pointofsale.ui.adapter.ProductAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class CheckoutFragment : BottomSheetDialogFragment(), ZXingScannerView.ResultHandler {

    private lateinit var scannerView: ZXingScannerView

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()

        scannerView = ZXingScannerView(requireContext())
        scannerView.setResultHandler(this@CheckoutFragment)
        scannerView.startCamera()
        binding.scanner.setResultHandler { rawResult ->

            val text: String = rawResult?.text.toString()
            val format: String = rawResult?.barcodeFormat.toString()

            Toast.makeText(
                requireContext(),
                "Text : $text\nFormat : $format",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvProduct.adapter = ProductAdapter()
        binding.scanner.startCamera()

    }

    override fun handleResult(rawResult: Result?) {
        val text: String
        val format: String

        text = rawResult?.text.toString()
        format = rawResult?.barcodeFormat.toString()

        Toast.makeText(requireContext(), "Text : $text\nFormat : $format", Toast.LENGTH_SHORT)
            .show()
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                requireContext(),
                "Aplikasi Tidak Di Izinkan Menggunakan Kamera",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(OnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        })
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet!!.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }


}