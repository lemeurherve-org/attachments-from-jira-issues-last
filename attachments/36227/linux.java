package Config;

import static Config.CompareResults.writeResults;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.ChannelSubsystem;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class LinuxConnect{

	public static String VerifyExecution(String Command)
			throws Exception {

		String s = null;

		try {
			
			String command1 = Command;
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.setConfig("PreferredAuthentications",
					"publickey,keyboard-interactive,password");
			session.connect();
			System.out.println("Connected");
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			System.out.println(in);
			channel.connect();
			byte[] tmp = new byte[10000000];

			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 10000000);
					if (i < 0)
						break;
					s = (new String(tmp, 0, i));
					System.out.println(s);

				}
				if (channel.isClosed()) {
					System.out.println("exit-status: "
							+ channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (s);
	}

	
	public static Session Connect()
	{
		JSch jsch = new JSch();
		Session session = null;
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no"); //setting up the configuration for java
		
		try {
	        			       
			config.put("StrictHostKeyChecking", "no");
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			session.setConfig(config);
			session.setConfig("PreferredAuthentications",
					"publickey,keyboard-interactive,password");
			session.connect();
			System.out.println("Connected");
		
			//return session;
		
		
		}catch(JSchException e){
			System.out.println("Connection errors"+e);
		}
		
		return session;

	}

	
}
		  
		
	
	

