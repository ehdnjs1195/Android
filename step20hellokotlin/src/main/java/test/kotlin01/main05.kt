package test.kotlin01

// 열거형 클래스 (상수값을 관리할 때 사용한다)
enum class MyColor{ //enum 이라는 예약어와 함께 쓰면 번호대로 정렬됨
    RED, BLUE, GREEN

}

fun main() {
    var c1: MyColor = MyColor.RED
    var c2 = MyColor.BLUE
    var c3 = MyColor.GREEN

    var color = MyColor.BLUE

    when(color){
        c1 -> println("c1 과 같아요")
        c2 -> println("c2 와 같아요")
        c3 -> println("c3 와 같아요")
    }

}