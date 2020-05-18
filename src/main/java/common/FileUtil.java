package common;

import java.io.*;

//帮我们更方便读写文件
public class FileUtil {
    //读文件
   public static String readFile(String filepath){
       try(FileReader fileReader=new FileReader(filepath);
           BufferedReader bufferedReader=new BufferedReader(fileReader)){
           StringBuilder stringBuilder=new StringBuilder();
           //按行读取文件
           String line="";
           while((line=bufferedReader.readLine())!=null){
               stringBuilder.append(line);
           }
           return stringBuilder.toString();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
   public  static void  witerFile(String filepath,String content){
       try(FileWriter fileWriter=new FileWriter(filepath)){
           fileWriter.write(content);
       }catch (IOException e){
           e.printStackTrace();
       }
   }
}
