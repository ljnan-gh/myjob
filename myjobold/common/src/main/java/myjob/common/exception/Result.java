package myjob.common.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Result<T> {
    @ApiModelProperty("错误代码")
    private String code;
    @ApiModelProperty("错误信息")
    private String message;
    @ApiModelProperty("是否成功1成功0失败")
    private int success = 1;
    @ApiModelProperty("返回给前端的结果集")
    private T value;

    private static Result successResult = new Result("执行成功", 1);

    public Result(T value) {
        this.value = value;
        this.message = "执行成功";
        this.success = 1;
    }

    public Result() {

    }

    public Result(String message, int success) {
        this.message = message;
        this.success = success;
    }

    public static Result fail(String message) {
        return new Result(message, 0);
    }

    public static Result success() {
        return successResult;
    }
}
