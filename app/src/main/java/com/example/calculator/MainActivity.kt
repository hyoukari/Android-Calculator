package com.example.calculator

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
    #17c1010013 チンシンケツ

    現段階の問題点
        ・#databinding未使用
        ・履歴機能未実装
        ・AC、BC、Cの機能が分けていない
        ・算式全体が表示されない
        ・小数点が複数の場合アプリが落ちる

* */

class MainActivity : AppCompatActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOf<Button>(
            btnAc, btnBc, btnC,
            btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnAdd, btnSub, btnMul, btnDiv, btnDot, btnEqual
        ).forEach { btn: Button -> btn.setOnClickListener(this) }
    }

    private var operand1 = ""   // 非演算子1
    private var operator = ""   // 演算子
    private var operand2 = ""   // 非演算子2

    override fun onClick(v: View?) {
        doClick((v as Button).text.toString())
    }

    // ボタン処理
    private fun doClick(value: String) {
        when (value) {
            "+", "-", "×", "÷" -> {
                if (operand1.isNotEmpty() && operand2.isEmpty()) {
                    operator = value
                } else if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
                    operator = value
                    doCount()
                }
            }
            "=" -> {
                if (operand1.isNotEmpty() && operand2.isNotEmpty()) {
                    doCount()
                    operator = ""
                }
            }
            "AC", "BC", "C" -> {
                operand1 = ""
                operator = ""
                operand2 = ""
                textView.text = ""
            }
            else -> {
                if (operator.isNotEmpty()) {
                    if (operand2.isEmpty() && value == "0") {
                        Toast.makeText(this, "二番目の数字0入力不可", Toast.LENGTH_LONG).show()
                    } else {
                        operand2 += value
                        textView.text = operand2
                    }
                } else {
                    operand1 += value
                    textView.text = operand1
                }
            }
        }
    }

    // 計算
    private fun doCount() {
        val result = when (operator) {
            "+" -> operand1.toDouble() + operand2.toDouble()
            "-" -> operand1.toDouble() - operand2.toDouble()
            "×" -> operand1.toDouble() * operand2.toDouble()
            "÷" -> operand1.toDouble() / operand2.toDouble()
            else -> 0.0
        }

        operand1 = result.toString()
        operand2 = ""
        textView.text = result.toString()
    }
}
