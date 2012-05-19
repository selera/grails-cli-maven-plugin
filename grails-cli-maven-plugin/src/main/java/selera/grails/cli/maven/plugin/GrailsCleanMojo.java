package selera.grails.cli.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Invokes 'grails clean' on a Grails project.
 *
 * @author Michael Lawler
 * @goal clean
 * @since 0.1
 */
public class GrailsCleanMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        runGrails("clean", new String[] { "--non-interactive" }, null);
    }
}