package com.sonyericsson.hudson.plugins.metadata;

import hudson.model.FreeStyleProject;
import org.jvnet.hudson.test.Bug;
import org.jvnet.hudson.test.HudsonTestCase;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * @author stevenchristou
 *         Date: 10/14/13
 *         Time: 7:16 PM
 */
public class DeleteTest extends HudsonTestCase {
    // ZD-13803
    @Bug(13803)
    public void testDeleteJob() throws Exception {
        FreeStyleProject freeStyleProject = super.createFreeStyleProject("test");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        System.out.println("Before delete: " + sdf.format(new File(freeStyleProject.getRootDir(), "config.xml").lastModified()));
        freeStyleProject.delete();
        Thread.sleep(3 * 1000); // Give it a few seconds to create the file
        System.out.println("After delete: " + sdf.format(new File(freeStyleProject.getRootDir(), "config.xml").lastModified()));
        assertFalse("Project should have been deleted", freeStyleProject.getRootDir().exists());
        jenkins.restart();
        jenkins.doReload();
        System.out.println("After reload: " + sdf.format(new File(freeStyleProject.getRootDir(), "config.xml").lastModified()));
        assertFalse("Project should have been deleted", freeStyleProject.getRootDir().exists());
        assertFalse("Should not be disabled but instead deleted.", freeStyleProject.isDisabled());
    }
}