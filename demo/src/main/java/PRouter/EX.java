package PRouter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;
/**
 * 
 * @author Adil M Ladadwah
 * This class
 */
public class EX {

	private static TelnetClient telnet = new TelnetClient();
	private static InputStream in;
	private static PrintStream out;
	private static String prompt = "#";

	public static void main(String[] args) {
		try {
			connect();
			sendCommand("sh run");
			sendCommand("config t");
			sendCommand("int gig0/1/5");
			sendCommand("ip address 100.0.0.100 255.255.255.0");
			sendCommand("no shutdown");
			sendCommand("exit");
			sendCommand("exit");
			sendCommand("sh run");
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect() throws SocketException, IOException {
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

	public static String readUntil(String pattern) {

		try {
			char lastChar = pattern.charAt(pattern.length() - 1);

			StringBuffer sb = new StringBuffer();
			char ch;
			ch = (char) in.read();
			while (true) {
				System.out.print(ch);
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

	public static String readUntilLine(String pattern) {

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

	public static void write(String value) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendCommand(String command) {
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

	public static void disconnect() {
		try {
			telnet.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
