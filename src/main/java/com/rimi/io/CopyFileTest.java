package com.rimi.io;

import java.io.*;

/**
 * 将一个目录下的所有.java文件复制到 d:jad目录下，并将原来文件的扩展名从.java改为.jad
 *
 */


public class CopyFileTest {

    public static void main(String[] args) throws IOException {
        //获去Java目录对象
        File file = new File("java");
        //判断file是否存在  是否为一个目录
        if (!(file.exists())&&file.isDirectory()){
            try {
                throw new Exception("目录不存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //目录存在且不为空时 只加载后缀名为.java的文件
        File[] files = file.listFiles(
                new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".java");
                    }
                }
        );
        System.out.println(files.length);

        //获去jad目录对象
        File jad = new File("jad");
        //判断jad目录是否存在
        if (!jad.exists()) jad.mkdir();

        //遍历files
        for (File file1 : files) {
            //获去每一个文件 并创建文件字节输入流读取文件
            FileInputStream fs = new FileInputStream(file1);
            //将文件后缀名改为.jad
            String s = file1.getName().replaceAll("\\.java$", ".jad");
            //创建一个文件字节输出流写出数据
            FileOutputStream fos = new FileOutputStream(new File(jad, s));
            copyFile(fs,fos);
            //关闭资源
            fs.close();
            fos.close();

        }
    }

    private static void copyFile(FileInputStream fs, FileOutputStream fos) throws IOException {
        int len = 0;
        byte[] buf = new byte[1024];
        while((len = fs.read(buf))!= -1){
            fos.write(buf,0,len);
        }
    }


}
