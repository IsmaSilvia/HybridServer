package es.uvigo.esei.dai.hybridserver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import es.uvigo.esei.dai.hybridserver.http.HTTPHeaders;
import es.uvigo.esei.dai.hybridserver.http.HTTPParseException;
import es.uvigo.esei.dai.hybridserver.http.HTTPRequest;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class Launcher {
	public static void main(String[] args) throws IOException, HTTPParseException {
		
		/*BufferedReader buffer = new BufferedReader(new FileReader("peticion.txt"));
		HTTPRequest solicitud  = new HTTPRequest(buffer);*/
		HybridServer server = new HybridServer();
		server.start();
		/*HTTPResponse respuesta = new HTTPResponse();
		
		respuesta.setStatus(HTTPResponseStatus.S200);
		respuesta.setVersion(HTTPHeaders.HTTP_1_1.getHeader());
		respuesta.putParameter("Parametro1", "Valor1");
		respuesta.putParameter("Parametro2", "Valor2");
		//respuesta.setContent("Hola mundo");
		
		BufferedWriter buffer = new BufferedWriter(new FileWriter("Respuesta.txt"));
		
		respuesta.print(buffer);*/
		
	}
}