package com.bookcomet.resources;

import com.bookcomet.dto.DtoBook;
import com.bookcomet.dto.DtoUser;
import com.bookcomet.service.BookService;
import com.bookcomet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoUser save(@RequestBody @Valid DtoUser dtoUser) {
        return userService.save(dtoUser);
    }

    @PostMapping("/auth")
    public String auth(@RequestBody @Valid DtoUser user) {
        return userService.login(user);
    }

}
