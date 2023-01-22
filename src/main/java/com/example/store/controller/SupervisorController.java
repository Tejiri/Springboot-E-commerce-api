package com.example.store.controller;

import com.example.store.factory.DTO_Factory;
import com.example.store.dto.SupervisorDTO;
import com.example.store.use_case.supervisor.SupervisorService;
import com.example.store.dto.UserCredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("api/v1/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;
    private final DTO_Factory dtoFactory;


    @PostMapping("check-credentials")
    public SupervisorDTO checkCredentials(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        try {
            return dtoFactory.create(supervisorService.checkCredentials(userCredentialsDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(path = "/logout/{supervisorId}")
    public String logOut(@PathVariable(name = "supervisorId") int supervisorId) {
        return supervisorService.clearToken(supervisorId);
    }
}
