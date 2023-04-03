package io.chatasticlogin.service;

import io.chatasticlogin.model.ConfirmationToken;

public interface ConfirmationTokenService {

    void save(ConfirmationToken token);

    String confirm(String token);
}
