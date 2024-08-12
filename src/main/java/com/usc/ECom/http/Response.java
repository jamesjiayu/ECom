package com.usc.ECom.http;
public class Response  { //Response <T>
    private boolean success;
    private int code;
    private String message;
	//private T data;
    
/*    
 *  data , static newSuccess and newFail, how to make it simpler and get together?
    private T data;
    public static <K> Response<K> newSuccess(K data) {
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static Response<Void> newFail(String errorMsg) {
        Response<Void> response = new Response<>();
        response.setMessage(errorMsg);
        response.setSuccess(false);
        return response;
    }

*/      
    
    public Response(boolean success, String message) {
        super();
        this.success = success;
        this.code = success ? 200 : 400;
        this.message = message;
    }

    public Response(boolean success) {
        super();
        this.success = success;
        this.code = success ? 200 : 400;
        this.message = "";
    }

    public Response() {
        super();
    }

    public Response(boolean success, int code, String message) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Response [success=" + success + ", code=" + code + ", message=" + message + "]"; // ", data=" + data + 
    }
}

/*
 * 
 * 
 * public class Response <T> { //Response <T>
    private boolean success;
    private int code;
    private String message;
	private T data;   
    
    public Response(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = success ? 200 : 400;
        this.message = message;
        this.data = data;
    }
        public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
    ...
  }
public class Response <T> {

    private T data;
    private boolean success;
    private String errorMsg;

    public static <K> Response<K> newSuccess(K data) {
        Response<K> response = new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static Response<Void> newFail(String errorMsg) {
        Response<Void> response = new Response<>();
        response.setErrorMsg(errorMsg);
        response.setSuccess(false);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
 * */
