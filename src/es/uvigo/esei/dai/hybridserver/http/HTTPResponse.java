package es.uvigo.esei.dai.hybridserver.http;

import java.io.BufferedWriter;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HTTPResponse {
	
	private HTTPResponseStatus status;
	private String version;
	private String content;
	private Map<String, String> parameters; 
	
	public HTTPResponse() {
		Map<String, String> parameters = new LinkedHashMap<String, String>();
		this.parameters = parameters;
		
	}

	public HTTPResponseStatus getStatus() {
		return status;
	}

	public void setStatus(HTTPResponseStatus status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public String putParameter(String name, String value) {
		this.parameters.put(name, value);
		return "Par clave valor introducido correctamente.";
	}

	public boolean containsParameter(String name) {
		return false;
	}

	public String removeParameter(String name) {
		return null;
	}

	public void clearParameters() {
	}

	public List<String> listParameters() {
		return null;
	}

	public void print(Writer writer) throws IOException {
		try(BufferedWriter buffer = new BufferedWriter(writer))
		{
	
		buffer.write(this.getVersion()+" "+this.getStatus().getCode()+" "+this.getStatus().getStatus());
		buffer.newLine();
		
		if(this.getParameters()!=null)
		{
		if(!this.parameters.isEmpty())
		{
		System.out.println("Entra aqui");
		Set<String> listaClaves = this.getParameters().keySet();
		Iterator<String> it = listaClaves.iterator();
		while(it.hasNext())
		{
			String clave = it.next();
			String parametro = this.getParameters().get(clave);
			buffer.newLine();
			buffer.write(clave+": "+parametro);
		}
		buffer.newLine();
		buffer.newLine();
		}
		}

		if(this.getContent()!=null)
		{
		int tamanho = this.getContent().length();
		buffer.write("Content-Length: "+tamanho);
		buffer.newLine();
		buffer.newLine();
		buffer.write(this.getContent());
		}
		else
		{
			buffer.newLine();
		}
		
		buffer.flush();
		}
		
	}

	@Override
	public String toString() {
		final StringWriter writer = new StringWriter();

		try {
			this.print(writer);
		} catch (IOException e) {
		}

		return writer.toString();
	}
}