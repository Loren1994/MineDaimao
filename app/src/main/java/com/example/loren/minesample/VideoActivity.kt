package com.example.loren.minesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

/**
 *                Copyright (c) 16-9-26 by loren
 */
class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity)
    }

    override fun onPause() {
        super.onPause()
        if (App.videoPlayer != null) {
            if (App.videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                App.videoPlayer.startButton.performClick()
            } else if (App.videoPlayer.currentState === JCVideoPlayer.CURRENT_STATE_PREPARING) {
                JCVideoPlayer.releaseAllVideos()
            }
        }
    }

    override fun onBackPressed() {
        App.videoPlayer = JCVideoPlayerStandard(this)
        super.onBackPressed()
    }

}