package me.xiaff.crawler.acmfellow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.NoSuchElementException;

public class HttpRequestFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestFactory.class);

    private static final String GOOGLE_BASE_URL = "https://www.google.com.hk/search?q={query}";
    private static final String GOOGLE_SCHOLAR_BASE_URL = "https://scholar.google.com.hk/scholar?hl=zh-CN&as_sdt=0%2C5&q={query}";
    private static final String MSA_BASE_URL = "https://academic.microsoft.com/api/search/GetEntityResults";

    /**
     * Google search page Http Request Header
     */
    public static HttpHeaders getGoogleHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie","1P_JAR=2018-2-9-6; NID=123=OKuJWyvEa9zi96r3NUHxkbHLFAgQRXnUa7AnEgcLsE3ZiopkNUJQOHDrrU5MaFmQN9hayhfvnRCZZ2uccMHPRNqkMIpC9P1qcPJX-h42eifN_r1vQzgnh-rCpu-_Nv3UqvnR3eriaEzZfs3_zBEisW-bJeZQDfq9GWFnrC6TVmQM8cbI0b1zIbi-1PNQpraxNYbmy49AUxhAPAd_ZVw37xy4rCCg11hnauIBwtXI; DV=ozm4E1tZsioe8GXw8l9VvHcZyXmTFxY");
        requestHeaders.add("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
        requestHeaders.add("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.5");
        requestHeaders.add("Host", "www.google.com.hk");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        return requestHeaders;
    }

    /**
     * Microsoft Academic Http Request Header
     */
    public static HttpHeaders getMsaHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", "*/*");
//        requestHeaders.add("Accept-Encoding", "gzip, deflate, br");
        requestHeaders.add("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.5");
        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.add("Cookie", "ai_user=zDrhD|2018-01-31T04:00:38.812Z; msacademic=a309e145-a9ad-4c50-9301-fa80aa66de7a; ARRAffinity=ae1ac20162ce072aa51dc1a79864331918bbcefc722f5f94ac9e9d88fa27a8d7; ai_session=kEcZO|1517391152882.778|1517391152882.778; optimizelyEndUserId=oeu1505452352296r0.08423786386787763; MC1=GUID=b9922ea4b1b19d44bd37734439266b42&HASH=a42e&LV=201709&V=4&LU=1505452354631; MSFPC=ID=7adc25b326ed6644b712bba955a77862&CS=1&LV=201709&V=1; MUID=36F073E5B13662192FA9791BB536647D; optimizelySegments=%7B%223335660429%22%3A%22false%22%2C%223350720654%22%3A%22none%22%2C%223351280313%22%3A%22edge%22%2C%223352180390%22%3A%22referral%22%7D; optimizelyBuckets=%7B%7D; A=I&I=AxUFAAAAAACRCAAAACskqjJOPpMeSwKvXeLGAg!!&V=4; smcflighting=100");
        requestHeaders.add("Host", "academic.microsoft.com");
        requestHeaders.add("Origin", "academic.microsoft.com");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        return requestHeaders;
    }


    /**
     * Microsoft Academic Http Request Header
     */
    public static HttpHeaders getMsaApiHttpHeaders() {
        String key1 = "0568f89860794b55957e1a2e6ad6fbbd";
//        String key2 = "21653f7d1372483daee79f28f5529795";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", "*/*");
        requestHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        requestHeaders.add("ocp-apim-subscription-key", key1);
        return requestHeaders;

    }

    public static SimpleClientHttpRequestFactory getLongHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15 * 1000);
        requestFactory.setReadTimeout(15 * 1000);
        return requestFactory;
    }

    public static String getGoogleScholarSearchPage(String query) {
        SimpleClientHttpRequestFactory requestFactory = HttpRequestFactory.getLongHttpRequestFactory();
        requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 1080)));
        return getGooglePage(GOOGLE_SCHOLAR_BASE_URL, query, requestFactory);
    }

    public static String getGoogleSearchPage(String query) {
        return getGooglePage(GOOGLE_BASE_URL, query, HttpRequestFactory.getLongHttpRequestFactory());
    }

    public static String getGooglePage(String url, String query, ClientHttpRequestFactory requestFactory) {
        String html;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(requestFactory);
            HttpEntity<Void> entity = new HttpEntity<>(HttpRequestFactory.getGoogleHttpHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, query);
            html = response.getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
        return html;
    }

    public static String postMsaSearchPage(String query) {
        String json;
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = HttpRequestFactory.getLongHttpRequestFactory();
        Proxy proxy = ProxyPool.getNextProxy();
        if (proxy == null) {
            return null;
        }
        requestFactory.setProxy(proxy);
        restTemplate.setRequestFactory(requestFactory);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("Query", query);
        form.add("Limit", "8");
        HttpEntity<?> entity = new HttpEntity<Object>(form, getMsaHttpHeaders());

        try {
            ResponseEntity<String> response = restTemplate.exchange(MSA_BASE_URL, HttpMethod.POST, entity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOGGER.warn("Access error", response.toString());
                return null;
            }
            json = response.getBody();
        } catch (RestClientException | NoSuchElementException e) {
            LOGGER.warn("Http error: {}", e.getMessage());
            restTemplate.getRequestFactory();
            return null;
        }
        ProxyPool.addBackProxy(proxy);
        return json;
    }

    public static String getMsaApiPage(String query) {
        String url = "https://westus.api.cognitive.microsoft.com/academic/v1.0/evaluate?" + query;

        SimpleClientHttpRequestFactory requestFactory = HttpRequestFactory.getLongHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);

        HttpEntity<?> entity = new HttpEntity<>(getMsaApiHttpHeaders());
        String json;
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                LOGGER.warn("Access error", response.toString());
                return null;
            }
            json = response.getBody();
        } catch (RestClientException | NoSuchElementException e) {
            LOGGER.warn("Http error: {}", e.getMessage());
            restTemplate.getRequestFactory();
            return null;
        }
        return json;
    }

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv6Addresses", "true");

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://v4v6.ipv6-test.com/api/myip.php", String.class);
        System.out.println(result);
    }
}
