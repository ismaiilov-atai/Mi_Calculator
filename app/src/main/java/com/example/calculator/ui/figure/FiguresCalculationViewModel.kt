package com.example.calculator.ui.figure

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.database.HistoryDatabase
import com.example.calculator.database.HistoryItem
import com.example.calculator.utils.Constants.KEY_MATH
import com.example.calculator.utils.Constants.KEY_RESULT
import com.example.calculator.utils.parser.ExpressionParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FiguresCalculationViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

    enum class Operation(var id: Int?, var type: String? = null) {
        AC(R.id.figures_clear),
        CLEAR(R.id.figures_delete),

        ZERO(R.id.figures_zero, "0"),
        ONE(R.id.figures_one, "1"),
        TWO(R.id.figures_two, "2"),
        THREE(R.id.figures_three, "3"),
        FOUR(R.id.figures_four, "4"),
        FIVE(R.id.figures_five, "5"),
        SIX(R.id.figures_six, "6"),
        SEVEN(R.id.figures_seven, "7"),
        EIGHT(R.id.figures_eight, "8"),
        NINE(R.id.figures_nine, "9"),

        DECREMENT(R.id.figures_minus, "-"),
        PLUS(R.id.figures_plus, "+"),
        MULTIPLICATION(R.id.figures_multiple, "*"),
        DIVIDE(R.id.figures_divide, "/"),
        PERCENTAGE(R.id.figures_presantage, "%"),
        DOT(R.id.figures_dot, "."),

        TRACTION(R.id.figures_transform),
        EQUAL(R.id.figures_equal),

        AC_NORMAL(R.id.figures_clear_normal),
        CLEAR_NORMAL(R.id.figures_delete_normal),

        ZERO_NORMAL(R.id.figures_zero_normal, "0"),
        ONE_NORMAL(R.id.figures_one_normal, "1"),
        TWO_NORMAL(R.id.figures_two_normal, "2"),
        THREE_NORMAL(R.id.figures_three_normal, "3"),
        FOUR_NORMAL(R.id.figures_four_normal, "4"),
        FIVE_NORMAL(R.id.figures_five_normal, "5"),
        SIX_NORMAL(R.id.figures_six_normal, "6"),
        SEVEN_NORMAL(R.id.figures_seven_normal, "7"),
        EIGHT_NORMAL(R.id.figures_eight_normal, "8"),
        NINE_NORMAL(R.id.figures_nine_normal, "9"),

        DECREMENT_NORMAL(R.id.figures_minus_normal, "-"),
        PLUS_NORMAL(R.id.figures_plus_normal, "+"),
        MULTIPLICATION_NORMAL(R.id.figures_multiple_normal, "*"),
        DIVIDE_NORMAL(R.id.figures_divide_normal, "/"),
        PERCENTAGE_NORMAL(R.id.figures_presantage_normal, "%"),
        DOT_NORMAL(R.id.figures_dot_normal, "."),

        TRACTION_NORMAL(R.id.figures_transform_normal),
        EQUAL_NORMAL(R.id.figures_equal_normal),

        SIN(R.id.figures_sin,"sin("),
        COS(R.id.figures_cos,"cos("),
        TAN(R.id.figures_sin,"tan("),
        XY(R.id.figures_x_y,"^"),
        LG(R.id.figures_lg,"log("),
        IN(R.id.figures_in,"ln("),

        LEFT_PARENTHESIS(R.id.figures_left_parenthesis,"("),
        RIGHT_PARENTHESIS(R.id.figures_right_parenthesis,")"),
        UNDER(R.id.figures_under_x,"sqrt("),
        X_EXCLAMATION(R.id.figures_x_exclamation,"!"),
        ONE_DIVIDED_X(R.id.figures_one_divide_x,"^(-1)"),
        ONE_CONSTANT(R.id.figures_p_constant,"PI"),
        E_COUNT(R.id.figures_e,"e");

        companion object {
            fun valueOf(value: Int?) = values().find { it.id == value }
        }
    }

    var layoutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var fieldLiveData: MutableLiveData<String> = MutableLiveData()
    var resultLiveData: MutableLiveData<String> = MutableLiveData()
    var historyLiveData: MutableLiveData<List<HistoryItem>> = MutableLiveData()
    var equalClickedAnimationLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var isEqualClicked = false
    private var mathString = String()

    private val checkOperation: ArrayList<String> by lazy {
        val checkArray = ArrayList<String>()
        checkArray.add("PI"); checkArray.add(" - ")
        checkArray.add(" * "); checkArray.add(" / ")
        checkArray.add(" % ")
        return@lazy checkArray
    }

    fun onButtonClick(viewId: Int?) {
        val operation = Operation.valueOf(viewId)
        if (operation?.type != null) {
            onClickMath(operation)
            equalClickedAnimationLiveData.value = false
        } else if (operation != null)
            onOperationClick(operation)
    }

    fun loadDataFromHistory(intent: Intent?) {
        if (intent != null) {
            val math = intent.getStringExtra(KEY_MATH)
            val result = intent.getStringExtra(KEY_RESULT)

            math?.let { mathString = it }
            math?.let { fieldLiveData.value = it }
            result?.let { resultLiveData.value = "= $it" }
        }
    }

    fun loadHistory() {
        GlobalScope.launch {
            var historys = HistoryDatabase.instance?.historyDao()?.getAll()
            historys = historys?.asReversed()
            uiScope.launch {
                historyLiveData.value = historys
            }
        }
    }

    private fun onClickMath(operation: Operation) {
        val forMath = arrayOf('-','+','/','*','E','%')
        if (!(checkOperation.contains(mathString.takeLast(3)) && checkOperation.contains(operation.type))) {
            if (!((operation.type == Operation.ZERO.type || operation.type == Operation.ZERO_NORMAL.type) && mathString.isEmpty())) {
                if (isEqualClicked && !checkOperation.contains(operation.type)) {
                    isEqualClicked = false
                    mathString = String()
                }
                else if (mathString.contains(".") && operation.type == Operation.DOT.type && operation.type == Operation.DOT_NORMAL.type) { return }
                else if ( checkLastContent(forMath,operation) ) { return }

                mathString += operation.type
                fieldLiveData.value = mathString
                realTimeResult()
            }
        }
    }

    private fun checkLastContent(forMath: Array<Char>, operation: Operation): Boolean {
        var lastOne = ""
        forMath.forEach { if ( mathString.takeLast(1) == it.toString())  lastOne = it.toString() }
        if ( mathString.takeLast(1) == lastOne && operation.type == lastOne) { return true }
        return false
    }

    private fun onOperationClick(operation: Operation) {
        when (operation) {
            Operation.AC, Operation.AC_NORMAL -> clearAll()
            Operation.CLEAR, Operation.CLEAR_NORMAL -> removeLastOne()
            Operation.EQUAL, Operation.EQUAL_NORMAL -> calculate()
            Operation.TRACTION -> showCalculatorLayout(false)
            Operation.TRACTION_NORMAL -> showCalculatorLayout(true)
        }
    }

    private fun showCalculatorLayout(mode: Boolean) {
        layoutLiveData.value = mode
    }

    private fun clearAll() {
        mathString = String()
        fieldLiveData.value = mathString
        resultLiveData.value = "0"
    }

    private fun removeLastOne() {
        mathString = if (checkOperation.contains(mathString.takeLast(3))) mathString.dropLast(3)
        else mathString.dropLast(1)
        fieldLiveData.value = mathString
        realTimeResult()
    }

    private fun calculate() {
        if (!isEqualClicked) {
            if (mathString.isNotEmpty()) {
                try {
                    val result = ExpressionParser().evaluate(mathString).toString()

                    GlobalScope.launch {
                        HistoryDatabase.instance?.historyDao()?.insertAll(HistoryItem(mathString, result))
                        var historys = HistoryDatabase.instance?.historyDao()?.getAll()
                        historys = historys?.asReversed()

                        uiScope.launch {
                            historyLiveData.value = historys
                        }

                        mathString = result
                    }
                } catch (e: Exception) {
                    if (mathString.isNotEmpty()) {
                        resultLiveData.value = "= 0"
                    } else {
                        resultLiveData.value = "0"
                    }
                } finally {
                    isEqualClicked = true
                    equalClickedAnimationLiveData.value = true
                }
            }
        }
    }

    private fun realTimeResult() {
        try {
            var result = ExpressionParser().evaluate(mathString).toString()
            if (result.takeLast(2) == ".0") { result = result.dropLast(2)}
            resultLiveData.value = "= $result"

        } catch (e: Exception) {
            if (mathString.isNotEmpty()) {
                resultLiveData.value = "= 0"
            } else {
                resultLiveData.value = "0"
            }
        }
    }
}