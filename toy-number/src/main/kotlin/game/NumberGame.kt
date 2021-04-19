package game

import kotlin.reflect.typeOf

class NumberGame(
    private val randomNumber: Int,
    private var tryCount: Int = 0
) {
    fun play(number: Int): String {
        tryCount++
        return when {
            number == randomNumber -> { "정답" }
            number > randomNumber -> { "크다" }
            else -> { "작다" }
        }
    }

    fun getTryCount() : Int = this.tryCount


}