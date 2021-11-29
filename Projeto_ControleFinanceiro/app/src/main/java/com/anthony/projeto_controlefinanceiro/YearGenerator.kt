package com.anthony.projeto_controlefinanceiro

class YearGenerator {
    companion object Generator {
        fun generate(start: Int, end: Int) : ArrayList<String> {
            val result = arrayListOf<String>()
            for (i in start..end) result.add(i.toString())
            return result
        }
    }
}