package com.gura.step21hellokotlin

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*       // xml에 있는 모든것을 임포트해서 사용할 수 있게 함.(synthetic) => id 만 써서 사용함. / findViewById 과정이 필요없다. => 따로 UI의 참조값을 얻어낼 필요가 없다!!! 대애애애애애애박!

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemLongClickListener{
    //    var inputMsg:EditText? = null   //처음에는 비워놓고 싶다. 그러면 타입에 ? 를 붙여줘서 null이 가능하게 한다. (null로 property를 초기화 하기)
//    lateinit var inputMsg:EditText  //lateinit이라는 키워드를 이용해서 나중에 초기화를 시키겠다!
    lateinit var adapter: ArrayAdapter<String>

    lateinit var msgList: MutableList<String>   // 처음에는 비어있다가 사이즈가 늘어나는 리스트이므로 MutableList로 사용한다.
    override fun onCreate(savedInstanceState: Bundle?) {    // ? 는 null일 가능성도 있다는 뜻
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Button 에 리스너 등록
        addBtn.setOnClickListener(this)
        clearBtn.setOnClickListener(this)
        //MutableList 객체의 참조값을 얻어내서 property 에 저장하기
        msgList = mutableListOf()
        //ArrayAdapter 객체의 참조값을 얻어내서 property 에 저장하기
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, msgList)
        //ListView 에 아답타 연결하기   / setAdapter(adapter)
//        findViewById<ListView>(R.id.myListView).adapter = adapter
        //id 를 이용해서 View 의 참조값 바로 사용 가능
        myListView.adapter = adapter
        //아이템 롱 클릭 리스너 등록하기
        myListView.setOnItemLongClickListener(this)
    }

    //추가, 모두 삭제 버튼을 누르면 호출되는 메소드
    override fun onClick(v: View?) {
        // v 는 눌러진 버튼의 참조값이 전달된다.
        when(v?.id){
            addBtn.id ->  { // R.id.addBtn 과 같다.
                //입력한 문자열 읽어오기
                val msg = inputMsg.text.toString()
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                //입력한 문자열을 모델에 추가하고
                msgList.add(msg)
                //ListView 가 없데이트 되도록 아답타에 알린다.
                adapter.notifyDataSetChanged()
            }
            R.id.clearBtn -> {
                adapter.clear()
            }
        }
    }


    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
        AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("자세히 보시겠습니까?")
                .setPositiveButton("네", object:DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //액티비티 이동
                        var intent= Intent(this@MainActivity, DetailActivity::class.java)   //여기서의 this 는 위의 object 즉, OnClickListener 인터페이스를 가리키는 것이기 때문에 MainActivity를 명시해주어야 한다.
                        intent.putExtra("item", msgList.get(position))
                        startActivity(intent)
                    }
                })
                .setNegativeButton("아니오", null)
                .create().show()

        return false
    }
}
