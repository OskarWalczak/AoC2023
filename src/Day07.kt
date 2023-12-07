import kotlin.math.max

fun main() {

    val cardToNum = hashMapOf( '2' to 1, '3' to 2, '4' to 3, '5' to 4, '6' to 5, '7' to 6,
                                '8' to 7, '9' to 8, 'T' to 9, 'J' to 10, 'Q' to 11, 'K' to 12, 'A' to 13)
    val cardToNum2 = hashMapOf( 'J' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7,
                                '8' to 8, '9' to 9, 'T' to 10, 'Q' to 11, 'K' to 12, 'A' to 13)

    fun determineType(hand: String): Int{
        val distinct = hand.toCharArray().distinct().size

        if(distinct == 1)
            return 7
        if(distinct == 2){
            val sorted = hand.toCharArray().sorted()
            for(i in 1 until sorted.size){
                if(sorted[i] != sorted[0]){
                    if(i == 1 || i == 4)
                        return 6
                    else if(i == 2 || i == 3)
                        return 5
                }
            }
            return -1
        }
        if(distinct == 3){
            val sorted = hand.toCharArray().sorted()
            var longestStreak = 1
            var currStreak = 1
            for(i in 1 until sorted.size){
                if(sorted[i] == sorted[i-1]){
                    currStreak++

                    if(currStreak > longestStreak)
                        longestStreak = currStreak

                }
                else {
                    currStreak = 1
                }

            }
            if(longestStreak == 3)
                return 4
            if(longestStreak == 2)
                return 3

            return -1
        }
        if(distinct == 4)
            return 2
        if(distinct == 5)
            return 1

        return 1
    }

    fun handScore(hand: String): Long {
        return determineType(hand) *   10000000000 +
                cardToNum[hand[0]]!! * 100000000 +
                cardToNum[hand[1]]!! * 1000000 +
                cardToNum[hand[2]]!! * 10000 +
                cardToNum[hand[3]]!! * 100 +
                cardToNum[hand[4]]!!
    }

    fun determineTypeWithJoker(hand: String): Int {
        if(!hand.contains('J') || hand.count { it == 'J' } == 5)
            return determineType(hand)

        val jIndices: MutableList<Int> = ArrayList()
        for(i in hand.indices)
            if(hand[i] == 'J')
                jIndices.add(i)

        val jNum = jIndices.size
        var bestType = 0
        val t = hand.toCharArray()

        for(c1 in cardToNum2.keys){
            t[jIndices[0]] = c1
            if(jNum > 1){
                for(c2 in cardToNum2.keys){
                    t[jIndices[1]] = c2
                    if(jNum > 2){
                        for(c3 in cardToNum2.keys){
                            t[jIndices[2]] = c3
                            if(jNum > 3){
                                for(c4 in cardToNum2.keys){
                                    t[jIndices[3]] = c4
                                    bestType = max(bestType, determineType(String(t)))
                                }
                            }
                            else{
                                bestType = max(bestType, determineType(String(t)))
                            }
                        }
                    }
                    else{
                        bestType = max(bestType, determineType(String(t)))
                    }
                }
            }
            else{
                bestType = max(bestType, determineType(String(t)))
            }
        }

        return bestType
    }

    fun handScore2(hand: String): Long {
        return determineTypeWithJoker(hand) *   10000000000 +
                cardToNum2[hand[0]]!! * 100000000 +
                cardToNum2[hand[1]]!! * 1000000 +
                cardToNum2[hand[2]]!! * 10000 +
                cardToNum2[hand[3]]!! * 100 +
                cardToNum2[hand[4]]!!
    }

    fun part1(input: List<String>): Long {

        val handBid: MutableMap<String, Int> = HashMap()

        input.forEach { line ->
            val info = line.split(' ')
            handBid[info[0]] = info[1].toInt()
        }
        val sortedHands = handBid.keys.sortedBy { handScore(it) }

        var result = 0L

        for(i in sortedHands.indices){
            val hand = sortedHands[i]
            result += handBid[hand]!! * (i+1)
        }

        return result
    }

    fun part2(input: List<String>): Long {
        val handBid: MutableMap<String, Int> = HashMap()

        input.forEach { line ->
            val info = line.split(' ')
            handBid[info[0]] = info[1].toInt()
        }

        val sortedHands = handBid.keys.sortedBy { handScore2(it) }

        var result = 0L

        for(i in sortedHands.indices){
            val hand = sortedHands[i]
            result += handBid[hand]!! * (i+1)
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
