package com.dzq.chartdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.dzq.chartdemo.R;
import com.dzq.chartdemo.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingzuoqiang on 2017/10/15.
 * Email: 530858106@qq.com
 */

public class DzqLineChart extends View {

    private float textSize = 20;// X、Y轴 字体大小  默认 20px
    private int textColor = Color.BLACK;// X、Y轴 字体颜色   默认黑色 Color.BLACK

    private int numberOfX = 6;// X轴 最多显示几个值  默认6个
    private int numberOfY = 8;// Y轴 最多显示几个值  默认8个

    private int paddingTop = 30;// View上边界距离 折线绘制区的 距离
    private int paddingLeft = 70;// View左边界距离 Y轴的 距离
    private int paddingRight = 50;// View右边界距离 折线绘制区的 距离
    private int paddingBottom = 50;// View下边界距离 X轴的 距离

    private int yTitle2RightPadding = 10;// Y 轴上标题距离右侧 内容区的距离

    private int appendXLength = 10;// 向左X轴突出的长度

    private int circleDotRadius = 10;//折线上圆点半径  默认10
    private int titleXRotateDegrees = 0;// X轴 标题 旋转角度
    private float xyAxisLineStrokeWidth = 2;// X、Y 轴 线宽度，（包括内部的X、Y 轴 线 ）

    private int selectedPositionInsideY = -1;// 选中的 内部Y轴 position, （ >=0 && < numberOfX &&绘制内部的Y轴完成）  才会绘制出来
    private int selectedPositionInsideYLineColor = Color.BLACK;//选中的 内部Y轴 的线颜色  默认黑色 Color.BLACK
    private float selectedPositionInsideYStrokeWidth = 2;// 选中的 内部Y轴线 宽度

    private int circleDotBitmapNotSet = Color.RED;//折线上圆点颜色  圆点Bitmap数组若不设置  圆点默认黑色 Color.RED
    private int lineColorNotSet = Color.BLACK;//折线颜色  若不设置  默认黑色 Color.BLACK
    private float lineChartStrokeWidth = 2;// 折线 宽度

    private int bgColor = Color.WHITE;// View 的背景色   默认白色 ffffff
    private int singleColumnFillColor = Color.rgb(Integer.parseInt("e7", 16),
            Integer.parseInt("e7", 16), Integer.parseInt("e9", 16));// 单数列的背景色
    private int doubleColumnFillColor = Color.rgb(Integer.parseInt("ea", 16),
            Integer.parseInt("e0", 16), Integer.parseInt("e8", 16));// 双数列的背景色
    private int fillDownColor = Color.rgb(Integer.parseInt("45", 16),
            Integer.parseInt("64", 16), Integer.parseInt("bf", 16));// 折线下方填充的颜色   注意 Alpha(50)
    private int xyAxisLineColor = Color.rgb(Integer.parseInt("74", 16),
            Integer.parseInt("6f", 16), Integer.parseInt("e3", 16));// X、Y 轴以及表格的线颜色

    private String yAxisUnit = "";// Y轴单位
    private int ScreenX;// View 宽度
    private int ScreenY;// View 高度
    private float maxNumber = 0;// Y轴最大值

    private List<Integer> circleDotBitmapList;// 圆点Bitmap数组，每一条线一种圆点，若不设置  默认黑色圆点 Color.BLACK
    private List<Integer> lineColorList;//折线颜色 数组，每一条线一种颜色，若不设置  默认黑色 Color.BLACK
    private List<String> titleXList;// 传入的X轴标题
    private List<String> titleYList;// 计算得出的Y轴标题
    private List<List<Float>> pointList;// 传入的数据

    private boolean isDrawYDashed = false;// 是否绘制Y轴 虚线
    private boolean isDrawY = true;// 是否绘制Y轴
    private boolean isDrawX = true;// 是否绘制X轴
    private boolean isDrawXDashed = false;// 是否绘制X轴 虚线

    private boolean isDrawInsideDashedY = true;// 是否绘制内部的Y轴 虚线
    private boolean isDrawInsedeY = true;// 是否绘制内部的Y轴
    private boolean isDrawInsideX = true;// 是否绘制内部的X轴
    private boolean isDrawInsideDashedX = true;// 是否绘制内部的X轴 虚线

    private boolean isColumnFillColor = false;// 是否填充单双列颜色

    private boolean isFillDown = true;// 是否填充点的下面部分
    private boolean isAppendX = false;// X轴是否向左突出一点

    public DzqLineChart(Context context) {
        super(context);
        init(null, 0);
    }

    public DzqLineChart(Context context, AttributeSet attr) {
        super(context, attr);
        init(attr, 0);
    }

    public DzqLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        if (attrs == null) return;

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DzqLineChart, defStyle, 0);
        isDrawX = array.getBoolean(R.styleable.DzqLineChart_isDrawX, isDrawX);
        isDrawY = array.getBoolean(R.styleable.DzqLineChart_isDrawY, isDrawY);
        isDrawXDashed = array.getBoolean(R.styleable.DzqLineChart_isDrawXDashed, isDrawXDashed);
        isDrawYDashed = array.getBoolean(R.styleable.DzqLineChart_isDrawYDashed, isDrawYDashed);
        isDrawInsideX = array.getBoolean(R.styleable.DzqLineChart_isDrawInsideX, isDrawInsideX);
        isDrawInsedeY = array.getBoolean(R.styleable.DzqLineChart_isDrawInsedeY, isDrawInsedeY);
        isDrawInsideDashedX = array.getBoolean(R.styleable.DzqLineChart_isDrawInsideDashedX, isDrawInsideDashedX);
        isDrawInsideDashedY = array.getBoolean(R.styleable.DzqLineChart_isDrawInsideDashedY, isDrawInsideDashedY);
        isColumnFillColor = array.getBoolean(R.styleable.DzqLineChart_isColumnFillColor, isColumnFillColor);
        isFillDown = array.getBoolean(R.styleable.DzqLineChart_isFillDown, isFillDown);
        isAppendX = array.getBoolean(R.styleable.DzqLineChart_isAppendX, isAppendX);

        textSize = array.getDimension(R.styleable.DzqLineChart_textSize, textSize);
        xyAxisLineStrokeWidth = array.getDimension(R.styleable.DzqLineChart_xyAxisLineStrokeWidth, xyAxisLineStrokeWidth);
        lineChartStrokeWidth = array.getDimension(R.styleable.DzqLineChart_lineChartStrokeWidth, lineChartStrokeWidth);

        textColor = array.getColor(R.styleable.DzqLineChart_textColor, textColor);
        bgColor = array.getColor(R.styleable.DzqLineChart_bgColor, bgColor);
        xyAxisLineColor = array.getColor(R.styleable.DzqLineChart_xyAxisLineColor, xyAxisLineColor);

        selectedPositionInsideY = array.getInt(R.styleable.DzqLineChart_selectedPositionInsideY, selectedPositionInsideY);
        selectedPositionInsideYLineColor = array.getColor(R.styleable.DzqLineChart_selectedPositionInsideYLineColor, selectedPositionInsideYLineColor);
        selectedPositionInsideYStrokeWidth = array.getDimension(R.styleable.DzqLineChart_selectedPositionInsideYStrokeWidth, selectedPositionInsideYStrokeWidth);

        array.recycle();

    }

    /**
     * 计算得出View的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredHeight = measureHeight(heightMeasureSpec);

        int measuredWidth = measureWidth(widthMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);

        ScreenX = measuredWidth;

        ScreenY = measuredHeight;

    }

    private int measureHeight(int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 300;
        if (specMode == MeasureSpec.AT_MOST) {

            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {

            result = specSize;
        }

        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 450;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {

            result = specSize;
        }

        return result;
    }

    /**
     * 绘画View方法
     *
     * @param canvas
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        maxNumber = 0;
        List<Point> listX = initNumberOfX();// 计算出X轴平均后的坐标
        List<Point> listY = initNumberOfY();// 计算出Y轴平均后的坐标
        canvas.drawColor(bgColor);// 背景色
        fillColorToColumn(listX, canvas);// 对每一列 填充颜色

        drawXyAxis(canvas, listX, listY);
        setXTitle(listX, canvas);// 画折线图X的单位
        setYTitle(listY, canvas);// 画折线图Y的单位，同时计算出最大的Y轴值

        List<List<Point>> positionList = countListPosition(listX);// 计算像素位置
        drawFill(canvas, positionList);// 填充折线和边框
        drawLineChart(canvas, positionList);// 画折线
        drawCicle(canvas, positionList);// 画点

    }

    // 计算像素位置
    private List<List<Point>> countListPosition(List<Point> listX) {
        List<List<Point>> positionList = new ArrayList<List<Point>>();
        if (pointList != null) {
            for (int i = 0; i < pointList.size(); i++) {
                List<Point> positionInList = new ArrayList<Point>();
                for (int j = 0; !isIndexOutOfBounds(j) && j < pointList.get(i).size(); j++) {
                    Point point = new Point();
                    Float z = pointList.get(i).get(j);
                    point.x = listX.get(j).x;
                    point.y = listX.get(j).y + paddingTop
                            - (int) ((listX.get(j).y) * (float) z / (float) maxNumber);
                    positionInList.add(point);
                }
                positionList.add(positionInList);
            }
        }
        return positionList;
    }

    // 判断 显示的点数据有没有超出 X、Y 轴 的最大显示数
    private boolean isIndexOutOfBounds(int index) {
        return index >= numberOfX || index >= numberOfY;
    }


    //对单双列做不同的填充颜色
    private void fillColorToColumn(List<Point> listX, Canvas canvas) {
        if (isColumnFillColor) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            for (int i = 0; i < numberOfX - 1; i++) {
                if (i % 2 == 0) {
                    paint.setColor(singleColumnFillColor);
                } else {
                    paint.setColor(doubleColumnFillColor);
                }
                canvas.drawRect(listX.get(i).x, paddingTop, listX.get(i + 1).x, ScreenY - paddingBottom,
                        paint);
            }
        }
    }

    // 绘制 XY 轴，以及 内部 表格线
    private void drawXyAxis(Canvas canvas, List<Point> listX, List<Point> listY) {
        if (canvas == null || listX == null || listX.size() == 0 || listY == null || listY.size() == 0)
            return;
        Paint paint = new Paint();
        paint.setColor(xyAxisLineColor);// //表格线颜色
        paint.setStrokeWidth(xyAxisLineStrokeWidth);

        if (isDrawX) {//是否绘制X轴
            int appendX = 0;
            if (isAppendX) {
                appendX = appendXLength;
            }
            if (isDrawXDashed) {
                paintDashed(canvas, paddingLeft - appendX, paddingTop + listY.get(0).y, listY.get(0).x
                                + paddingLeft,
                        paddingTop + listY.get(0).y, xyAxisLineColor);
            } else
                canvas.drawLine(paddingLeft - appendX, paddingTop + listY.get(0).y, listY.get(0).x
                                + paddingLeft,
                        paddingTop + listY.get(0).y, paint);
        }
        if (isDrawY) {//是否绘制Y轴
            if (isDrawYDashed) {
                paintDashed(canvas, listX.get(0).x, paddingTop, listX.get(0).x, listX.get(0).y + paddingTop, xyAxisLineColor);
            } else
                canvas.drawLine(listX.get(0).x, paddingTop, listX.get(0).x, listX.get(0).y + paddingTop, paint);
        }

        if (isDrawInsedeY) {// 是否绘制内部的Y轴
            for (int i = 0; i < listX.size(); i++) {// 第一根内部 y轴线 和 Y轴线 重叠，故绘制Y轴的时候 不绘制第一条线
                if (isDrawY && i == 0) continue;
                Point point = listX.get(i);
                if (isDrawInsideDashedY) {
                    paintDashed(canvas, point.x, paddingTop, point.x, point.y + paddingTop, xyAxisLineColor);
                } else {
                    canvas.drawLine(point.x, paddingTop, point.x, point.y + paddingTop, paint);
                }

            }
        }
        if (isDrawInsideX) {// 是否绘制内部的X轴

            int appendX = 0;
            if (isAppendX) {
                appendX = appendXLength;
            }
            for (int i = (isDrawX ? 1 : 0); i < listY.size(); i++) {// 第一根内部 x轴线 和 X轴线 重叠，故绘制X轴的时候 不绘制第一条线
                Point point = listY.get(i);
                if (isDrawInsideDashedX) {
                    paintDashed(canvas, paddingLeft - appendX, paddingTop + point.y, point.x + paddingLeft,
                            paddingTop + point.y, xyAxisLineColor);
                } else {
                    canvas.drawLine(paddingLeft - appendX, paddingTop + point.y, point.x + paddingLeft,
                            paddingTop + point.y, paint);
                }

            }

        }

        //  选中的 内部Y轴 position, （selectedPositionInsideY >=0 && < numberOfX &&绘制内部的Y轴完成）  才会绘制出来
        paint.setColor(selectedPositionInsideYLineColor);// //表格线颜色
        paint.setStrokeWidth(selectedPositionInsideYStrokeWidth);
        if (selectedPositionInsideY >= 0 && selectedPositionInsideY < listX.size()) {
            Point point = listX.get(selectedPositionInsideY);
            canvas.drawLine(point.x, paddingTop, point.x, point.y + paddingTop, paint);
        }

    }

    //为X轴每个标题
    private void setXTitle(List<Point> listX, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        if (titleXList == null) {
            titleXList = new ArrayList<String>();
            for (int i = 1; i <= numberOfX; i++) {
                titleXList.add("title" + i);
            }
        }
        for (int i = 0; i < numberOfX && i < titleXList.size(); i++) {
            canvas.save();
            String xTitle = titleXList.get(i);
            if (xTitle != null) {
                int finalX = listX.get(i).x;
                if (i != 0)
                    finalX = finalX - CommonUtils.getTextWidth(xTitle, paint) / 2;
                canvas.rotate(titleXRotateDegrees, finalX,
                        listX.get(i).y + paddingTop + paddingBottom / 2);
                canvas.drawText(titleXList.get(i), finalX,
                        listX.get(i).y + paddingTop + paddingBottom / 2
                        , paint);
                canvas.restore();
            }
        }
    }

    //为Y轴找到最大值以及它的每个标题
    private void setYTitle(List<Point> listY, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        if (pointList == null) {
            titleYList = new ArrayList<String>();
            for (int i = 1; i <= numberOfY; i++) {
                titleYList.add(0, String.valueOf(100 / i));
            }
        } else {
            for (int i = 0; i < pointList.size(); i++) {
                for (int j = 0; j < pointList.get(i).size(); j++) {

                    if (pointList.get(i).get(j) > maxNumber) {
                        maxNumber = pointList.get(i).get(j);
                    }
                }
            }
            maxNumber = maxNumber + maxNumber / 3;
            titleYList = new ArrayList<String>();
            for (int i = 0; i < numberOfY; i++) {
                titleYList.add(String.valueOf((int) (0 + i * (maxNumber / (numberOfY - 1)))));
            }
        }

        int finalAppendX = (isAppendX && appendXLength > 0) ? appendXLength : 0;
        finalAppendX = finalAppendX + yTitle2RightPadding;

        for (int i = 0; i < numberOfY; i++) {
            String title = titleYList.get(i);
            if (!TextUtils.isEmpty(title)) {
                if (i == 0)
                    title = title + yAxisUnit;
                Rect bounds = new Rect();
                paint.getTextBounds(title, 0, title.length(), bounds);
                canvas.drawText(titleYList.get(i), paddingLeft - finalAppendX - CommonUtils.getTextWidth(title, paint),
                        paddingTop
                                + listY.get(i).y + CommonUtils.getTextHeight(title, paint) / 2, paint);

            }

        }
    }


    // 填充折线和边框
    private void drawFill(Canvas canvas, List<List<Point>> positionList) {
        if (!isFillDown || positionList == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(fillDownColor);
        paint.setAlpha(50);
        for (int i = 0; i < positionList.size(); i++) {
            Path path = new Path();
            path.moveTo(paddingLeft, ScreenY - paddingBottom);
            int size = positionList.get(i).size();
            for (int j = 0; j < size; j++) {
                path.lineTo(positionList.get(i).get(j).x, positionList.get(i).get(j).y);
            }
            if (size > 0)
                path.lineTo(positionList.get(i).get(size - 1).x, ScreenY - paddingBottom);
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    // 画折线
    private void drawLineChart(Canvas canvas, List<List<Point>> positionList) {
        if (positionList == null) return;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineChartStrokeWidth);

        for (int i = 0; i < positionList.size(); i++) {
            if (lineColorList != null && lineColorList.size() - 1 >= i && lineColorList.get(i) != null) {
                paint.setColor(lineColorList.get(i));
            } else {
                paint.setColor(lineColorNotSet);
            }
            for (int j = 0; j < positionList.get(i).size() - 1; j++) {
                canvas.drawLine(positionList.get(i).get(j).x, positionList.get(i).get(j).y,
                        positionList.get(i).get(j + 1).x, positionList.get(i).get(j + 1).y, paint);
            }
        }
    }

    // 画点
    private void drawCicle(Canvas canvas, List<List<Point>> positionList) {
        if (positionList == null) return;
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置画笔为无锯齿
        paint.setColor(circleDotBitmapNotSet);//设置画笔颜色
        for (int i = 0; i < positionList.size(); i++) {
            Bitmap bitmap = null;
            if (circleDotBitmapList != null && circleDotBitmapList.size() - 1 >= i && circleDotBitmapList.get(i) != null) {
                int resouceId = circleDotBitmapList.get(i);
                bitmap = BitmapFactory.decodeResource(getResources(), resouceId);
            }

            for (int j = 0; j < positionList.get(i).size(); j++) {
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, positionList.get(i).get(j).x + 0.5f - bitmap.getWidth()
                                    / 2,
                            positionList.get(i).get(j).y + 0.5f - bitmap.getHeight() / 2, paint);
                } else {
                    canvas.drawCircle(positionList.get(i).get(j).x, positionList.get(i).get(j).y,
                            circleDotRadius, paint);
                }
            }

        }
    }

    // 计算出X轴平均后的坐标
    private List<Point> initNumberOfX() {
        int num = (ScreenX - paddingLeft - paddingRight) / (numberOfX - 1);
        List<Point> list = new ArrayList<Point>();
        for (int i = 0; i < numberOfX; i++) {
            Point point = new Point();
            point.y = ScreenY - paddingBottom - paddingTop;
            point.x = paddingLeft + num * i;
            list.add(point);
        }
        return list;
    }

    // 计算出Y轴平均后的坐标
    private List<Point> initNumberOfY() {
        int num = (ScreenY - paddingBottom - paddingTop) / (numberOfY - 1);
        List<Point> list = new ArrayList<Point>();
        for (int i = 0; i < numberOfY; i++) {
            Point point = new Point();
            point.x = ScreenX - paddingLeft - paddingRight;
            point.y = ScreenY - paddingBottom - paddingTop - num * i;
            list.add(point);
        }
        return list;
    }

    /**
     * 画虚线
     *
     * @param canvas
     * @param moveToX
     * @param moveToY
     * @param lineToX
     * @param lineToY
     */
    private void paintDashed(Canvas canvas, int moveToX, int moveToY, int lineToX, int lineToY, int color) {
        DashPathEffect pathEffect = new DashPathEffect(new float[]{6, 4}, 0);
        Paint paint = new Paint();
        paint.reset();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(xyAxisLineStrokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setPathEffect(pathEffect);
        Path path = new Path();
        path.moveTo(moveToX, moveToY);
        path.lineTo(lineToX, lineToY);
        canvas.drawPath(path, paint);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setNumberOfX(int numberOfX) {
        this.numberOfX = numberOfX;
    }

    public void setNumberOfY(int numberOfY) {
        this.numberOfY = numberOfY;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public void setyTitle2RightPadding(int yTitle2RightPadding) {
        this.yTitle2RightPadding = yTitle2RightPadding;
    }

    public void setAppendXLength(int appendXLength) {
        this.appendXLength = appendXLength;
    }

    public void setCircleDotRadius(int circleDotRadius) {
        this.circleDotRadius = circleDotRadius;
    }

    public void setTitleXRotateDegrees(int titleXRotateDegrees) {
        this.titleXRotateDegrees = titleXRotateDegrees;
    }

    public void setXyAxisLineStrokeWidth(float xyAxisLineStrokeWidth) {
        this.xyAxisLineStrokeWidth = xyAxisLineStrokeWidth;
    }

    public void setSelectedPositionInsideY(int selectedPositionInsideY) {
        this.selectedPositionInsideY = selectedPositionInsideY;
    }

    public void setSelectedPositionInsideYLineColor(int selectedPositionInsideYLineColor) {
        this.selectedPositionInsideYLineColor = selectedPositionInsideYLineColor;
    }

    public void setSelectedPositionInsideYStrokeWidth(float selectedPositionInsideYStrokeWidth) {
        this.selectedPositionInsideYStrokeWidth = selectedPositionInsideYStrokeWidth;
    }

    public void setCircleDotBitmapNotSet(int circleDotBitmapNotSet) {
        this.circleDotBitmapNotSet = circleDotBitmapNotSet;
    }

    public void setLineColorNotSet(int lineColorNotSet) {
        this.lineColorNotSet = lineColorNotSet;
    }

    public void setLineChartStrokeWidth(float lineChartStrokeWidth) {
        this.lineChartStrokeWidth = lineChartStrokeWidth;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setSingleColumnFillColor(int singleColumnFillColor) {
        this.singleColumnFillColor = singleColumnFillColor;
    }

    public void setDoubleColumnFillColor(int doubleColumnFillColor) {
        this.doubleColumnFillColor = doubleColumnFillColor;
    }

    public void setFillDownColor(int fillDownColor) {
        this.fillDownColor = fillDownColor;
    }

    public void setXyAxisLineColor(int xyAxisLineColor) {
        this.xyAxisLineColor = xyAxisLineColor;
    }

    public void setyAxisUnit(String yAxisUnit) {
        this.yAxisUnit = yAxisUnit;
    }

    public void setCircleDotBitmapList(List<Integer> circleDotBitmapList) {
        this.circleDotBitmapList = circleDotBitmapList;
    }

    public void setLineColorList(List<Integer> lineColorList) {
        this.lineColorList = lineColorList;
    }

    public void setTitleXList(List<String> titleXList) {
        this.titleXList = titleXList;
    }

    public void setPointList(List<List<Float>> pointList) {
        this.pointList = pointList;
    }

    public void setDrawYDashed(boolean drawYDashed) {
        isDrawYDashed = drawYDashed;
    }

    public void setDrawY(boolean drawY) {
        isDrawY = drawY;
    }

    public void setDrawX(boolean drawX) {
        isDrawX = drawX;
    }

    public void setDrawXDashed(boolean drawXDashed) {
        isDrawXDashed = drawXDashed;
    }

    public void setDrawInsideDashedY(boolean drawInsideDashedY) {
        isDrawInsideDashedY = drawInsideDashedY;
    }

    public void setDrawInsedeY(boolean drawInsedeY) {
        isDrawInsedeY = drawInsedeY;
    }

    public void setDrawInsideX(boolean drawInsideX) {
        isDrawInsideX = drawInsideX;
    }

    public void setDrawInsideDashedX(boolean drawInsideDashedX) {
        isDrawInsideDashedX = drawInsideDashedX;
    }

    public void setColumnFillColor(boolean columnFillColor) {
        isColumnFillColor = columnFillColor;
    }

    public void setFillDown(boolean fillDown) {
        isFillDown = fillDown;
    }

    public void setAppendX(boolean appendX) {
        isAppendX = appendX;
    }
}
