package com.groupeonepoint.transcoder.utils;

import java.io.InputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class S3Utils {
	
	private static final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).build();

	public static S3Object getObject(String bucketName, String fileName) {
		S3Object file = s3.getObject(bucketName, fileName);
		return file;
	}

	public static void putJSONObject(String bucketName, String fileName, InputStream stream) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("application/json");
		s3.putObject(bucketName, fileName, stream, metadata);
	}
}
