package com.hth.java;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hth on 2017/2/22.
 */
public class TrieTree {
    Node root;
    public TrieTree(String name) {
        root = new Node(name);
        root.setFre(0);
        root.setEnd(false);
        root.setRoot(true);
    }
    public void insert(String word) {
        Node node = root;
        char[] words = word.toCharArray();
        for (int i = 0; i < words.length; i++) {
            if (node.getChildrens().containsKey(words[i] + "")) {
                if (i == words.length - 1) {
                    Node endNode = node.getChildrens().get(words[i] + "");
                    endNode.setFre(endNode.getFre() + 1);
                    endNode.setEnd(true);
                }
            } else {
                Node newNode = new Node(words[i] + "");
                if (i == words.length - 1) {
                    newNode.setFre(1);
                    newNode.setEnd(true);
                    newNode.setRoot(false);
                }

                node.getChildrens().put(words[i] + "", newNode);
            }

            node = node.getChildrens().get(words[i] + "");
        }
    }
    public int searchFre(String word) {
        int fre = -1;

        Node node = root;
        char[] words = word.toCharArray();
        for (int i = 0; i < words.length; i++) {
            if (node.getChildrens().containsKey(words[i] + "")) {
                node = node.getChildrens().get(words[i] + "");
                fre = node.getFre();
            } else {
                fre = -1;
                break;
            }
        }

        return fre;
    }
    public String splitSpell(String spell) {
        Node node = root;
        char[] letters = spell.toCharArray();
        String spells = "";
        for (int i = 0; i < letters.length; i++) {
            if (node.getChildrens().containsKey(letters[i] + "")) {
                spells += letters[i];
                node = node.getChildrens().get(letters[i] + "");
            } else {
                node = root;
                spells += " ";
                i--;
            }
        }

        return spells;
    }

    public static String splitSpell1(String s) {
        String regEx = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
        int tag = 0;
        String spell = "";
        List<String> tokenResult = new LinkedList<String>();
        for (int i = s.length(); i > 0; i = i - tag) {
            Pattern pat = Pattern.compile(regEx);
            Matcher matcher = pat.matcher(s);
            matcher.find();
            spell += (matcher.group() + " ");
            tag = matcher.end() - matcher.start();
            tokenResult.add(s.substring(0, 1));
            s = s.substring(tag);
        }

        return spell;
    }

    /**
     * 3.返回Trie中某一节点被添加的次数
     此功能的应用点在于，词频统计。
     我们在每次新增一个元素时都会在原来的基本上，对词频进行自增处理。
     如果新增的词在之前的字典树中是不存在的，就设置初始值为1，如果原本有这个节点，就在原来的词频上+1.在上一步(插入一个新的节点元素)中可以看到具体操作。
     那么这里介绍一下查询词频的操作。代码如下：
     * @param number
     * @return
     */
//    public int searchFre(String number) {
//        int fre = -1;
//
//        Node node = root;
//        char[] numberCells = number.toCharArray();
//        for (int i = 0; i < numberCells.length; i++) {
//            int num = Integer.parseInt(String.valueOf(numberCells[i]));
//            if (node.getChildren()[num] != null) {
//                node = node.getChildren()[num];
//                fre = node.getFre();
//            } else {
//                fre = -1;
//                break;
//            }
//        }

//        return fre;
    public static void main(String[] args) {
        TrieTree tree = new TrieTree("test");
        shudy(tree);
        System.out.println("qin:::"+tree.splitSpell("qinshimingyuezhijunlintianxia"));

        String qinshimingyuezhijunlintianxia = splitSpell1("qinshimingyuezhijunlintianxia");
        System.out.println(qinshimingyuezhijunlintianxia);
    }

    private static void shudy(TrieTree tree) {
        tree.insert("qin");
        tree.insert("shi");
        tree.insert("ming");
        tree.insert("yue");
        tree.insert("zhi");
        tree.insert("jun");
        tree.insert("jung");
        tree.insert("lin");
        tree.insert("tian");
        tree.insert("xia");
    }
}
