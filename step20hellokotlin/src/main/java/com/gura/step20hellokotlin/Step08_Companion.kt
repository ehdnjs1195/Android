package com.gura.step20hellokotlin

class MyUtil{
    //클래스명에 . 찍어서 사용할 수 있는 자원 만들기
    companion object{   //static 으로 만들기
        val color = "RED"
        fun write(){
            println(color + "색으로 필기를 해요!")
        }
    }
}

// java 에서 싱글톤으로 클래스를 정의하듯이 비슷하게 흉내내보면 복잡한 모습이다.
class MyDao private constructor(){
    companion object{
        private var dao:MyDao? = null
        fun getInstance(): MyDao?{
            if(dao == null){
                dao = MyDao()
            }
            return dao
        }
    }
    //일반 메소드
    fun insert(){}
    fun update(){}
}
// single ton 으로 사용할 수 있는 Dao. (companion object를 이용해서 static 메소드를 통한 싱글톤 클래스 정의. 위와 다르게 깔끔하게 만들 수 있다.)
class YourDao private constructor(){    //외부에서 객체 생성하지 못하도록 private으로 막아둔다. default는 public.
    companion object{
        private val dao = YourDao()
        fun getInstance():YourDao{
            return dao
        }
    }
    fun insert(){}
    fun update(){}
}


fun main() {
    MyUtil.write()  //static 메소드 호출
    var a = MyUtil.color    //static 필드 참조
    println("main 함수가 종료 됩니다.")

    MyDao.getInstance()?.insert()   //MyDao.getInstance() 가 null 일 수 있으므로 ? 를 붙여준다.
    MyDao.getInstance()?.update()

    YourDao.getInstance().insert()  // null일 가능성이 없으므로 ? 가 필요없다.
    YourDao.getInstance().update()
}