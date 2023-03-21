package io.chatasticlogin.service;

import io.chatasticlogin.model.ConfirmationToken;

public interface ConfirmationTokenService {

    ConfirmationToken get(String token);

    void save(ConfirmationToken token);

    String confirm(String token);
}
