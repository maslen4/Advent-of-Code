import java.io.File


fun readFile1(fileName: String, map: Map<String, Int>): Int {
    var result = 0
    File(fileName).forEachLine {
        val line = splitLineIntoGameNumberAndBalls(it);
        val gameNumber = line[0].split(" ")[1]
        val balls = line[1]
        val rounds = splitBallsIntoRounds(balls)
        var isValid = true
        rounds.forEach { round ->
            splitRoundIntoColors(round).forEach { color ->
                val c = splitColorIntoNumberAndColor(removeFirstSpace(color));
                if (map.get(c[1])!! < c[0].toInt()) {
                    isValid = false
                }
            }
        }
        result = if (isValid) result + gameNumber.toInt() else result
    }
    return result
}

fun readFile2(fileName: String): Int {
    var result = 0
    File(fileName).forEachLine {
        val line = splitLineIntoGameNumberAndBalls(it);
        val balls = line[1]
        val rounds = splitBallsIntoRounds(balls)
        val map = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)
        rounds.forEach { round ->
            splitRoundIntoColors(round).forEach { color ->
                val c = splitColorIntoNumberAndColor(removeFirstSpace(color));
                if (map.get(c[1])!! < c[0].toInt()) {
                    map.set(c[1], c[0].toInt())
                }
            }
        }
        var power = 1
        for (value in map.values) {power *= value}
        result += power
    }
    return result
}

fun splitLineIntoGameNumberAndBalls(line: String): List<String> = line.split(":")

fun splitBallsIntoRounds(line: String): List<String> = line.split(";")

fun splitRoundIntoColors(line: String): List<String> = line.split(",")

fun splitColorIntoNumberAndColor(line: String): List<String> = line.split(" ")

fun removeFirstSpace(line: String): String = line.removePrefix(" ")



fun main(args: Array<String>) {
    val map: Map<String, Int> = mutableMapOf("red" to 12, "blue" to 14, "green" to 13)
    println(readFile1("input.txt", map))
    println(readFile2("input.txt"))
}