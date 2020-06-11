/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: ImageCanvas.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame.component

import com.sigmay.quizviewer.common.ImageReadingFailureException
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.MediaTracker
import javax.swing.JPanel

/**
 * 画像を表示部となるキャンバス。
 *
 * @property img 表示する画像
 */
internal class ImageCanvas(private var img: Image) : JPanel() {

    init {
        changeImage(img)
    }

    /**
     * 画像をパネルに貼り付ける。
     *
     * @param g グラフィックス
     */
    override fun paintComponent(g: Graphics?) {
        if (g == null)
            throw ImageReadingFailureException("画像の読み込みが失敗しました。")

        // キャンバスを黒で初期化
        g.color = Color.BLACK
        g.fillRect(0, 0, width, height)

        // サイズを計算
        val w: Int
        val h: Int
        if (img.getWidth(this) /width.toDouble() > img.getHeight(this)/height.toDouble()) {
            w = width
            h = (img.getHeight(this) * width / img.getWidth(this).toDouble()).toInt()
        } else {
            h = height
            w = (img.getWidth(this) * height / img.getHeight(this).toDouble()).toInt()
        }

        // サイズをもとに画像左上の座標を計算
        val x = (width - w) / 2
        val y = (height - h) / 2
        g.drawImage(img, x, y, w, h, this)
    }

    /**
     * キャンバスの画像を変更する。
     *
     * @param img 新しい画像
     */
    fun changeImage(img: Image) {
        this.img = img
        val mt = MediaTracker(this)
        mt.addImage(img, 0)
        mt.waitForAll()
        if (img.getWidth(this) <= 0)
            throw ImageReadingFailureException("画像の読み込みが失敗しました。")
        repaint()
    }
}
