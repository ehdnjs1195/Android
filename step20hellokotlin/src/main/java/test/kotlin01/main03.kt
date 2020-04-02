package test.kotlin01

class Friend {
    //property
    var num: Int = 0
    var name: String? = null
        set(value){ //name 에 대한 setter 를 제공한 것과 같다.
            field = value + "님"
        }
    var phone: String? = null
        get(){  // getter 사용.
            return field ?: "전화번호 없음"   //null이 아니면 field , null이면 "전화번호 없음" 이 리턴된다.
        }
    fun showInfo(){
        println("num: ${num} name: ${name} addr: ${phone}")
    }
}

fun main() {
    val f1 = Friend()
    f1.name = "김구라"
    f1.showInfo()
}