package es.uvigo.esei.dai.hybridserver.http;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class HTTPResponse {
	
	private HTTPResponseStatus status;
	private String version;
	private String content;
	private Map<String, String> parameters; 
	
	public HTTPResponse() {
		
		
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
		return null;
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
