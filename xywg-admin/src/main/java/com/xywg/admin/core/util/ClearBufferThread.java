package com.xywg.admin.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 清理输入流缓存的线程
 * Created by kagome on 2016/8/9.
 */
public class ClearBufferThread implements Runnable {
    private InputStream inputStream;

    public ClearBufferThread(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public void run() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(br.readLine() != null);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}