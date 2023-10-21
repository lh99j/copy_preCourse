package baseball

import camp.nextstep.edu.missionutils.Randoms
import camp.nextstep.edu.missionutils.Console
import java.lang.IllegalArgumentException

class Game {
    private var gameFlag = true

    fun printGameStartMessage() {
        println("숫자 야구 게임을 시작합니다.")
    }

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

    fun inputUserNumber(): MutableList<Int> {
        print("숫자를 입력해주세요 : ")
        val userNumber = Console.readLine()
        checkValidNumber(userNumber)

        return userNumber.map { it.digitToInt() }.toMutableList()
    }

    fun checkValidNumber(number: String) {
        //사용자의 입력이 3자리인지 확인
        if (number.length != 3) {
            throw IllegalArgumentException("3자리의 입력이 아닙니다.")
        }

        //사용자의 입력이 숫자인지 확인
        for (i in 0..<number.length) {
            val asciiCode = number[i].code

            if (asciiCode !in 48..57) {
                throw IllegalArgumentException("사용자의 입력이 숫자가 아닙니다.")
            }
        }

        //사용자의 입력된 숫자에 중복된 숫자가 있는지 확인
        if (number.toList().distinct().size != 3) {
            throw IllegalArgumentException("사용자의 입력 중 중복된 숫자가 존재합니다.")
        }

        //사용자의 입력 숫자의 각 자리수가 1부터 9까지인지 확인
        number.forEach { num ->
            val iNum = num.digitToInt()

            if (iNum !in 1..9) {
                throw IllegalArgumentException("사용자의 입력이 1부터 9까지의 숫자가 아닙니다.")
            }
        }
    }

    fun countStrikeAndBall(computer: MutableList<Int>, user: MutableList<Int>): Pair<Int, Int> {
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

    fun printGameEndMessage() {
        println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
        println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")
    }

    fun decideGame() {
        val userInput = Console.readLine()
        checkValidInput(userInput)

        if (userInput == "1") {
            gameFlag = true
            gameStart()
        }
    }

    fun checkValidInput(input: String) {
        if (input == "1" || input == "2") {
            return
        }

        throw IllegalArgumentException("입력이 1 또는 2가 아닙니다.")
    }

    fun gameStart() {
        val computerNumber = setComputerNumber()
        println(computerNumber)

        while (gameFlag) {
            val userNumber = inputUserNumber()
            compareAndPrintHint(computerNumber, userNumber)

            if (!gameFlag) {
                printGameEndMessage()
                decideGame()
            }
        }
    }
}