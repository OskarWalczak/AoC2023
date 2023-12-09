fun main() {
    fun createPrediction(sequence: List<Int>): Int{
        val subSequences: MutableList<MutableList<Int>> = ArrayList()
        subSequences.add(sequence.toMutableList())

        var counter = 0

        while(subSequences[counter].any { it != 0 }){

            val newSequence: MutableList<Int> = ArrayList()
            for(i in 0 until subSequences[counter].lastIndex){
                newSequence.add(subSequences[counter][i+1] - subSequences[counter][i])
            }

            if(newSequence.size != 0)
                subSequences.add(newSequence)

            counter++
        }

        subSequences[counter].add(0)

        for(i in subSequences.lastIndex - 1 downTo 0){
            subSequences[i].add(subSequences[i].last() + subSequences[i+1].last())
        }


        return subSequences[0].last()
    }

    fun createBackwardPrediction(sequence: List<Int>): Int{
        val subSequences: MutableList<MutableList<Int>> = ArrayList()
        subSequences.add(sequence.toMutableList())

        var counter = 0

        while(subSequences[counter].any { it != 0 }){

            val newSequence: MutableList<Int> = ArrayList()
            for(i in 0 until subSequences[counter].lastIndex){
                newSequence.add(subSequences[counter][i+1] - subSequences[counter][i])
            }

            if(newSequence.size != 0)
                subSequences.add(newSequence)

            counter++
        }

        subSequences[counter].add(0, 0)

        for(i in subSequences.lastIndex - 1 downTo 0){
            subSequences[i].add(0, subSequences[i][0] - subSequences[i+1][0])
        }


        return subSequences[0][0]
    }

    fun part1(input: List<String>): Int {
        var sumOfPredictions = 0

        input.forEach { line ->
            sumOfPredictions += createPrediction(line.split(' ').map { it.toInt() })
        }


        return sumOfPredictions
    }

    fun part2(input: List<String>): Int {
        var sumOfPredictions = 0

        input.forEach { line ->
            sumOfPredictions += createBackwardPrediction(line.split(' ').map { it.toInt() })
        }


        return sumOfPredictions
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
