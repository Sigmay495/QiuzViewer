/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: FinishFlagHandler.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.12
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.common

/**
 * アプリ終了フラグのハンドラ。
 *
 * @constructor
 * 終了用のハンドラを発生させる。
 *
 * @param msg 例外メッセージ
 */
class FinishFlagHandler(msg: String): Exception(msg)
