package com.hth.java;

/**
 * Created by hth on 2017/2/22.
 */
public class TireTest {
    public static void main(String[] args) {
        TrieTree tree = new TrieTree("test");
//        System.out.println("qin:::"+tree.splitSpell("qinshimingyuezhijunlintianxia"));
        tree.insert("word");
        tree.insert("worrd");
        tree.insert("hello");
        tree.insert("hi");
        System.out.println("word " + tree.searchFre("word"));
        System.out.println("hello " + tree.searchFre("hello"));
        System.out.println("hi " + tree.searchFre("hi"));
        System.out.println("hell " + tree.searchFre("hell"));
        System.out.println("hellt " + tree.searchFre("hellt")); // qinshimingyuezhijunlintianxia
    }
}
