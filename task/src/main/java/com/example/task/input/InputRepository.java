package com.example.task.input;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface InputRepository
    extends JpaRepository<Input, Long> {

    @Override
    Optional<Input> findById(Long aLong);

    ArrayList<Input> findByState(String state);

}
