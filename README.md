# LineChartDemo
android自定义控件-折线图，绝对呕心沥血之作！！！
可以显示多条折线，能够设置的功能有：图表背景色；标题字体的大小，
颜色；XY轴的颜色、线的宽度，实线还是虚线；折线的颜色、宽度；数据点的图标，
不设置的话，默认红色圆点，可设置圆点半径；特定选中Y轴内部线的 颜色、宽度。。。其他功能详见代码

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


![image](https://github.com/dingzuoqiang/LineChartDemo/blob/master/img1.png)
![image](https://github.com/dingzuoqiang/LineChartDemo/blob/master/img2.png)
![image](https://github.com/dingzuoqiang/LineChartDemo/blob/master/img3.png)
![image](https://github.com/dingzuoqiang/LineChartDemo/blob/master/img4.png)