import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.saucelabs.ci.sauceconnect.SauceConnectTwoManager;

public class SauceConnectManager {

	private static final String SAUCE_CONNECT_KEY = "SAUCE_CONNECT";

	public static final String SAUCE_TUNNEL_IDENTIFIER = "testTunnel";

	private static Map<String, SauceConnectTwoManager> context = new HashMap<String, SauceConnectTwoManager>();

	private static String sauceUsername;

	public static void connect(String sauceUsername, String sauceAccessKey, int port, String httpsProtocol, String options,
							boolean quietMode) {
		Logger.debug("Starting Sauce Connect");
		SauceConnectManager.sauceUsername = sauceUsername;
		if (sauceUsername == null || sauceUsername.equals("")) {
			Logger.error("Sauce sauceUsername not specified");
			return;
		}
		if (sauceAccessKey == null || sauceAccessKey.equals("")) {
			Logger.error("Sauce access key not specified");
			return;
		}

		if (context.get(SAUCE_CONNECT_KEY) != null) {
			// process already running
		} else {
			// find Sauce Connect jar file location
			SauceConnectTwoManager sauceConnectTwoManager = new SauceConnectTwoManager(quietMode);
			try {
				sauceConnectTwoManager.openConnection(sauceUsername, sauceAccessKey, port, null, options, httpsProtocol, null);
				context.put(SAUCE_CONNECT_KEY, sauceConnectTwoManager);
			} catch (IOException e) {
				Logger.error("Error generated when launching Sauce Connect", e);
			}
		}

	}

	public static void stop() {
		SauceConnectTwoManager manager = context.get(SAUCE_CONNECT_KEY);
		if (manager == null) {
			// no process available
			Logger.debug("There were no running Sauce Connect instances found");
		} else {
			// close running process
			manager.closeTunnelsForPlan(SauceConnectManager.sauceUsername, null);
			Logger.info("Sauce Connect stopped");
		}
	}

}