package problem;

import common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//数据访问层
public class problemDAO {
    //获取题目所有信息
    public List<Problem> selectAll(){
        List<Problem> result=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        //1.创建数据库连接
        connection= DBUtil.getConnection();
        String sql="select * from oj_table";
        try {
            //2.创建操作对象
            statement=connection.prepareStatement(sql);
            //3.执行sql语句
            resultSet=statement.executeQuery();
            //4.处理结果集
            while(resultSet.next()){
                Problem problem=new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
//                problem.setDescription(resultSet.getString("description"));
//                problem.setTemplateCode(resultSet.getString("templateCode"));
//                problem.setTestCode(resultSet.getString("testCode"));
                result.add(problem);
            }
        } catch (SQLException e) {
            throw new RuntimeException("全部查询失败",e);
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return result;
    }
    //指定id获取信息
    public Problem selectOne(int id){

        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        connection=DBUtil.getConnection();
        String sql="select * from oj_table where id=?";
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet=statement.executeQuery();
            if (resultSet.next()){
                Problem problem=new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setTitle(resultSet.getString("title"));
                problem.setLevel(resultSet.getString("level"));
                problem.setDescription(resultSet.getString("description"));
                problem.setTemplateCode(resultSet.getString("templateCode"));
                problem.setTestCode(resultSet.getString("testCode"));
                return problem;
            }
        } catch (SQLException e) {
            throw new RuntimeException("id查询失败",e);
        } finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }
    //新增
    public void insert(Problem problem){
        Connection connection=null;
        PreparedStatement statement=null;
        //1获取数据库连接
        connection= DBUtil.getConnection();
        String sql="insert into oj_table values(null,?,?,?,?,?)";
        try {
            //2创建操作对象
            statement=connection.prepareStatement(sql);
            statement.setString(1,problem.getTitle());
            statement.setString(2,problem.getLevel());
            statement.setString(3,problem.getDescription());
            statement.setString(4,problem.getTemplateCode());
            statement.setString(5,problem.getTestCode());
            System.out.println("insert: "+statement);
            //3执行SQL语句
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("插入数据失败",e);
        }finally {
            DBUtil.close(connection,statement,null);
        }
    }
    //删除指定题目信息
    public void delete(int id){
        Connection connection=null;
        PreparedStatement statement=null;
        connection=DBUtil.getConnection();
        String sql="delete from oj_table where id=?";
        try {
            statement=connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("删除失败",e);
        } finally {
            DBUtil.close(connection,statement,null);
        }
    }

    public static void main(String[] args) {
        //1.测试insert()
//        Problem problem=new Problem();
//        problem.setTitle("个位相加");
//        problem.setLevel("简单");
//        problem.setDescription("给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。\n" +
//                "\n" +
//                "示例:\n" +
//                "\n" +
//                "输入: 38\n" +
//                "输出: 2 \n" +
//                "解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。\n");
//        problem.setTemplateCode("class Solution {\n" +
//                "    public int addDigits(int num) {\n" +
//                "\n" +
//                "    }\n" +
//                "}");
//        problem.setTestCode("public class TestCode {\n" +
//                "    public static void main(String[] args) {\n" +
//                "        Solution s=new Solution();\n" +
//                "        if(s.addDigits(38)==2){\n" +
//                "            System.out.println(\"test ok\");\n" +
//                "        }else{\n" +
//                "            System.out.println(\"test failed\");\n" +
//                "        }\n" +
//                "        \n" +
//                "        if(s.addDigits(1)==1){\n" +
//                "            System.out.println(\"test ok\");\n" +
//                "        }else{\n" +
//                "            System.out.println(\"test failed\");\n" +
//                "        }\n" +
//                "    }\n" +
//                "}\n");
//        problemDAO problemDAO=new problemDAO();
//        problemDAO.insert(problem);
//        System.out.println("insert ok");
        //2.测试selectAll()
//        problemDAO problemDAO=new problemDAO();
//        List<Problem> problems=problemDAO.selectAll();
//        System.out.println("selectAll: "+problems);
        //3.测试selectOne(id)
//        problemDAO problemDAO=new problemDAO();
//        Problem problem=problemDAO.selectOne(1);
//        System.out.println("selectOne: "+problem);
        //4测试delete(id)
//        problemDAO problemDAO=new problemDAO();
//        problemDAO.delete(1);
    }
}
