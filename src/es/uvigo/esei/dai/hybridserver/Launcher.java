package es.uvigo.esei.dai.hybridserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;

public class Launcher {
	public static void main(String[] args) throws IOException, HTTPParseException {
		BufferedReader buffer = new BufferedReader(new FileReader("peticion.txt"));
		HTTPRequest solicitud  = new HTTPRequest(buffer);
	}
}
