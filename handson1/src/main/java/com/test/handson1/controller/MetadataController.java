package com.test.handson1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.handson1.model.MetaData;
import com.test.handson1.service.MetaDataService;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

	@Autowired
	private MetaDataService metaDataService;

	@GetMapping("/{id}")
	public ResponseEntity<Object> getMetadata(@PathVariable Integer id) {

		MetaData data = metaDataService.retrieveMetaDataForId(id);

		if (data == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(data);
		return new ResponseEntity<>(data.toString(), HttpStatus.OK);
	}

}
