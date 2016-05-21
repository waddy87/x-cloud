package org.waddys.xcloud.common.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomUtil {
    private static Random randGen = new Random();
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();
    private static int LENGTH = 6;
    private static long CYCLE = 10000L;

    private static void sleep() {
        // 空循环，确保该方法执行了 1 毫秒
        for (int i = 0; i < 10000; i++) {
        }
    }

    /**
     * 使用系统时间和随机数产生一个与时间相关的一个随机数<br>
     * 当前采用策略，当前时间（毫秒级）去掉头两位+4为随机数<br>
     * 保证ID在长度15<br>
     * 由于开发机的CPU获取的系统时间精度为16毫秒，所以不能够保证每毫秒的四位随机，仅能够保证每16毫秒的四位随机
     * 
     * @return 产生的随机数
     */
    public static long generateLongRandomWithTime() {
        // 获取当前时间，并模一个CYCLE，然后再补充到LENGTH位
        long time = System.currentTimeMillis() % CYCLE;
        int length = String.valueOf(time).length();
        Long max = Math.round(Math.pow(10, LENGTH - length));

        // 获取一个随机数字
        long v = randGen.nextInt(max.intValue() - 1);

        // 组合两个值，生成一个新的与时间有关随机值
        // 组合方式为字符串形式加和
        long n = time * max + v;
        return n;
    }
    
    public static String generateTimeSeq() {
        sleep();
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + generateNumericStrRandom(2);
    }

    public static String generateTimeSeq(String prefix) {
        sleep();
        StringBuilder sb = new StringBuilder(prefix);
        sb.append("_").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))
                .append(generateNumericStrRandom(2));
        return sb.toString();
    }

    public static String generateNumericStrRandomWithTime(String pre) {
        if (pre == null) {
            pre = "";
        }
        StringBuilder sb = new StringBuilder(pre);
        return sb.append("_").append(generateLongRandomWithTime()).toString();
    }

    public static void main(String[] args) {
        String str1 = generateTimeSeq("waret");
        String str2 = generateTimeSeq("waret");
        System.out.println(str1);
        System.out.println(str2);
    }

    /**
     * @param digit
     *            随机数的位数
     * @return 返回数字字符串
     */
    public static String generateNumericStrRandom(int digit) {
        if (digit <= 0) {
            throw new RuntimeException("Random number digit is negative!");
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < digit; j++) {
            sb.append(randGen.nextInt(10));
        }
        return sb.toString();
    }

    public static final String generateStrRandom(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(numbersAndLetters.length)];
        }
        return new String(randBuffer);
    }
}
