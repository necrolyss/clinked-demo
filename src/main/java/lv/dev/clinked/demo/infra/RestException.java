package lv.dev.clinked.demo.infra;

public class RestException extends RuntimeException {

    public RestException(String message) {
        super(message);
    }

}