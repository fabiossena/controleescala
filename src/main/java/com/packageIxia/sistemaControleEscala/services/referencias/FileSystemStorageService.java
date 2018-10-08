package com.packageIxia.sistemaControleEscala.services.referencias;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.packageIxia.sistemaControleEscala.helpers.Utilities;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final Path tempLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
     
    	this.rootLocation = Paths.get(System.getProperty("user.home") + "/upload-dir"); // + properties.getLocation()); // // Paths.get(properties.getLocation());
    	this.tempLocation = Paths.get(System.getProperty("user.home") + "\\temp-dir");
    }

    @Override
    public String store(MultipartFile file) {
    	return store(file, null); 
    }

    @Override
    public String store(MultipartFile file, String filename) {
        filename = (filename == null || filename.isEmpty() ? StringUtils.cleanPath(file.getOriginalFilename()) : filename + "." + Utilities.getExtension(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
        
        return filename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.createDirectories(tempLocation);
            Files.createDirectories(tempLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
        
        FileSystemUtils.deleteRecursively(Paths.get(System.getProperty("user.home") + "/temp-dir").toFile());//rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	@Override
	public Path getTempPath() {
		return tempLocation;
	}

	@Override
	public Path getRootPath() {
		return rootLocation;
	}
}