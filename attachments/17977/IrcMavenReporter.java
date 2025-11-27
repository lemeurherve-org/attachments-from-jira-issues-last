package hudson.plugins.ircbot;

import hudson.Launcher;
import hudson.Util;
import hudson.maven.MavenBuild;
import hudson.maven.MavenReporter;
import hudson.maven.MavenReporterDescriptor;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.plugins.ircbot.IrcPublisher.DescriptorImpl.IrcBot;
import hudson.tasks.Mailer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

/**
 * @author Mikko Peltonen
 */
public class IrcMavenReporter extends MavenReporter {
    private List<String> channels = new ArrayList<String>();

    @Override
    public MavenReporterDescriptor getDescriptor() {
        return Descriptor.DESCRIPTOR;
    }

    @Override
    public boolean end(MavenBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        if (!isAborted(build) && isStatusChanged(build)) {
            notifyChannels(build);
        }
        return true;
    }

    private void notifyChannels(MavenBuild build) {
        String buildAbsoluteUrl = Mailer.DESCRIPTOR.getUrl() + Util.encode(build.getUrl());

        StringBuilder message = new StringBuilder();
        if (build.getResult() == Result.FAILURE) {
            message.append("Hudson build failed: " + buildAbsoluteUrl + "console");
        } else if (build.getResult() == Result.UNSTABLE) {
            message.append("Hudson build became unstable: " + buildAbsoluteUrl + "testReport");
        } else if (build.getResult() == Result.SUCCESS) {
            MavenBuild previousBuild = build.getPreviousBuild();
            if (previousBuild != null) {
                if (previousBuild.getResult() == Result.FAILURE) {
                    message.append("Hudson build is back to normal: " + buildAbsoluteUrl);
                }
                if (previousBuild.getResult() == Result.UNSTABLE) {
                    message.append("Hudson build is back to stable: " + buildAbsoluteUrl);
                }
            }
        }

        if (message.length() > 0) {
            IrcBot bot = IrcPublisher.DESCRIPTOR.bot;
            List<String> globalChannels = IrcPublisher.DESCRIPTOR.channels;

            for (String channel : globalChannels) {
                bot.sendMessage(channel, message.toString());
            }
            for (String channel : channels) {
                bot.sendMessage(channel, message.toString());
            }
        }
    }

    private boolean isAborted(MavenBuild build) {
        return build.getResult() == Result.ABORTED;
    }

    private boolean isStatusChanged(MavenBuild build) {
        return build.getResult() != build.getPreviousBuild().getResult();
    }

    public void setChannels(String channels) {
        if (channels == null) {
            this.channels.clear();
            return;
        }
        for (String c : Arrays.asList(channels.split(" "))) {
            if (c.trim().length() > 0) {
                this.channels.add(c.trim());
            }
        }
    }

    public String getChannels() {
        StringBuilder sb = new StringBuilder();
        for (String c : channels) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    static class Descriptor extends MavenReporterDescriptor {
        public static Descriptor DESCRIPTOR = new Descriptor();

        private Descriptor() {
            super(IrcMavenReporter.class);
        }

        @Override
        public String getConfigPage() {
            return getViewPage(IrcPublisher.class, "config.jelly");
        }

        @Override
        public MavenReporter newInstance(StaplerRequest req) throws FormException {
            MavenReporter reporter = new IrcMavenReporter();
            req.bindParameters(reporter);
            return reporter;
        }

        @Override
        public String getDisplayName() {
            return "IRC Reporter";
        }
    }
}
