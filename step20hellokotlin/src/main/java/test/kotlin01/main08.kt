package test.kotlin01

//정수 리스트
val nums= listOf<Int>(10, 5, 100, 65, 4, 9)

fun main() {
    println("first : ${nums.first()}")
    println("last : ${nums.last()}")
    println("max : ${nums.max()}")
    println("min : ${nums.min()}")

    var nums2 = mutableListOf<Int>()
    for(num in 1..10){  //1에서 10까지의 배열이라고 생각해도 된다.
        nums2.add(num)
    }
    println(nums2)
    nums2.shuffle()
    println(nums2)
}