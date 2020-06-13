/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: NoDataException.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.common

/**
 * データファイルの形式異常時の例外。
 *
 * @constructor
 * 例外を発生させる。
 *
 * @param msg 例外メッセージ
 */
class IllegalFileFormatException(msg: String): Exception(msg)
