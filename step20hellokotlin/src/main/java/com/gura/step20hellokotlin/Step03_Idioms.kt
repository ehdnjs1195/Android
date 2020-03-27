package com.gura.step20hellokotlin

//MemberDto 클래스 정의하기
data class MemberDto(var num: Int, var name: String, var addr: String)

fun foo(num: Int = 0, name: String = "이름") {    //Default값을 줄 수 있다.
    println("num: $num name: $name")
}

fun main() {

    var sql = "SELECT *" +
            " FROM member" +
            " WHERE num = 1"
    // 아래처럼 핵 편하게 쓸 수 있다!!
    var sql2 = """
        SELECT *
        FROM member
        WHERE num = 1
    """.trimIndent()    //trimIndent() 가 왼쪽의 들여쓰기를 없애준다.
    println(sql2)


    //변경불가(immutable) 한 Map /tip. 제너릭타입도 생략이 가능하다.(추론)   => 변경가능 여부가 있는 이유: 변경 불가능한 map이나 list가 성능이 더 좋다. 더 빠르다.
    var info = mapOf<String, Any>("num" to 1, "name" to "김구라", "isMan" to true)
    //Map 의 데이터 참조
    var myNum:Int = info["num"] as Int              // as 연산자를 이용해서 casting
    var myName:String = info["name"] as String
    var myIsMan:Boolean = info["isMan"] as Boolean


    //숫자 배열
    var nums = listOf(-20, -10, 0, 10, 20)

    for(i in 0 until nums.size){
        println("$i 번방 : ${nums[i]}")
    }
    // item 중에서 0 보다 큰 값만 추려서 새로운 배열 얻어내기
//    var result = nums.filter { gura -> gura > 0 }  => 여기서 gura로 쓸 수 있는것은 변수로 아무거나 지정해 줄수 있다.
    var result = nums.filter { it > 0 } //위와 같다. it은 예약어이다.
    println(nums.toString())
    println(result.toString())

    foo(1, "김구라")
    foo()
    foo(2)
    foo(name = "해골")
    foo(name = "원숭이", num = 3)

    //MemberDto 에 한명의 회원 정보를 저장
    var mem1 = MemberDto(1, "김구라", "노량진")
    //내부적으로 getter 메소드가 사용됨
    var num = mem1.num
    var name = mem1.name
    var addr = mem1.addr
    //내부적으로 setter 메소드가 사용됨 (단, Dto 클래스에서 변수를 선언할 때 val로 하게 되면 setter를 사용할 수 없다. => primary key를 사용할 때)
    mem1.num = 2
    mem1.name = "이정호"
    mem1.addr = "독산동"

    // MemberDto 객체를 Any type 변수에 담기 (Any 는 어떤 타입이든 담을 수 있다.)
    var whoami: Any = mem1  // mem1 자리에 String이나 Int 등 다른 타입을 넣고 테스트 해보기
    when(whoami){
        is String -> println("String type 이네?")
        is MemberDto -> println("MemberDto type 이네?")
        else -> println("무슨 type 인지 모르겠다 이게 대체 뭐람?!")
    }
}