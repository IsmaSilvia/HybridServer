package es.uvigo.esei.dai.hybridserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HTTPRequest {

	private HTTPRequestMethod metodo;
	private String cadenaRecurso;
	private String[] resourcePath;
	private String resourceName;
	private Map<String, String> resourceParameters;
	private String httpVersion;
	private Map<String, String> headersParameters;
	private String content;
	private int contentLength;

	// El constructor recibe un reader y tiene que parsear
	public HTTPRequest(Reader reader) throws IOException, HTTPParseException {
		try(BufferedReader buffer = new BufferedReader(reader))
		{
		inicializarValores(buffer);
		}
	}

	public HTTPRequestMethod getMethod() {
		return metodo;
	}

	public String getResourceChain() {
		return cadenaRecurso;
	}

	public String[] getResourcePath() {
		return resourcePath;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Map<String, String> getResourceParameters() {
		return resourceParameters;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public Map<String, String> getHeaderParameters() {
		return headersParameters;
	}

	public String getContent() {
		return content;
	}

	public int getContentLength() {

		return contentLength;
	}

	public void setMetodo(String parametro) {
		switch (parametro) {
		case ("GET"):
			this.metodo = HTTPRequestMethod.GET;
			break;
		case ("POST"):
			this.metodo = HTTPRequestMethod.POST;
			break;
		case ("PUT"):
			this.metodo = HTTPRequestMethod.PUT;
			break;
		case ("DELETE"):
			this.metodo = HTTPRequestMethod.DELETE;
			break;
		case ("OPTIONS"):
			this.metodo = HTTPRequestMethod.OPTIONS;
			break;
		case ("TRACE"):
			this.metodo = HTTPRequestMethod.TRACE;
			break;
		case ("CONNECT"):
			this.metodo = HTTPRequestMethod.CONNECT;
			break;
		case ("HEAD"):
			this.metodo = HTTPRequestMethod.HEAD;
			break;
		}
	}

	private void inicializarValores(BufferedReader buffer) throws IOException, HTTPParseException {
		String linea;

		linea = buffer.readLine();
		System.out.println(linea);

		String[] division = linea.split(" ");

		if (division.length != 3) {
			throw new HTTPParseException("La primera linea no es correcta.");
		}

		switch (division[0]) {
		case "GET":
			setMetodo(division[0]);

			System.out.println(division[1]);

			this.cadenaRecurso = division[1];

			resourceParameters = new LinkedHashMap<String, String>();
			if (division[1].equals("/")) {
				String[] path = new String[0];
				this.resourcePath = path;
			} else if (division[1].matches(".+\\?.+")) {
				String[] allParam = division[1].split("\\?");
				String[] consultas = allParam[1].split("&");

				String aux = allParam[0].substring(1);
				this.resourcePath = aux.split("/");

				System.out.println("Esto es " + this.resourcePath.length);

				for (int i = 0; i < consultas.length; i++) {
					String[] divAux = consultas[i].split("=");
					resourceParameters.put(divAux[0], divAux[1]);
				}
			} else if (division[1].substring(1).matches(".*/*")) {
				String[] aux = division[1].substring(1).split("/");
				this.resourcePath = aux;
			}
			/*
			 * else if(division[1].substring(1)== ""){ String aux =
			 * division[1].substring(1); this.resourcePath = aux.split("/");
			 * System.out.println("Esto"+this.resourcePath.length); }
			 */

			String[] division1 = division[1].split("\\?");

			this.resourceName = division1[0].substring(1);
			this.httpVersion = division[2];

			headersParameters = new LinkedHashMap<String, String>();

			while ((linea = buffer.readLine()) != null && !linea.matches("")) {
				if (linea.matches("Content-Length:.+")) {
					String[] divisionAux = linea.split(": ");
					this.contentLength = Integer.parseInt(divisionAux[1]);
				}

				else if (linea.matches(".*:.*")) {
					String[] divCab = linea.split(": ");
					headersParameters.put(divCab[0], divCab[1]);
				} else {
					throw new HTTPParseException("La cabecera no se ha escrito correctamente.");
				}
			}
			break;
		case "POST":
			System.out.println("POST");
			setMetodo(division[0]);
			this.httpVersion = division[2];
			
			resourceParameters = new LinkedHashMap<String, String>();
			this.cadenaRecurso = division[1];
			this.resourceName = division[1].substring(1);
			
			if (division[1].equals("/")) {
				String[] path = new String[0];
				this.resourcePath = path;
			}
			else
			{
				this.resourcePath = division[1].substring(1).split("/");
			}
			
			headersParameters = new LinkedHashMap<String, String>();
			
			while((linea = buffer.readLine())!=null && linea.matches(".*: .*"))
			{
				if (linea.matches("Content-Length:.+")) {
					String[] divisionAux = linea.split(": ");
					this.contentLength = Integer.parseInt(divisionAux[1]);
					System.out.println("Content-Length: "+divisionAux[1]);
				}			
				    String[] parametros = linea.split(": ");
				    headersParameters.put(parametros[0], parametros[1]);
			}
			
			String contenido = buffer.readLine();
			String type = headersParameters.get("Content-Type");
			if (type != null && type.startsWith("application/x-www-form-urlencoded")) {
			   this.content = URLDecoder.decode(contenido, "UTF-8");
			}
			else
			{
				this.content = contenido;
			}
			this.resourceParameters = new LinkedHashMap<String, String>();
			if(this.content.matches(".*&.*"))
			{
				String[] aux1 = this.content.split("&");
				
				for(int i = 0;i<aux1.length;i++)
				{
					String[] aux2 = aux1[i].split("=");
					this.resourceParameters.put(aux2[0], aux2[1]);
					
				}
			}
			else
			{
				String[] aux1 = this.content.split("=");
				this.resourceParameters.put(aux1[0], aux1[1]);
			}
			break;
		
			/*if (this.getMethod().equals(HTTPRequestMethod.POST)) {
				if (linea.matches(".*&.*")) {
					resourceParameters = new LinkedHashMap<String, String>();
					String[] cadenaAux = linea.split("&");
					System.out.println(cadenaAux[1]);

					for (int i = 0; i < cadenaAux.length; i++) {
						String[] cadenaAux1 = cadenaAux[i].split("=");
						resourceParameters.put(cadenaAux1[0], cadenaAux1[1]);
					}
				}

			}*/
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getMethod().name()).append(' ').append(this.getResourceChain())
				.append(' ').append(this.getHttpVersion()).append("\r\n");

		for (Map.Entry<String, String> param : this.getHeaderParameters().entrySet()) {
			sb.append(param.getKey()).append(": ").append(param.getValue()).append("\r\n");
		}

		if (this.getContentLength() > 0) {
			sb.append("\r\n").append(this.getContent());
		}

		return sb.toString();
	}
}
