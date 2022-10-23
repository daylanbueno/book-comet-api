package com.bookcomet.service;

import com.bookcomet.dto.DtoUser;

public interface UserService {

    DtoUser save(DtoUser user);

    String  login(DtoUser user);
}
