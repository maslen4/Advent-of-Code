import java.io.File
import kotlin.math.pow

fun readFile1(fileName: String){
    var sum = 0
    File(fileName).forEachLine {
        val sequences = getSequences(it)
        val winning = getNumbersFromSequence(sequences[0])
        val all = getNumbersFromSequence(sequences[1])
        var winningCount = getNumberOfWinningNumbers(winning, all).toDouble()
        if (winningCount>0){
            sum += 2.0.pow(winningCount - 1).toInt()
        }
    }
    println(sum)
}

fun getSequences(line: String): List<String>{
    return line.split(":")[1].split("|")
}

fun getNumbersFromSequence(sequence: String): Set<Int>{
    var numbers = mutableSetOf<Int>()
    sequence.split(" ").forEach{if(it!="")numbers.add(it.toInt())}
    return numbers
}

fun getNumberOfWinningNumbers(winning: Set<Int>, all: Set<Int>): Int{
    var winningNumbers = 0
    winning.forEach{if(all.contains(it)) winningNumbers++}
    return winningNumbers
}

fun getCardNumber(line: String): Int{
    return line.split(":")[0].split(" ")[line.split(":")[0].split(" ").size-1].toInt()
}

fun readFile2(fileName: String){
    var result = 0
    var map = mutableMapOf<Int, Int>()
    File(fileName).forEachLine {
        val cardNumber = getCardNumber(it)
        val n = map[cardNumber]
        val copies = if (n!=null) n+1 else 1
        val sequences = getSequences(it)
        val winning = getNumbersFromSequence(sequences[0])
        val all = getNumbersFromSequence(sequences[1])
        var winningCount = getNumberOfWinningNumbers(winning, all)

        for (i in 1 .. winningCount){
            val n = map[cardNumber+i]
            map[cardNumber+i] = if (n!=null) n+copies else copies
        }
        result += copies
    }
    println(result)
}


fun main(args: Array<String>) {
    readFile1("input.txt")
    readFile2("input.txt")
}