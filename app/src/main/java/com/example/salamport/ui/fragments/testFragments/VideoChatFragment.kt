package com.example.salamport.ui.fragments.testFragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.salamport.R
import com.example.salamport.components.GLCircleDrawer
import com.example.salamport.ui.fragments.BaseFragment
import com.example.salamport.videocall.VideoCallSession
import com.example.salamport.videocall.VideoCallStatus
import com.example.salamport.videocall.VideoRenderers
import org.webrtc.EglBase
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer


class VideoChatFragment : BaseFragment(R.layout.fragment_video_chat) {

    companion object{
        private const val CAMERA_AUDIO_PERMISSION_REQUEST = 1
    }

    private var videoSession: VideoCallSession? = null
    private lateinit var statusTextView: TextView
    private lateinit var localVideoView: SurfaceViewRenderer
    private lateinit var remoteVideoView: SurfaceViewRenderer
    private var audioManager: AudioManager? = null
    private var savedMicrophoneState: Boolean? = null
    private var savedAudioMode: Int? = null



    var isOffer = false
    lateinit var id: String

    override fun onResume() {
        super.onResume()

        val fragment = VideoChatFragment()
        fragment.isOffer = false
        fragment.id = "j1Va7VzHMOeFKOmOMkZSNh5wmQB2"

        retainInstance = true

        audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        savedAudioMode = audioManager?.mode
        audioManager?.mode = AudioManager.MODE_IN_COMMUNICATION

        savedMicrophoneState = audioManager?.isMicrophoneMute
        audioManager?.isMicrophoneMute = false
        audioManager?.isSpeakerphoneOn = true


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_video_chat, container, false)

        statusTextView = root.findViewById(R.id.status_text)
        localVideoView = root.findViewById(R.id.pip_video)
        remoteVideoView = root.findViewById(R.id.remote_video)

        val hangup: ImageButton = root.findViewById(R.id.hangup_button)
        hangup.setOnClickListener { activity!!.finish() }

        val toggle: ImageButton = root.findViewById(R.id.btn_toggle_camera)
        toggle.setOnClickListener { videoSession?.toggleCamera() }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null)
            handlePermissions()
        else videoSession?.let {
            initVideoVews()
            it.videoRenderers.updateViewRenders(localVideoView, remoteVideoView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        localVideoView.release()
        remoteVideoView.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoSession?.terminate()

        if (savedAudioMode !== null) {
            audioManager?.mode = savedAudioMode!!
        }
        if (savedMicrophoneState != null) {
            audioManager?.isMicrophoneMute = savedMicrophoneState!!
        }
    }

    private fun onStatusChanged(newStatus: VideoCallStatus) {

        if (!isAdded) {

            return
        }
        activity?.runOnUiThread {
            when (newStatus) {
                VideoCallStatus.FINISHED -> activity!!.finish()
                else -> {
                    statusTextView.text = resources.getString(newStatus.label)
                    statusTextView.setTextColor(ContextCompat.getColor(context!!, newStatus.color))
                }
            }
        }
    }

    private fun handlePermissions() {
        val canAccessCamera = ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val canRecordAudio = ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (!canAccessCamera || !canRecordAudio) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                VideoChatFragment.CAMERA_AUDIO_PERMISSION_REQUEST
            )
        } else {
            startVideoSession()
        }
    }

    private fun startVideoSession() {
        videoSession = VideoCallSession.connect(context!!, id, isOffer, VideoRenderers(localVideoView, remoteVideoView), this::onStatusChanged)
        initVideoVews()
    }

    private fun initVideoVews() {
        localVideoView.apply {
            init(videoSession?.renderContext, null, EglBase.CONFIG_RGBA, GLCircleDrawer())
            setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT)
//                setZOrderMediaOverlay(true)
            //To make transparent
            setZOrderOnTop(true)
            holder.setFormat(PixelFormat.TRANSLUCENT)
            setEnableHardwareScaler(true)
            setMirror(true)
        }

        remoteVideoView.apply {
            init(videoSession?.renderContext, null)
            setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
            setEnableHardwareScaler(true)
        }
//        remoteVideoView?.setMirror(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            VideoChatFragment.CAMERA_AUDIO_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    startVideoSession()
                } else {
                    activity!!.finish()
                }
                return
            }
        }
    }
}