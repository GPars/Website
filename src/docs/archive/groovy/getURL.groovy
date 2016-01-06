//@Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1')  don't need this as groovy1.8+ alreadey has GPars in it !
// trial to get the 3 RSS URL feeds we use in GParsDocs gpars.groovy code
import groovyx.gpars.GParsPool


def urls = ['http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/','https://groups.google.com/forum/feed/gpars-developers/msgs/rss.xml?num=30', 'https://groups.google.com/forum/feed/gpars-users/msgs/rss.xml?num=30']

String tx="";

int count=0;
def result=[:];
def starttask = System.currentTimeMillis();


GParsPool.withPool() {

    Closure download = {String url ->
    try{
            url.toURL().text
        }
        catch(Exception x) { return ""; }
    } //end of download


    Closure downloadFormat = {String url ->
        def start = System.currentTimeMillis();
        tx = download(url)
        result[url]=tx;
        def fini = ( System.currentTimeMillis() - start ) / 1000;
        println "found "+tx.size()+" bytes from "+url+" in "+fini+" sec.s";
    } // end of downloadFormat
    
    Closure cachingDownload = downloadFormat.gmemoize();
    
    // grab front pages from several websites and measure their size plus time to access
    def tx9 = urls.findAllParallel {url -> cachingDownload(url)}

    def finitask = ( System.currentTimeMillis() - starttask ) / 1000;
    println "elapsed in "+finitask+" sec.s";

    println "tx9 size()="+tx9.size()+" tx="+tx9
    println "result size()="+result.size()
    result.eachWithIndex{e,ix->
        println e.key+"="+e.value.size();
    } // end of each
    
} // withPool


println "--- the end ---"