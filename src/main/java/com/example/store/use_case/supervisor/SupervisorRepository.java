package com.example.store.use_case.supervisor;

import com.example.store.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
    Supervisor findByEmail(String email);

    Supervisor findByToken(String token);
}
