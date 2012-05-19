package selera.grails.cli.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Compiles a grails plugin or application.
 *
 * @author Michael Lawler
 * @goal compile
 * @since 0.1
 */
public class GrailsCompileMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        runGrails("compile", new String[] { "--non-interactive" }, null);
    }
}
