package com.example.store.use_case.supervisor;

import com.example.store.entity.Supervisor;
import com.example.store.dto.UserCredentialsDTO;
import com.example.store.utils.StringHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final StringHasher stringHasher;

    public Supervisor checkCredentials(UserCredentialsDTO userCredentialsDTO) {
        Supervisor supervisor = supervisorRepository.findByEmail(userCredentialsDTO.getEmail());
        if (supervisor != null && (supervisor.getPassword().equals(userCredentialsDTO.getPassword()) || supervisor.getPassword().equals(userCredentialsDTO.getPassword()))) {
            String token = stringHasher.hashString(supervisor.getEmail() + ":" + LocalDate.now().toString());
            supervisor.setToken(token);
            supervisor = supervisorRepository.save(supervisor);
            return supervisor;
        }
        return null;
    }

    public Supervisor checkCredentials(String token) {
        Supervisor supervisor = supervisorRepository.findByToken(token);
        if (supervisor != null && supervisor.getToken() != null) {
            return supervisor;
        }
        return null;
    }

    public String clearToken(int id) {
        Supervisor supervisor = supervisorRepository.findById(id).get();
        if (supervisor != null) {
            supervisor.setToken(null);
            supervisorRepository.save(supervisor);
            return "Token cleared and value set to null";
        }
        return "Unable to clear token";
    }
}
