package com.hemant.blog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hemant.blog.entity.Post;
import com.hemant.blog.exceptions.ResourceNotFoundException;
import com.hemant.blog.payloads.PostDto;
import com.hemant.blog.repository.PostRepo;

@Service
public class FileServiceImpl implements IFileService {

	@Autowired
	private IPostService postServ;
	
	@Override
	public String uploadImage(String path, MultipartFile file, Integer postId) throws IOException {
		
		PostDto post = postServ.getPost(postId);
		
		// take name of file 
		String name = file.getOriginalFilename();
		
		String randomId = UUID.randomUUID().toString();
		String fileName= randomId + name.substring(name.lastIndexOf("."));
		
		//db operation
		post.setImageUrl(fileName);
		postServ.updatePost(post, postId);
		
		//full path
		String fullPath = path + File.separator + fileName;
		
		//create folder
		File folder = new File(path);
		
		//if folder is not present, it will create 
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		Files.copy(file.getInputStream(), Paths.get(fullPath) );	
		
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath= path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
		
	}
}
