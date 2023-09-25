package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
public class UserControllerLegacy {
    @Autowired
    private UserServiceAPI userService;

    @Autowired
    private PlayerServiceAPI playerService;

    @PostMapping
    public ResponseEntity<?> saveUserV1(@RequestBody CreateUserDto userData) {
        User user = new User(userData.getUsername(), userData.getEmail(), userData.getImage(), userData.getPassword(), 0);
        ServiceResult<User> saveUserResult = userService.save(user);
        ServiceResult<Player> savePlayerResult = playerService.save(user.toPlayer());
        if (savePlayerResult.isSuccess())
            return buildResponse(saveUserResult);
        else
            return new ResponseEntity<>(savePlayerResult.getErrorMessage(), HttpStatus.CONFLICT);

    }

    @NotNull
    private static <A> ResponseEntity<?> buildResponse(ServiceResult<A> result) {
        if (result.isSuccess())
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.CONFLICT);

    }


}
