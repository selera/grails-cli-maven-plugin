grails-wrapper-plugin
=====================

Simple maven wrapper plugin around grails via the command line. Looks for grails on your path. Grails retains ownership of build dependency management. Maven is only used to invoke the phases of the build lifecycle. Grails project is not aware and does not depend on the presence of Maven. Good if you want your larger project driven by Maven, but want to be able to drop into a specific grails project and pretend Maven (and the complexity of Maven&lt;-&gt;Grails integration) does not exist. Grails command line support and compatibility is retained.

"mvn compile" and "grails compile" should be identical from the perspective of dependencies and classloaders etc.

Usage:

POM Setup
---------

1. Create a simple maven pom for your grails project (plugin or application).
2. Use packaging 'grails-plugin' or 'grails-app'. The packaging triggers the maven lifecycle of goals appropriate for each.
3. Add the plugin to the build section:
> `		<plugin>  
> 			<groupId>selera.maven</groupId>  
> 			<artifactId>grails-wrapper-plugin</artifactId>  
> 			<version>0.9.2</version>  
> 			<extensions>true</extensions>  
> 		</plugin>`  

See the example multi-module project in the /examples folder which includes an example of an plugin and an app. Via a maven parent pom, the grails plugin is built, packaged and installed as a binary jar into the local repo, then when the grails app is built, it resolves its binary grails plugin dependency from the local maven repo.  

Grails Setup
------------

In the dependency.resolution of our BuildConfig.groovy, we want "pom false". We are not using maven for dependency management, just to invoke the grails commands at the appropriate time within the maven build lifecycle.

For plugins:
 - In your XxxPlugin.groovy set your maven groupId and set packaging = "binary"
For apps:
 - enabled mavenLocal() in your BuildConfig.groovy to allow pulling in your own grails plugins from maven.

Maven Command Line
------------------

Typically use: 'mvn clean install' and you're set.

 - `mvn --offline ...`			is passed through as --offline to all grails commands.
 - `mvn ... -Dstacktrace`			is passed through as --stacktrace to all grails commands.
 - `mvn ... -Dmaven.skip.tests` 		will cause grails test-app to be skipped during the maven test phase
 - `mvn ... -DskipIntegrationTests` 	will cause grails test-app to skip integration tests during the maven test phase
 - `mvn ... -DweaveAgentPath=` 		will cause the jvm weave agent arg to be set during grails test-app
 - `mvn ... -DsetVersion`			will cause the grails version to be set from the maven version at the process-sources phase.

Both plugin and app lifecycles include the standard maven :resources and :testResources goals during the process-resources phase.

Dependencies:
-------------

Grails projects that are managed by this plugin will typically depend on the grails release plugin because the maven install phase is hooked up to the release plugin's maven-install command. This will cause a binary plugin's jar to be installed in your m2 repo, or alternatively the war to be packaged and installed for you app. 

Known Issues:
-------------

 1. POM generation conflict.
We define a very simple pom.xml for each of our grails plugins or apps. But we dont' put any dependencies in these poms. But when the release plugin does the maven install, it sees the pom.xml and assumes maven is controlling the dependencies. So when your plugin is installed into your repo, it gets an empty pom that doesnt contain the dependencies that the plugin actually defines via BuildConfig.groovy. I have a simple patch for the release plugin that recognises that "pom false" means ignore the pom.xml that exists when "generating" a pom for the release plugin's maven-install script. i.e. if "pom false" then always ignore any existing pom.xml and go ahead and generate one anyway. I'll try to get this behaviour pulled into the official grails release plugin as I dont think the behaviour will cause a problem for anyone else.

Resolved Issues:
----------------

 1. Resource Processing conflict with Grails Scripts.
Maven resource processing into /target was occasionally being clobbered by grails plugin installation scripts. Resolved by moving maven resource processing from process-resources phase to within the compile phase but after the grails compile.

