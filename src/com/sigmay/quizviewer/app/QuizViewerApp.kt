/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: QuizViewerApp.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.app

import com.sigmay.quizviewer.common.*
import com.sigmay.quizviewer.dao.AnswerDao
import com.sigmay.quizviewer.dao.PropertyDao
import com.sigmay.quizviewer.dao.RecordDao
import com.sigmay.quizviewer.dao.SentenceDao
import com.sigmay.quizviewer.entity.BlanksQuiz
import java.io.File
import javax.swing.JFileChooser
import kotlin.system.exitProcess

class QuizViewerApp {

    companion object {

        /**
         * クイズが格納されたルートディレクトリ
         */
        private var rootDir: String = EMPTY_STRING

        /**
         * クイズのタイプ
         */
        private var quizType: String = EMPTY_STRING

        /**
         * クイズのタイトル
         */
        private var quizTitle: String = EMPTY_STRING

        /**
         * クイズ情報を格納したマップ
         */
        private lateinit var quizMap: MutableMap<String, BlanksQuiz>

        /**
         * GUIでクイズファイルを探索する。
         *
         * @return クイズファイル
         */
        private fun getQuizFilePath(): File {
            val fc = JFileChooser(".")
            val selected = fc.showOpenDialog(null)
            if (selected != JFileChooser.APPROVE_OPTION)
                exitProcess(0)
            return fc.selectedFile
        }

        /**
         * クイズファイルを読み込む。
         *
         * @param quizFilePath クイズファイルのパス（存在しない場合GUIで入力）
         */
        private fun readQuizFile(quizFilePath: String) {
            val quizFile = if (!File(quizFilePath).exists())
                getQuizFilePath()
            else
                File(quizFilePath)

            val lines = quizFile.readLines()

            for (line in lines) {
                val param = line.readCsvLine()

                if (param[0].matches(".*root.*") && param.size > 1)
                    rootDir = param[1]
                else if (param[0].matches(".*type.*") && param.size > 1)
                    quizType = param[1].toLowerCase()
                else if (param[0].matches(".*title.*") && param.size > 1)
                    quizTitle = param[1]
            }

            if (!File(rootDir).exists())
                throw ApplicationException("ディレクトリが存在しません。")
            rootDir = "${File(rootDir).canonicalPath}\\"

            if (quizType != "blank")
                throw ApplicationException("この形式のクイズには対応していません。")

            if (quizTitle == EMPTY_STRING)
                throw ApplicationException("クイズのタイトルが未設定です。")
        }

        /**
         * ファイルを読み込んでクイズ情報を格納したマップを生成する。
         */
        fun makeQuizMap() {
            quizMap = HashMap<String, BlanksQuiz>()
            AnswerDao.readBlankQuiz(quizMap, rootDir + DEFAULT_ANSWER_FILE)
            SentenceDao.readBlankQuiz(quizMap, rootDir + DEFAULT_SENTENCE_FILE)
            PropertyDao.readBlankQuiz(quizMap, rootDir + DEFAULT_PROPERTY_FILE)
            RecordDao.readBlankQuiz(quizMap, rootDir + DEFAULT_RECORD_FILE)
            for (quiz in quizMap.values) {
                if (quiz.imgFilePath != EMPTY_STRING)
                    quiz.imgFilePath = rootDir + quiz.imgFilePath
                quiz.isEditable = false
            }
        }

        fun showQuiz() {
            val quizNoList = quizMap.keys.shuffled()
            try {
                for (i in quizNoList.indices)
                    BlanksQuizApp.execute("$quizTitle（${i + 1}／${quizNoList.size}問目）", quizMap[quizNoList[i]]!!)
                println("クイズが終了しました。")
            } catch (e: FinishFlagHandler) {
                println("クイズを中断します。")
            } finally {
                RecordDao.writeBlankQuiz(quizMap, rootDir + DEFAULT_RECORD_FILE)
            }
        }

        fun execute(quizFilePath: String = EMPTY_STRING) {
            readQuizFile(quizFilePath)
            makeQuizMap()
            showQuiz()
        }
    }
}
