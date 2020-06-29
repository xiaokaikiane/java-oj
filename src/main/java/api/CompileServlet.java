package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import compile.Answer;
import compile.Question;
import compile.Task;
import problem.Problem;
import problem.problemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class CompileServlet  extends HttpServlet {
    private Gson gson=new GsonBuilder().create();
    //创建两个辅助类,用来完成请求解析和响应构建
    //用于辅助解析body中的数据请求
    static  class CompileRequest{
       private int id;
       private String code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
    //用于辅助构造最终响应的body数据
    static class CompileResponse{
        private int ok;
        private String reason;
        private String stdout;

        public int getOk() {
            return ok;
        }

        public void setOk(int ok) {
            this.ok = ok;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getStdout() {
            return stdout;
        }

        public void setStdout(String stdout) {
            this.stdout = stdout;
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //1.读取请求的body的所有数据
        String body=readbody(req);
        //2.按照API约定的格式来解析JSON数据,的到CompileRequest对象
        CompileRequest compileRequest=gson.fromJson(body,CompileRequest.class);
        //3.按照id 从数据库读取对应的测试用例
        problemDAO problemDAO=new problemDAO();
        Problem problem=problemDAO.selectOne(compileRequest.getId());
        String testCode=problem.getTestCode();//得到测试用例
        String requestCode=compileRequest.getCode();//得到用户输入代码
        //4.把用户输入的代码和测试用例组装好
        String finalCode =mergeCode(requestCode,testCode);
        //5.创建task对象对组装好的代码进行编译运行
        Question question=new Question();
        question.setCode(finalCode);
        question.setStdin("");
        Task task=new Task();
        Answer answer=null;
        try{
            answer=task.compileAndRun(question);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //6.把运行结果构造成响应数据,并写会客户端
        CompileResponse compileResponse=new CompileResponse();
        compileResponse.setOk(answer.getErr());
        compileResponse.setReason(answer.getReason());
        compileResponse.setStdout(answer.getStdout());
        String JsonString=gson.toJson(compileResponse);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(JsonString);
    }

    private String mergeCode(String requestCode, String testCode) {
        //把testCode 中的main方法 内容嵌入到requestCode中
        //1.先找到requestCode中的最后一个 }
        //2.把最后一个}干掉之后,和testCode进行字符串拼接
        //3.拼接完毕后,在补上一个}
        int pos=requestCode.lastIndexOf("}");
        if(pos==-1){
            return null;
        }
        return requestCode.substring(0,pos)+testCode+"\n}";
    }

    private String readbody(HttpServletRequest req) {
        //body 的长度在header 中的一个Content-length自段中
        //Content_length 单位就是字节
        int contentLength=req.getContentLength();
        byte[] buf=new byte[contentLength];
        try(InputStream inputStream=req.getInputStream()){
            inputStream.read(buf,0,contentLength);
        }catch (IOException e){
           e.printStackTrace();
        }
        return  new String(buf);
    }
}
