package base.upp.nc.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface StorageService {
	void store (MultipartFile file);
	public void store(MultipartFile file,String filename);
	Resource loadFile(String filename);
	void deleteAll();
	void init();
	void delete(String filename);
}