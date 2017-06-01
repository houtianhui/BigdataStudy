package com.hth.java.hash;

/**
 * Created by hth on 2017/3/1.
 */
public class HashArithmetic {
    public static void main(String[] args) {
        String str = "候";
        int str1 = 20505;
        char str2 = (char) str1;
//        System.out.println(str2);
//        System.out.println(Integer.toBinaryString(str1>>4));
//        System.out.println(Integer.toBinaryString(str1));
//        System.out.println(str.hashCode());
//        System.out.println(0+str.charAt(0));
//        System.out.println( additiveHash("候天辉",1000));
        System.out.println( rotatingHash("候天辉",1000));



    }

    static int additiveHash(String key, int prime)
    {
        int hash, i;
        for (hash = key.length(), i = 0; i < key.length(); i++)
            hash += key.charAt(i);
        return (hash % prime);
    }
    static int rotatingHash(String key, int prime)
    {
        int hash, i;
        for (hash=key.length(), i=0; i<key.length(); ++i){
            System.out.println(Integer.toBinaryString(hash<<4));
            System.out.println(Integer.toBinaryString(hash>>28));
            System.out.println(Integer.toBinaryString(key.charAt(i)));
            hash = (hash<<4)^(hash>>28)^key.charAt(i);
            System.out.println(Integer.toBinaryString(hash));
        }
        if((i&1) == 0)
        {
            hash ^= (hash<<7) ^ key.charAt(i) ^ (hash>>3);
        }
        return (hash % prime);
    }

}
