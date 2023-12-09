fun main() {

    fun part1(input: List<String>): Int {

        val nodes: MutableMap<String, Pair<String, String>> = HashMap()

        for(i in 2 until input.size){
            val line = input[i]
            val lineInfo = line.split(' ')
            val startingNode = lineInfo[0].trim()
            val leftChild = lineInfo[2].removePrefix("(").removeSuffix(",")
            val rightChild = lineInfo[3].removeSuffix(")")

            nodes[startingNode] = Pair(leftChild, rightChild)
        }

        val directions = input[0]

        var currentNode = "AAA"
        var stepCounter = 0

        while(currentNode != "ZZZ"){
            if(directions[stepCounter.mod(directions.length)] == 'L'){
                currentNode = nodes[currentNode]!!.first
            }
            else{
                currentNode = nodes[currentNode]!!.second
            }
            stepCounter++
        }

        return stepCounter
    }

    fun part2(input: List<String>): Long {
        val nodes: MutableMap<String, Pair<String, String>> = HashMap()

        for(i in 2 until input.size){
            val line = input[i]
            val lineInfo = line.split(' ')
            val startingNode = lineInfo[0].trim()
            val leftChild = lineInfo[2].removePrefix("(").removeSuffix(",")
            val rightChild = lineInfo[3].removeSuffix(")")

            nodes[startingNode] = Pair(leftChild, rightChild)
        }

        val directions = input[0]

        var currentNodes: MutableList<String> = ArrayList()
        var stepCounter = 0L

        currentNodes = nodes.keys.filter { it.endsWith('A') }.toMutableList()

        while(currentNodes.any { !it.endsWith('Z') }){
            val newCurrentNodes: MutableList<String> = ArrayList(currentNodes.size)
            for(node in currentNodes){
                if(directions[stepCounter.mod(directions.length)] == 'L'){
                    if(!newCurrentNodes.contains(nodes[node]!!.first))
                        newCurrentNodes.add(nodes[node]!!.first)
                }
                else{
                    if(!newCurrentNodes.contains(nodes[node]!!.second))
                        newCurrentNodes.add(nodes[node]!!.second)
                }
            }
            currentNodes = newCurrentNodes

            stepCounter++
        }


        return stepCounter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 6)
    check(part2(testInput) == 6L)
    println("check done")

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
