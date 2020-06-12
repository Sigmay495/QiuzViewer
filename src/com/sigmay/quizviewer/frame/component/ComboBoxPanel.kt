/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: ComboBoxPanel.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame.component

import com.sigmay.quizviewer.common.DEFAULT_FONT_SIZE
import com.sigmay.quizviewer.common.DEFAULT_MARGIN
import com.sigmay.quizviewer.common.DEFAULT_THRESHOLD
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

/**
 * テキストフィールド表示部分のパネル。
 *
 * @constructor
 * 表示したいクイズ [quiz] を入力してテキストフィールドパネルを作成する。
 */
class ComboBoxPanel(quiz: BlanksQuiz) : InputPanel() {
    /**
     * 選択用のデータ
     */
    private val comboData = quiz.answers.toMutableList().shuffled()

    /**
     * 入力するテキストフィールド
     */
    private val comboBox = Array(quiz.answers.size) { JComboBox(comboData.toTypedArray()) }

    init {
        layout = FlowLayout()

        // 全てのテキストフィールドを生成
        for (i in comboBox.indices) {
            val rowPanel = JPanel()

            // 問題番号を表示
            val numberLabel = JLabel("(${quiz.numberString[i]})")
            numberLabel.font = Font(numberLabel.font.name, numberLabel.font.style, DEFAULT_FONT_SIZE)
            numberLabel.preferredSize = Dimension(DEFAULT_FONT_SIZE * 3, (DEFAULT_FONT_SIZE * 1.5).toInt())
            numberLabel.horizontalAlignment = JLabel.CENTER
            numberLabel.verticalAlignment = JLabel.CENTER
            rowPanel.add(numberLabel)

            // テキストフィールドを表示
            comboBox[i]
            comboBox[i].font = Font(comboBox[i].font.name, comboBox[i].font.style, DEFAULT_FONT_SIZE)
            comboBox[i].preferredSize = Dimension(DEFAULT_FONT_SIZE * 10, (DEFAULT_FONT_SIZE * 1.5).toInt())
            if (quiz.correctCount[i] >= DEFAULT_THRESHOLD) {
                comboBox[i].selectedIndex = comboData.indexOfFirst { it == quiz.answers[i] }
                comboBox[i].isEnabled = false
            }
            rowPanel.add(comboBox[i])

            // 問題番号を表示
            val suffixberLabel = JLabel(quiz.suffix[i])
            suffixberLabel.font = Font(suffixberLabel.font.name, suffixberLabel.font.style, DEFAULT_FONT_SIZE)
            suffixberLabel.preferredSize = Dimension(DEFAULT_FONT_SIZE * 5, (DEFAULT_FONT_SIZE * 1.5).toInt())
            suffixberLabel.verticalAlignment = JLabel.CENTER
            rowPanel.add(suffixberLabel)

            add(rowPanel)
        }
        preferredSize = Dimension(this.width, (DEFAULT_FONT_SIZE * 1.25 * quiz.answers.size).toInt() + 10)
        border = EmptyBorder(DEFAULT_MARGIN)
    }

    /**
     * 入力結果を取得する。
     *
     * @return 入力結果の配列
     */
    override fun getResponse() = Array(comboBox.size) { comboBox[it].selectedItem!!.toString() }
}
