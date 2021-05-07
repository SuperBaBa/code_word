package org.jarvis.pprpc.api.common.dto;

/**
 * @author Marcus
 * @date 2021/2/16-17:18
 * @description 远程调用想用数据传输实体
 */
public class PPRpcResponse {
    private String requestId;
    private Exception exception;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
