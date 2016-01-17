//@Grab(group='org.codehaus.gpars', module='gpars', version='1.1.0')   <-- didn't work ! had to put gpars.jar in /lib
// read more here: http://gpars.org/guide/guide/single.html#introduction

import groovyx.gpars.AsyncFun
import  java.util.concurrent.Future;
import groovyx.gpars.GParsPool

def ans = 0;
def start
def tx=""
def fini
def tx2=""

def tab="<table><tr><th>Domain</th><th>Front Page<br />Byte Size</th><th>Sec.s to Read</th></tr>\n";
def urls = ['https://groups.google.com/forum/feed/gpars-users/msgs/rss.xml?num=30', 
'https://groups.google.com/forum/feed/gpars-developers/msgs/rss.xml?num=30', 
'http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/']

// measure runtime : get start time here
def before = System.currentTimeMillis()

GParsPool.withPool() {

    Closure download = {String url ->
    try{
            url.toURL().text
        }
        catch(Exception x) { return ""; }
    } //end of download

    // use annotation rather than .async()
    //@AsyncFun 
    Closure downloadFormat = {String url ->
        start = System.currentTimeMillis()
          tx = download(url)
        tx = tx.size()
        fini = ( System.currentTimeMillis() - start ) / 1000;
          tx2 = "<tr><td><a href=\"${url}\">${url}</a></td><td class=\"ar\">${tx}</td><td class=\"ar\">${fini}</td></tr>\n"
        tab += tx2
    } // end of downloadFormat
    
    Closure cachingDownload = downloadFormat.gmemoize()
    
    
    // grab front pages from several websites and measure their size plus time to access
    def tx9 = urls.findAllParallel {url -> cachingDownload(url).contains('GROOVY')}
    tab +="</table>";
} // withPool


// compute elapsed time for all tasks in seconds
def after = ( System.currentTimeMillis() - before ) / 1000;
println "elapsed  time:"+after;
println tab;