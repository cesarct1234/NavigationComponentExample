package com.cesar.navigationcomponentexample

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.IOException

class SecondFragment : Fragment() {

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFile: File
    private val RECORD_AUDIO_PERMISSION_CODE = 101
    private var isRecording = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val navController = findNavController()
        view.findViewById<Button>(R.id.btnNavi)?.setOnClickListener {
            navController.navigate(R.id.action_secondFragment_to_thirdFragment)
        }
        val recordButton = view.findViewById<Button>(R.id.recordButton)
        val playButton = view.findViewById<Button>(R.id.playButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)

        // Verificar permisos
        if (!hasPermissions()) {
            requestPermissions()
        }

        // Inicializar MediaRecorder y MediaPlayer
        mediaRecorder = MediaRecorder()
        mediaPlayer = MediaPlayer()

        // Inicializar archivo de audio
        audioFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audioRecording.3gp")

        // Configurar botones
        recordButton.setOnClickListener {
            if (hasPermissions()) {
                startRecording()
            } else {
                requestPermissions()
            }
        }

        stopButton.setOnClickListener {
            stopRecording()
        }

        playButton.setOnClickListener {
            playRecording()
        }

        return view
    }

    private fun hasPermissions(): Boolean {
        val recordAudioPermission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.RECORD_AUDIO
        )
        return recordAudioPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_AUDIO_PERMISSION_CODE
        )
    }

    private fun startRecording() {
        try {
            audioFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audioRecording.3gp")
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(audioFile.absolutePath)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                prepare()
                start()
            }
            isRecording = true
            Toast.makeText(requireContext(), "Grabación iniciada", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al iniciar la grabación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        if (!isRecording) {
            Toast.makeText(requireContext(), "No hay ninguna grabación en curso", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            mediaRecorder.apply {
                stop()
                reset()
            }
            isRecording = false
            Toast.makeText(requireContext(), "Grabación detenida. Archivo guardado en: ${audioFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: RuntimeException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al detener la grabación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playRecording() {
        if (!audioFile.exists()) {
            Toast.makeText(requireContext(), "Archivo de audio no encontrado", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            mediaPlayer.apply {
                reset()
                setDataSource(audioFile.absolutePath)
                prepare()
                start()
            }
            Toast.makeText(requireContext(), "Reproduciendo grabación", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Error al reproducir la grabación", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaRecorder.release()
        mediaPlayer.release()
    }
}


