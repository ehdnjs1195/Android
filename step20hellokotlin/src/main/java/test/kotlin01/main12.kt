package test.kotlin01

fun main(){
    // key : Int type, value : String type
    var map1 = mutableMapOf<Int, String>()

    for(num in 0..255){
        //num 에 해당하는 이진수의 문자
        var bin = Integer.toBinaryString(num)
        //map 에 저장하기
        map1.put(num, bin) // xx 에 해당하는 이진수가 담긴다
    }

    for((key, value) in map1){
        println("정수 ${key} 는 이진수 ${value} 입니다.")
    }

    //println(map1.get(100))
}

//알아서 커밋하세유~