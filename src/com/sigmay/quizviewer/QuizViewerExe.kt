/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: QuizViewerExe.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer

import com.sigmay.quizviewer.app.QuizViewerApp
import javax.swing.JOptionPane

fun main(args: Array<String>) {
    try {
        if (args.isEmpty())
            QuizViewerApp.execute()
        else
            QuizViewerApp.execute(args[0])
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, e.message, "エラー発生", JOptionPane.ERROR_MESSAGE)
        e.printStackTrace()
    }
}
