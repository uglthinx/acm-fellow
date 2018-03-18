package me.xiaff.crawler.acmfellow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProxyPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyPool.class);
//    private static List<Proxy> proxyList;

    private static Queue<Proxy> proxyQueue;

    private static int index = 0;

    public static synchronized Proxy getNextProxy() {
        if (CollectionUtils.isEmpty(proxyQueue)) {
//            proxyList = new ArrayList<>();
            proxyQueue = new ConcurrentLinkedQueue<>();
        }
        if (proxyQueue.size() < 20) {
            String jsonStr = new RestTemplate().getForObject("http://10.2.2.4:9000/api/proxy/get/{num}",
                    String.class, 200);
            JSONArray proxyJsonArray = JSON.parseArray(jsonStr);
            if (proxyJsonArray == null) {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException ignore) {
                }
                return null;
            }
            for (Object o : proxyJsonArray) {
                JSONObject proxyJson = (JSONObject) o;
                String host = proxyJson.getString("host");
                int port = proxyJson.getInteger("port");
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                proxyQueue.add(proxy);
            }
        }

        return proxyQueue.poll();
    }

    /**
     * Useful Proxy
     */
    public static void addBackProxy(Proxy proxy) {
        proxyQueue.add(proxy);
        LOGGER.info("Proxy pool size: {}.", proxyQueue.size());
    }
}
