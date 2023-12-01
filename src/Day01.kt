fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach { line ->
            var firstNum: Int? = null
            var lastNum: Int? = null
            line.forEach { c ->
                if(c.isDigit()){
                    if(firstNum == null) {
                        firstNum = c.digitToInt()
                        lastNum = c.digitToInt()
                    }
                    else
                        lastNum = c.digitToInt()
                }
            }
            val confNum = firstNum!! * 10 + lastNum!!
            sum += confNum
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val numbers = hashMapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four",
                                5 to "five", 6 to "six", 7 to "seven", 8 to "eight", 9 to "nine")
        var sum = 0
        input.forEach { line ->
            var firstNum = 0
            var lastNum = 0
            var firstIndex = 1000
            var lastIndex = -1
            for(i: Int in 0 .. 9){
                val firstIndexDigit = line.indexOf(i.digitToChar())
                val lastIndexDigit = line.lastIndexOf(i.digitToChar())
                if(firstIndexDigit != -1){
                    if(firstIndexDigit < firstIndex){
                        firstIndex = firstIndexDigit
                        firstNum = i
                    }
                    if(lastIndexDigit > lastIndex){
                        lastIndex = lastIndexDigit
                        lastNum = i
                    }
                }

                if(i > 0) {
                    val firstIndexString = line.indexOf(numbers[i]!!)
                    val lastIndexString = line.lastIndexOf(numbers[i]!!)
                    if (firstIndexString != -1) {
                        if (firstIndexString < firstIndex) {
                            firstIndex = firstIndexString
                            firstNum = i
                        }
                        if(lastIndexString > lastIndex){
                            lastIndex = lastIndexString
                            lastNum = i
                        }
                    }
                }
            }
            val confNum = firstNum * 10 + lastNum
            sum += confNum
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
