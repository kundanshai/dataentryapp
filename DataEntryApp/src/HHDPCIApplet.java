/************************************************************************************************************
 * @(#) HHDPCIApplet.java   05 Aug 2013
 * 
 *
 *************************************************************************************************************/

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * <p>
 * Main Class for HHD PCI Application.
 * </p>
 * 
 * @author Matiur Rahman(Tata Consultancy Services)
 * @since 05-08-2013
 * 
 */
public class HHDPCIApplet extends Applet implements ActionListener {

	/** Password of the remote server to be connected */
	private static String COPY_FROM_SYSTEM_PASSWORD = "cissys123";
	/** URL of the remote server to be connected */
	private static String COPY_FROM_SYSTEM_URL = "172.18.41.156";
	/** User Name of the remote server to be connected */
	private static String COPY_FROM_SYSTEM_USERNAME = "cissys";
	/** Path of the remote server to where the files will be copied */
	private static String COPY_TO_SYSTEM_ABSOLUTE_PATH = "/home/cissys";
	/** Password of the remote server to be connected */
	private static String COPY_TO_SYSTEM_PASSWORD = "cissys123"; // gdbios6025
	/** URL of the remote server to be connected */
	private static String COPY_TO_SYSTEM_URL = "172.18.41.156";// 223.0.0.10
	/** User Name of the remote server to be connected */
	private static String COPY_TO_SYSTEM_USERNAME = "cissys";// root
	/** Default SFTP port to be used while connecting to FTP server */
	private static final int DEFAULT_PORT = 22;
	/** Name of the files will be down-loaded or uploaded */
	private static String DIR_NAME = getCurrentDate();
	/** Name of the files will be down-loaded or uploaded */
	private static String FILE_NAME = "djb.4.12.tar.gz";
	/** Path of the local system to where the files will be down-loaded */
	private static String LOCAL_SYSTEM_ABSOLUTE_PATH = "c:\\Temp" + "\\"
			+ DIR_NAME;
	/** Default EXEC protocol to be used while connecting to server */
	private static final String PROTOCOL_EXEC = "exec";

	/** Default SFTP protocol to be used while connecting to FTP server */
	private static final String PROTOCOL_SFTP = "sftp";

	/** Default SHELL protocol to be used while connecting to server */
	private static final String PROTOCOL_SHELL = "shell";
	/** Path of the remote server from where the files will be down-loaded */
	private static String COPY_FROM_SYSTEM_ABSOLUTE_PATH = "/home/cissys/"
			+ DIR_NAME;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default SFTP strict host key checking to be used while connecting to FTP
	 * server
	 */
	private static final String STRICT_HOST_KEY_CHECKING = "no";

	/**
	 * <p>
	 * To read the contents in a File and to check if records exists.
	 * </p>
	 * 
	 * @param in
	 * @return b
	 * @throws IOException
	 */
	static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

	/**
	 * <p>
	 * Copy a File To a Remote System.
	 * </p>
	 * 
	 * @param remoteSystemURL
	 *            URL of the Remote System to be connected.
	 * @param remoteSystemUserName
	 *            User Name of the Remote System to be connected.
	 * @param remoteSystemPassword
	 *            Password of the Remote System to be connected.
	 * @param port
	 *            Port of the Remote System to be connected.
	 * @param remoteSystemAbsolutePath
	 *            Absolute Path on the Remote System to be copied.
	 * @param remoteFileName
	 *            FileName of the file to be copied.
	 * @param localSystemAbsolutePath
	 *            Absolute Path on the Local System to be copied.
	 */
	public static void copyFileToRemoteSystem(String remoteSystemURL,
			String remoteSystemUserName, String remoteSystemPassword, int port,
			String remoteSystemAbsolutePath, String dirName,
			String remoteFileName, String localSystemAbsolutePath) {
		// System.out.println("Copying Start");
		FileInputStream fis = null;
		Session session = null;
		try {
			String lfile = localSystemAbsolutePath + "\\" + remoteFileName;
			String rfile = remoteSystemAbsolutePath + "/" + dirName + "/"
					+ remoteFileName;
			JSch jsch = new JSch();
			session = jsch.getSession(remoteSystemUserName, remoteSystemURL,
					port);
			if (session != null) {
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
				session.setConfig(config);
				session.setPassword(remoteSystemPassword);
				session.connect();
			}
			boolean ptimestamp = true;

			// exec 'scp -t rfile' remotely
			String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
			System.out.println(getCurrentTimeStamp() + "::Command sent:\n"
					+ command);
			Channel channel = session.openChannel(PROTOCOL_EXEC);
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			if (checkAck(in) != 0) {
				System.exit(0);
			}

			File _lfile = new File(lfile);

			if (ptimestamp) {
				command = "T " + (_lfile.lastModified() / 1000) + " 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
				out.write(command.getBytes());
				out.flush();
				if (checkAck(in) != 0) {
					System.exit(0);
				}
			}

			// send "C0644 filesize filename", where filename should not include
			// '/'
			long filesize = _lfile.length();
			command = "C0644 " + filesize + " ";
			if (lfile.lastIndexOf('/') > 0) {
				command += lfile.substring(lfile.lastIndexOf('/') + 1);
			} else {
				command += lfile;
			}
			command += "\n";
			out.write(command.getBytes());
			out.flush();
			if (checkAck(in) != 0) {
				System.exit(0);
			}

			// send a content of lfile
			fis = new FileInputStream(lfile);
			byte[] buf = new byte[1024];
			while (true) {
				int len = fis.read(buf, 0, buf.length);
				if (len <= 0)
					break;
				out.write(buf, 0, len); // out.flush();
			}
			fis.close();
			fis = null;
			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();
			if (checkAck(in) != 0) {
				System.exit(0);
			}
			out.close();

			channel.disconnect();
			System.out
					.println(getCurrentTimeStamp() + "::Coppied Successfully");
		} catch (Exception e) {
			System.out.println("Copying Failed");
			System.out.println(e);
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception ee) {
			}
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
	}

	/**
	 *<p>
	 * Create Directory in local system with name as current date in yyyy-MM-dd
	 * format.
	 * </p>
	 * 
	 * @param localSystemAbsolutePath
	 *            Absolute Path on the Local System the directory to be created.
	 */
	public static void createDirectoryOnLocalSystem(
			String localSystemAbsolutePath) {
		try {
			File files = new File(localSystemAbsolutePath);
			if (!files.exists()) {
				System.out.println(getCurrentTimeStamp() + "::File Path::"
						+ localSystemAbsolutePath);
				if (files.mkdirs()) {
					System.out.println(getCurrentTimeStamp()
							+ "::Multiple directories are created!");
				} else {
					System.out.println(getCurrentTimeStamp()
							+ "::Failed to create multiple directories!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Create a Directory On a Remote System.
	 * </p>
	 * 
	 * @param remoteSystemURL
	 *            URL of the Remote System to be connected.
	 * @param remoteSystemUserName
	 *            User Name of the Remote System to be connected.
	 * @param remoteSystemPassword
	 *            Password of the Remote System to be connected.
	 * @param port
	 *            Port of the Remote System to be connected.
	 * @param dirName
	 *            Name of the Directory on the Remote System to be Created.
	 */
	public static void createDirectoryOnRemoteSystem(String remoteSystemURL,
			String remoteSystemUserName, String remoteSystemPassword, int port,
			String dirName) {
		System.out.println(getCurrentTimeStamp() + "::Create directory Start");
		Session session = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(remoteSystemUserName, remoteSystemURL,
					port);
			if (session != null) {
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
				session.setConfig(config);
				session.setPassword(remoteSystemPassword);
				session.connect();
			}
			// exec 'mkdir CurrentDate' remotely
			String command = "mkdir " + dirName;
			System.out.println(getCurrentTimeStamp() + "::Command sent::"
					+ command);
			Channel channel = session.openChannel(PROTOCOL_EXEC);
			((ChannelExec) channel).setCommand(command);
			// get I/O streams for remote scp
			// OutputStream out = channel.getOutputStream();
			// InputStream in = channel.getInputStream();
			channel.connect();
			channel.disconnect();
			System.out.println(getCurrentTimeStamp()
					+ "::Directory Created Successfully.");
		} catch (Exception e) {
			System.out.println("Directory Create Failed");
			System.out.println(e);
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
	}

	/**
	 * <p>
	 * Down-load File From Remote System.
	 * <p>
	 * 
	 * @param remoteSystemURL
	 *            URL of the Remote System to be connected.
	 * @param remoteSystemUserName
	 *            User Name of the Remote System to be connected.
	 * @param remoteSystemPassword
	 *            Password of the Remote System to be connected.
	 * @param port
	 *            Port of the Remote System to be connected.
	 * @param remoteSystemAbsolutePath
	 *            Absolute Path on the Remote System to be copied.
	 * @param remoteFileName
	 *            File Name of the file to be copied.
	 * @param localSystemAbsolutePath
	 *            Absolute Path on the Local System to be copied.
	 */
	public static void downloadFileFromRemoteSystem(String remoteSystemURL,
			String remoteSystemUserName, String remoteSystemPassword, int port,
			String remoteSystemAbsolutePath, String remoteFileName,
			String localSystemAbsolutePath) {
		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession(remoteSystemUserName, remoteSystemURL,
					port);
			if (session != null) {
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
				session.setConfig(config);
				session.setPassword(remoteSystemPassword);
				session.connect();
				Channel channel = session.openChannel(PROTOCOL_SFTP);
				if (channel != null) {
					channel.connect();
					ChannelSftp sftpChannel = (ChannelSftp) channel;
					try {
						// System.out.println("Copying from :: "
						// + remoteSystemAbsolutePath + " ::to:: "
						// + localSystemAbsolutePath);
						String remoteFileAbsolutePath = remoteSystemAbsolutePath
								+ "/" + remoteFileName;
						sftpChannel.get(remoteFileAbsolutePath,
								localSystemAbsolutePath);
					} catch (SftpException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					sftpChannel.exit();
				}
			}
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
	}

	/**
	 * <p>
	 * Get Current System Date.
	 * </p>
	 * 
	 * @return Current Date in yyyy-MM-dd format.
	 */
	private static String getCurrentDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	/**
	 * <p>
	 * Get Current System Time Stamp.
	 * </p>
	 * 
	 * @return Current System Time Stamp in yyyy-MM-dd HH:mm:ss.SSS format.
	 */
	private static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	/**
	 * <p>
	 * Run shell commands. Commands are set to a list.
	 * </p>
	 * 
	 *@param remoteSystemURL
	 *            URL of the Remote System to be connected.
	 * @param remoteSystemUserName
	 *            User Name of the Remote System to be connected.
	 * @param remoteSystemPassword
	 *            Password of the Remote System to be connected.
	 * @param port
	 *            Port of the Remote System to be connected.
	 * @param commandList
	 *            List of commands to be executed.
	 */
	public  void runCommands(String remoteSystemURL,
			String remoteSystemUserName, String remoteSystemPassword, int port,
			List<String> commandList) {
		Session session = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(remoteSystemUserName, remoteSystemURL,
					port);
			if (null != session) {
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", STRICT_HOST_KEY_CHECKING);
				session.setConfig(config);
				session.setPassword(remoteSystemPassword);
				session.connect();
				/*
				 * only shell
				 */
				Channel channel = session.openChannel(PROTOCOL_SHELL);
				channel.setOutputStream(System.out);
				/*
				 * printStream for convenience
				 */
				PrintStream shellStream = new PrintStream(channel
						.getOutputStream());
				// OutputStream out = channel.getOutputStream();
				InputStream in = channel.getInputStream();
				channel.connect();
				for (String command : commandList) {
					shellStream.println(command);
					shellStream.flush();
				}
				byte[] tmp = new byte[1024];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);
						if (i < 0)
							break;
						System.out.print(new String(tmp, 0, i));
						t1.setText(new String(tmp, 0, i));
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
				Thread.sleep(5000);
				channel.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.disconnect();
			}
		}
	}

	Button synch,cancel,info;

//	Button b[] = new Button[10];

	StringBuffer buffer = new StringBuffer();
	String msg = " ";
	char OP;
	TextField t1;
	int v1, v2, result;

	/**
	 * <p>
	 * This method is used to call the main method to Synchronize contents
	 * </p>
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		String str = ae.getActionCommand();
		if (str.equals("Synchronize")) {
			t1.setText("Started Synchronization...");
			synchronize();
			t1.setText("Synchronization Complete.");
		}
		if (str.equals("Cancel")) {
			t1.setText("Exit");
			System.exit(0);
		}
		if (str.equals("Info")) {
			t1.setText("This under Developement.");
			System.exit(0);
		}

	}

	/**
	 * <p>
	 * This method sets color, background and adds buttons Cancel, Synchronize
	 * and Info to PCI application
	 * </p>
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#init()
	 */
	public void init() {
		Color k = new Color(120, 89, 90);
		setBackground(k);
		t1 = new TextField(100);
		GridLayout gl = new GridLayout(4, 5);
		setLayout(gl);
		cancel = new Button("Cancel");
		synch=new Button("Synchronize");
		info=new Button("Info");
		t1.addActionListener(this);
		add(t1);
		add(cancel);
		add(synch);
		add(info);
		cancel.addActionListener(this);
		synch.addActionListener(this);
		info.addActionListener(this);
	}

	/**
	 * <p>
	 * Main Method of HHD PCI Application.
	 * </p>
	 * 
	 * @param arg
	 */
	public void synchronize() {
		try {
//			createDirectoryOnLocalSystem(LOCAL_SYSTEM_ABSOLUTE_PATH);
//			System.out.println(getCurrentTimeStamp() + "::Download "
//					+ FILE_NAME + " Started from "
//					+ COPY_FROM_SYSTEM_ABSOLUTE_PATH + " of "
//					+ COPY_FROM_SYSTEM_URL + " to "
//					+ LOCAL_SYSTEM_ABSOLUTE_PATH);
//			buffer.append(getCurrentTimeStamp() + "::Download " + FILE_NAME
//					+ " Started from " + COPY_FROM_SYSTEM_ABSOLUTE_PATH
//					+ " of " + COPY_FROM_SYSTEM_URL + " to "
//					+ LOCAL_SYSTEM_ABSOLUTE_PATH);
//			repaint();
//			downloadFileFromRemoteSystem(COPY_FROM_SYSTEM_URL,
//					COPY_FROM_SYSTEM_USERNAME, COPY_FROM_SYSTEM_PASSWORD,
//					DEFAULT_PORT, COPY_FROM_SYSTEM_ABSOLUTE_PATH, FILE_NAME,
//					LOCAL_SYSTEM_ABSOLUTE_PATH);
//			buffer.append(getCurrentTimeStamp() + "::Download " + FILE_NAME
//					+ " Completed from " + COPY_FROM_SYSTEM_ABSOLUTE_PATH
//					+ " of " + COPY_FROM_SYSTEM_URL + " to "
//					+ LOCAL_SYSTEM_ABSOLUTE_PATH);
//			repaint();
//			// HHDPCIAppUtil.createDirectoryOnRemoteSystem(COPY_TO_SYSTEM_URL,
//			// COPY_TO_SYSTEM_USERNAME, COPY_TO_SYSTEM_PASSWORD, DEFAULT_PORT,
//			// DIR_NAME);
//			buffer.append(getCurrentTimeStamp() + "::Copy Started to "
//					+ COPY_TO_SYSTEM_URL + " from "
//					+ LOCAL_SYSTEM_ABSOLUTE_PATH + " to "
//					+ COPY_TO_SYSTEM_ABSOLUTE_PATH + "/" + DIR_NAME);
//			repaint();
//			copyFileToRemoteSystem(COPY_TO_SYSTEM_URL, COPY_TO_SYSTEM_USERNAME,
//					COPY_TO_SYSTEM_PASSWORD, DEFAULT_PORT,
//					COPY_TO_SYSTEM_ABSOLUTE_PATH, DIR_NAME, FILE_NAME,
//					LOCAL_SYSTEM_ABSOLUTE_PATH);
//			System.out.println(getCurrentTimeStamp() + "::Copy Completed to "
//					+ COPY_TO_SYSTEM_URL + " from "
//					+ LOCAL_SYSTEM_ABSOLUTE_PATH + " to "
//					+ COPY_TO_SYSTEM_ABSOLUTE_PATH + "/" + DIR_NAME);
			List<String> commandList = new ArrayList<String>();
			commandList.add("pwd");
//			commandList.add("cd " + DIR_NAME);
//			commandList.add("pwd");
//			commandList.add("ls -ltr");
//			commandList.add("tar -zxvf " + FILE_NAME);
//			commandList.add("ls -ltr");
			runCommands(COPY_TO_SYSTEM_URL, COPY_TO_SYSTEM_USERNAME,
					COPY_TO_SYSTEM_PASSWORD, DEFAULT_PORT, commandList);
//			Thread.sleep(5000);
//			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
