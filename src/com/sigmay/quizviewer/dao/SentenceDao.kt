/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: SentenseDao.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.dao

import com.sigmay.quizviewer.common.IllegalFileFormatException
import com.sigmay.quizviewer.common.matches
import com.sigmay.quizviewer.common.readCsvLine
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.io.File

/**
 * クイズの問題文ファイル用のDAO。
 */
class SentenceDao {

    companion object {
        /**
         * クイズ情報を格納するマップ
         */
        private lateinit var quizMap: MutableMap<String, BlanksQuiz>

        /**
         * 見出し行 [headerLine] の形式が正しいか確認する。異常時は例外が発生する。
         */
        private fun checkHeader(headerLine: String) {
            val header = headerLine.readCsvLine()

            if (header.size < 2)
                throw IllegalFileFormatException("見出しの形式が異常です。")

            if (!header[0].matches("quiz_*no"))
                throw IllegalFileFormatException("見出しの形式が異常です。")

            if (!header[1].matches("sentence"))
                throw IllegalFileFormatException("見出しの形式が異常です。")
        }

        /**
         * クイズの問題文行 [sentenceLine] を読み込み、マップに格納する。
         */
        private fun putSentence(sentenceLine: String) {
            try {
                val sentence = sentenceLine.readCsvLine()
                quizMap[sentence[0]]!!.sentence = sentence[1]
            } catch (e: Exception) {
                throw IllegalFileFormatException("問題文の形式が異常です。")
            }
        }

        /**
         * クイズの問題文ファイルを読み込み、クイズ情報を付加する。
         *
         * @param quizMap クイズ情報を格納したマップ
         * @param filePath クイズの問題文ファイルのパス
         */
        fun readBlankQuiz(quizMap: MutableMap<String, BlanksQuiz>, filePath: String) {
            this.quizMap = quizMap

            if (!File(filePath).exists())
                return

            val lines = File(filePath).readLines()

            if (lines.isEmpty())
                return

            checkHeader(lines[0])

            for (i in 1 until lines.size)
                putSentence(lines[i])
        }
    }
}
