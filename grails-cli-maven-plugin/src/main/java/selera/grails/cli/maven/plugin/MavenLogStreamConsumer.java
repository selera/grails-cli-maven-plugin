package selera.grails.cli.maven.plugin;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.cli.StreamConsumer;

public class MavenLogStreamConsumer implements StreamConsumer 
{ 
    private Log log; 
    private String linePrefix; 
    private String level; 
    
    /** 
     * @param log 
     */ 
    public MavenLogStreamConsumer(Log log, String linePrefix, String level) 
    { 
        this.log = log; 
        this.linePrefix = linePrefix; 
        this.level = level; 
    } 

    /** 
     * @see org.codehaus.plexus.util.cli.StreamConsumer#consumeLine(java.lang.String) 
     */ 
    public void consumeLine(String line) 
    {
        if (level.equals("error")) {
            log.error(linePrefix + ' ' + line); 
        } else if (level.equals("info")) {
            log.info(linePrefix + ' ' + line); 
        } else if (level.equals("debug")) {
            log.debug(linePrefix + ' ' + line); 
        }   
    }   
}