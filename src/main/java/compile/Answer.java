package compile;

public class Answer {
    //通过error表示当前的错误类型
    //0表示没错,1编译错误,2运行错误
    private int err;
    //便是具体出错的原因
    private String reason;
    //对应标准输出的内容
    private String stdout;
    //对应标准错误的内容
    private String stderr;

    public int getErr() {
        return err;
    }

    public String getReason() {
        return reason;
    }

    public String getStdout() {
        return stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "err=" + err +
                ", reason='" + reason + '\'' +
                ", stdout='" + stdout + '\'' +
                ", stderr='" + stderr + '\'' +
                '}';
    }
}
