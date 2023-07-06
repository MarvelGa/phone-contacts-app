package com.contacts.phone.controller;

import com.contacts.phone.dto.RemoteResponse;
import com.contacts.phone.dto.UserDto;
import com.contacts.phone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.contacts.phone.exception.StatusCodes.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;


    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful registration", content = @Content(schema = @Schema(implementation = RemoteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation Error. For example, password len less then 7 or more then 30 characters", content = @Content(schema = @Schema(implementation = RemoteResponse.class))),
            @ApiResponse(responseCode = "409", description = "Username or Email already exist", content = @Content(schema = @Schema(implementation = RemoteResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected internal error", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    public ResponseEntity<RemoteResponse> registerUser(
            @Parameter(description = "User data to register", required = true)  @RequestBody UserDto userDto) {
        log.info("Start user registration with email={}", userDto.getEmail());

        UserDto createdUserDto = userService.registerUser(userDto);

        log.info("User with email={} registered successfully", userDto.getEmail());

        RemoteResponse successfulResponse = RemoteResponse.create(true, OK.name(), "User is successfully registered", List.of(createdUserDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(successfulResponse);
    }

}
