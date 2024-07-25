package vn.edu.nlu.web.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.nlu.web.chat.enums.EntityStatus;
import vn.edu.nlu.web.chat.exception.ResourceNotFoundException;
import vn.edu.nlu.web.chat.model.Token;
import vn.edu.nlu.web.chat.repository.TokenRepository;
import vn.edu.nlu.web.chat.service.TokenService;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;


    @Override
    public boolean isTokenExpired(Token token) {
        return token.getExpiredAt().before(new Date());
    }

    @Override
    public Token getToken(String tokenValue) {

        return tokenRepository.findByValue(tokenValue).orElseThrow(() -> new ResourceNotFoundException("Cannot found the token"));
    }

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public void delete(Token token) {
        token.setEntityStatus(EntityStatus.DELETED);
        tokenRepository.save(token);
    }


}