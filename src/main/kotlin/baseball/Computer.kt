package baseball

import camp.nextstep.edu.missionutils.Randoms

class Computer {
    var gameFlag = true

    fun setComputerNumber(): MutableList<Int> {
        val computer = mutableListOf<Int>()

        while (computer.size < 3) {
            val randomNumber = Randoms.pickNumberInRange(1, 9)
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber)
            }
        }

        return computer
    }

    private fun countStrikeAndBall(computer: MutableList<Int>, user: MutableList<Int>): Pair<Int, Int> {
        var strike = 0
        var ball = 0

        user.forEach {
            if (computer.contains(it)) {
                ball++
            }
        }

        for (i in user.indices) {
            if (user[i] == computer[i]) {
                strike++
                ball--
            }
        }

        if (strike == 3) {
            gameFlag = false
        }

        return Pair(strike, ball)
    }

    fun compareAndPrintHint(computer: MutableList<Int>, user: MutableList<Int>) {
        val (strike, ball) = countStrikeAndBall(computer, user)
        val hint = when {
            strike == 0 && ball == 0 -> "낫싱"
            strike > 0 && ball == 0 -> "${strike}스트라이크"
            strike == 0 && ball > 0 -> "${ball}볼"
            else -> "${ball}볼 ${strike}스트라이크"
        }

        println(hint)
    }
}