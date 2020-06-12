/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: InputPanel.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.12
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.frame.component

import javax.swing.JPanel

/**
 * 入力表示部分のパネル。
 */
abstract class InputPanel: JPanel() {
    /**
     * 入力結果を取得する。
     *
     * @return 入力結果の配列
     */
    abstract fun getResponse(): Array<String>
}
