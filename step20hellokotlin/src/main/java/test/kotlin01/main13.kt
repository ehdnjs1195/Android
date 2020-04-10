package test.kotlin01

/*
        1. var & val 의 차이
         var 은 들어있는 값을 수정가능하고, val 은 수정 불가능한 변수다.
        2. var num : Int = 999
         수정가능한 Int type num 이라는 변수에 999가 대입된다.
     */
fun main() {
    //3. a() 메소드가 호출되면서 콘솔창에 "대경아 놀자" 가 출력된다.
    fun a() {
        println("대경아 놀자")
    }
    a()

    //4. 콘솔창에 "내이름은 김구라"가 출력된다.
    var a = "김구라"
    println("내이름은 $a")

    //5. 콘솔창에 "aaa"가 출력된다.
    var names = listOf("aaa", "bbb", "ccc")   //String 배열
    println(names[0])

    //6. 콘솔창에 0부터 9까지 순서대로 출력된다.
    for (a in 0..9) { //코틀린 반복문. 0..9 =>range 0부터9 까지 하나씩 빼낸다. listOf(0,1,2,3,4,5,6,7,8,9) 와 같다.
        println(a)
    }

    //7. 콘솔창에 1 부터 9까지 순서대로 출력된다.
    for (a in 1 until 10) { //1부터 10 이전까지
        println(a)
    }

    //8. 콘솔창에 10이 출력된다.
    fun test(num: Int = 0) {  //어떤 값을 전달하려면 준비를 해야지.    num:Int 와 num:Int=0 의 차이는 값을 전달하지 않았을 때 기본값을 정할 수 있다는 것.
        println(num)
    }
    test(10)

    //9. 값을 전달하지 않으면 기본값으로 0과 "기본"이 출력되고, 전달하면 전달된 값으로 10과 "나야나!"가 출력된다.
    fun test(num: Int = 0, a: String = "기본") {
        println(num)
    }
    test()
    test(10, "나야나!")

    //10. a1라는 이름의 변수를 만들고 정수 100을 대입해 보세요.
    //단, a1 는 수정 가능한 변수여야 합니다.
    var a1 = 100
    //11. b 라는 이름의 변수를 만들고 문자열 "안녕" 을 대입해 보세요.
    //단, b 는 수정 불가능한 변수여야 합니다.
    val b = "안녕"

    //12. gura 라는 이름의 함수를 만들어 보세요
    //단, 해당 함수를 호출하면 콘솔창에 "hello" 라는 문자열이 출력되어야 합니다.
    fun gura() {
        println("hello")
    }

    //13. 콘솔창에 5부터 10 까지 순서대로 출력하는 for 문을 구성해 보세요
    for (num in 5..10) {
        println(num)
    }

    //14. 전달하는 두 실수의 곱을 리턴해주는 mul 이라는 이름의 함수를 만들어 보세요.
    fun mul(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    //15. 콘솔창에 "원숭이"가 출력된다.
    var mem = mapOf("num" to 10, "name" to "원숭이")
    println(mem["name"])

    //16. List< 가 > 가 안에 들어갈 타입은 Any 이다.(여러타입이 섞여있을 때, 주의. Object 아님!) (listOf() 안의 타입을 보고 유추, Any, Double, String, Boolean, Object 등)
    var nums: List<Any> = listOf(1, "kim", true)  //콜론 다음에 타입을 적어준다.
    var nums2 = listOf<Any>(1, "kim", true) //같은 모양이다.

}

//17. Phone2 이라는 클래스를 정의해 보세요.
class Phone2 {

}

//18. 생성자의 인자로 수정 가능한 문자열(전화기 이름) 을 전달 받도록 생성자를 정의해 보세요.
class Phone(var name: String) {
    //19. call 이라는 이름의 함수를 클래스 안에 정의해 보세요.
    //20. 만일 외부에서 call 이라는 이름의 함수를 호출하면 "xxx 가 전화를 걸어요" 가 콘솔창에 출력되도록 해보세요.( xxx 는 생성자로 전달된 전화기의 이름)
    fun call() {
        println("$name 가 전화를 걸어요")
    }

}

//21. Phone 클래스로 객체를 생성해서 Phone1이라는 이름의 변수에 담는 코드를 작성해보세요.
var phone1 = Phone("아이폰")
