package com.hjw.keytools.utils;

import java.io.*;


public class ConsoleStream {
    static PipedInputStream pipedInputStream = new PipedInputStream();//PipedInputStream运用的是一个1024字节固定大小的循环缓冲区
    static PipedOutputStream pipedOutputStream = new PipedOutputStream();

    public static void connectPipe()  {
        try {
            pipedOutputStream.connect(pipedInputStream);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("管道链接失败");
            System.exit(1);//非正常退出，无论程序正在执行与否
        }
    }

    /**
     * 用同一个线程执行读/写操作导致线程阻塞
     */
    public static void example1(){
        connectPipe();
        byte[] inByte = new byte[10];
        byte[] outByte = new byte[20];

        int readByte = 0;

        try {
            pipedOutputStream.write(outByte,0,20);//写入PipedOutputStream的数据实际上保存到对应的PipedInputStream的内部缓冲区
            System.out.println("     qqq已发送20字节...");

            readByte = pipedInputStream.read(inByte,0,10);
            int i=0;
            while(readByte != -1) {
                pipedOutputStream.write(outByte, 0, 20);
                System.out.println("     已发送20字节..."+i);
                i++;
                readByte = pipedInputStream.read(inByte, 0, 10);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 为证明一次写入的数据可以超过1024字节，我们让写操作线程每次调用PipedOutputStream的write()方法时写入2000字节。那么，在startWriterThread()方法中创建的线程是否会阻塞呢？
     * 按照Java运行时线程调度机制，它当然会阻塞。写操作在阻塞之前实际上最多只能写入1024字节的有效载荷（即PipedInputStream缓冲区的大小）。但这并不会成为问题，因为主线程（main）
     * 很快就会从PipedInputStream的循环缓冲区读取数据，空出缓冲区空间。最终，写操作线程会从上一次中止的地方重新开始，写入2000字节有效载荷中的剩余部分。
     */
    public static void example2(){
        connectPipe();
        byte[] inByte = new byte[500];
        int readByte = 0;

        //启动线程，解决example1线程阻塞问题
        startWriteThread();
        try {
            readByte = pipedInputStream.read(inByte,0,500);

            while(readByte != -1){
                System.out.println("已经读取" + readByte + "字节...");
                readByte = pipedInputStream.read(inByte, 0, 500);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("读取操作发生错误");
            System.exit(1);
        }

    }

    public static void startWriteThread(){
        //匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] outByte = new byte[2000];
                while(true){
                    // 在该线程阻塞之前，有最多1024字节的数据被写入.因为PipedInputStream运用的是一个1024字节固定大小的循环缓冲区
                    try {
                        pipedOutputStream.write(outByte,0,2000);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("写操作错误");
                        System.exit(1);
                    }
                    System.out.println("     已经发送2000字节...");
                }
            }
        }).start();
    }


    class LoopStream{
        PipedOutputStream pos = new PipedOutputStream();
        boolean keepRunning = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(){
            @Override
            public void close() throws IOException {
                keepRunning = false;
                super.close();
                pos.close();
            }
        };

        PipedInputStream pio = new PipedInputStream(){
            @Override
            public void close() throws IOException {
                keepRunning = false;
                super.close();
//                pio.close();
            }
        };


        public LoopStream() throws IOException {
            pos.connect(pio);
            startByteArrayReaderThread();
        }

        public OutputStream getOutputStream() {
            return baos;
        }

        public InputStream getInputStream() {
            return pio;
        }

        //创建一个定期地检查ByteArrayOutputStream缓冲区的线程。
        public void startByteArrayReaderThread(){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (keepRunning){
                            //检查流里面的字节数
                            if(baos.size()>0){
                                byte[] buffer = null;
                                synchronized (baos){
                                    buffer = baos.toByteArray();
                                    baos.reset();// 清除缓冲区
                                };
                                try {
                                    // 把提取到的数据发送给PipedOutputStream
                                    pos.write(buffer,0,buffer.length);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.exit(1);
                                }

                            }else{//没有数据可以用就进入休眠
                                try {
                                // 每隔1秒查看ByteArrayOutputStream检查新数据
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
        }
    }

    public static void main(String[] args) {
        example2();
    }
}
