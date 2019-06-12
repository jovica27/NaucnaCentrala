package base.upp.nc.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageServiceImpl implements StorageService{
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	public static final Path rootLocation = Paths.get("files");
	

	@Override
	public void store(MultipartFile file) {
		try{
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));			
		}
		catch (Exception e){
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public void store(MultipartFile file,String filename) {
		try{
			Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));			
		}
		catch (Exception e){
			throw new RuntimeException("FAIL!");
		}
	}
	
	@Override
	public Resource loadFile(String filename) {		
		try {
			System.out.println(rootLocation);
			Path file = rootLocation.resolve(filename);					
			Resource resource = new UrlResource(file.toUri());	
			if(resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}		
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void delete(String filename) {
		try{
			Files.deleteIfExists(rootLocation.resolve(filename));
		}catch (IOException e){
			System.out.println(e);
		}
	}

	@Override
	public void init() {
		if(!Files.exists(rootLocation)) {
			try {
				Files.createDirectory(rootLocation);
			} catch (IOException e) {
				throw new RuntimeException("Could not initialize storage!");
			}
		}		
	}

}