package test.kotlin01;

public class JavaMain {
    public static void main(String[] args) {
        Util.Companion.download();  //자바 클래스에서 코틀린 클래스를 사용하기
        Util.upload();  //kotlin 메소드에 @JvmStatic 을 붙여주면 마치 그냥 static 메소드 호출하듯 사용할 수 있다.

        System.out.println("Util version: "+Util.Companion.getVersion());
        System.out.println("Util author: "+Util.author);    //필드에 @JvmField 를 붙여주면 자바의 static 필드를 사용하듯 참조할 수 있다.
    }
}
