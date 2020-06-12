/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: Config.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495s
 *  ==============================
 */

package com.sigmay.quizviewer.common

import java.awt.Dimension
import java.awt.Insets

/**
 * 空文字列
 */
const val EMPTY_STRING = ""

/**
 * イメージフレームのデフォルトサイズ
 */
val DEFAULT_IMG_FRAME_SIZE = Dimension(640, 640)

/**
 * デフォルトフォントサイズ
 */
const val DEFAULT_FONT_SIZE = 16

/**
 * クイズフレームのデフォルトマージン
 */
val DEFAULT_MARGIN = Insets(DEFAULT_FONT_SIZE / 4, DEFAULT_FONT_SIZE / 2, DEFAULT_FONT_SIZE / 4, DEFAULT_FONT_SIZE / 4)

/**
 * クイズをスキップするしきい値（この値以上なら表示しない）
 */
const val DEFAULT_THRESHOLD = 4
