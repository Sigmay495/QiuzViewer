/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: ButtonPanel.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.12
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame.component

import com.sigmay.quizviewer.common.DEFAULT_FONT_SIZE
import com.sigmay.quizviewer.common.DEFAULT_MARGIN
import com.sigmay.quizviewer.common.EMPTY_STRING
import com.sigmay.quizviewer.entity.BlanksQuiz
import com.sigmay.quizviewer.frame.QuizFrame
import java.awt.Color
import java.awt.Font
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * ボタン表示部分のパネル。
 *
 * @constructor
 * ボタン表示部を作成する。
 */
class ButtonPanel(frame: QuizFrame, quiz: BlanksQuiz) : JPanel() {
    var response: Array<String> = Array(quiz.answers.size) { EMPTY_STRING }
        private set

    init {
        // 決定ボタンを作成
        val enterButton = JButton("決定")
        enterButton.addActionListener {
            frame.isWaiting = false
        }
        enterButton.font = Font(enterButton.font.name, enterButton.font.style, DEFAULT_FONT_SIZE)
        enterButton.margin = DEFAULT_MARGIN
        add(enterButton)

        val emptyLabel = JLabel("　　　　　　")
        add(emptyLabel)

        val finishButton = JButton("終了")
        finishButton.addActionListener {
            frame.isWaiting = false
            frame.isFinished = true
        }
        finishButton.font = Font(finishButton.font.name, finishButton.font.style, DEFAULT_FONT_SIZE)
        finishButton.foreground = Color.RED
        finishButton.margin = DEFAULT_MARGIN
        add(finishButton)

        border = EmptyBorder(DEFAULT_MARGIN)
    }

}
