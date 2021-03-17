package by.grsu.iot.service.domain.response;

/**
 * Used by the wrapper for the returned object in order to supplement the response with some text message and status
 *
 * @param <T> any object which need to send from controller
 */
public class HttpMessageWrapper<T> {

    private HttpMessageEnum status;
    private String message;

    private T body;

    public HttpMessageWrapper(HttpMessageEnum status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public HttpMessageWrapper() {
    }

    public HttpMessageEnum getStatus() {
        return status;
    }

    public void setStatus(HttpMessageEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
