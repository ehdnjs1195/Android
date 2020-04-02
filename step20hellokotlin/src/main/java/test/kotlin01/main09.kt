package test.kotlin01

import java.lang.NumberFormatException

fun main() {
    var str = "1000"
    var str2 = "천"

    //try 식
    var result = try{
        Integer.parseInt(str2)      //숫자가 있으면(str을 넣으면) 여기서의 결과가 result에 대입되고
    } catch(ne : NumberFormatException){
        10                          //Exception 이 발생하면 10 이 result에 대입된다.
    }

    println(result + 10)
}