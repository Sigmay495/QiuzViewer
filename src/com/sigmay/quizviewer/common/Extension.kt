/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: Extension.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.common

/**
 * CSVファイルの行を整形して読み込む。
 */
fun String.readCsvLine() = this.split(",").map { it.trim() }.toList()

/**
 * 文字列が一致しているか確認する（大文字小文字は無視）。
 *
 * @param regex
 */
fun String.matches(regex: String) = Regex(regex.toLowerCase()).matches(this.toLowerCase())
