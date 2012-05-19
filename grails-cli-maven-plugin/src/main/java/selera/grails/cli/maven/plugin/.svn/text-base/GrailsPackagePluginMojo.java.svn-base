package selera.grails.cli.maven.plugin;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Package a grails plugin.
 *
 * @author Michael Lawler
 * @goal package-plugin
 * @since 0.1
 */
public class GrailsPackagePluginMojo extends AbstractGrailsMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        runGrails("package-plugin", new String[] { "--non-interactive" }, null);
    }
}
