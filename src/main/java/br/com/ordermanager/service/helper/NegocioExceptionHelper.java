package br.com.ordermanager.service.helper;

public class NegocioExceptionHelper extends Exception {

    public NegocioExceptionHelper(String msg) {
        super(msg);
    }

    public NegocioExceptionHelper(String msg, Throwable e) {
        super(msg, e);
    }
}
