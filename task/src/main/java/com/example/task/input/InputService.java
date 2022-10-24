package com.example.task.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {

    private final InputRepository inputRepository;

    @Autowired
    public InputService(InputRepository inputRepository) {
        this.inputRepository = inputRepository;
    }

    public List<Input> getInputs() {
        return inputRepository.findAll();
    }

    public Input getInput(Long inputId) {
        Input input = inputRepository.findById(inputId)
                .orElseThrow(() -> new IllegalStateException("Input with id " + inputId + " doesn't exist."));
        return input;
    }

    public ArrayList<Input> getRunningInputs() {
        ArrayList<Input> runningInputs = inputRepository.findByState("running");
        return runningInputs;
    }
    @Transactional
    public void addInput(Input input) {
        input.setChars();
        inputRepository.save(input);
    }
    @Transactional
    public void updateState(Long inputId) {
        Input input = inputRepository.findById(inputId)
                .orElseThrow(() -> new IllegalStateException("Input with id " + inputId + " doesn't exist"));
        input.completed();
    }
}
