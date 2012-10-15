package com.crowdstore.web.util;

import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Embedded;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 * A utility class to launch an embedded version of tomcat.
 */
public class TomcatEmbed {
	private int port;
	private Embedded tomcatServer;

	/**
	 * Starts tomcat on an available port.
	 *
	 * @return the TomcatEmbed instance for fluid API.
	 * @throws org.apache.catalina.LifecycleException
	 */
	public TomcatEmbed start() throws LifecycleException, IOException {
		return start("localhost", getAvailablePort());
	}

	/**
	 * Starts tomcat on given port.
	 *
	 * @param hostname the host name on which this server should be bound
	 * @param port
	 *            the port to listen to
	 * @return the TomcatEmbed instance for fluid API.
	 * @throws LifecycleException
	 */
	public TomcatEmbed start(String hostname, int port) throws LifecycleException, IOException {
		this.port = port;
		String noIdeaWhatThisDoes = new File("build/app").getAbsolutePath();
		String app = new File("src/main/webapp").getAbsolutePath();
		tomcatServer = new Embedded();
		Engine engine = tomcatServer.createEngine();
		engine.setDefaultHost(hostname);

		Host host = tomcatServer.createHost(hostname, noIdeaWhatThisDoes);
		engine.addChild(host);

		StandardContext context = ((StandardContext) tomcatServer
				.createContext("", app));
		context.setWorkDir(new File("target/work").getAbsolutePath());
		context.setDefaultWebXml(new ClassPathResource("META-INF/web-resources/default-web.xml").getFile().getAbsolutePath());
		host.addChild(context);

		tomcatServer.addEngine(engine);
		Connector connector = tomcatServer.createConnector(hostname, port, false);
        connector.setURIEncoding("UTF-8");
		tomcatServer.addConnector(connector);
		tomcatServer.setAwait(true);
		tomcatServer.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
					TomcatEmbed.this.stop();
				} catch (LifecycleException e) {
				}
            }
        });

		return this;
	}

    /**
	 *
	 * @return the port on which the server is listening, 0 if not started.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Stops tomcat embedded.
	 * @throws LifecycleException
	 */
	public void stop() throws LifecycleException {
		if (tomcatServer != null) {
			tomcatServer.stop();
		}
		port = 0;
		tomcatServer = null;
	}

	/**
	 * Finds an available port for tomcat to listen to.
	 * @return an available port.
	 */
	private int getAvailablePort() {
		for (int i = 9000; i < 9100; i++) {
			if (available(i)) {
				return i;
			}
		}
		throw new IllegalStateException("unable to find an available port");
	}

	/**
	 * Checks if a port is available.
	 *
	 * @param port
	 *            the port to test.
	 * @return <code>true</code> if port is availble.
	 */
	private boolean available(int port) {
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}

}
