package hudson;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import mockit.Mockit;

public class EnvVarsTest extends TestCase {

	public static class MockSystem {
		public Map<String, String> getenv() {
			Map<String, String> mockEnv = new HashMap<String, String>();
			mockEnv.put("CaseSensitiveEnvVarName", "testvalue");
			return mockEnv;
		}
	}

	public void testCaseInsensitiveHandlingOfMasterEnvVars() {
		String x = "hi";
		System.out.println(EnvVarsTest.class.getClassLoader());
		System.out.println(String.class.getClassLoader());
		Mockit.redefineMethods(System.class, MockSystem.class);
		assertNotNull(EnvVars.masterEnvVars.get("CaseSensitiveEnvVarName"));
		assertNotNull(EnvVars.masterEnvVars.get("CASESENSITIVEENVVARNAME"));
	}

	@Override protected void tearDown() throws Exception {
		super.tearDown();
		Mockit.restoreAllOriginalDefinitions();
	}
}
