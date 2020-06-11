/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: QuizMarkingFailureException.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.12
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.common

/**
 * クイズ採点失敗時の例外。
 *
 * @constructor
 * 例外を発生させる。
 *
 * @param msg 例外メッセージ
 */
class QuizMarkingFailureException(msg: String): Exception(msg)
