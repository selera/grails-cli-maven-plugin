package selera.grails.cli.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Build a WAR for a grails application.
 *
 * @author Michael Lawler
 * @goal war
 * @since 0.1
 */
public class GrailsWarMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        
        runGrails("war", new String[] { "--non-interactive" }, null);
        
        // now attach the war as the main artifact of this module
        String warFilePath = project.getBasedir().getAbsolutePath() + "/target/" + project.getArtifactId() + "-" + project.getVersion() + ".war";
        File warFile = new File(warFilePath);
        if (warFile.exists()) {
            getLog().info("Attaching artefact: " + warFilePath);
            project.getArtifact().setFile(warFile); // sets the main artifact
        } else {
            getLog().error("Expected artefact not found: " + warFilePath);
        }
    }
}
