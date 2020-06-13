/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: CorrectCountDao.kt
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
 * クイズの正答数ファイル用のDAO。
 */
class RecordDao {

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

            if (!header[1].matches("correct_*count"))
                throw IllegalFileFormatException("見出しの形式が異常です。")
        }

        /**
         * クイズの正答数行 [recordLine] を読み込み、マップに格納する。
         */
        private fun putRecord(recordLine: String) {
            try {
                val record = recordLine.readCsvLine()
                for (i in 1 until record.size)
                    quizMap[record[0]]!!.record[i - 1] = record[i].toInt()
            } catch (e: NumberFormatException) {
                throw IllegalFileFormatException("正答数は整数である必要があります。")
            } catch (e: Exception) {
                throw IllegalFileFormatException("正答数の形式が異常です。")
            }
        }

        /**
         * クイズの正答数ファイルを読み込み、クイズ情報を付加する。
         *
         * @param quizMap クイズ情報を格納したマップ
         * @param filePath クイズの正答数ファイルのパス
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
                putRecord(lines[i])
        }

        /**
         * クイズの正答数ファイルを書き出す。
         *
         * @param quizMap クイズ情報を格納したマップ
         * @param filePath クイズの正答数ファイルのパス
         */
        fun writeBlankQuiz(quizMap: MutableMap<String, BlanksQuiz>, filePath: String) {
            this.quizMap = quizMap

            if (File(filePath).exists())
                File(filePath).delete()

            val writer = File(filePath).bufferedWriter()
            writer.write("quizNo,correctCount")
            writer.newLine()

            for ((quizNo, quiz) in quizMap.toSortedMap()) {
                writer.write("$quizNo")
                for (correctCount in quiz.record)
                    writer.write(",$correctCount")
                writer.newLine()
            }

            writer.close()
        }
    }
}
