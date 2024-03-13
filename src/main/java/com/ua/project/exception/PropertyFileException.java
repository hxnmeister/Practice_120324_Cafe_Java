package com.ua.project.exception;

import java.io.IOException;

public class PropertyFileException extends IOException {
    public PropertyFileException(String errorMessage) {
        super(errorMessage);
    }
}
