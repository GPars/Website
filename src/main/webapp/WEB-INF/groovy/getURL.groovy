@Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1') 

import groovyx.gpars.GParsPool


def urls = ['http://status.anynines.com/history.rss','http://blog.anynines.com/feed/','https://script.google.com/macros/s/AKfycbyHOJ1GxXDU3DxyaW88niykuDEbBDHzMTDV0j5shw/exec?639826364674215936']

String tx="";

int count=0;
def result=[:];


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

    println "tx9 size()="+tx9.size()+" tx="+tx9
    println "result size()="+result.size()
    result.eachWithIndex{e,ix->
        println e.key+"="+e.value.size();
    } // end of each
    
} // withPool


println "--- the end ---"