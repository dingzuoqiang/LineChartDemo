package com.dzq.chartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dzq.chartdemo.widget.DzqLineChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Integer> circleDotBitmapList;// 圆点Bitmap数组，每一条线一种圆点，若不设置  默认黑色圆点 Color.BLACK
    private List<Integer> lineColorList;//折线颜色 数组，每一条线一种颜色，若不设置  默认黑色 Color.BLACK
    private List<String> titleXList;// 传入的X轴标题
    private List<List<Float>> pointList;// 传入的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DzqLineChart dzqLineChart = (DzqLineChart) findViewById(R.id.lay_lineChart);

        pointList = new ArrayList<List<Float>>();
        titleXList = new ArrayList<String>();
        lineColorList = new ArrayList<Integer>();
        lineColorList.add(Color.BLUE);
        circleDotBitmapList = new ArrayList<>();
        circleDotBitmapList.add(R.mipmap.icon_chart_dot);

        for (int i = 0; i < 2; i++) {
            List<Float> pointInList = new ArrayList<Float>();
            for (int j = 0; j < 5; j++) {
                Random r = new Random();
                Float z = r.nextFloat() * 100;
                pointInList.add(z);
                titleXList.add("title" + (i + 1));
            }
            pointList.add(pointInList);
        }

//        dzqLineChart.setBgColor(getResources().getColor(R.color.colorAccent));
        dzqLineChart.setPointList(pointList);
        dzqLineChart.setTitleXList(titleXList);
//        dzqLineChart.setLineColorList(lineColorList);
//        dzqLineChart.setCircleDotBitmapList(circleDotBitmapList);
//        dzqLineChart.setSingleColumnFillColor(getResources().getColor(R.color.colorAccent));

    }
}
