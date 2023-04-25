package com.zjq.dailyrecord.algorithm.list;

import cn.hutool.core.util.CreditCodeUtil;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zjq
 * @date 2023/4/14 10:43
 * @description:
 */
public class aync {

    public static void main(String[] args) {


        //定时任务执行
        String s = timeTask();
        System.out.println(s);
        return;
    }

    private static String  timeTask() {
        CreditCodeUtil.isCreditCode("");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000*10);
                System.out.println("异步任务执行完毕");
            }
        });
        executor.shutdown();
        return "执行完毕";
    }
}
