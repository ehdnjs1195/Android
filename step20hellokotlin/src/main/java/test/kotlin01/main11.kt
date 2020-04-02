package test.kotlin01

fun main(){
    val map1 = mutableMapOf<String, String>()
    map1.put("house", "집")
    map1.put("phone", "전화기")
    map1.put("suger", "설탕")

    for((key, value) in map1){
        println("map1 ${key}:${value}")
    }

    println("----")

    for(key in map1.keys){ //key 의 배열을 얻어내서 사용할 수 도 있음
        println("map1 ${key}:${map1.get(key)}") // key 값을 이용해서 get 할 수 있다
    }
}

//안도원바보멍청이똥개해삼말미잘