package api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import problem.Problem;
import problem.problemDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProblemServlet extends HttpServlet {
    private Gson gson=new GsonBuilder().create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id=req.getParameter("id");
        if(id==null||"".equals(id)){
            //没有这个id参数,,执行全部查找
            selectAll(resp);
        }else{
            //存在这个id,查找这个id题目
            selectOne(Integer.parseInt(id),resp);
        }
    }


    private void selectAll(HttpServletResponse resp)throws IOException {
        //Content-Type:描述了body数据的类型
        //常见取值
        //html:text/html
        //图片:image/png   image/jpg
        //json:application/json
        //css:text/css
        //javaScript:application/Javascript
        resp.setContentType("application/json;charset=utf-8");
        problemDAO problemDAO=new problemDAO();
        List<Problem> problems=problemDAO.selectAll();
        //把结果组织成json结构
        String JsonString=gson.toJson(problems);
        resp.getWriter().write(JsonString);
    }
    private void selectOne(int parseInt, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        problemDAO problemDAO=new problemDAO();
        Problem problem=problemDAO.selectOne(parseInt);
        problem.setTestCode("");
        String JsonString =gson.toJson(problem);
        resp.getWriter().write(JsonString);
    }
}
