package com.company;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    private final Map<String, HttpServlet> servlets = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();
    public void addServlet(String url, HttpServlet servlet) {
        servlets.put(url, servlet);
    }
    public void listen(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(Socket clientSocket) {
        new Thread(() -> {
            try {
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();

                String requestLine = new BufferedReader(new InputStreamReader(input)).readLine();
                if (requestLine == null || requestLine.isEmpty()) {
                    String badRequestMessage = "400 Bad Request";
                    output.write(badRequestMessage.getBytes());
                    output.flush();
                    clientSocket.close();
                    return;
                }

                String[] requestParts = requestLine.split(" ");
                if (requestParts.length < 3) {
                    String badRequestMessage = "400 Bad Request";
                    output.write(badRequestMessage.getBytes());
                    output.flush();
                    clientSocket.close();
                    return;
                }

                String method = requestParts[0];
                String uri = requestParts[1];
                String[] parts = uri.split("\\?", 2);
                uri = parts[0];  // To będzie ścieżka bez parametrów
                String httpVersion = requestParts[2];
                MyHttpServletRequest request = new MyHttpServletRequest(method, uri, httpVersion);
                MyHttpServletResponse response = new MyHttpServletResponse();
                if (parts.length > 1) {
                    String paramString = parts[1];
                    for (String param : paramString.split("&")) {
                        String[] pair = param.split("=", 2);
                        if (pair.length == 2) {
                            request.addQueryParam(pair[0], pair[1]);
                        }
                    }
                }



                HttpServlet servlet = servlets.get(request.getRequestURI());
                if (servlet != null) {
                    servlet.service(request, response);
                    byte[] responseBody = response.getResponseBody();
                    output.write(responseBody);
                } else {
                    String notFoundMessage = "404 Not Found";
                    output.write(notFoundMessage.getBytes());
                }

                output.flush();
                clientSocket.close();
            } catch (IOException | ServletException e) {
                e.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }

}