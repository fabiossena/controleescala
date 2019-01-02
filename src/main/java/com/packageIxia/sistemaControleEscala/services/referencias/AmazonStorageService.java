package com.packageIxia.sistemaControleEscala.services.referencias;

import java.io.IOException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

@Service
public class AmazonStorageService {
	
    private String clientRegion;
    private String bucketName;
    private AmazonS3 s3Client;

    @Autowired
    public AmazonStorageService (Environment environment) {
    	clientRegion = environment.getProperty("aws.clientRegion");
    	bucketName = environment.getProperty("aws.bucketName");
    	
//    	String accessKeyId = environment.getProperty("aws.accessKeyId");
//    	String secretKey = environment.getProperty("aws.secretKey");
    	//BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
    	//BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .withCredentials(new EnvironmentVariableCredentialsProvider())
        		// new SystemPropertiesCredentialsProvider()) 
                // new AWSStaticCredentialsProvider(awsCreds)) 
                // new EnvironmentVariableCredentialsProvider()) 
                // new EnvironmentVariableCredentialsProvider()) //new ProfileCredentialsProvider()) //      
                .build();
    }
    
	public Resource getFile(String key) throws IOException {

		Resource resource = null;

		try {
            System.out.println("Downloading an object");
            
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60; // 1 minute
            expiration.setTime(expTimeMillis);

            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = 
                    new GeneratePresignedUrlRequest(bucketName, key)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);
            
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            resource = new UrlResource(url);
            if (!(resource.exists() || resource.isReadable())) {
                throw new StorageFileNotFoundException("Could not read file");
            }
            
        } catch (AmazonServiceException ase) {
        	ase.printStackTrace();
        } catch (AmazonClientException ace) {
        	ace.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
		}
		
        return resource;
	}
	    
	public void uploadFile(String keyName, MultipartFile multipartFile) throws IOException, AmazonClientException, InterruptedException {

        try {        
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(this.s3Client)
                    .build();
            
            Upload upload = tm.upload(bucketName, keyName, multipartFile.getInputStream(), new ObjectMetadata());
            System.out.println("Object upload started");
            upload.waitForCompletion();
            System.out.println("Object upload complete");
        }
        catch(AmazonServiceException e) {
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            e.printStackTrace();
        }
	}
}
