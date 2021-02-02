package  yaotai.googlepay.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.yaotai.google_pay.itf.PayResultCallBack;
import com.yaotai.google_pay.main.GooglePayClient;
import com.yaotai.googlepay.R;

public class MainActivity extends AppCompatActivity {
    private EditText et_sku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_sku=(EditText)findViewById(R.id.et_sku);
        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayResultCallBack mPayResultCallBack=new GooglePayClient(MainActivity.this,et_sku.getText().toString());
                ((GooglePayClient) mPayResultCallBack).setmPayReslutCall(new GooglePayClient.PayReslutCall() {
                    @Override
                    public void initFail(String e) {
                        Log.i("google Pay","initFail:"+e);
                    }

                    @Override
                    public void pay_success(String purchaseToken) {
                        Log.i("google Pay","pay_success purchaseToken："+purchaseToken);
                    }

                    @Override
                    public void pay_fail(int code) {
                        Log.i("google Pay","pay_fail code："+code);
                    }

                    @Override
                    public void consume(boolean isConsume) {
                        Log.i("google Pay","consume isConsume："+isConsume);
                    }
                });
                mPayResultCallBack.pay();
            }
        });

    }
}
