package test.kotlin01

//실행순서가 시작되는 main 함수
fun main() {
    // kotlin 에서 java class 사용 가능?
    val mem1 = MemberDto()
    // mem1 에 . 을 찍어보면 (from getName() / setName()) 등 이렇게 나오는 것을 확인할 수 있다. => . 을 찍어서 사용할 수 있다.
    // java class 를 import 해서 setter 메소드 사용하기
    mem1.num = 1
    mem1.name = "김구라"
    mem1.addr = "노량진"
    // java class 를 import 해서 getter 메소드 사용하기
    var a = mem1.num
    var b = mem1.name
    var c = mem1.addr

    val mem2 = MemberDto(2,"해골","행신동")


}