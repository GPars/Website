@Grab (group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.5.0')

// see: https://app.apiary.io/editadoc/editor to edit the API and for documentation: http://docs.editadoc.apiary.io/
import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.JSON
import groovy.json.JsonSlurper
import groovy.json.JsonOutput


// notes from http://www.labnol.org/internet/twitter-rss-feed/28149/

def googleWidgetURL = "https://script.google.com/macros/s/AKfycbyHOJ1GxXDU3DxyaW88niykuDEbBDHzMTDV0j5shw/exec?639826364674215936"

try
{
    def rss = new XmlSlurper().parse(googleWidgetURL)
    rss.channel.item.each {
        def desc = it.description.toString()
        desc = (desc.size()>2500) ? desc.substring(0,2500) : desc ;
        println "<a href='" +it.link+"'>"+it.title+" (size=${desc.size()})</a>"
        println "->"+desc+"\n\n/n"
    }
}
catch(Exception x)
{
    println "failed to get RSS feed:"+x.message;
}
 
println "--- the end ---"