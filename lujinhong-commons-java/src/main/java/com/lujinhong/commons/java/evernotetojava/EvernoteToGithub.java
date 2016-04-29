package com.lujinhong.commons.java.evernotetojava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


/*
 * 本类将马克飞象导出的md文章修改成符合github规范的md文件，主要是文件头内容。
 */
public class EvernoteToGithub {
	
	private static String everNoteDirLocation = "/Users/liaoliuqing/99_Project/Blog/md/";
	private static String githubDirLocation = "/Users/liaoliuqing/99_Project/git/lujinhong.github.io/_posts/";
	private static File everNoteDir = new File(everNoteDirLocation);
	
	public static void main(String[] args) throws IOException {
		System.out.println(evernoteToGithub());
		
	}
	
	public static String evernoteToGithub() throws IOException{
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
		if(!everNoteDir.isDirectory()){
			System.out.println("Please Enter the dir of evernote file");
			System.exit(-1);
		}
		String[] everNoteFiles = everNoteDir.list();
		for(String fileName : everNoteFiles){
			if(fileName.contains("DS_")) continue;
			String header = "---\n";
			File file = new File(everNoteDirLocation + fileName);
		     String modifiTime = sdf.format(file.lastModified());
			
			String newFileName = githubDirLocation + modifiTime.substring(0,10)+"-"+fileName;
			System.out.println("begin handle"  + everNoteDirLocation + fileName);
			//File file = new File(fileName);
			Scanner sc = new Scanner(new FileInputStream(everNoteDirLocation + fileName));
			String rawFileName = fileName.replace(".md", "");
			//原文中的内容
			String originalContent = "";
			//需要添加的内容，主要是jeklly的头部信息
			header += "layout: post\n";
			header += "tile:  \"" + rawFileName +"\"\n";
			header += "date:  " + modifiTime +"\n";
			while(sc.hasNext()){
				String line = sc.nextLine();
				
				if(line.startsWith("@(")){
					String[] categories = line.split("\\[")[1].split("\\|");
					header += "categories: ";
					for(String category : categories){
						if(category.endsWith("]")){
							category=category.substring(0,category.length()-1);
						}
						header += category+ " ";
					}	
					header +="\n";
				}else if(line.contains("[toc]")||line.contains(rawFileName)){
					
				}else{
					originalContent += (line+"\n");
				}

			}
			header += "excerpt: " + rawFileName +"\n";
			header += "---\n\n";
			header += "* content" +"\n";
			header += "{:toc}\n\n";
			header += originalContent;
			writeToNewFile(newFileName,header);
			System.out.println("generate "+ newFileName);
		}
		
		return "";
	}
	
	
	public static void writeToNewFile(String fileName, String content) throws IOException{
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.close();
	}
	
}


