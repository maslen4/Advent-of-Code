import java.io.File

fun readFile(fileName: String){
    var numbers = mutableListOf<String>()
    var result = 0

    File(fileName).forEachLine {
        println(it)
        numbers.add(it)
    }

    for (line in numbers.indices){
        result += extractNumbers(line, numbers)

    }
    println("Gear ratios: " + extractStars(numbers))
    println("Engine parts: " + result)
}

fun extractNumbers(line: Int, allNumbers: List<String>): Int {
    val numbers = mutableListOf<Int>()

    var pointer = 0
    while (pointer < allNumbers[line].length){
        if (allNumbers[line][pointer].isDigit()){
            var number = ""
            var i = pointer
            while (i<allNumbers[line].length && allNumbers[line][i].isDigit()){
                number += allNumbers[line][i]
                i++
            }
            if (isValid(line, pointer, i-1, allNumbers)){
                numbers.add(number.toInt())
            }
            pointer = i
        }
        pointer++
    }
    return numbers.sum()
}

fun extractStars(numbers: List<String>): Int{
    var result = 0
    for (line in 0 until  numbers.size){
        for (char in 0 until  numbers[0].length){
            if (numbers[line][char] == '*'){
                result += gearRatio(line, char, numbers)
            }
        }
    }
    return result
}

fun isSpecial(c: Char): Boolean{
    return !c.isDigit() && c!='.'
}


fun gearRatio(line: Int, index: Int, numbers: List<String>): Int{
    var parts = mutableListOf<Int>()

    //left
    if (index>0 && numbers[line][index-1].isDigit()){
        parts.add(getNumber(line, index-1, numbers))
    }

    //right
    if (index+1<numbers[line].length && numbers[line][index+1].isDigit()){
        parts.add(getNumber(line, index+1, numbers))
    }

    //top
    if (line>0 && numbers[line-1][index].isDigit()){
        parts.add(getNumber(line-1, index, numbers))
    }else{
        //top left
        if (index>0 && numbers[line-1][index-1].isDigit()){
            parts.add(getNumber(line-1, index-1, numbers))
        }
        //top right
        if (index+1<numbers[line].length && numbers[line-1][index+1].isDigit()){
            parts.add(getNumber(line-1, index+1, numbers))
        }
    }

    //bottom
    if (line+1<numbers.size && numbers[line+1][index].isDigit()){
        parts.add(getNumber(line+1, index, numbers))
    }else{
        //bottom left
        if (index>0 && numbers[line+1][index-1].isDigit()){
            parts.add(getNumber(line+1, index-1, numbers))
        }
        //bottom right
        if (index+1<numbers[line].length && numbers[line+1][index+1].isDigit()){
            parts.add(getNumber(line+1, index+1, numbers))
        }
    }

    if (parts.size == 2){
        return parts[0]*parts[1]
    }
    return 0
}

fun getNumber(line: Int, index: Int, numbers: List<String>): Int{
    var number = ""

    var start = index
    while(start-1 >= 0 && numbers[line][start-1].isDigit()){
        start--
    }

    var char = start
    while (char<numbers[line].length && numbers[line][char].isDigit()){
        number += numbers[line][char]
        char++
    }
    return number.toInt()
}

fun isValid(line: Int, start: Int, end: Int, numbers: List<String>): Boolean{
    for (i in start .. end){
        if ((i>0 && isSpecial(numbers[line][i-1])) ||
            (((i+1)< numbers[line].length && isSpecial(numbers[line][i+1]))) ||
            (line>0 && isSpecial(numbers[line-1][i])) ||
            (line+1 < numbers[line].length && isSpecial(numbers[line+1][i]))){
            return true
        }
    }
    if (line>0){
        if(start>0 && isSpecial(numbers[line-1][start-1])){
            return true
        }
        if(end+1 < numbers[line].length && isSpecial(numbers[line-1][end+1])){
            return true
        }
    }
    if (line+1 < numbers.size){
        if(start>0 && isSpecial(numbers[line+1][start-1])){
            return true
        }
        if(end+1 < numbers[line].length && isSpecial(numbers[line+1][end+1])){
            return true
        }
    }
    return false
}

fun main(args: Array<String>) {
    readFile("input.txt")
}