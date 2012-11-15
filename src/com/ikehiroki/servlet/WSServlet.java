package com.ikehiroki.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

/**
 * Jetty WebSocketServlet implementation class WSServlet
 */
@WebServlet("/WSServlet")
public class WSServlet extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * @see org.eclipse.jetty.websocket.WebSocketServlet#doWebSocketConnect(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return new WSSocket(getServletContext());
	}
	


}
