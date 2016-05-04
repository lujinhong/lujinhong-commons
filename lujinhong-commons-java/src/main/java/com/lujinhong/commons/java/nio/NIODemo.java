/**
 * 
 */
package com.lujinhong.commons.java.nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * date: 2016年5月4日 上午10:37:48
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年5月4日 上午10:37:48
 */

public class NIODemo {

	private static final String fileName = "/Users/liaoliuqing/Downloads/1.txt";
	private static final Path path = Paths.get(fileName);

	public static void main(String[] args) throws IOException {

		// Part1:读写文件

		// 向文件中写入内容
		Charset charset = Charset.forName("utf-8");
		try (BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.APPEND,
				StandardOpenOption.CREATE)) {
			writer.write("hello world\n");
			writer.write("中文");
		}

		// 读取文件内容
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			do {
				line = reader.readLine();
				if (line != null)
					System.out.println(line);
			} while (line != null);
		}

		// Part2: 获取路径和文件的信息
		/*
		 * 输出： name: liaoliuqing path: /Users/liaoliuqing/Downloads/1.txt
		 * absolute path: /Users/liaoliuqing/Downloads/1.txt parent:
		 * /Users/liaoliuqing/Downloads
		 */
		System.out.println("name: " + path.getName(1) + "\npath: " + path + "\nabsolute path: " + path.toAbsolutePath()
				+ "\nparent: " + path.getParent());
		// 输出：truefalsetruetrue
		System.out.println(
				"" + Files.exists(path) + Files.isHidden(path) + Files.isWritable(path) + Files.isWritable(path));

		BasicFileAttributes attribute = Files.readAttributes(path, BasicFileAttributes.class);
		// 输出： falsetruefalsefalse
		System.out.println("" + attribute.isDirectory() + attribute.isRegularFile() + attribute.isSymbolicLink()
				+ attribute.isOther());
		// 输出： 2016-05-04T03:05:14Z1232016-05-04T03:05:14Z2016-05-04T03:05:05Z
		System.out.println("" + attribute.lastModifiedTime() + attribute.size() + attribute.creationTime()
				+ attribute.lastAccessTime());
		
		//Part3：列出目录中的内容
		Path dir = Paths.get("/Users/liaoliuqing/Downloads");
		try(DirectoryStream<Path> dirstream = Files.newDirectoryStream(dir)){
			for(Path entry : dirstream){
				System.out.println("" + entry.getFileName() + entry.toAbsolutePath() );
			}
		}
		//以下只会输出以.txt结尾的文件
		try(DirectoryStream<Path> dirstream = Files.newDirectoryStream(dir, "*.txt")){
			for(Path entry : dirstream){
				System.out.println("" + entry.getFileName() + entry.toAbsolutePath() );
			}
		}
		
		//Part4: 列出目录树
		Files.walkFileTree(dir, new MyFileVistor());
		
		//Part5: 复制、移动、创建、删除文件
		Files.copy(path, Paths.get("/Users/liaoliuqing/Downloads/2.txt"));
		Files.move(Paths.get("/Users/liaoliuqing/Downloads/2.txt"), Paths.get("/Users/liaoliuqing/Downloads/3.txt"));
		Files.delete(Paths.get("/Users/liaoliuqing/Downloads/3.txt"));
		Files.createDirectories(Paths.get("/Users/liaoliuqing/Downloads/dir"));
		Files.createFile(Paths.get("/Users/liaoliuqing/Downloads/file"));
		Files.delete(Paths.get("/Users/liaoliuqing/Downloads/dir"));
		Files.delete(Paths.get("/Users/liaoliuqing/Downloads/file"));
		

	}

}

class MyFileVistor extends SimpleFileVisitor<Path>{
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		System.out.println(file.getFileName() + "====");
		return FileVisitResult.CONTINUE;
	}
}
