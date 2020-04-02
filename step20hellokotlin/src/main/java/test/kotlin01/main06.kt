package test.kotlin01

enum class Color(val r:Int, val g:Int, val b:Int){  //enum 에다가 생성자를 정의하면
    RED(255,0,0),       //위의 파라미터는 상수값이지만
    GREEN(0,255,0),
    BLUE(0, 0, 255);    //함수와 구분하기 위해 ; 필ㅇ함

    //문자열을 리턴하는 함수
    fun toHex(): String {
        var result: String  = Integer.toHexString(r) +  //정수를 16진수로 나타낸다
                Integer.toHexString(g) +
                Integer.toHexString(b);
        return result
    }
}

fun main() {
    var c1 = Color.RED
    var c2 = Color.GREEN
    var c3 = Color.BLUE

    println("c1.r : ${c1.r} c1.g : ${c1.g} c1.b: ${c1.b}")
    println(c1.toHex())
}