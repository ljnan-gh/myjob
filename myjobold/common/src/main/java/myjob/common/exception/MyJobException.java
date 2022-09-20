package myjob.common.exception;


import lombok.Data;

@Data
public class MyJobException extends Exception {
	private static final long serialVersionUID = 1L;
	private String code;

    public MyJobException(String code) {
        this.code = code;
    }
}
