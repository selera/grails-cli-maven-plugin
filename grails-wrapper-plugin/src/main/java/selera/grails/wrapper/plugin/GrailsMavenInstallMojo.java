package selera.grails.wrapper.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Install a grails project or plugin into local Maven repository.
 *
 * @author Michael Lawler
 * @goal maven-install
 */
public class GrailsMavenInstallMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        runGrails("maven-install", new String[] { "--non-interactive" }, null);
    }
}
