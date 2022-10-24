package com.example.task.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/input")
public class InputController {
    private final InputService inputService;

    @Autowired
    public InputController(InputService inputService) {
        this.inputService = inputService;
    }


    @GetMapping
    public String getRunning() {
        List<Input> inputs = inputService.getRunningInputs();
        String runningInputs = "";
        for (Input input : inputs) {
            runningInputs = runningInputs + input + "\n";
        }
        if (runningInputs.isEmpty()) {
            return "no running queries at the moment";
        }
        int runningNumber = inputs.size();
        return "Currently running: " + runningNumber + "\n" + runningInputs;
    }


    @GetMapping(path = "{inputId}")
    public String getOutput(@PathVariable("inputId") Long inputId) {
        Input input = inputService.getInput(inputId);
        if (!input.isCompleted()) {
            return "still running...";
        }
        String output = input.getFileOutput();
        return output;
    }

    @PostMapping
    public ResponseEntity<? extends Object> CreateStrings(@RequestBody Input input) {
        if(input.isPossible()) {
            inputService.addInput(input);
            input.stringsToFile();
            inputService.updateState(input.getId());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("impossible request");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}


