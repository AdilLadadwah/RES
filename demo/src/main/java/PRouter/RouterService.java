package PRouter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;
import org.springframework.stereotype.Service;


/**
 * 
 * @author Adil M Ladadwah
 * This class use to send command line to
 * Router and get out that command
 */


@Service
public class RouterService {

	private static TelnetClient telnet = new TelnetClient();
	private static InputStream in;
	private static PrintStream out;
	private static String prompt = "#";
	public static String ResposeCommand="";
	
	/**
	 * 
	 * @throws SocketException
	 * @throws IOException
	 * 
	 * This function use to establish connection
	 */
	public  void connect() throws SocketException, IOException {
		try {
			int remoteport = 23;
			telnet.connect("10.63.10.206", remoteport);
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			String password = "lab";
			readUntil("Password: ");
			write(password);
			readUntil(">");
			write("en");
			readUntilLine("\n");
			readUntil("Password: ");
			write(password);
			// Advance to a prompt
			readUntil(prompt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  String readUntil(String pattern) {

		try {
			char lastChar = pattern.charAt(pattern.length() - 1);

			StringBuffer sb = new StringBuffer();
			char ch;
			ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				ResposeCommand=ResposeCommand+ch;
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String readUntilLine(String pattern) {

		try {
			char lastChar = pattern.charAt(pattern.length() - 1);

			StringBuffer sb = new StringBuffer();
			char ch;
			ch = (char) in.read();
			while (true) {
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  void write(String value) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
			ResposeCommand=ResposeCommand+value;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendCommand(String command) {
		try {
			write(command);
			readUntilLine("\n");
			readUntil(prompt);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public  void disconnect() {
		try {
			telnet.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
