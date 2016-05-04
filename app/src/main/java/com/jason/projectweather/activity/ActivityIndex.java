package com.jason.projectweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.jason.projectweather.R;

import java.util.List;

public class ActivityIndex extends AppCompatActivity {

    TextView tvTitle1;
    TextView tvIndex1;
    TextView tvTip1;
    TextView tvTitle2;
    TextView tvIndex2;
    TextView tvTip2;
    TextView tvTitle3;
    TextView tvIndex3;
    TextView tvTip3;
    TextView tvTitle4;
    TextView tvIndex4;
    TextView tvTip4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weatherindex);

        List<String> listIndexInfos = getIntent().getStringArrayListExtra("IndexInfos");

        setTextViews();

        tvTitle1.setText(listIndexInfos.get(0));
        tvIndex1.setText(listIndexInfos.get(1));
        tvTip1.setText(listIndexInfos.get(2) + ": " + listIndexInfos.get(3));

        tvTitle2.setText(listIndexInfos.get(4));
        tvIndex2.setText(listIndexInfos.get(5));
        tvTip2.setText(listIndexInfos.get(6) + ": " + listIndexInfos.get(7));

        tvTitle3.setText(listIndexInfos.get(8));
        tvIndex3.setText(listIndexInfos.get(9));
        tvTip3.setText(listIndexInfos.get(10) + ": " + listIndexInfos.get(11));

        tvTitle4.setText(listIndexInfos.get(12));
        tvIndex4.setText(listIndexInfos.get(13));
        tvTip4.setText(listIndexInfos.get(14) + ": " + listIndexInfos.get(15));

    }

    private void setTextViews() {
        tvTitle1 = (TextView)findViewById(R.id.title1);
        tvTitle2 = (TextView)findViewById(R.id.title2);
        tvTitle3 = (TextView)findViewById(R.id.title3);
        tvTitle4 = (TextView)findViewById(R.id.title4);
        tvIndex1 = (TextView)findViewById(R.id.index1);
        tvIndex2 = (TextView)findViewById(R.id.index2);
        tvIndex3= (TextView)findViewById(R.id.index3);
        tvIndex4 = (TextView)findViewById(R.id.index4);
        tvTip1 = (TextView)findViewById(R.id.tip1);
        tvTip2 = (TextView)findViewById(R.id.tip2);
        tvTip3 = (TextView)findViewById(R.id.tip3);
        tvTip4 = (TextView)findViewById(R.id.tip4);
    }
}
