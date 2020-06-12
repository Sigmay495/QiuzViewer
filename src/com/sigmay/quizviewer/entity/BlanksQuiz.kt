/*
 * Copyright (c) 2020 Sigmay
 *
 *  ==============================
 *  Project Name: QuizViewer
 *  File Name: BlanksQuiz.kt
 *  Encoding: UTF-8
 *  Creation Date: 2020.6.5
 *
 *  Twitter: @sigmay_495
 *  GitHub: Sigmay495
 *  ==============================
 */

package com.sigmay.quizviewer.entity

import com.sigmay.quizviewer.common.DEFAULT_THRESHOLD
import com.sigmay.quizviewer.common.EMPTY_STRING
import com.sigmay.quizviewer.common.QuizEditingFailureException
import com.sigmay.quizviewer.common.QuizMarkingFailureException
import java.io.Serializable

/**
 * 空白穴埋め形式のクイズ。
 *
 * @constructor
 * 解答の配列 [answers] を入れてオブジェクトを生成する。
 */
class BlanksQuiz(val answers: Array<String>) : Serializable {

    companion object {
        /**
         * ひらがなの配列
         */
        val hiragana = arrayOf("あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ",
                "さ", "し", "す", "せ", "そ", "た", "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の",
                "は", "ひ", "ふ", "へ", "ほ", "ま", "み", "む", "め", "も", "や", "ゆ", "よ",
                "ら", "り", "る", "れ", "ろ", "わ")

        /**
         * カタカナの配列
         */
        val katakana = arrayOf("ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ",
                "サ", "シ", "ス", "セ", "ソ", "タ", "チ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ",
                "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ",
                "ラ", "リ", "ル", "レ", "ロ", "ワ")
    }

    /**
     * 問題文（＃を自動で採番する）
     * セットしない場合、問題文は表示されない
     */
    var sentence = EMPTY_STRING
        set(value) {
            if (isEditable) {
                var count = 1
                field = value.replace(Regex("＃")) { " (${count++}) " }
            } else
                throw QuizEditingFailureException("編集不可となっています。")
        }

    /**
     * 各問の正解数カウンタ（初期値はすべて0）
     */
    val correctCount = IntArray(answers.size) { 0 }

    /**
     * 各問の問番号（初期値は1からスタート）
     */
    val numberString: Array<String> by lazy {
        if (numberFirstIndex == EMPTY_STRING)
            numberFirstIndex = "1"
        when (numberFirstIndex.length) {
            1 -> {
                // 文字が1桁の場合
                when {
                    numberFirstIndex[0] in 'あ'..'わ' -> {
                        val index = hiragana.indexOfFirst { it == numberFirstIndex }
                        if (index == -1 || index + answers.size > 44)
                            throw QuizEditingFailureException("不正な文字列 $numberFirstIndex が指定されています。")
                        Array(answers.size) { hiragana[index + it] }
                    }
                    numberFirstIndex[0] in 'ア'..'ワ' -> {
                        val index = katakana.indexOfFirst { it == numberFirstIndex }
                        if (index == -1 || index + answers.size > 44)
                            throw QuizEditingFailureException("不正な文字列 $numberFirstIndex が指定されています。")
                        Array(answers.size) { katakana[index + it] }
                    }
                    numberFirstIndex[0] in 'A'..'Z' && numberFirstIndex[0] + answers.size <= 'Z' + 1 ->
                        Array(answers.size) { "${numberFirstIndex[0] + it}" }
                    numberFirstIndex[0] in 'a'..'z' && numberFirstIndex[0] + answers.size <= 'z' + 1 ->
                        Array(answers.size) { "${numberFirstIndex[0] + it}" }
                    numberFirstIndex[0] in '1'..'9' -> Array(answers.size) { "${numberFirstIndex.toInt() + it}" }
                    else -> throw QuizEditingFailureException("不正な文字列 $numberFirstIndex が指定されています。")
                }
            }
            // 文字が2桁以上の場合
            else -> {
                try {
                    Array(answers.size) { "${numberFirstIndex.toInt() + it}" }
                } catch (e: NumberFormatException) {
                    throw QuizEditingFailureException("不正な文字列 $numberFirstIndex が指定されています。")
                }
            }
        }
    }

    /**
     * 1問目の問題番号
     */
    var numberFirstIndex = "1"
        set(value) {
            if (isEditable)
                field = value
            else
                throw QuizEditingFailureException("編集不可となっています。")
        }

    /**
     * 各問の接尾語（初期値は空文字列）
     */
    val suffix = Array(answers.size) { EMPTY_STRING }

    /**
     * 表示する画像のファイルパス（空の場合、画像は表示されない）
     */
    var imgFilePath = EMPTY_STRING
        set(value) {
            if (isEditable)
                field = value
            else
                throw QuizEditingFailureException("編集不可となっています。")
        }

    /**
     * [true] ならばプルダウン形式、[false] ならばテキストフィールド形式
     */
    var hasCandidate = false
        set(value) {
            if (isEditable)
                field = value
            else
                throw QuizEditingFailureException("編集不可となっています。")
        }

    /**
     * 編集可能かを管理するフラグ（1回閉じたらもう開けない）
     */
    var isEditable = true
        set(value) {
            if (isEditable && !value) {
                numberString
                field = value
            } else
                throw QuizEditingFailureException("編集不可となっています。")
        }

    /**
     * 編集が終了しているかチェックする。
     *
     * @return 解答が一つでも空なら [false] 、問題文と画像の両方がない場合も [false]
     */
    fun isComplete(): Boolean {
        if (isEditable)
            return false
        if (answers.any { it == EMPTY_STRING })
            return false
        return sentence != EMPTY_STRING || imgFilePath != EMPTY_STRING
    }

    /**
     * クイズの解答を採点し、正解数を更新する。
     *
     * @param response クイズの解答
     * @return 採点結果を格納した配列
     */
    fun markAnswers(response: Array<String>): BooleanArray {
        if (answers.size != response.size)
            throw QuizMarkingFailureException("クイズの回答数が一致しません。")
        val result = BooleanArray(response.size) { false }
        for (i in result.indices)
            if (correctCount[i] < DEFAULT_THRESHOLD && answers[i] == response[i]) {
                result[i] = true
                correctCount[i]++
            }
        return result
    }
}
