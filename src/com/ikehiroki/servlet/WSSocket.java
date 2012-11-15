package com.ikehiroki.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.eclipse.jetty.websocket.WebSocket;

public class WSSocket implements WebSocket.OnTextMessage {

	List<String> chatlog;
	List<Connection> connections = null;

	Connection connection;
	
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	@SuppressWarnings("unchecked")
	public WSSocket(ServletContext context) {
		chatlog = (List<String>) context.getAttribute("chatlog");
		connections = (List<Connection>) context.getAttribute("connections");
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("Running Server Message Sending");
				try {
					if (connection != null) {
						connection.sendMessage("Server To Client Polling:" + new Date());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 10, 610, TimeUnit.SECONDS);
	}

	
	public void onOpen(Connection connection) {
		connection.setMaxIdleTime(Integer.MAX_VALUE);
		this.connection = connection;
		connections.add(connection);
		for (String log : chatlog) {
			sendlog(log);
		}
	}

	public void onClose(int closeCode, String message) {
		connections.remove(connection);
		connection.close();
	}

	public void onMessage(String data) {
		chatlog.add(data);
		sendlog(data);
	}

	private void sendlog(String data) {
		for (Connection c : connections) {
			try {
				c.sendMessage(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
