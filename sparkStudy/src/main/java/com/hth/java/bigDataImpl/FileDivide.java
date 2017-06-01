package com.hth.java.bigDataImpl;

import java.io.IOException;

/**
 * Created by hth on 2017/3/3.
 * 大数据算法：对5亿数据进行排序
 * 思路分析：
 拿到这样的一个问题，你的第一感觉是什么？冒泡排序？选择排序？插入排序？堆排？还是快排？可能你的想法是我的内存不够。的确，这么大的一个数据量，我们的内存的确不够。
 因为单是5亿的整数数据就有3.7个G(别说你是壕，内存大着呢)。既然内存不够，那么我们要怎么来解决呢？
 要知道我们一步做不了的事，两步总能做到。那么我们就来尝试第一步做一些，剩下的一些就等会再来搞定吧。基于这样的思路，就有下面的一个解题方法——分治！
 1.分治——根据数据存在文件中的位置分裂文件到批量小文件中
 相对于朴素的排序，这是一种比较稳妥的解决方法。因为数据量太大了！我们不得不将大事化小，小事化了。
 这里我们的做法是每次读取待排序文件的10000个数据，把这10000个数据进行快速排序，再写到一个小文件bigdata.part.i.sorted中。这样我们就得到了50000个已排序好的小文件了。
 在有已排序小文件的基础上，我只要每次拿到这些文件中当前位置的最小值就OK了。再把这些值依次写入bigdata.sorted中。
 2.分治——根据数据自身大小分裂文件到批量小文件中
 按照数据位置进行分裂大文件也可以。不过这样就导致了一个问题，在把小文件合并成大文件的时候并不那么高效。那么，这里我们就有了另一种思路：我们先把文件中的数据按照大小把到不同的文件中。

 再对这些不同的文件进行排序。这样我们可以直接按文件的字典序输出即可。
 3.字典树
 关于字典树的基本使用，大家可以参见本人的另一篇博客：《数据结构：字典树的基本使用》
 基于《数据结构：字典树的基本使用》这篇博客中对字典序的讲解，我们知道我们要做就是对字典树进行广度优先搜索。

 */
public class FileDivide {
    /**
     *
     * 0.分治
     (0)分割大文件
     此步对大文件的分割是按序进行的，这样我们就可以确保数据的离散化，不会让一个小文件中的数据很多，一个小文件的数据很少。
     * @throws IOException
     */
//    public static void splitBigFile2PartBySerial(String filePath, String partPath) throws IOException {
//        File file = new File(filePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        StringBuffer buffer = new StringBuffer();
//
//        String readerLine = "";
//        int line = 0;
//        while ((readerLine = reader.readLine()) != null) {
//            buffer.append(readerLine + " ");
//            if (++line % Config.PART_NUMBER_COUNT == 0) {
//                sortStringBuffer(buffer);
//                int splitLine = line / Config.PART_NUMBER_COUNT;
//                write(partPath.replace("xxx", "" + splitLine), buffer.toString());
//                buffer.setLength(0);
//                System.out.println("SPLIT: " + splitLine);
//            }
//        }
//
//        reader.close();
//    }

    /**
     * (1)排序
     即使是已经切割成小份的了，不过每个小文件中的数据集仍然有50000个。
     因为50000个数据也不是一个小数据，在排序的过程中，也会有一些讲究，所有这里我们使用的是快排。如下：
     */
//    public static void sortStringBuffer(StringBuffer buffer) {
//        String[] numberTexts = buffer.toString().split(" ");
//        buffer.setLength(0);
//        int[] numbers = new int[numberTexts.length];
//        for (int i = 0; i < numberTexts.length; i++) {
//            numbers[i] = Integer.parseInt(numberTexts[i]);
//        }
//
//        int[] sorted = QKSort.quickSort(numbers);
//        for (int i = 0; i < sorted.length; i++) {
//            buffer.append(sorted[i] + "\n");
//        }
//    }

    /**
     * (2)合并
     在合并的时候，我们要明确一个问题。虽然我们的单个小文件已经有序，不过我们还并不知道整体的顺序。比如：
     文件1:1 2 4 6 9 34 288
     文件2:4 5 6 87 99 104 135
     上面的两个文件虽然每个文件内部已经有序，不过整体来说，是无序的。对于在单个文件有序的基础上，我们可以做一些事情。我们可以把每个文件中的数据看成是一个队列，我们总是从队列的首部开始进行出队(因为队列的头部总是最小的数)。
     这样，我们就把问题转化成从N个小文件中依次比较，得到最小的结果并记入文件(当然，我们不可以生成一个数就写一次文件，这样太低效了，我们可以使用一个变量缓存这此"最小值"，在累计到一定数量之后再一次性写入。再清空变量，循环反复，直到文件全部写入完毕)。
     * @param dirPath
     * @throws NumberFormatException
     * @throws IOException
     */
//    public static void mergeSorted(String dirPath) throws NumberFormatException, IOException {
//        long t = System.currentTimeMillis();
//
//        File dirFile = new File(dirPath);
//        File[] partFiles = dirFile.listFiles();
//
//        FileInputStream[] inputStreams = new FileInputStream[partFiles.length];
//        BufferedReader[] readers = new BufferedReader[partFiles.length];
//
//        int[] minNumbers = new int[partFiles.length];
//        for (int i = 0; i < partFiles.length; i++) {
//            inputStreams[i] = new FileInputStream(partFiles[i]);
//            readers[i] = new BufferedReader(new InputStreamReader(inputStreams[i]));
//            minNumbers[i] = Integer.parseInt(readers[i].readLine());
//        }
//
//        int numberCount = Config.TOTAL_NUMBER_COUNT;
//        while (true) {
//            int index = Tools.minNumberIndex(minNumbers);
//            System.out.println(minNumbers[index]);
//            write(Config.BIGDATA_NUMBER_FILEPATH_SORTED, minNumbers[index] + "\n");
//            minNumbers[index] = Integer.parseInt(readers[index].readLine());
//
//            if (numberCount-- <= 0) {
//                break;
//            }
//        }
//
//        System.err.println("TIME: " + (System.currentTimeMillis() - t));
//
//        for (int i = 0; i < partFiles.length; i++) {
//            inputStreams[i].close();
//            readers[i].close();
//        }
//    }
    /**
     * 注：这里关于分治的算法，我就只提供一种实现过程了。可能从上面的说明中，大家也意识到了一个问题，如果我们把大文件中的数据按照数值大小化分到不同的小文件中。
     * 这样会有一个很致命的问题，那就是可能我们的小文件会出现两极分化的局面，即有一部分文件中的数据很少，有一部分小文件中的数据很多。
     * 所以，这里我就不再提供实现过程，在上面有所说明，只是想说我们在解决问题的时候，可能会有很多不同的想法，这些想法都很好，只是有时我们需要一个最优的来提升逼格(^_^)。
     */


    /**
     * 1.字典树
     因为我们知道字典树是可以压缩数据量的一种数据结构，尤其是针对那么使用的字符串个数有限(比如：'0','1','2','3','4','5','6','7','8','9')，并整体数量很多的情况。因为我们可以可以让同一个字符重复使用多次。
     比如："123456789"和"123456780"其实只使用了'0'-'9'这10个字符而已。关于字典树的实现，我想是很简单的一种方法。如果你还是感觉有些朦胧和模糊的话，就请参见本人的另一篇博客《数据结构：字典树的基本使用》，在那一篇博客中，我有很详细地介绍对字典树的各种基本操作及说明。
     这里我还是贴出一部分关键的代码，和大家一起学习吧。代码如下：
     */

//    将数据记入文件
//    public static void sortTrie(String filePath) throws IOException {
//        File file = new File(filePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        TrieTree tree = new TrieTree("sorting");
//        String readerLine = "";
//        int line = 0;
//        while ((readerLine = reader.readLine()) != null) {
//            tree.insert(readerLine);
//            if (++line % Config.PART_NUMBER_COUNT == 0) {
//                System.out.println("LINE: " + line);
//            }
//        }
//
//        System.out.println("文件读取完毕");
//
//        writeTrieTree2File(Config.BIGDATA_NUMBER_FILEPATH_SORTED, tree);
//
//        reader.close();
//    }
//    (1)对字典树进行广度优先搜索
//    public static void sortNumberOrder(String filePath, Node node) throws IOException {
//        Queue<Node> queuing = new LinkedList<Node>();
//        queuing.offer(node);
//
//        while (!queuing.isEmpty()) {
//            Node currentNode = queuing.poll();
//            if (currentNode.isEnd()) {
//                buffer.append(getNodePath(currentNode) + "\n");
//                if (++index % 50000 == 0) {
//                    write(filePath, buffer.toString());
//                }
//            }
//            Node[] children = currentNode.getChildren();
//            for (Node sonNode : children) {
//                if (sonNode != null) {
//                    queuing.offer(sonNode);
//                }
//            }
//        }
//    }

    /**
     * 获得某一节点的上层节点，即前缀字符串
     * @param node
     * @return
     */
//    public static String getNodePath(Node node) {
//        StringBuffer path = new StringBuffer();
//        Node currentNode = node;
//        while (currentNode.getParent() != null) {
//            path.append(currentNode.getName());
//            currentNode = currentNode.getParent();
//        }
//
//        return path.reverse().toString();
//    }
    /**
     * 小结：
     在大数据的探索还远不止于此。还有很多东西等着我们去了解，去发现，以及创造。
     而对于大量数据的问题，我们可以利用分治来化解它的大，从而可以更方便地去观察全局。也可以使用我们已经学习过的一些数据结构及算法来求解问题。
     不过随着我们不断地学习，不断地探索，我们可能会感觉到自己学习的一些固有的数据结构和算法并不能完全地解决我们遇到的问题，那么就需要我们的创造力了。
     */

}
