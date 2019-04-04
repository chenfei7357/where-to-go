package com.chenfei.where.to.go.Test;
/*
 * Created by chenfei on 2019/3/29 16:37
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.commons.codec.Charsets.UTF_8;

public class HttpDemo {

    static ExecutorService executorService = Executors.newFixedThreadPool(30);


    @Test
    public void testHttp() throws Exception {
        for (int i = 0; i <20 ; i++) {
            executorService.execute(() -> {
                try {
                    String s=test1();
                    System.err.println("线程:[" + Thread.currentThread().getName() + "]拿到结果=》" + s + System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        System.in.read();
    }



    public static String  test1() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:9999/where-to-go/configs/queryConfigByRedLock/GLZX_02");
        httpGet.setHeader("x-token","111111");
        HttpResponse response  = null;
        try {
            response = httpClient.execute(httpGet);
            org.apache.http.HttpEntity entity = response.getEntity();
            String s =  EntityUtils.toString(entity, UTF_8);
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.close();
        }
        return null;
    }
}
