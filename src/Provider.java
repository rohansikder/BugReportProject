

import java.io.*;
import java.net.*;
public class Provider{
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	ServerThread s;
	UserList sharedUser;
	BugList sharedBug;
	Provider(){}
	void run()
	{
		try{
			//1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			sharedUser = new UserList();
			sharedBug = new BugList();
			//2. Wait for connection
			while(true)
			{
				System.out.println("Waiting for connection");
				connection = providerSocket.accept();
				System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				s = new ServerThread(connection, sharedBug, sharedUser);
				s.start();
			}
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("Server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	void sendLoginVerfication(boolean loginStatus)
	{
		try{
			out.writeObject(loginStatus);
			out.flush();
			//System.out.println("server>" + loginStatus);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		Provider server = new Provider();
		while(true){
			server.run();
		}
	}
}
