/**
 *  Temario DAI
 *  Copyright (C) 2014 Miguel Reboiro-Jato
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.uvigo.esei.dai.hybridserver.threads;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import es.uvigo.esei.dai.hybridserver.http.HTTPHeaders;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponse;
import es.uvigo.esei.dai.hybridserver.http.HTTPResponseStatus;

public class ServiceThread implements Runnable {
   private final Socket socket;

   public ServiceThread(Socket clientSocket) throws IOException {
      this.socket = clientSocket;
   }

   @Override
   public void run() {
      try (Socket socket = this.socket) {
            OutputStream out = socket.getOutputStream();
			// Responder al cliente
			HTTPResponse respuesta = new HTTPResponse();
			respuesta.setStatus(HTTPResponseStatus.S200);
			respuesta.setVersion(HTTPHeaders.HTTP_1_1.getHeader());
			respuesta.setContent("Hybrid Server");
			
			respuesta.print(new OutputStreamWriter(out));

			out.flush();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}