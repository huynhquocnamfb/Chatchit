package vn.edu.nlu.web.chat.service;


import vn.edu.nlu.web.chat.model.Token;

public interface TokenService {

    boolean isTokenExpired(Token token);

    Token getToken(String tokenValue);

    void save(Token token);

    void delete(Token token);
}