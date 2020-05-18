package compile;
//要编译的代码
public class Question {
    //要编译和执行的代码内容
    private String code;
    //执行标准输入要输入的内容
    private String stdin;

    public void setCode(String code) {
        this.code = code;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public String getCode() {
        return code;
    }

    public String getStdin() {
        return stdin;
    }
}
