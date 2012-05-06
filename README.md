grails-wrapper-plugin
=====================

Simple maven wrapper plugin around grails via the command line. Grails retains ownership of build dependency management. Maven is only used to invoke the phases of the build lifecycle. Grails project is not aware and does not depend on the presence of Maven. Good if you want your larger project driven by Maven, but want to be able to drop into a specific grails project and pretend Maven (and the complexity of Maven&lt;-&gt;Grails integration) does not exist. 