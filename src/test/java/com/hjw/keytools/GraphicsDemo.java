package com.hjw.keytools;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GraphicsDemo{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyFrame myFrame = new MyFrame();
                myFrame.setVisible(true);
            }
        });
    }

    //静态内部类
    public static class MyFrame extends JFrame{
        public static final String TITLE = "JAVA 绘图";
        public static final int WIDTH = 330;
        public static final int HEIGHT = 300;

        public MyFrame(){
            super();
            initFrame();
        }

        private void initFrame() {
            setTitle(TITLE);
            setSize(new Dimension(WIDTH,HEIGHT));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLocationByPlatform(true);
            MyPanel myPanel = new MyPanel(this);
            setContentPane(myPanel);

        }
    }

    public static class MyPanel extends JPanel {
        private MyFrame myFrame;

        public MyPanel(MyFrame frame) {
            super();
            this.myFrame = frame;
        }

        /**
         * 绘制面板的内容: 创建 JPanel 后会调用一次该方法绘制内容,
         * 之后如果数据改变需要重新绘制, 可调用 updateUI() 方法触发
         * 系统再次调用该方法绘制更新 JPanel 的内容。
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//            drawline(g);
//            drawRect(g);
//            drawArc(g);
//            drawStr(g);
            drawImage(g);
        }

        private void drawline(Graphics g) {
            myFrame.setTitle("线条");
            // 创建 Graphics 的副本, 需要改变 Graphics 的参数,
            // 这里必须使用副本, 避免影响到 Graphics 原有的设置
            Graphics2D graphics2D = (Graphics2D) g.create();
            // 抗锯齿
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(Color.RED);

            //两点
            graphics2D.drawLine(10, 10, 200, 50);
            //多点
            int[] xPoint = new int[]{50, 100, 150, 200};
            int[] yPoint = new int[]{100, 120, 80, 100};
            graphics2D.drawPolygon(xPoint, yPoint, 4);

            //线段
            BasicStroke basicStroke = new BasicStroke(5); // 笔画的轮廓（画笔宽度/线宽为5px）
            graphics2D.setStroke(basicStroke);//画板设置画笔
            graphics2D.drawLine(50, 150, 200, 150);

            //虚线
            float[] dash = new float[]{5, 10};
            BasicStroke basicStroke1 = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
                    10.0f, dash, 0.0f);
            graphics2D.setStroke(basicStroke1);
            graphics2D.drawLine(50, 200, 200, 200);

            graphics2D.dispose();
        }

        private void drawRect(Graphics g){
            myFrame.setTitle("矩形");
            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            BasicStroke basicStroke = new BasicStroke(5);
            graphics2D.setColor(Color.BLUE);
            graphics2D.setStroke(basicStroke);

            //矩形
            graphics2D.drawRect(30,80,100,100);
            //填充的矩形
            graphics2D.fillRect(140, 20, 80, 100);
            //绘制圆角矩形
            graphics2D.drawRoundRect(300,160,30,60,22,22);
            //绘制多边矩形
            int[] xPoint = new int[]{ 140, 180, 220};
            int[] yPoint = new int[]{150, 250, 200};
            graphics2D.drawPolygon(xPoint,yPoint,3);

            graphics2D.dispose();
        }

        private void drawArc(Graphics g){
            myFrame.setTitle("绘制椭圆/扇形");
            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(Color.BLUE);
            //绘制一条圆弧
            //绘制一条圆弧: 椭圆的外切矩形 左上角坐标为(0, 0), 宽100, 高100,
            //                弧的开始角度为0度, 需要绘制的角度数为-90度,
            //                椭圆右边水平线为0度, 逆时针为正角度, 顺时针为负角度
            graphics2D.drawArc(0,0,100,100,0,-90);
            //绘制圆
            graphics2D.drawArc(100,100,60,60,0,360);
            //绘制扇形
            graphics2D.fillArc(80,150,100,100,90,270);
            //绘制一个圆
            graphics2D.drawOval(200,33,100,100);
            //填充一个椭圆
            graphics2D.fillOval(250,60,150,100);

            graphics2D.dispose();
        }

        private void drawStr(Graphics g){
            myFrame.setTitle("绘制文字");
            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(Color.BLUE);
            graphics2D.setFont(new Font("",Font.PLAIN,33));
            graphics2D.drawString("hello world",111,222);

            graphics2D.dispose();
        }

        private void drawImage(Graphics g){
            myFrame.setTitle("绘制图片");
            Graphics2D graphics2D = (Graphics2D) g.create();
            //加载资源文件夹的图片
            URL filePath = GraphicsDemo.class.getClassLoader().getResource("demo.png");
            Image image = Toolkit.getDefaultToolkit().getImage(filePath);
            // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
            // 绘制一张图片（所有组件类实现了 ImageObserver 接口，即组件实例即为 ImageObserver）
            //boolean drawImage(Image image, int x, int y, int width, int height, ImageObserver observer)
            graphics2D.drawImage(image,50,50,image.getWidth(this),image.getHeight(this),this);
            graphics2D.dispose();
        }
    }
}
