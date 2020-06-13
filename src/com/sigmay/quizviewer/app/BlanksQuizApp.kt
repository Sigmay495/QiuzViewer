/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: BlankQuizApp.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.13
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.app

import com.sigmay.quizviewer.common.EMPTY_STRING
import com.sigmay.quizviewer.common.FinishFlagHandler
import com.sigmay.quizviewer.entity.BlanksQuiz
import com.sigmay.quizviewer.frame.BlanksQuizFrame
import com.sigmay.quizviewer.frame.BlanksQuizResultFrame
import com.sigmay.quizviewer.frame.ImageFrame

class BlanksQuizApp {
    companion object {
        /**
         * フレームのタイトル
         */
        private lateinit var title: String

        /**
         * クイズ
         */
        private lateinit var quiz: BlanksQuiz

        /**
         * 画像を表示する。
         *
         * @return 表示する画像のフレーム
         */
        private fun showImage(): ImageFrame? {
            if (quiz.imgFilePath == EMPTY_STRING)
                return null
            return ImageFrame("$title - 図", quiz.imgFilePath)
        }

        /**
         * クイズを表示する。
         */
        private fun showQuiz() = BlanksQuizFrame("test", quiz).response

        /**
         * クイズの答えを表示する。
         *
         * @param response クイズの回答。
         */
        private fun showResult(response: Array<String>) = BlanksQuizResultFrame("$title - 解説", quiz, response)

        /**
         * クイズの入力から回答までを図付きで表示する。
         *
         * @param title フレームのタイトル
         * @param quiz 空欄穴埋めクイズ
         */
        fun execute(title: String, quiz: BlanksQuiz) {
            this.title = title
            this.quiz = quiz
            val imgFrame = showImage()
            try {
                val response = showQuiz()
                showResult(response)
            } catch (e: FinishFlagHandler) {
                throw e
            } finally {
                imgFrame?.dispose()
            }
        }
    }
}
