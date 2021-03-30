package com.example.calculator.ui.figure

import android.graphics.Path
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calculator.R
import com.example.calculator.base.BaseViewModel
import com.example.calculator.base.BaseViewModelEventListener
import com.example.calculator.utils.Expressions

class FiguresCalculationViewModel(event: BaseViewModelEventListener) : BaseViewModel(event) {

    enum class Operation(var id: Int?, var type: String? = null) {
        AC(R.id.figures_ac),
        CLEAR(R.id.figures_clear),

        ZERO(R.id.figures_btn_zero, "0"),
        ONE(R.id.figures_btn_one, "1"),
        TWO(R.id.figures_btn_two, "2"),
        THREE(R.id.figures_btn_three, "3"),
        FOUR(R.id.figures_btn_four, "4"),
        FIVE(R.id.figures_btn_five, "5"),
        SIX(R.id.figures_btn_six, "6"),
        SEVEN(R.id.figures_btn_seven, "7"),
        EIGHT(R.id.figures_btn_eight, "8"),
        NINE(R.id.figures_btn_nine, "9"),

        DECREMENT(R.id.figures_btn_minus, " - "),
        PLUS(R.id.figures_btn_plus, " + "),
        MULTIPLICATION(R.id.figures_btn_multiplication, " * "),
        DIVIDE(R.id.figures_divide, " / "),
        PERCENTAGE(R.id.figures_pracentage, " % "),
        DOT(R.id.figures_btn_dot, "."),

        TRACTION(R.id.figures_btn_transaction),
        EQUAL(R.id.figures_btn_equal);

        companion object {
            fun valueOf(value: Int?) = values().find { it.id == value }
        }
    }

    var fieldLiveData: MutableLiveData<String> = MutableLiveData()
    var resultLiveData: MutableLiveData<String> = MutableLiveData()
    var isEqualClicked: MutableLiveData<Boolean> = MutableLiveData()

    private var mathString = String()

    private val checkOperation: ArrayList<String> by lazy {
        val checkArray = ArrayList<String>()
        checkArray.add(" + "); checkArray.add(" - ")
        checkArray.add(" * "); checkArray.add(" / ")
        checkArray.add(" % ")
        return@lazy checkArray
    }

    fun onButtonClick(viewId: Int?) {
        val operation = Operation.valueOf(viewId)
        if (operation?.type != null) {
            onClickMath(operation)
            isEqualClicked.value = false
        } else if (operation != null)
            onOperationClick(operation)
    }

    private fun onClickMath(operation: Operation) {
        if (!(checkOperation.contains(mathString.takeLast(3)) && checkOperation.contains(operation.type))) {
            if (operation.type == Operation.ZERO.type && mathString.isEmpty()){
                fieldLiveData.value = fieldLiveData.value
            }else {
                mathString += operation.type
                fieldLiveData.value = mathString
                realTimeResult()
            }
        }
    }

    private fun onOperationClick(operation: Operation) {
        when (operation) {
            Operation.AC -> removeLastOne()
            Operation.EQUAL -> calculate()
        }
    }

    private fun removeLastOne() {
        mathString = if (checkOperation.contains(mathString.takeLast(3))) mathString.dropLast(3)
        else mathString.dropLast(1)

        fieldLiveData.value = mathString

        realTimeResult()
    }

    private fun calculate() {
        isEqualClicked.value = true
    }

    private fun realTimeResult() {
        try {
            val result = Expressions().eval(mathString)
            resultLiveData.value = "= $result"
        }catch (e:Exception){
            resultLiveData.value = "0"
        }
    }
}