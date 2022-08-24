package com.ramiyon.soulmath.presentation.ui.material.video

import android.content.Intent
import android.graphics.Color
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ramiyon.soulmath.base.BaseActivity
import com.ramiyon.soulmath.databinding.ActivityMaterialVideoPlayerBinding
import com.ramiyon.soulmath.domain.model.material.MaterialDetail
import com.ramiyon.soulmath.presentation.ui.material.reward.MaterialRewardActivity
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import com.ramiyon.soulmath.util.Constanta.ARG_XP
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaterialVideoPlayerActivity : BaseActivity<ActivityMaterialVideoPlayerBinding>() {

    private val viewModel by viewModel<MaterialVideoPlayerViewModel>()
    private var exoPlayer: ExoPlayer? = null
    private var playerView: StyledPlayerView? = null
    private lateinit var material: MaterialDetail
    private lateinit var mediaDataSourceFactory: DataSource.Factory

    override fun inflateViewBinding(): ActivityMaterialVideoPlayerBinding {
        return ActivityMaterialVideoPlayerBinding.inflate(layoutInflater)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.LANDSCAPE
    }

    override fun ActivityMaterialVideoPlayerBinding.binder() {
        val materialId = intent.getStringExtra(ARG_MATERIAL_ID)
        viewModel.fetchMaterialDetail(materialId!!).observe(this@MaterialVideoPlayerActivity) {
            when(it) {
                is Resource.Loading -> videoPlayerResourceCallback.onResourceLoading()
                is Resource.Success -> videoPlayerResourceCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> videoPlayerResourceCallback.onResourceError(it.message, null)
                is Resource.Empty -> videoPlayerResourceCallback.onResourceEmpty()
            }
        }
        playerView?.setOnClickListener {
            if (exoPlayer?.playWhenReady == true)
                onPause()
            else
                onStart()
        }
    }

    private val videoPlayerResourceCallback = object : ResourceStateCallback<MaterialDetail>() {
        override fun onResourceLoading() {

        }

        override fun onResourceSuccess(data: MaterialDetail) {
            material = data
            initializePlayer(data.videoUrl)

            if (exoPlayer?.playbackState == Player.STATE_ENDED) {
                val intent = Intent(this@MaterialVideoPlayerActivity, MaterialRewardActivity::class.java)
                intent.putExtra(ARG_XP, data.xpEarned)
                startActivity(intent)
            }
        }

        override fun onResourceError(message: String?, data: MaterialDetail?) {

        }
    }

    private fun initializePlayer(streamUrl: String?) {
        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            MediaItem.fromUri(streamUrl.toString()))

        val mediaSourceFactory: MediaSource.Factory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        exoPlayer!!.addMediaSource(mediaSource)

        exoPlayer!!.playWhenReady = true

        binding.materialVideoPlayer?.setShutterBackgroundColor(Color.TRANSPARENT)
        binding.materialVideoPlayer?.player = exoPlayer
        binding.materialVideoPlayer?.requestFocus()
    }

    private fun releasePlayer() {
        exoPlayer?.release()
    }

    override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer(material.videoUrl)
    }

    override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer(material.videoUrl)
    }

    override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

}