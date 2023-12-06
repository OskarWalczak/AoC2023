
fun main() {

    fun part1(input: List<String>): Int {
        val races: MutableMap<Int, Int> = HashMap()

        val times = input[0].split(' ').filter { s -> s.toIntOrNull() != null }.map { s -> s.toInt() }
        val distances = input[1].split(' ').filter { s -> s.toIntOrNull() != null }.map { s -> s.toInt() }

        for(i: Int in times.indices){
            races[times[i]] = distances[i]
        }

        var result = 1
        for(race in races){
            var waysToWin = 0
            for(i: Int in 0 .. race.key){
                val distance = -1 * i * i + race.key * i
                if(distance > race.value)
                    waysToWin++
            }
            result *= waysToWin
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val recordTime = input[0].split(' ').filter { s -> s.toIntOrNull() != null }.joinToString("").toLong()
        val recordDistance = input[1].split(' ').filter { s -> s.toIntOrNull() != null }.joinToString("").toLong()

        var waysToWin = 0L
        for(i: Long in 0 .. recordTime){
            val distance = -1L * i * i + recordTime * i
            if(distance > recordDistance)
                waysToWin++
        }

        return waysToWin
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
