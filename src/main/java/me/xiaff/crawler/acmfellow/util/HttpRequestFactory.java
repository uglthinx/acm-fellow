package me.xiaff.crawler.acmfellow.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class HttpRequestFactory {

    private static final String BASE_URL = "https://www.google.com/search?q={query}";

    public static HttpHeaders getGoogleHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        requestHeaders.add("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.5");
        requestHeaders.add("Host", "www.google.com");
        requestHeaders.add("Cookie", "OGPC=19004048-1:19002972-1:19004092-1:65517568-1:19004116-1:; OGP=-19004048:-19002972:-19004092:-65517568:; NID=122=kCBpLmW2CfztMNm8I6LDznzN7UQ4Bpb8HQTTPaOOJ3wRtcODNMILHc_eLM3qeEmpJwvCbYBl9427h7ttiqvc2SRnF5IXYj2VLuTo7XTDdQAxZjIbw-oRjtmqlyHr8XtrSqFSKUdxhfa1xcgWi1M; 1P_JAR=2018-1-29-7; DV=I1iguhWv4BAYcPGwA5PDgxjz7YgOFBY");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063");
        return requestHeaders;
    }

    public static ClientHttpRequestFactory getLongHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15 * 1000);
        requestFactory.setReadTimeout(15 * 1000);
        return requestFactory;
    }

    public static String getGoogleSearchPage(String query) {
        String html;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(HttpRequestFactory.getLongHttpRequestFactory());
            HttpEntity<Void> entity = new HttpEntity<>(HttpRequestFactory.getGoogleHttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, String.class, query);
            html = response.getBody();
        } catch (ResourceAccessException | HttpServerErrorException e) {
            e.printStackTrace();
            return null;
        }
        return html;
    }
}
