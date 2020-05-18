package compile;

import common.FileUtil;

import java.io.File;
import java.io.IOException;

//借助这个类来描述一次编译运行的过程
public class Task {
    private static final String WORK_DIR="./tmp/";//临时文件都放到tmp
    private static final String CLASS="Solution";//要编译代码的类名
    private static final String CODE=WORK_DIR+"Solution.java";//要编译的文件名,与类名保持一致
    private static final String STDIN=WORK_DIR+"stdin.txt";//标准输入对应文件
    private static final String STDOUT=WORK_DIR+"stdout.txt";//标准输出对应文件
    private static final String STDERR=WORK_DIR+"stderr.txt";//标准错误对应文件
    //编译错误对应的文件
    private static final String COMPILE_ERROR=WORK_DIR+"compile_error.txt";
    public Answer compileAndRun(Question question) throws
            IOException, InterruptedException {
        Answer answer=new Answer();
        //0.先创建好存放临时文件的目录
        File workDir=new File(WORK_DIR);
        if(!workDir.exists()){
            workDir.mkdirs();
        }
        //1.根据Question对象,构造需要的一些临时文件
        FileUtil.witerFile(CODE,question.getCode());
        FileUtil.witerFile(STDIN,question.getStdin());
        //2.构造编译环境
        //String cmd="javac-encoding utf8"+CODE+" -d"+WORK_DIR;
        String cmd=String.format("javac -encoding utf8 %s -d %s",CODE,WORK_DIR);
        System.out.println("编译命令"+cmd);
        CommandUtil.run(cmd,null,COMPILE_ERROR);
        //判定编译是否出错 COMPILE_ERROR文件为空则顺利,非空为出错
        String compileError=FileUtil.readFile(COMPILE_ERROR);
        if(!"".equals(compileError)){
            //编译出错
            System.out.println("编译出错");
            answer.setErr(1);
            answer.setReason(compileError);
            return answer;
        }
        //3.构造运行环境
        cmd=String.format("java -classpath %s %s",WORK_DIR,CLASS);
        System.out.println("运行命令"+cmd);
        CommandUtil.run(cmd,STDOUT,STDERR);
        //判定运行是否出错
        String stdError=FileUtil.readFile(STDERR);
        if(!"".equals(stdError)){
            System.out.println("运行出错");
            answer.setErr(2);
            answer.setReason(stdError);
            answer.setStderr(stdError);
            return answer;
        }
        //4.将最终的运行结果包装到Answer中
        answer.setErr(0);
        answer.setStdout(FileUtil.readFile(STDOUT));
        return answer;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Question question=new Question();
        question.setCode(
                "public class Solution{\n"+
                "public static void main(String[] args) {\n"+
                 "String s=null;"+
                "System.out.println(s.length());\n"+
                "}\n"+
                "}\n"
        );
        question.setStdin("");
        Task task=new Task();
        Answer answer=task.compileAndRun(question);
        System.out.println(answer);
    }
}
