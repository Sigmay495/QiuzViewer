/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: QuizFrame.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.12
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame

import com.sigmay.quizviewer.common.DEFAULT_FONT_SIZE
import com.sigmay.quizviewer.common.EMPTY_STRING
import com.sigmay.quizviewer.common.FinishFlagHandler
import com.sigmay.quizviewer.common.QuizEditingFailureException
import com.sigmay.quizviewer.entity.BlanksQuiz
import com.sigmay.quizviewer.frame.component.*
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JScrollPane
import javax.swing.WindowConstants

/**
 * 空欄穴埋めクイズを表示するフレーム。
 *
 * @constructor
 * [title] をタイトルとして、[quiz] の値を表示するフレームを作る。
 */
class BlanksQuizFrame(title: String, quiz: BlanksQuiz) : QuizFrame(title) {
    var response: Array<String>
    private set

    init {
        // クイズに不備があれば例外を返す
        if (!quiz.isComplete())
            throw QuizEditingFailureException("クイズに不備があります。")

        // サイズの設定
        size = Dimension(DEFAULT_FONT_SIZE*48, DEFAULT_FONT_SIZE*27)
        isResizable = false

        // 問題文の表示
        if (quiz.sentence != EMPTY_STRING)
            contentPane.add(SentencePanel(quiz), BorderLayout.NORTH)

        // 入力部の作成
        val inputPanel: InputPanel = if (quiz.hasCandidate)
            ComboBoxPanel(quiz)
        else
            TextFieldsPanel(quiz)
        contentPane.add(JScrollPane(inputPanel), BorderLayout.CENTER)

        // ボタンの表示
        contentPane.add(ButtonPanel(this, quiz), BorderLayout.SOUTH)

        // 表示の設定
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        isVisible = true

        // 終了するまでスリープで待機させる
        while (isWaiting)
            Thread.sleep(500)

        // 解答を受け取る
        response = inputPanel.getResponse()
        dispose()

        // 終了する場合ハンドラを投げる
        if (isFinished)
            throw FinishFlagHandler()
    }
}
