/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package selera.grails.wrapper.plugin;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.CommandLineException;


/**
 * Common services for all Mojos using Grails.
 *
 * @author Michael Lawler
 */
public abstract class AbstractGrailsMojo extends AbstractMojo {

    /**
     * Maven POM.
     *
     * @parameter expression="${project}"
     * @readonly
     * @required
     */
    protected MavenProject project;
    
    /**
     * Turns on/off stacktraces in the console output for Grails commands.
     *
     * @parameter expression="${showStacktrace}" default-value="false"
     */
    protected boolean showStacktrace;
    
    /** 
     * @parameter expression="${settings.offline}" 
     */ 
    boolean offline; 

    /** Runs the specified Grails goal.
     * @param nameOfTarget The name of the Grails goal to run.
     * @param grailsArgs Arguments for the Grails goal
     * @throws MojoExecutionException Thrown if there are failures executing the goal.
     */
    protected void runGrails(String nameOfTarget, String[] grailsArgs, Map<String,String> envVars) throws MojoExecutionException {

        String grailsHome = System.getenv("GRAILS_HOME");
        StringBuilder consoleFeedback = new StringBuilder();

        String grailsBinary = "grails";
        if (grailsHome != null && !grailsHome.trim().isEmpty()) {
            grailsBinary = grailsHome + File.separator + "bin" + File.separator + grailsBinary;
        }
        
        // Build commandline
        Commandline cl = new Commandline(grailsBinary);
        consoleFeedback.append(grailsBinary).append(" ");
        
        // If maven is set to 'offline' pass turn on the grails offline flag in the arguments.
        if (this.offline) {
            cl.addArguments( new String[] { "--offline" } );
            consoleFeedback.append("--offline ");
        }
        
        cl.addArguments( new String[] { nameOfTarget } );
        consoleFeedback.append(nameOfTarget);
        if (grailsArgs != null) {
            cl.addArguments( grailsArgs );
            for(String arg : grailsArgs) {
                consoleFeedback.append(" " + arg);
            }
        }
        
        // If the project has specified to print stacktraces to the console
        // turn on the flag in the arguments.
        if(this.showStacktrace) {
            cl.addArguments( new String[] { "--stacktrace" } );
            consoleFeedback.append(" --stacktrace");
        }
        
        cl.addArguments( new String[] { "--verbose", "--plain-output" } );
        consoleFeedback.append(" --verbose --plain-output");

        if (envVars != null) {
            for (Map.Entry<String, String> entry : envVars.entrySet()) {
                getLog().info("Setting environment variable: " + entry.getKey() + "=" + entry.getValue());
                cl.addEnvironment(entry.getKey(), entry.getValue());
            }
        }
        cl.setWorkingDirectory( project.getBasedir().getAbsolutePath() );

        try {
            // provide some console feedback...
            getLog().info("Executing command: " + consoleFeedback.toString()); 
            if ( getLog().isDebugEnabled() ) {
                getLog().debug("Plexus commandline is: " + cl.toString());
            }
            
            MavenLogStreamConsumer err = new MavenLogStreamConsumer(getLog(), "[grails]", "error");
            MavenLogStreamConsumer out = new MavenLogStreamConsumer(getLog(), "[grails]", "info");

            // execute the command
            int exitCode = CommandLineUtils.executeCommandLine(cl, out, err);

            if ( exitCode != 0 ) {
                String cmdLine = CommandLineUtils.toString(cl.getCommandline());

                StringBuffer msg = new StringBuffer( "Exit Code: " + exitCode);
                msg.append(" from command:   ").append(cmdLine);

                throw new MojoExecutionException(msg.toString());
            }           
        }
        catch ( CommandLineException e ) {
            if ( getLog().isErrorEnabled() ) {
                getLog().error( "Exception: " + e.getMessage());
            }
            throw new MojoExecutionException((e.toString()));
        }
    }
}