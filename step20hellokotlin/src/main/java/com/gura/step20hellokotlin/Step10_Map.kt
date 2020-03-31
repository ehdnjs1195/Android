package com.gura.step20hellokotlin

fun main() {
    //수정 불가능한 Map
    var map1:Map<String, Any> = mapOf("num" to 1, "name" to "김구라")
    var map2 = mapOf("num" to 2, "name" to "해골")    //type inferred

    //저장된 값 참조할 때 2가지가 모두 가능
    var a = map1.get("name")
    var b = map1["name"]

    //수정 가능한 Map
    var map3: MutableMap<String, Any> = mutableMapOf()
    var map4 = mutableMapOf<String, Any>()  //type inferred

    map3.put("num", 3)
    map3.put("name", "원숭이")

}