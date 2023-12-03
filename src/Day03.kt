fun main() {

    fun isNumberValid(symbols: Array<BooleanArray>, lineIndex: Int, startIndex: Int, endIndex: Int): Boolean{
        for(i: Int in maxOf(0, lineIndex - 1) .. minOf(symbols.lastIndex, lineIndex + 1)){
            for(j: Int in maxOf(0, startIndex - 1) .. minOf(symbols[i].lastIndex, endIndex + 1)){
                if(symbols[i][j])
                    return true
            }
        }

        return false
    }

    fun part1(input: List<String>): Int {
        var sumOfPartNums = 0

        val symbols = Array(input.size) { BooleanArray(input[0].length) { false } }

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, c ->
                if(c != '.' && !c.isDigit())
                    symbols[lineIndex][charIndex] = true

            }
        }

        input.forEachIndexed { lineIndex, line ->
            var tempNumber = 0
            var tempStart = 0
            var tempEnd = 0
            line.forEachIndexed { charIndex, c ->
                if(c != '.' && c.isDigit()){
                    if(tempNumber == 0){
                        tempStart = charIndex
                    }
                    tempNumber = tempNumber * 10 + c.digitToInt()
                }

                if(c == '.' || !c.isDigit() || charIndex == line.lastIndex) {
                    tempEnd = charIndex - 1

                    if(isNumberValid(symbols, lineIndex, tempStart, tempEnd)){
                        sumOfPartNums += tempNumber
                    }

                    tempNumber = 0
                    tempStart = 0
                    tempEnd = 0
                }
            }
        }

        return sumOfPartNums
    }

    fun addToGearMap(gears: Array<BooleanArray>, lineIndex: Int, startIndex: Int, endIndex: Int, number: Int, gearMap: MutableMap<Pair<Int, Int>, MutableList<Int>>){
        for(i: Int in maxOf(0, lineIndex - 1) .. minOf(gears.lastIndex, lineIndex + 1)){
            for(j: Int in maxOf(0, startIndex - 1) .. minOf(gears[i].lastIndex, endIndex + 1)){
                if(gears[i][j]) {
                    if(!gearMap.containsKey(Pair(i, j)))
                        gearMap[Pair(i, j)] = ArrayList()

                    gearMap[Pair(i, j)]!!.add(number)
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        var sumOfGearRatios = 0

        val gears = Array(input.size) { BooleanArray(input[0].length) { false } }
        val gearMap: MutableMap<Pair<Int, Int>, MutableList<Int>> = HashMap()
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, c ->
                if(c == '*') {
                    gears[lineIndex][charIndex] = true
                    gearMap[Pair(lineIndex, charIndex)] = ArrayList()
                }

            }
        }

        input.forEachIndexed { lineIndex, line ->
            var tempNumber = 0
            var tempStart = 0
            var tempEnd = 0
            line.forEachIndexed { charIndex, c ->
                if(c != '.' && c.isDigit()){
                    if(tempNumber == 0){
                        tempStart = charIndex
                    }
                    tempNumber = tempNumber * 10 + c.digitToInt()
                }

                if(c == '.' || !c.isDigit() || charIndex == line.lastIndex) {
                    tempEnd = charIndex - 1

                    if(tempNumber != 0)
                        addToGearMap(gears, lineIndex, tempStart, tempEnd, tempNumber, gearMap)

                    tempNumber = 0
                    tempStart = 0
                    tempEnd = 0
                }
            }
        }

        for(gear in gearMap.keys){
            if(gearMap[gear]!!.size == 2){
                sumOfGearRatios += gearMap[gear]!![0] * gearMap[gear]!![1]
            }
        }

        return sumOfGearRatios
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
