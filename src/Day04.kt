fun main() {
    fun part1(input: List<String>): Int {
        var sumOfPoints = 0

        input.forEach { line ->
            var linePoints = 0

            val colonIndex = line.indexOf(':')
            val pipeIndex = line.indexOf('|')
            val winningNumbers = line.substring(colonIndex + 1 until pipeIndex).trim().split(' ')
            val myNumbers = line.substring(pipeIndex + 1).trim().split(' ')

            for(num: String in myNumbers){
                if(winningNumbers.contains(num) && num.toIntOrNull() != null){
                    if(linePoints == 0)
                        linePoints = 1
                    else
                        linePoints *= 2
                }
            }

            sumOfPoints += linePoints
        }

        return sumOfPoints
    }

    fun part2(input: List<String>): Int {

        val cardQuantity: MutableMap<Int, Int> = HashMap()
        for(i: Int in 1 .. input.size){
            cardQuantity[i] = 1
        }

        input.forEach { line ->
            var linePoints = 0

            val colonIndex = line.indexOf(':')
            val pipeIndex = line.indexOf('|')
            val cardNumber = line.substring(0 until colonIndex).split(' ').last().toInt()
            val winningNumbers = line.substring(colonIndex + 1 until pipeIndex).trim().split(' ')
            val myNumbers = line.substring(pipeIndex + 1).trim().split(' ')

            for(num: String in myNumbers){
                if(winningNumbers.contains(num) && num.toIntOrNull() != null){
                    linePoints++
                }
            }

            for(i: Int in cardNumber + 1 .. cardNumber + linePoints){
                cardQuantity[i] = cardQuantity[i]!! + cardQuantity[cardNumber]!!
            }

        }

        var numberOfCards = 0

        for(num in cardQuantity.values)
            numberOfCards += num

        return numberOfCards
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
