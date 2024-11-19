package com.zjq.dailyrecord.tree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 这个程序使用FileWriter类来创建和写入Markdown文件。它会将目录树结构写入到directory_tree.md文件中，同时排除.git和.idea目录。
 *
 * 要使用这个程序，请按照以下步骤操作：
 *
 * 将上述代码保存到一个名为DirectoryTreePrinter.java的文件中。
 * 打开命令提示符（CMD）并导航到保存该文件的目录。
 * 使用javac DirectoryTreePrinter.java命令编译Java程序。
 * 编译成功后，使用java DirectoryTreePrinter <path>命令运行程序，其中<path>是你想要打印树结构的目录路径。
 * 例如，如果你的Java项目位于C:\Projects\MyJavaProject，你可以这样运行程序：
 *
 * bash：
 * java DirectoryTreePrinter C:\Projects\MyJavaProject
 * 这将创建一个名为directory_tree.md的文件，其中包含从C:\Projects\MyJavaProject开始的目录树结构，同时排除.git和.idea目录。
 * @author zjq
 */
public class DirectoryTreePrinter {

    public static void printTree(File file, int level, FileWriter writer) throws IOException {
        if (!file.exists()) {
            writer.write("The path does not exist: " + file.getPath());
            return;
        }

        // 打印当前目录或文件
        for (int i = 0; i < level; i++) {
            writer.write("    ");
        }
        if (file.isFile()) {
            writer.write(file.getName() + "\n");
        } else {
            writer.write(file.getName() + "/\n");
        }

        // 如果是目录，递归打印子目录
        if (file.isDirectory()) {
            File[] files = file.listFiles(file1 -> !file1.getName().equals(".git") && !file1.getName().equals("target") && !file1.getName().equals(".idea"));
            if (files != null) {
                for (File child : files) {
                    if (!child.getName().equals(".git") && !child.getName().equals("target") && !child.getName().equals(".idea")) {
                        printTree(child, level + 1, writer);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Usage: java DirectoryTreePrinter <path>");
//            return;
//        }
//        String path = args[0];

        try {
            File root = new File("D:\\work\\项目路径");
            FileWriter writer = new FileWriter("D:\\work\\directory_tree.md");
            printTree(root, 0, writer);
            writer.close();
            System.out.println("Directory tree has been written to directory_tree.md");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}