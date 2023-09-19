package com.groupeonepoint.transcoder.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.json.CDL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3Object;
import com.groupeonepoint.transcoder.utils.S3Utils;

@RestController
@RequestMapping("/transcoder")
public class TranscoderController {

	@GetMapping("/migrateCsvToJson")
	public String migrateCsvToJson(@RequestParam() String inputBucketName, @RequestParam() String outputBucketName, @RequestParam() String fileName) {
		// Recuperer le CSV depuis l'input bucket
		S3Object csv = S3Utils.getObject(inputBucketName, fileName + ".csv");
		
		// Transformer le CSV en JSON
		InputStream stream = csv.getObjectContent();
		String csvString = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining("\n"));
		String jsonString = CDL.toJSONArray(csvString).toString();
		
		// Insérer le JSON dans l'output bucket
		S3Utils.putJSONObject(outputBucketName, fileName + ".json", new ByteArrayInputStream(jsonString.getBytes()));
		
		return jsonString;
	}

}
