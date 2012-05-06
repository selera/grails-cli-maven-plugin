package selera.grails.wrapper.plugin;

import java.util.HashMap;
import java.util.Map;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Tests a grails plugin or application.
 *
 * @author Michael Lawler
 * @goal test-app
 * @since 0.1
 */
public class GrailsTestAppMojo extends AbstractGrailsMojo {

    /**
     * Set this to 'true' to bypass unit/integration tests entirely. Its use is
     * NOT RECOMMENDED, but quite convenient on occasion.
     *
     * @parameter expression="${maven.test.skip}"
     * @since 0.3
     */
    private boolean mavenTestSkip;

    /**
     * Set this to 'true' to skip integration tests. Its use is NOT
     * RECOMMENDED, but quite convenient on occasion.
     *
     * @parameter expression="${skipIntegrationTests}"
     */
    protected boolean skipIntegrationTests;
    
    /**
     * Allows setting of path to AspectJ load-time-weaver agent jar.
     *
     * @parameter expression="${weaveAgentPath}"
     */
    protected String weaveAgentPath;


    public void execute() throws MojoExecutionException, MojoFailureException {

        if (mavenTestSkip) {
            return;
        }

        // Don't run integration tests if the flag has been set
        String[] grailsArgs = skipIntegrationTests ? new String[] { "--unit", "--non-interactive" } : new String[] { "--unit", "--integration", "--non-interactive" };
        
        Map<String,String> envVars = null;
        if (weaveAgentPath != null) {
            envVars = new HashMap<String,String>();
            envVars.put("GRAILS_OPTS","-Dorg.aspectj.weaver.showWeaveInfo=true -javaagent:" + weaveAgentPath);
        }

        runGrails("test-app", grailsArgs, envVars);
    }
}
