package com.hqtc.common.utils;

import java.util.*;

/**
 * @Description:
 * @Author: WangBin
 * @Date: 2018/9/21 14:00
 */
public class UUIDUtil {
    /**
     * 生成默认32位随机数
     * @return
     */
    public static String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成指定长度随机数
     * @param n
     * @return
     */
    public static String generate(int n) {
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pwd.append(str[Math.abs(r.nextInt(str.length))]);
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度字母
     * @param n
     * @return
     */
    public static String generateChar(int n) {
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pwd.append(str[Math.abs(r.nextInt(str.length))]);
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度字母去除I,O
     * @param n
     * @return
     */
    public static String generateCharButIO(int n) {
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pwd.append(str[Math.abs(r.nextInt(str.length))]);
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度数字
     * @param n
     * @return
     */
    public static String generateNumber(int n) {
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pwd.append(str[Math.abs(r.nextInt(str.length))]);
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度数字去除0,1
     * @param n
     * @return
     */
    public static String generateNumberButo1(int n) {
        char[] str = {'2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            pwd.append(str[Math.abs(r.nextInt(str.length))]);
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度数字和字母
     * @param n
     * @param c
     * @return
     */
    public static String generateNumberAndChar(int n,int c){

        String number=generateNumber(n);
        String character=generateChar(c);
        String str=number+character;
        char[] array=str.toCharArray();
        int length=n+c;
        List<Character> list=new LinkedList<Character>();
        for(int i=0;i<length;i++){
            list.add(array[i]);
        }
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        int tmp=length;
        for (int i = 0; i < length; i++) {
            pwd.append(list.remove(r.nextInt(tmp)));
            tmp=tmp-1;
        }
        return pwd.toString();
    }
    /**
     * 生成指定长度数字和字母,去除0，1，I,O
     * @param n
     * @param c
     * @return
     */
    public static String generateNumberAndCharButo1IO(int n,int c){

        String number=generateNumberButo1(n);
        String character=generateCharButIO(c);
        String str=number+character;
        char[] array=str.toCharArray();
        int length=n+c;
        List<Character> list=new LinkedList<Character>();
        for(int i=0;i<length;i++){
            list.add(array[i]);
        }
        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        int tmp=length;
        for (int i = 0; i < length; i++) {
            pwd.append(list.remove(r.nextInt(tmp)));
            tmp=tmp-1;
        }
        return pwd.toString();
    }
    public static void main(String args[]){
        System.out.println(generateNumberAndCharButo1IO(10,6));
    }
}
