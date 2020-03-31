package com.gura.step20hellokotlin

interface Remocon{
    fun up()
    fun down()
}

//인터페이스를 구현한 클래스
class TvRemocon : Remocon{
    override fun up() {
        println("채널을 올려요")
    }
    override fun down() {
        println("채널을 내려요")
    }
}

//Weapon 클래스를 상속 받고 Remocon 인터페이스를 구현한 클래스      콤마로 구분해서 여러개를 적어줄 수 있다. 클래스는 소괄호가 있고, 인터페이스는 없다(생성자가 없으므로).
class MultiClass : Weapon(), Remocon{
    override fun attack() {
        println("아무거나 공격해요")
    }
    override fun up() {
        println("UP!!")
    }
    override fun down() {
        println("DOWN!!")
    }

}

fun main() {
    var r1 = TvRemocon()
    r1.up()
    r1.down()

    var r2 = object : Remocon{
        override fun up() {
            println("볼륨을 올려요")
        }

        override fun down() {
            println("볼륨을 내려요")
        }

    }
    r2.up()
    r2.down()
    //MultiClass 객체의 참조값을 다양한 type 변수에 담기
    var m1 : MultiClass = MultiClass()
    var m2 : Weapon = MultiClass()
    var m3 : Remocon = MultiClass()
    // 자식 type 참조값을 부모 type 변수에 casting 없이 들어간다.
    var m4 : Weapon = m1
    var m5 : Remocon = m1
    // 부모 type 참조값을 자식 type 변수에 대입할 때는 casting이 필요하다.
    var m6 : MultiClass = m2 as MultiClass
    var m7 : MultiClass = m3 as MultiClass
}