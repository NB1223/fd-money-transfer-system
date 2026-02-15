package com.fd;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fd.dto.TransferRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class TransferRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRequest() {
        TransferRequest request =
                new TransferRequest(1L, 2L, 100.0, "idem-111");

        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidAmount() {
        TransferRequest request =
                new TransferRequest(1L, 2L, 0.0, "idem-222");

        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
    }

    @Test
    void testNullFields() {
        TransferRequest request =
                new TransferRequest(null, null, 100.0, "idem-333");

        Set<ConstraintViolation<TransferRequest>> violations =
                validator.validate(request);

        assertFalse(violations.isEmpty());
    }
}