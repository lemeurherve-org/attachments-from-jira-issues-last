import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Starts a Hudson Agent.
 * First it will download the <tt>slave.jar</tt> from the Hudson server and
 * then start the agent in <i>normal</i> way.
 */
public class StartHudsonAgent {

	/** Main class of the Agent. */
	public static final String AGENT_MAIN_CLASS = "hudson.remoting.Launcher";

	/**
	 * Starts the Hudson Agent.
	 * @param args <ol>
	 *   <li>URL of the Hudson master</li>
	 *   <li>name of the Agent</li></ol>
	 * @throws MalformedURLException if the given Master-URL is invalid
	 * @throws ClassNotFoundException if the Agent main class could not be found
	 * @throws NoSuchMethodException on problems while invocating main-method
	 * @throws SecurityException on problems while invocating main-method
	 * @throws InvocationTargetException on problems while invocating main-method
	 * @throws IllegalAccessException on problems while invocating main-method
	 * @throws IllegalArgumentException on problems while invocating main-method
	 */
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// URL of Hudson master, eg "http://localhost:8080"
		String master = args[0];
		// Name of the already defined Agent, eg "agent1"
		String agentName = args[1]; 
		
		// URL of the slave.jar, available on the Master
		URL slaveJar = new URL(master + "/WEB-INF/slave.jar");
		// URL of the JNLP launch script
		String jnlp = master + "/computer/" + agentName + "/slave-agent.jnlp";
		
		// Launch the Agent via Reflection and ClassLoading directly from the master
		URLClassLoader loader = new URLClassLoader(new URL[]{slaveJar});
		Class<?> clazz = loader.loadClass(AGENT_MAIN_CLASS);
		Method mainMethod = clazz.getMethod("main", String[].class);
		String[] arguments = new String[]{"-jnlpUrl", jnlp};
		mainMethod.invoke(null, (Object)arguments);
	}
	
}
