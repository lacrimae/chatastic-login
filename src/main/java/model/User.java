package model;

import jakarta.persistence.Id;

public class User {

    @Id
    private String uuid;

    private String login;
    private String nickname;
    private String password;

}
