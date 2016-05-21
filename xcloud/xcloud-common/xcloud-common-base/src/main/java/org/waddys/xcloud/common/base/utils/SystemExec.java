package org.waddys.xcloud.common.base.utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemExec {
    public static int PingTimeOut = 1000;
    private static Logger logger = LoggerFactory.getLogger(SystemExec.class);

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        List<String> list = new ArrayList<String>();
        list.add("10.0.30.254");
        list.add("1.2.3.4");
        list.add("1.1.1.1");
        list.add("127.0.0.1");
        System.out.println(checkConnect(list));
        System.out.println(System.currentTimeMillis());
    }

    public static boolean checkConnect(String ip) {
        if (ip == null || ip.trim().length() == 0) {
            logger.debug("IP Parameter is empty!");
            return false;
        }

        try {
            if (InetAddress.getByName(ip).isReachable(PingTimeOut)) {
                logger.debug("ip " + ip + " is reachable");
                return true;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    public static boolean checkConnect(List<String> ips) {
        try {
            if (ips != null && false == ips.isEmpty()) {
                return _checkConnect2(ips);
            }
        } catch (Exception ignore) {
        }
        return false;
    }

    public static boolean checkConnect(String[] ips) {
        try {
            if (ips != null && ips.length > 0) {
                return _checkConnect2(Arrays.asList(ips));
            }
        } catch (Exception ignore) {
        }
        return false;
    }

    public static class PingThread implements Callable<Boolean> {
        private String ip;

        public PingThread(String ip) {
            this.ip = ip;
        }

        @Override
        public Boolean call() throws Exception {
            return checkConnect(ip);
        }
    }

    private static boolean _checkConnect2(List<String> ips) throws InterruptedException {
        int size = ips.size();
        ExecutorService pool = Executors.newFixedThreadPool(size);
        CompletionService<Boolean> ecs = new ExecutorCompletionService<Boolean>(pool);
        List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>(size);
        List<PingThread> threads = new ArrayList<PingThread>(size);
        for (String ip : ips) {
            threads.add(new PingThread(ip));
        }

        try {
            for (Callable<Boolean> s : threads) {
                futures.add(ecs.submit(s));
            }
            // 不再提交新的任务
            pool.shutdown();

            for (int i = 0; i < size; ++i) {
                try {
                    Boolean r = ecs.take().get();
                    if (r != null && r) {
                        return true;
                    }
                } catch (ExecutionException ignore) {
                }
            }
        } finally {
            for (Future<Boolean> f : futures) {
                f.cancel(true);
            }
        }
        return false;
    }

}
