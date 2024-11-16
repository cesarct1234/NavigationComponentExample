package com.cesar.navigationcomponentexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var btnCamera: Button
    private lateinit var btnRecordVideo: Button
    private lateinit var btnNavi: Button
    private var photoUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCamera = view.findViewById(R.id.btnCamera)
        btnRecordVideo = view.findViewById(R.id.btnRecordVideo)
        btnNavi = view.findViewById(R.id.btnNavi)

        // Navegar al primer fragmento
        btnNavi.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_firstFragment)
        }

        // Botón para tomar foto
        btnCamera.setOnClickListener {
            checkPermissionsAndTakePhoto()
        }
        // Botón para grabar video
        btnRecordVideo.setOnClickListener {
            recordVideo()
        }
    }

    private fun checkPermissionsAndTakePhoto() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (permissions.all {
                ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
            }) {
            takePhoto()
        } else {
            requestPermissions(permissions, 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            takePhoto()
        } else {
            Toast.makeText(
                requireContext(),
                "Permiso de cámara denegado. No se puede tomar fotos.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile()
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                photoFile
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Error al crear el archivo para la foto.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun recordVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivity(intent)
    }
    private fun createImageFile(): File? {
        return try {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error al crear archivo: ${e.message}", Toast.LENGTH_SHORT).show()
            null
        }
    }
}


















