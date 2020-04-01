package test.kotlin01



fun main() {
    // import 된 java 클래스의 클래스 type 참조하기   (자바에서는 xxxx.class 이렇게 참조함)    =>Intent 객체를 이용해서 액티비티를 활성화 시킬때 사용함.
    val a: Class<MemberDto> = MemberDto::class.java
    val b = MemberDto::class.java

}