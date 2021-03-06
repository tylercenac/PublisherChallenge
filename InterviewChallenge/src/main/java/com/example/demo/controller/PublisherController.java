package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.File;
import com.example.demo.data.PublisherEntity;
import com.example.demo.service.PublisherSerivce;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class PublisherController {

	@Autowired
	private Environment env;

	@Autowired
	private PublisherSerivce publisherService;

	final String FILE_BASE = "s3://some-special-bucket/production/publisher-data/";

	// Check to verify app is working
	@GetMapping("/status")
	public String checkStatus() {
		return "Working on port " + env.getProperty("server.port") + "!";
	}


	// Import provided data from databasePublishers.json
	@GetMapping("/importPublishers")
	public String importPublishers() {

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("/databasePublishers.json")) {
			// Store contents of input file in JSONArray
			JSONArray publisherList = (JSONArray) jsonParser.parse(reader);

			// Stores publishers as PublisherEntity to be saved to the database
			List<PublisherEntity> publishers = new ArrayList<PublisherEntity>();

			// Iterate over publishers from input and add to list
			publisherList.forEach(publisher -> publishers.add(convertJSONToPublisherEntity((JSONObject) publisher)));

			// Save all publishers from input to database
			publisherService.savePublishers(publishers);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "Successfully imported publishers to database from databasePublishers.json!";
	}

	// View all publishers in database
	@GetMapping("/allPublishers")
	public List<PublisherEntity> getAllPublishers() {

		return publisherService.getAllPublishers();

	}
	
	// Find publisher by code
	@GetMapping("/publisherByCode/{code}")
	public PublisherEntity getPublisherByCode(@PathVariable String code) {
		
		return publisherService.getPublisherByCode(code);

	}

	@GetMapping("/updatePublishers")
	public String updateDb() {

		// Stores the most recent file for each publisher
		// Key: publisher code
		// Value: File Object (consists of the name of the most recent file and the date it was updated)
		HashMap<String, File> updatedFiles = new HashMap<String, File>();

		try {
			// Read from publishersFileList.txt
			FileInputStream inputStream = new FileInputStream("/publishersFileList.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

			String strLine;

			// Steps through publishersFileList.txt line by line
			// Finds and stores the most recently updated file for each publisher in the hashMap
			while ((strLine = bufferedReader.readLine()) != null) {

				String[] line = strLine.split(" ");
				String code = line[2].split("-")[0];
				String datetime = line[0] + " " + line[1];

				SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = dateParser.parse(datetime);

				File file = new File(date, line[2]);
				
				
				if (updatedFiles.containsKey(code)) {

					// check if file is newer than the one already in hashMap
					if (fileIsNewer(file, updatedFiles.get(code))) {
						updatedFiles.put(code, file);
					}
				} else {
					updatedFiles.put(code, file);
				}

			}

			// Close the input stream
			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		List<PublisherEntity> publishers = getAllPublishers();

		// For each publisher in the database, update the file according to the hashMap
		for (PublisherEntity publisher : publishers) {

			if (updatedFiles.get(publisher.getCode().toUpperCase()) != null) {
				publisher.setFile(FILE_BASE + publisher.getCode()
						+ updatedFiles.get(publisher.getCode().toUpperCase()).getFile().substring(6));
			}

		}

		// Save all publishers to the database
		publisherService.savePublishers(publishers);

		return "Successfully updated files for publishers in the database!";

	}

	@GetMapping("/printDatabase")
	public String printDatabase() throws IOException {

		// Write output to 'output.json'
		FileWriter outputFile = new FileWriter(
				"output.json");

		// Stores the JSON of each publisher
		JSONArray outputArray = new JSONArray();

		List<PublisherEntity> publishers = getAllPublishers();

		// Convert each PublisherEntity object in the database to JSON and add it to the
		// outputArray
		for (PublisherEntity publisher : publishers) {

			outputArray.add(convertPublisherEntityToJSON(publisher));
		}

		// format JSON
		String formattedJson = formatJson(outputArray);

		try {

			// write JSON to output.json
			outputFile.write(formattedJson);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			outputFile.flush();
			outputFile.close();
		}

		return "Successfully wrote updated files to output.json!";
	}

	// Convert JSONArray into String
	public String formatJson(JSONArray jsonArray) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Object json = mapper.readValue(jsonArray.toJSONString(), Object.class);
		String formattedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

		formattedJson = formattedJson.substring(0, 1) + "\n" + formattedJson.substring(1);
		formattedJson = formattedJson.replaceAll(Pattern.quote("}, {"), "},\n{");
		formattedJson = formattedJson.substring(0, formattedJson.length() - 2) + "\n"
				+ formattedJson.substring(formattedJson.length() - 1);
		return formattedJson;
	}

	// returns true if file1 is newer than file2
	private boolean fileIsNewer(File file1, File file2) {

		return file1.getDate().after(file2.getDate());

	}

	// Create PublisherEntity from JSON
	public PublisherEntity convertJSONToPublisherEntity(JSONObject publisher) {

		String code = (String) publisher.get("code");
		String name = (String) publisher.get("name");
		String file = (String) publisher.get("file");
		boolean active = (boolean) publisher.get("active");
		JSONArray categories = (JSONArray) publisher.get("categories");

		return new PublisherEntity(code, name, file, active, categories);

	}

	// Create JSON from PublisherEntity
	public JSONObject convertPublisherEntityToJSON(PublisherEntity publisher) {

		JSONObject json = new JSONObject();
		json.put("code", publisher.getCode());
		json.put("name", publisher.getName());
		json.put("file", publisher.getFile());
		json.put("active", publisher.isActive());
		json.put("categories", publisher.getCategories());
		return json;

	}

}
