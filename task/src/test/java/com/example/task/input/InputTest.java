package com.example.task.input;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    @Test
    void itShouldNotBePossibleMinBiggerThanMax() {
        Input input = new Input(
                "ab",
                2,
                1,
                1
        );
        boolean possible = input.isPossible();
        assertThat(possible).isFalse();
    }

    @Test
    void itShouldNotBePossibleTooManyStrings() {
        Input input = new Input(
                "abc",
                1,
                3,
                100
        );
        boolean possible = input.isPossible();
        assertThat(possible).isFalse();
    }

    @Test
    void itShouldNotBePossibleLengthLessThanOne() {
        Input input = new Input(
                "abc",
                -1,
                3,
                1
        );
        boolean possible = input.isPossible();
        assertThat(possible).isFalse();
    }

    @Test
    void itShouldBePossible() {
        Input input = new Input(
                "abc",
                1,
                3,
                10
        );
        boolean possible = input.isPossible();
        assertThat(possible).isTrue();
    }
}