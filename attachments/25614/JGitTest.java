import hudson.EnvVars;
import hudson.FilePath;
import hudson.model.TaskListener;
import hudson.plugins.git.GitChangeSet;
import hudson.plugins.git.GitException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.jenkinsci.plugins.gitclient.Git;
import org.jenkinsci.plugins.gitclient.GitClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.WithoutJenkins;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class JGitTest {
    public final PersonIdent bob = new PersonIdent("Bob", "bob@bob.com");

    private GitClient git;
    private File gitDir;

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();
    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        TaskListener taskListener = jenkinsRule.createTaskListener();
        EnvVars envVars = new EnvVars();
        gitDir = tmpFolder.getRoot();
        FilePath gitDirPath = new FilePath(gitDir);
        git = Git.with(taskListener, envVars).in(gitDirPath).getClient();
        git.init();
    }

    public void commit(final File file, final String fileContent, final PersonIdent author, final String msg) throws GitException, InterruptedException {
        FilePath filePath = new FilePath(file);
        try {
            filePath.write(fileContent, null);
        } catch (Exception e) {
            throw new GitException("unable to write file", e);
        }
        String path = filePath.getRemote().replaceFirst(gitDir.getAbsolutePath() + "/", "");
        git.add(path);
        git.setAuthor(author);
        git.setCommitter(author);
        git.commit(msg);
    }

    @WithoutJenkins
    @Test
    public void testShowRevisionForFirstCommit() throws IOException, InterruptedException {
        commit(tmpFolder.newFile("1"), "1", bob, "add 1");
        List<ObjectId> revs = git.revListAll();
        List<String> revLines = git.showRevision(revs.get(0));
        GitChangeSet changeSet = new GitChangeSet(revLines, true);
        assertThat(changeSet.getComment(), is("add 1\n"));
        assertThat(changeSet.getAuthorName(), is(bob.getName()));
    }

}
