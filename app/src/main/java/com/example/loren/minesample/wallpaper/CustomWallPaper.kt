package com.example.loren.minesample.wallpaper

import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

/**
 * Copyright © 2018/4/18 by loren
 */
class CustomWallPaper : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return CustomEngine()
    }

    inner class CustomEngine : Engine() {

        private lateinit var mediaPlayer: MediaPlayer

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            mediaPlayer = MediaPlayer()
            mediaPlayer.setSurface(holder.surface)
            val assetManager = applicationContext.assets
            val fileDes = assetManager.openFd("video.mp4")
            mediaPlayer.setDataSource(fileDes.fileDescriptor, fileDes.startOffset, fileDes.length)
            mediaPlayer.isLooping = true
            mediaPlayer.setVolume(0f, 0f)
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                mediaPlayer.start()
            } else {
                mediaPlayer.pause()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            mediaPlayer.release()
        }
    }
}