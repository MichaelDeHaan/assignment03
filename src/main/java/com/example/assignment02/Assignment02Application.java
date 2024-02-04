package com.example.assignment02;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Assignment02Application {


	public static void main(String[] args) {
		SpringApplication.run(Assignment02Application.class, args);
		getDefinition();
		System.exit(0);
	}

	/**
	 * Takes an input string then searches and parses the dictionary api to display the
	 * word, part of speech, and definition.
	 * Notice: This only displays the first meaning of the word.
	 */
	public static void getDefinition() {
		Scanner sc = new Scanner(System.in);
		System.out.println("*********Input*********");
		System.out.print("Enter a word: ");
		String input = sc.nextLine();

		try {
			String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + input;
			RestTemplate restTemplate = new RestTemplate();
			ObjectMapper mapper = new ObjectMapper();

			String jsonInfo = restTemplate.getForObject(url, String.class);
			JsonNode array = mapper.readTree(jsonInfo);


			String word = array.findValue("word").asText();
			String partOfSpeech = array.findValue("partOfSpeech").asText();
			String definition = array.findValue("definition").asText();

			System.out.println("*********Your Word*********");
			System.out.println("Word: " + word);
			System.out.println("Part of speech: " + partOfSpeech);
			System.out.println("Definition: " + definition);
		} catch (JsonProcessingException ex) {
			Logger.getLogger(Assignment02Application.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("error in getAirportInfo");
		} catch (HttpClientErrorException ex) {
			Logger.getLogger(Assignment02Application.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("No Definitions Found For: " + input);
		}
	}
}
