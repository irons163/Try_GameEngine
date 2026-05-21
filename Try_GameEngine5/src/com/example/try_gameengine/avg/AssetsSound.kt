package com.example.try_gameengine.avg

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import kotlin.math.ln
import kotlin.math.log10

/**
 * Copyright 2008 - 2010
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loonframework
 * @author chenpeng
 * @email ceponline@yahoo.com.cn
 * @version 0.1.2
 // */
class AssetsSound {
    private var player: MediaPlayer? = null

    var name: String? = null
        private set

    private var playing = false

    private var loop = false

    private var context: Context? = null

    constructor(handler: SystemHandler) {
        try {
            context = handler.context
            player = MediaPlayer()
            player!!
                .setOnCompletionListener(object : OnCompletionListener {
                    override fun onCompletion(mp: MediaPlayer) {
                        playing = false
                        if (loop) {
                            mp.start()
                        }
                    }
                })
        } catch (e: Exception) {
        }
    }

    constructor(ctx: Context) {
        try {
            context = ctx
            player = MediaPlayer()
            player!!
                .setOnCompletionListener(object : OnCompletionListener {
                    override fun onCompletion(mp: MediaPlayer) {
                        playing = false
                        if (loop) {
                            mp.start()
                        }
                    }
                })
        } catch (e: Exception) {
        }
    }

    constructor(file: String) : this(LSystem.systemHandler!!) {
        setDataSource(file)
    }

    constructor(ctx: Context, uri: Uri) {
        try {
            name = uri.toString()
            context = ctx
            player = MediaPlayer.create(ctx, uri)
            player!!
                .setOnCompletionListener(object : OnCompletionListener {
                    override fun onCompletion(mp: MediaPlayer) {
                        playing = false
                        if (loop) {
                            mp.start()
                        } else {
                            mp.stop()
                        }
                    }
                })
        } catch (e: Exception) {
        }
    }

    fun play() {
        try {
            if (playing) {
                player!!.seekTo(0)
                return
            }
            if (player != null) {
                playing = true
                player!!.start()
            }
        } catch (e: Exception) {
        }
    }

    fun setDataSource(file: String) {
        try {
            player!!.setDataSource(
                context!!.getAssets().openFd(file)
                    .getFileDescriptor(), context!!.getAssets().openFd(file)
                    .getStartOffset(), context!!.getAssets().openFd(file)
                    .getLength()
            )
            player!!.prepare()
        } catch (e: Exception) {
        }
    }

    fun play(vol: Int) {
        try {
            if (playing) {
                player!!.seekTo(0)
            }
            if (player != null) {
                playing = true
                player!!
                    .setVolume(log10(vol.toDouble()).toFloat(), ln(vol.toDouble()).toFloat())
                player!!.start()
            }
        } catch (e: Exception) {
        }
    }

    fun loop() {
        try {
            if (playing) {
                player!!.seekTo(0)
            }
            if (player != null) {
                loop = true
                playing = true
                player!!.start()
            }
        } catch (e: Exception) {
        }
    }

    fun stop() {
        try {
            loop = false
            if (playing) {
                playing = false
                player!!.pause()
            }
        } catch (e: Exception) {
        }
    }

    fun stopPlayer() {
        try {
            loop = false
            playing = false
            player!!.pause()
            player!!.stop()
        } catch (e: Exception) {
        }
    }

    fun reset() {
        try {
            if (player != null) {
                player!!.reset()
            }
        } catch (e: Exception) {
        }
    }

    fun release() {
        try {
            if (player != null) {
                player!!.release()
                player = null
            }
        } catch (e: Exception) {
        }
    }

    fun setVolume(vol: Int) {
        try {
            if (player != null) {
                player!!.setVolume(log10(vol.toDouble()).toFloat(), log10(vol.toDouble()).toFloat())
            }
        } catch (e: Exception) {
        }
    }
}
