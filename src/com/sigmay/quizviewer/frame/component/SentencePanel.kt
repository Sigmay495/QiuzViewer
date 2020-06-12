/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: Sentence.kt
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
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.awt.Color
import java.awt.Font
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.border.EmptyBorder

/**
 * 問題文表示部分のパネル。
 *
 * @constructor
 * 表示したいクイズ [quiz] を入力して問題文パネルを作成する。
 */
class SentencePanel(quiz: BlanksQuiz) : JPanel() {

    init {
        val textArea = JTextArea(quiz.sentence, 5, 50)
        textArea.font = Font(textArea.font.name, textArea.font.style, DEFAULT_FONT_SIZE)
        textArea.background = Color.DARK_GRAY
        textArea.foreground = Color.WHITE
        textArea.caretColor = Color.WHITE
        textArea.lineWrap = true
        textArea.isEditable = false
        textArea.margin = DEFAULT_MARGIN
        add(JScrollPane(textArea))
        border = EmptyBorder(DEFAULT_MARGIN)
    }
}
