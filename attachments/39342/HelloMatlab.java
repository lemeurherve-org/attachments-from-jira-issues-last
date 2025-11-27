package matlabjenkins.matlabjnk;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.TaskListener;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

import javax.annotation.Nonnull;

import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class HelloMatlab  extends Builder implements SimpleBuildStep{

	private final String testPath;

	@DataBoundConstructor
	public HelloMatlab(String testPath)
	{
		this.testPath = testPath;
	}

	public String getTestPath(){

		return this.testPath;
	}



	@Extension
	public static class Descriptor extends BuildStepDescriptor<Builder> {


		// This is to identify which project type in jenkins this should be applicable.
		@Override
		public boolean isApplicable(@SuppressWarnings("rawtypes") Class<? extends AbstractProject> jobType) {
			//return FreeStyleProject.class.isAssignableFrom(jobType);
			//return MatlabItem.class.isAssignableFrom(jobType);
			return true;
		}


		//Overridden Method used to show the text under build dropdown
		@Override
		public String getDisplayName() {
			return "Run MATLAB Tests";
		}



		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {

			save();
			return super.configure(req,formData);
		}


	}



	@Override
	public void perform(@Nonnull Run<?, ?> build,
			@Nonnull FilePath workspace, @Nonnull Launcher launcher,
			@Nonnull TaskListener listener) throws InterruptedException,
			IOException {
		boolean isError = false;
		listener.getLogger().println("This Jenkins is running on "+System.getProperty("os.name"));
		listener.getLogger().println("Invoking tests from , "+this.testPath);

		System.console().flush();

		ProcessBuilder pb = new ProcessBuilder("matlab","-nosplash","-nodesktop","-sd",this.testPath,"-minimize","-r","try;runtests;catch;end;quit","-wait","-log");
		//Indicates that subprocess I/O source or destination will be the same as those of the current process.
		pb.redirectOutput(Redirect.INHERIT);
		Process proc = pb.start();


		//Process proc = Runtime.getRuntime().exec("matlab -nosplash -nodesktop -sd "+this.testPath+" -minimize -r try;runtests;catch;end;quit -wait -log");


		//proc.cmdAsSingleString("matlab -nosplash -nodesktop -sd "+this.testPath+" -minimize -r try;runtests;catch;end;quit -wait -log").join();
		//proc.stdin(proc.cmdAsSingleString("matlab -nosplash -nodesktop -sd "+this.testPath+" -minimize -r try;runtests;catch;end;quit -wait -log").stdin());




		BufferedReader stdInput = new BufferedReader(new
				InputStreamReader(proc.getInputStream()));
		BufferedReader stdError = new BufferedReader(new
				InputStreamReader(proc.getErrorStream()));




		// read the output from the command

		String s = null;
		while ((s = stdInput.readLine()) != null) {

			listener.getLogger().println(s);
		}


		while ((s = stdError.readLine()) != null) {

			listener.getLogger().println(s);
		}

listener.error("");







	}





}
