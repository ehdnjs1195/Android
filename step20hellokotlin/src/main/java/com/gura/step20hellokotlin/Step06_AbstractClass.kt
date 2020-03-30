package com.gura.step20hellokotlin

// 추상 클래스       => 추상 클래스 , 메소드는 open이 포함되있는 것이다.
abstract class Weapon {
    fun move() {
        println("이동 합니다.")
    }

    // 추상 함수( 모양만 정의된 함수)
    abstract fun attack()
}

//추상 클래스를 상속 받은 클래스
class MyWeapon : Weapon() {
    //추상 함수 오버라이드
    override fun attack() {
        println("지상 공격을 해요")
    }

}

fun main() {
    var w1 = MyWeapon()
    w1.move()
    w1.attack()

    //annonymous inner type
    var w2 = object : Weapon() {    //w2 : Weapon() 이라고 타입을 명시해도 되지만 추론되기 때문에 생략가능하다.
        override fun attack() {
            println("공중을 공격해요")
        }
    }
    w2.move()
    w2.attack()
}