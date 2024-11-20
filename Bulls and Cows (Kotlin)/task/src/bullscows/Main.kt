package bullscows

const val RANGE = "0123456789abcdefghijklmnopqrstuvwxyz"

fun main() {
    println("Input the length of the secret code:")
    val length = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("Error: input is not an integer")
        return
    }
    require(length in 1..36) {
        println("Error: input is not in 1..36")
        return
    }

    println("Input the number of possible symbols")
    val possibleSymbolsNum = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println("Error: input is not an integer")
        return
    }
    require(possibleSymbolsNum in length..36) {
        println("Error: input is not in length..36")
        return
    }

    val rangeList = RANGE.toList().subList(0, possibleSymbolsNum)
    val secretCode = generateSecretCode(length, rangeList)
    printPrepared(length, possibleSymbolsNum)

    println("Okay, let's start a game!")
    var turn = 1
    while (true) {
        var bulls = 0
        var cows = 0
        println("Turn $turn:")
        val input = readln()
        require(input.length == length && input.all { it in rangeList }) {
            println("Error: User input has incorrect length or symbol range")
            return
        }
        
        input.forEachIndexed { index, ch ->
            if (secretCode.contains(ch)) {
                if (secretCode[index] == ch) bulls++
                else cows++
            }
        }
        when {
            bulls == length -> {
                println("Grade: $bulls bulls.")
                println("Congratulations! You guessed the secret code.")
                break
            }
            bulls == 0 && cows ==0 -> println("Grade: None.")
            bulls == 0 -> println("Grade: $cows cow(s).")
            cows == 0 -> println("Grade: $bulls bull(s).")
            else -> println("Grade: $bulls bull(s) and $cows cow(s).")
        }
        turn++
    }
}

fun generateSecretCode(length: Int, rangeList: List<Char>): String {
    val symbols = mutableSetOf<Char>()
    while (symbols.size < length) symbols.add(rangeList.random())
    return symbols.joinToString("")
}

fun printPrepared(length: Int, possibleSymbolsNum: Int) {
    print("The secret is prepared: ")
    repeat(length) {
        print("*")
    }
    when (possibleSymbolsNum) {
        in 1..10 -> println(" (0-${RANGE[possibleSymbolsNum - 1]}).")
        in 11..36 -> println(" (0-9, a-${RANGE[possibleSymbolsNum - 1]}).")
    }
}