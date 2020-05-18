package compile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//借助这个类,让java代码能够去执行一个具体的命令
public class CommandUtil {
    //cmd 要执行的命令
    //stdoutFile 标准输出重定向的文件
    //stderrFile 标准错误重定向的文件
    public static int  run(String cmd,String stdoutFile
            ,String stderrFile) throws IOException, InterruptedException {
        //获取Runtime对象, Runtime对象是一个单例的
        Runtime runtime=Runtime.getRuntime();
        //通过Runtime对象中的exec方法来执行一个命令.
        Process process=runtime.exec(cmd);
        //针对标准输出进行重定向
        if(stdoutFile!=null){
            //进程的标准输出中的结果通过这个InputStream获取
            InputStream inputStream=process.getInputStream();
            OutputStream outputStream=new FileOutputStream(stdoutFile);
            int ch=-1;
            while((ch=inputStream.read())!=-1){
                outputStream.write(ch);
            }
            inputStream.close();
            outputStream.close();
        }
        //标准错误进行重定向
        if(stderrFile!=null){
            InputStream inputStream=process.getErrorStream();
            OutputStream outputStream=new FileOutputStream(stderrFile);
            int ch=-1;
            while((ch=inputStream.read())!=-1){
                outputStream.write(ch);
            }
            inputStream.close();
            outputStream.close();
        }
        //为了确保子进程优先执行完,需要加上进程等待
        int exitcode =process.waitFor();
        return exitcode;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        run("javac","F:\\idea\\java-oj\\a.txt","F:\\idea\\java-oj\\b.txt");
    }
}
