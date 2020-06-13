/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: PropertyDao.kt
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
import java.util.ArrayList
import kotlin.math.min

/**
 * クイズの設定ファイル用のDAO。
 */
class PropertyDao {

    companion object {
        /**
         * クイズ情報を格納するマップ
         */
        private lateinit var quizMap: MutableMap<String, BlanksQuiz>

        /**
         * 設定ファイルの見出し
         */
        private lateinit var headings: MutableList<String>

        /**
         * 見出し行 [headerLine] の形式が正しいか確認する。異常時は例外が発生する。
         */
        private fun checkHeader(headerLine: String) {
            val header = headerLine.readCsvLine()

            if (header.size < 2)
                throw IllegalFileFormatException("見出しの形式が異常です。")

            if (!header[0].matches("quiz_*no"))
                throw IllegalFileFormatException("見出しの形式が異常です。")

            headings = ArrayList()

            for (i in 1 until header.size)
                when {
                    header[i].matches(".*index.*")|| header[i].matches(".*idx.*") -> headings.add("idx")
                    header[i].matches(".*image.*") || header[i].matches(".*img.*") -> headings.add("img")
                    header[i].matches(".*cand.*") -> headings.add("cand")
                    else -> throw IllegalFileFormatException("見出しの形式が異常です。")
                }
        }

        /**
         * クイズの正答数行 [propertyLine] を読み込み、マップに格納する。
         */
        private fun putProperty(propertyLine: String) {
            try {
                val property = propertyLine.readCsvLine()
                for (i in 1 until property.size)
                    for (j in 0 until min(headings.size, property.size - 1)) {
                        when (headings[j]) {
                            "idx" -> quizMap[property[0]]!!.numberFirstIndex = property[j + 1]
                            "img" -> quizMap[property[0]]!!.imgFilePath = property[j + 1]
                            "cand" -> if (property[j + 1].toLowerCase() == "true" || property[j + 1] == "1") quizMap[property[0]]!!.hasCandidate = true
                        }
                    }
            } catch (e: Exception) {
                throw IllegalFileFormatException("設定値の形式が異常です。")
            }
        }

        /**
         * クイズの設定ファイルを読み込み、クイズ情報を付加する。
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
                putProperty(lines[i])
        }
    }
}
