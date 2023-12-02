import java.util.*

enum class Color {
    RED, GREEN, BLUE
}

fun main() {

    fun part1(input: List<String>): Int {
        val maxCount = hashMapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)
        var gameIdSum = 0

        input.forEach { line ->
            val fullInfo = line.split(':')
            val gameId = fullInfo[0].split(' ')[1].toInt()
            val gamesString = fullInfo[1].trim()
            val draws = gamesString.split(';')

            var isLinePossible = true

            draws.forEach { it ->
                if(isLinePossible) {
                    val dice = it.split(',')

                    dice.forEach { it ->
                        val die = it.trim().split(' ')
                        val number = die[0].toInt()
                        val color = Color.valueOf(die[1].uppercase(Locale.getDefault()))

                        if (number > maxCount[color]!!)
                            isLinePossible = false
                    }
                }
            }

            if(isLinePossible)
                gameIdSum += gameId
        }

        return gameIdSum
    }

    fun part2(input: List<String>): Int {
        var sumOfPowers = 0

        input.forEach { line ->
            val lineMax = hashMapOf(Color.RED to 0, Color.GREEN to 0, Color.BLUE to 0)

            val fullInfo = line.split(':')
            val gameId = fullInfo[0].split(' ')[1].toInt()
            val gamesString = fullInfo[1].trim()
            val draws = gamesString.split(';')

            draws.forEach { it ->
                val dice = it.split(',')

                dice.forEach { it ->
                    val die = it.trim().split(' ')
                    val number = die[0].toInt()
                    val color = Color.valueOf(die[1].uppercase(Locale.getDefault()))

                    if(number > lineMax[color]!!)
                        lineMax[color] = number
                }
            }

            var linePower = 1
            for(num in lineMax.values)
                linePower *= num

            sumOfPowers += linePower

        }
        return sumOfPowers
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
