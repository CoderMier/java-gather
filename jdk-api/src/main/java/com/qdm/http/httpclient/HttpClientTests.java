package com.qdm.http.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sun.misc.Request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author qiudm
 * @date 2019/2/26 17:00
 * @desc
 */
public class HttpClientTests {

    @Test
    public void getTest() {
        HttpResponse response = null;
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("192.168.100.3")
                    .setPort(9191)
                    .setPath("/user/open/test")
                    .build();

            HttpGet httpGet = new HttpGet(uri);
            System.out.println("httpGet.getURI() = " + httpGet.getURI());

            HttpClient httpClient = HttpClients.createMinimal();

            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            byte[] resByte =  EntityUtils.toByteArray(entity);
            String res = new String(resByte, "UTF-8" ) ;
            System.out.println("entity = " + entity);
            System.out.println("entity.getContent() = " + entity.getContent());
            System.out.println("res = " + res);
            System.out.println("response = " + response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    ((CloseableHttpResponse) response).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }






    public String fluentGet() {
        String res = null;





        return res;
    }


















}
