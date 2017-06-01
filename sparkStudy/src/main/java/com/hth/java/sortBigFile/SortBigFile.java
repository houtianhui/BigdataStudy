package com.hth.java.sortBigFile;//package com.camelot.hth.sortBigFile;
//
//import com.camelot.hth.Node;
//import com.camelot.hth.TrieTree;
//import jdk.nashorn.internal.runtime.regexp.joni.Config;
//
//import java.io.*;
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * Created by hth on 2017/2/24.
// */
//public class SortBigFile {
////
////    public static void splitBigFile2PartBySerial(String filePath, String partPath) throws IOException {
////        File file = new File(filePath);
////        FileInputStream inputStream = new FileInputStream(file);
////        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
////
////        StringBuffer buffer = new StringBuffer();
////
////        String readerLine = "";
////        int line = 0;
////        while ((readerLine = reader.readLine()) != null) {
////            buffer.append(readerLine + " ");
////            if (++line % Config.PART_NUMBER_COUNT == 0) {
////                sortStringBuffer(buffer);
////                int splitLine = line / Config.PART_NUMBER_COUNT;
////                write(partPath.replace("xxx", "" + splitLine), buffer.toString());
////                buffer.setLength(0);
////                System.out.println("SPLIT: " + splitLine);
////            }
////        }
////
////        reader.close();
////    }
////    public static void sortStringBuffer(StringBuffer buffer) {
////        String[] numberTexts = buffer.toString().split(" ");
////        buffer.setLength(0);
////        int[] numbers = new int[numberTexts.length];
////        for (int i = 0; i < numberTexts.length; i++) {
////            numbers[i] = Integer.parseInt(numberTexts[i]);
////        }
////
////        int[] sorted = QKSort.quickSort(numbers);
////        for (int i = 0; i < sorted.length; i++) {
////            buffer.append(sorted[i] + "\n");
////        }
////    }
////    public static void mergeSorted(String dirPath) throws NumberFormatException, IOException {
////        long t = System.currentTimeMillis();
////
////        File dirFile = new File(dirPath);
////        File[] partFiles = dirFile.listFiles();
////
////        FileInputStream[] inputStreams = new FileInputStream[partFiles.length];
////        BufferedReader[] readers = new BufferedReader[partFiles.length];
////
////        int[] minNumbers = new int[partFiles.length];
////        for (int i = 0; i < partFiles.length; i++) {
////            inputStreams[i] = new FileInputStream(partFiles[i]);
////            readers[i] = new BufferedReader(new InputStreamReader(inputStreams[i]));
////            minNumbers[i] = Integer.parseInt(readers[i].readLine());
////        }
////
////        int numberCount = Config.TOTAL_NUMBER_COUNT;
////        while (true) {
////            int index = Tools.minNumberIndex(minNumbers);
////            System.out.println(minNumbers[index]);
////            write(Config.BIGDATA_NUMBER_FILEPATH_SORTED, minNumbers[index] + "\n");
////            minNumbers[index] = Integer.parseInt(sareaders[index].readLine());
////
////            if (numberCount-- <= 0) {
////                break;
////            }
////        }
////
////        System.err.println("TIME: " + (System.currentTimeMillis() - t));
////
////        for (int i = 0; i < partFiles.length; i++) {
////            inputStreams[i].close();
////            readers[i].close();
////        }
////    }
//public static void sortTrie(String filePath) throws IOException {
//    File file = new File(filePath);
//    FileInputStream inputStream = new FileInputStream(file);
//    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//    TrieTree tree = new TrieTree("sorting");
//    String readerLine = "";
//    int line = 0;
//    while ((readerLine = reader.readLine()) != null) {
//        tree.insert(readerLine);
//        if (++line % Config.PART_NUMBER_COUNT == 0) {
//            System.out.println("LINE: " + line);
//        }
//    }
//
//    System.out.println("文件读取完毕");
//
//    writeTrieTree2File(Config.BIGDATA_NUMBER_FILEPATH_SORTED, tree);
//
//    reader.close();
//}
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
//
//    /**
//     * 获得某一节点的上层节点，即前缀字符串
//     * @param node
//     * @return
//     */
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
//}
