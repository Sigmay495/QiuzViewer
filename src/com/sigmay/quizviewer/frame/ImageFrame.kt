/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: ImageFrame.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame

import com.sigmay.quizviewer.common.DEFAULT_IMG_FRAME_SIZE
import com.sigmay.quizviewer.frame.component.ImageCanvas
import java.awt.Image
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.swing.JFrame
import javax.swing.WindowConstants

/**
 * 画像を表示するフレーム。
 *
 * @constructor
 * 画像を中央に表示するフレームを生成。
 *
 * @param title フレームのタイトル
 * @param img 表示する画像（省略時は何も表示されない）
 */
class ImageFrame(title: String, img: Image = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)) : JFrame(title) {

    /**
     * 画像表示部のキャンバス
     */
    private val imgCanvas = ImageCanvas(img)

    init {
        size = DEFAULT_IMG_FRAME_SIZE

        add(imgCanvas)

        //setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        isVisible = true
    }

    /**
     * 画像を中央に表示するフレームを生成。
     *
     * @param title フレームのタイトル
     * @param imgFilePath 表示する画像のファイルパス
     */
    constructor(title: String, imgFilePath: String) : this(title, Toolkit.getDefaultToolkit().getImage(imgFilePath))

    /**
     * 表示する画像を変更する。
     *
     * @param img 新しい画像
     */
    fun changeImage(img: Image) {
        imgCanvas.changeImage(img)
    }

    /**
     * 表示する画像を変更する。
     *
     * @param imgFilePath 新しい画像のファイルパス
     */
    fun changeImage(imgFilePath: String) {
        changeImage(Toolkit.getDefaultToolkit().getImage(imgFilePath))
    }

}
