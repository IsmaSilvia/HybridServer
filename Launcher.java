package es.uvigo.esei.dai.hybridserver;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;

public class Launcher {
	public static void main(String[] args) throws IOException, HTTPParseException {
		
		BufferedReader buffer = new BufferedReader(new FileReader("peticion.txt"));
		HTTPRequest solicitud  = new HTTPRequest(buffer);
		
	}
}
