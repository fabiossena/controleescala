package com.packageIxia.sistemaControleEscala.services.referencias;

@SuppressWarnings("serial")
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}