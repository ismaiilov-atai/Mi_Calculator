package com.example.calculator.utils

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import kotlin.collections.LinkedHashMap


internal class Evaluator() : ExprVisitor<BigDecimal> {
    internal var mathContext: MathContext = MathContext.DECIMAL64

    private val variables: LinkedHashMap<String, BigDecimal> = linkedMapOf()
    private val functions: MutableMap<String, Function> = mutableMapOf()

    private fun define(name: String, value: BigDecimal) {
        variables += name to value
    }

    fun define(name: String, expr: Expr): Evaluator {
        define(name.toLowerCase(Locale.ROOT), eval(expr))

        return this
    }

    fun addFunction(name: String, function: Function): Evaluator {
        functions += name.toLowerCase(Locale.ROOT) to function

        return this
    }

    fun eval(expr: Expr): BigDecimal {
        return expr.accept(this)
    }

    override fun visitAssignExpr(expr: AssignExpr): BigDecimal {
        val value = eval(expr.value)

        define(expr.name.lexeme, value)

        return value
    }

    override fun visitLogicalExpr(expr: LogicalExpr): BigDecimal {
        val left = expr.left
        val right = expr.right

        return when (expr.operator.type) {
            TokenType.BAR_BAR -> left or right
            TokenType.AMP_AMP -> left and right
            else -> throw ExpressionException(
                "Invalid logical operator '${expr.operator.lexeme}'")
        }
    }

    override fun visitBinaryExpr(expr: BinaryExpr): BigDecimal {
        val left = eval(expr.left)
        val right = eval(expr.right)

        return when (expr.operator.type) {
            TokenType.PLUS -> left + right
            TokenType.MINUS -> left - right
            TokenType.STAR -> left * right
            TokenType.SLASH -> left.divide(right, mathContext)
            TokenType.MODULO -> left.remainder(right, mathContext)
            TokenType.EXPONENT -> left pow right
            TokenType.EQUAL_EQUAL -> (left == right).toBigDecimal()
            TokenType.NOT_EQUAL -> (left != right).toBigDecimal()
            TokenType.GREATER -> (left > right).toBigDecimal()
            TokenType.GREATER_EQUAL -> (left >= right).toBigDecimal()
            TokenType.LESS -> (left < right).toBigDecimal()
            TokenType.LESS_EQUAL -> (left <= right).toBigDecimal()
            else -> throw ExpressionException(
                "Invalid binary operator '${expr.operator.lexeme}'")
        }
    }

    override fun visitUnaryExpr(expr: UnaryExpr): BigDecimal {
        val right = eval(expr.right)

        return when (expr.operator.type) {
            TokenType.MINUS -> {
                right.negate()
            }
            else -> throw ExpressionException("Invalid unary operator")
        }
    }

    override fun visitCallExpr(expr: CallExpr): BigDecimal {
        val name = expr.name
        val function = functions[name.toLowerCase(Locale.ROOT)]
            ?: throw ExpressionException("Undefined function '$name'")

        return function.call(expr.arguments.map { eval(it) })
    }

    override fun visitLiteralExpr(expr: LiteralExpr): BigDecimal {
        return expr.value
    }

    override fun visitVariableExpr(expr: VariableExpr): BigDecimal {
        val name = expr.name.lexeme

        return variables[name.toLowerCase(Locale.ROOT)]
            ?: throw ExpressionException("Undefined variable '$name'")
    }

    override fun visitGroupingExpr(expr: GroupingExpr): BigDecimal {
        return eval(expr.expression)
    }

    private infix fun Expr.or(right: Expr): BigDecimal {
        val left = eval(this)

        // short-circuit if left is truthy
        if (left.isTruthy()) return BigDecimal.ONE

        return eval(right).isTruthy().toBigDecimal()
    }

    private infix fun Expr.and(right: Expr): BigDecimal {
        val left = eval(this)

        // short-circuit if left is falsey
        if (!left.isTruthy()) return BigDecimal.ZERO

        return eval(right).isTruthy().toBigDecimal()
    }

    private fun BigDecimal.isTruthy(): Boolean {
        return this != BigDecimal.ZERO
    }

    private fun Boolean.toBigDecimal(): BigDecimal {
        return if (this) BigDecimal.ONE else BigDecimal.ZERO
    }

    private infix fun BigDecimal.pow(n: BigDecimal): BigDecimal {
        var right = n
        val signOfRight = right.signum()
        right = right.multiply(signOfRight.toBigDecimal())
        val remainderOfRight = right.remainder(BigDecimal.ONE)
        val n2IntPart = right.subtract(remainderOfRight)
        val intPow = pow(n2IntPart.intValueExact(), mathContext)
        val doublePow = BigDecimal(Math
            .pow(toDouble(), remainderOfRight.toDouble()))

        var result = intPow.multiply(doublePow, mathContext)
        if (signOfRight == -1) result = BigDecimal
            .ONE.divide(result, mathContext.precision, RoundingMode.HALF_UP)

        return result
    }

}