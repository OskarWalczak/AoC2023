import java.util.stream.Collectors

fun main() {

    fun translate(input: List<String>, name: String, value: Long): Long {
        val headLineIndex = input.indexOf("$name map:")
        var emptyIndex = headLineIndex
        while(emptyIndex < input.size && input[emptyIndex].isNotEmpty()){
            emptyIndex++
        }

        var result = value
        var resultGot = false
        input.subList(headLineIndex + 1, emptyIndex).forEach { l ->
            if(!resultGot) {
                val line = l.split(' ')
                val destinationRangeStart = line[0].toLong()
                val sourceRangeStart = line[1].toLong()
                val rangeLength = line[2].toLong()

                if (value in sourceRangeStart..sourceRangeStart + rangeLength) {
                    val diff = value - sourceRangeStart
                    result = destinationRangeStart + diff
                    resultGot = true
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Long {
        val seeds: MutableList<Long> = ArrayList()

        val line0 = input[0].split(' ')
        for(i: Int in 1 until line0.size){
            seeds.add(line0[i].toLong())
        }

        val seedToLocation: MutableMap<Long, Long> = HashMap()
        seeds.forEach { seed ->
            val soil = translate(input, "seed-to-soil", seed)
            val fertilizer = translate(input, "soil-to-fertilizer", soil)
            val water = translate(input, "fertilizer-to-water", fertilizer)
            val light = translate(input, "water-to-light", water)
            val temperature = translate(input, "light-to-temperature", light)
            val humidity = translate(input, "temperature-to-humidity", temperature)
            val location = translate(input, "humidity-to-location", humidity)

            seedToLocation[seed] = location
        }

        return seedToLocation.values.min()
    }

    fun part2(input: List<String>): Long {
        var seedString = input[0].split(' ')
        seedString = seedString.subList(1, seedString.size)

        var index = 0

        println("number of seeds: ${seedString.size/2}")

        var minLocation: Long? = null

        while(index + 1 < seedString.size){
            val rangeStart = seedString[index].toLong()
            val rangeLength = seedString[index+1].toLong()

            for(s in rangeStart .. rangeStart + rangeLength){
                val soil = translate(input, "seed-to-soil", s)
                val fertilizer = translate(input, "soil-to-fertilizer", soil)
                val water = translate(input, "fertilizer-to-water", fertilizer)
                val light = translate(input, "water-to-light", water)
                val temperature = translate(input, "light-to-temperature", light)
                val humidity = translate(input, "temperature-to-humidity", temperature)
                val location = translate(input, "humidity-to-location", humidity)

                if(minLocation == null || location < minLocation){
                    minLocation = location
                }
            }

            println("seed: $index")
            index+=2
        }

        return minLocation!!
    }

    var seedDoneCounter = 0

    fun seedRangeToLocation(input: List<String>, seedRange: Pair<Long, Long>): Long {
        println("range size: ${seedRange.second}")
        var minLocation: Long? = null
        for(s in seedRange.first .. seedRange.first + seedRange.second){
            val soil = translate(input, "seed-to-soil", s)
            val fertilizer = translate(input, "soil-to-fertilizer", soil)
            val water = translate(input, "fertilizer-to-water", fertilizer)
            val light = translate(input, "water-to-light", water)
            val temperature = translate(input, "light-to-temperature", light)
            val humidity = translate(input, "temperature-to-humidity", temperature)
            val location = translate(input, "humidity-to-location", humidity)

            if(minLocation == null || location < minLocation!!){
                minLocation = location
            }
        }

        seedDoneCounter++
        println("seeds done: $seedDoneCounter")
        return minLocation!!
    }

    fun part2parallel(input: List<String>): Long {
        var seedString = input[0].split(' ')
        seedString = seedString.subList(1, seedString.size)

        var index = 0

        println("number of seeds: ${seedString.size/2}")

        val seedRanges: MutableList<Pair<Long, Long>> = ArrayList()

        while(index + 1 < seedString.size){
            val rangeStart = seedString[index].toLong()
            val rangeLength = seedString[index+1].toLong()

            seedRanges.add(Pair(rangeStart, rangeLength))

            index+=2
        }

        val locations: List<Long> = seedRanges.parallelStream()
            .map { pair -> seedRangeToLocation(input, pair) }.collect(Collectors.toList())

        seedDoneCounter = 0
        return locations.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())
    check(part2parallel(testInput) == 46.toLong())
    println("==========")

    val input = readInput("Day05")
    part1(input).println()
    part2parallel(input).println()
}
