package selera.grails.cli.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Deploys a grails project or plugin into local Maven repository.
 *
 * @author Michael Lawler
 * @goal maven-deploy
 */
public class GrailsMavenDeployMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        runGrails("maven-install", new String[] { "--non-interactive" }, null);
    }
}
