import java.io.File



fun extractNumberFromLine(line: String): Int{
    var number = ""
    for(i in line.indices){
        if(line[i].isDigit()){
            number += line[i].toString()
            break
        }
    }
    for(i in line.indices){
        if(line[line.length-i-1].isDigit()){
            number += line[line.length-i-1].toString()
            break
        }
    }
    println(number)
    return number.toInt()
}

fun compareChar(char: Char, array: ArrayList<String>): MutableList<String>{
    val result = mutableListOf<String>()
    for (i in array){
        if (i[0] == char){
            result.add(i)
        }
    }
    return result
}

fun compareNumber(string: String, index: Int, number: String): Boolean{
    for (i in index until index + number.length){
        if (i >= string.length || string[i] != number[i-index]){
            return false
        }
    }
    return true
}

fun extractNumberFromLine2(line: String): Int{
    val numbers = arrayListOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    var number = ""
    var first = false
    var second = false
    for(i in line.indices){
        if (first){
            break
        }
        val c = line[i]
        if(c.isDigit()){
            number += c.toString()
            break
        }else{
            val possibleNumbers = compareChar(c, numbers)
            for (n in possibleNumbers){
                if (compareNumber(line, i, n)){
                    number += numbers.indexOf(n)+1
                    first = true
                    break
                }
            }
        }
    }
    for(i in line.indices){
        if (second){
            break
        }
        val c = line[line.length-i-1]
        if(c.isDigit()){
            number += c.toString()
            break
        }else{
            val possibleNumbers = compareChar(c, numbers)
            for (n in possibleNumbers){
                if (compareNumber(line, line.length-i-1, n)){
                    number += numbers.indexOf(n)+1
                    second = true
                    break
                }
            }
        }
    }
    println(number)
    return number.toInt()
}

fun readFile(fileName: String): Int {
    var number = 0
    File(fileName).forEachLine { number += extractNumberFromLine2(it) }
    return number
}

fun main(args: Array<String>) {
    println(readFile("input.txt"))
}