/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: AnswersDao.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.dao

import com.sigmay.quizviewer.common.EMPTY_STRING
import com.sigmay.quizviewer.common.IllegalFileFormatException
import com.sigmay.quizviewer.common.matches
import com.sigmay.quizviewer.common.readCsvLine
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.io.File
import kotlin.collections.HashMap

/**
 * クイズの回答ファイル用のDAO。
 */
class AnswerDao {

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

            if (!header[1].matches("answers*"))
                throw IllegalFileFormatException("見出しの形式が異常です。")
        }

        /**
         * クイズの回答行 [answersLine] を読み込み、マップに格納する。
         */
        private fun putAnswer(answersLine: String) {
            try {
                val answer = answersLine.readCsvLine().filter { it != EMPTY_STRING }
                val quiz = BlanksQuiz(answer.map { it.split("’")[0] }.subList(1, answer.size))
                for (i in 1 until answer.size)
                    if (answer[i].contains('’'))
                        quiz.suffix[i-1] = answer[i].split("’")[1]
                quizMap[answer[0]] = quiz
            } catch (e: Exception) {
                throw IllegalFileFormatException("回答の形式が異常です。")
            }
        }

        /**
         * クイズの問題文ファイルを読み込み、クイズ情報を付加する。
         *
         * @param quizMap クイズ情報を格納したマップ
         * @param filePath クイズの回答ファイルのパス
         */
        fun readBlankQuiz(quizMap: MutableMap<String, BlanksQuiz>, filePath: String) {
            this.quizMap = quizMap

            if (!File(filePath).exists())
                throw IllegalFileFormatException("回答データが存在しません。")

            val lines = File(filePath).readLines()

            if (lines.isEmpty())
                throw IllegalFileFormatException("回答データが存在しません。")

            checkHeader(lines[0])

            for (i in 1 until lines.size)
                putAnswer(lines[i])
        }
    }
}
