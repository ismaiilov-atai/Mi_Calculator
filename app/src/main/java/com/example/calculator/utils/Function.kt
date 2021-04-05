package com.example.calculator.utils

import java.math.BigDecimal

abstract class Function {

    abstract fun call(arguments: List<BigDecimal>): BigDecimal

}