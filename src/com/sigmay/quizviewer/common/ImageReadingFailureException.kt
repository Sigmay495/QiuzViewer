/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: SlideShow
 *  File Name: ImageReadingFailureException.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.4
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.common

/**
 * 画像読み込み失敗時の例外。
 *
 * @constructor
 * 例外を発生させる。
 *
 * @param msg 例外メッセージ
 */
class ImageReadingFailureException(msg: String): Exception(msg)
