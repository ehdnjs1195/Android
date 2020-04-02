package test.kotlin01

fun main() {
    Util.download()   //Util 에 . 을 찍어보면 객체를 생성하지 않고도 필드나 메소드를 사용할 수 있는것을 확인할 수 있다. 마치 java에서 static 필드나 메소드를 사용하듯이.
    Util.upload()
    println("Util version: ${Util.version}")
    println("Util author: ${Util.author}")
}