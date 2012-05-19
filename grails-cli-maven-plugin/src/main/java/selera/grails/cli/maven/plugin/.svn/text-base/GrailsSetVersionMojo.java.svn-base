package selera.grails.cli.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Set the grails application version from the Maven POM version.
 *
 * @author Michael Lawler
 * @goal set-version
 * @requiresProject true
 * @since 0.1
 */
public class GrailsSetVersionMojo extends AbstractGrailsMojo {

    /**
     * Set this property at the command line (-DsetVersion) to engage setVersion as part of the build.
     * By default, this plugin will not call set-version as a build-time performance optimsation.
     *
     * @parameter expression="${setVersion}"
     */
    protected boolean setVersion;


    public void execute() throws MojoExecutionException, MojoFailureException {

        if (setVersion) {
            runGrails("set-version", new String[] { project.getVersion() }, null );
        }        
    }
}
