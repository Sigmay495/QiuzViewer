/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: QuizFrame.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame

import javax.swing.JFrame

abstract class QuizFrame(title: String): JFrame(title) {
    var isWaiting = true
    var isFinished = false
}
