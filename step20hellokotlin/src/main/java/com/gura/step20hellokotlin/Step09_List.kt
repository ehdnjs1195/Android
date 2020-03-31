package com.gura.step20hellokotlin

//data class 정의하기 (DTO 와 유사하다)  => num, name에 대한 필드, setter, getter가 만들어진다.
data class Member(var num: Int, var name: String)

fun main() {
    //수정 불가능한 List => listOf 는 수정불가능한 고정배열.
    var nums: List<Int> = listOf(10, 20, 30, 40, 50)    //listOf 는 return 타입이 List이고, 안에 들어있는 내용을 가지고 추론이 가능하다. => 앞의 List<Int> 생략 가능.
    var names: List<String> = listOf("kim", "lee", "park")

    //data class 로 객체를 생성하고
    var mem1 = Member(1, "김구라")
    var mem2 = Member(2, "해골")
    var mem3 = Member(3, "원숭이")
    // 배열에 참조값을 담아놓기
    var members = listOf(mem1, mem2, mem3)  // members: List<Member>

    var a = members[0].num  // 1
    var b = members[0].name // "김구라"
    // 둘이 같다
    var c = members.get(0).num  // 1
    var d = members.get(0).name // "김구라"

    // members[0] = Member(4,"주뎅이")  //배열 자체의 데이터는 수정이 불가하다.(immutable List 이기 때문에)
    members[0].name = "이정호"          //배열 안에있는 객체의 필드를 수정하는 것은 가능하다. 단, var 로 객체의 필드가 선언되어 있다면.
}