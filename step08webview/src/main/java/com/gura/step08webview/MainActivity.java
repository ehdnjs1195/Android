package com.gura.step08webview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WebView 의 참조값 얻어오기
        WebView webView=findViewById(R.id.webView);
        //WebSettings 객체
        WebSettings ws=webView.getSettings();
        ws.setJavaScriptEnabled(true);  //javascript 사용 가능하도록

        //필요한 객체 생성해서 넣어주기
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new MyWebViewClient());
        //url 로딩시키기
        webView.loadUrl("http://14.63.164.99/");         // =>인터넷 자원을 사용하는 것이므로 permission이 필요하다.

    }

    public class MyWebViewClient extends WebChromeClient {
        // For Android Version < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {     //파일을 선택할 수 있도록 열어주는 역할
            //System.out.println("WebViewActivity OS Version : " + Build.VERSION.SDK_INT + "\t openFC(VCU), n=1");
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(TYPE_IMAGE);
            startActivityForResult(intent, INPUT_FILE_REQUEST_CODE);
        }

        // For 3.0 <= Android Version < 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            openFileChooser(uploadMsg, acceptType, "");
        }

        // For Android 4.1+

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {

            openFileChooser(uploadMsg, acceptType);
        }

        // For Android 5.0+         안드로이드 버전에 따라서 대응되는 코드들.. 파일 츄져를 여는 방법이 다 다르기 때문이다.

        @Override   //부모 메서드 오버라이드 해둔것.
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadFile, WebChromeClient.FileChooserParams fileChooserParams) {


            if(mFilePathCallback !=null) {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }

            mFilePathCallback = uploadFile;
            Intent i =new Intent(Intent.ACTION_GET_CONTENT);    //인텐트 객체는 다른 어플리케이션에 있는 특정 액티비티를 활성화 시킬 수 있다.
            i.addCategory(Intent.CATEGORY_OPENABLE);        //ACTION_GET_CONTENT 를 운영체제에 던져주고 그 액티비티를 활성화 시켜줘라!
            i.setType("image/*");               //어떤타입? image에 있는 모든것.

            startActivityForResult(Intent.createChooser(i, "File Chooser"), INPUT_FILE_REQUEST_CODE);       //startActivity... 를 통해 운영체제로 인텐트 객체를 던지는 것.
            //운영체제가 저 인텐트를 해결하기 위해 앱을 찾는다.
            //찾는 어플리케이션이 하나면 바로 실행시키고, 여러개면 선택하라고 나온다.
            //예를 들어 카메라 어플 .. 사용이 끝나고 나면 onActivityResult() 메서드가 실행된다.(결과에 대해 성공인지 실패인지 확인작업이 필요하기도 하고, 여러 식별값을 확인하기도 하고, 등..)
            return true;

        }


        private void imageChooser() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(MainActivity.this.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e(getClass().getName(), "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:"+photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType(TYPE_IMAGE);

            Intent[] intentArray;
            if(takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);     //안드로이드 운영체제야 이 액티비티를 받아줄 수 있는 앱을 찾아줘 하고 보내는 것.
        }


    }
    //변수
    private static final String TYPE_IMAGE = "image/*";
    private static final int INPUT_FILE_REQUEST_CODE = 1;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    @Override   //이건 액티비티의 메서드를 오버라이드 한 것이다.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //Intent에 결과 코드를 담아서 온다.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INPUT_FILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (mFilePathCallback == null) {
                        super.onActivityResult(requestCode, resultCode, data);
                        return;
                    }
                    Uri[] results = new Uri[]{data.getData()};  //data 에서 uri 를 가져왔다.   => 폰 내부에서 어떤 파일을 선택했을 때 그 파일을 정확하게 지칭할 수 있는 정보가 uri 이다.(파일 탐색기에서 파일의 경로를 나타내는 것! => uri)

                    mFilePathCallback.onReceiveValue(results);
                    mFilePathCallback = null;
                } else {
                    if (mUploadMessage == null) {
                        super.onActivityResult(requestCode, resultCode, data);
                        return;
                    }
                    Uri result = data.getData();

                    Log.d(getClass().getName(), "openFileChooser : " + result);
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            } else {
                if (mFilePathCallback != null) mFilePathCallback.onReceiveValue(null);
                if (mUploadMessage != null) mUploadMessage.onReceiveValue(null);
                mFilePathCallback = null;
                mUploadMessage = null;
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
    /*
      WebView:  html,css,javascript 을 해석해주는 것

      로딩할 페이지는 프로그래밍 적으로 설정할 수 있다.
      앱 안에 html,css,javascript를 써놓거나 외부에서 요청해서 로딩을 시킬 수 있다.

      만일 웹 사이트가 반응형으로 제작해두고 여기에 로딩시키면 마치 앱처럽 사용할 수 있다.
    */