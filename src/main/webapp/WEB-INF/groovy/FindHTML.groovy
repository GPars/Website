/*
/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindHTML.groovy  finds any .html files in the 'path' variable and 
harvests each href and prints that out.Creates a Menu.adoc file in that path. But Note that re-running this module will destroy
any manual changes made to Menu.adoc. Renamed tonites Menu.adoc as Menus.adoc
*/
@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7')
@Grab(group='org.apache.httpcomponents', module='httpclient', version='4.2.5' )

import static groovy.io.FileType.FILES
import groovyx.net.http.RESTClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpUriRequest;

int count = 0;
int href = 0;
int gpars=0;
hdrDone=false;
path = "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp";
String name = "";
outputfile = new File("$path/Menu.adoc")

String hdr = """= GPars - Groovy Parallel Systems
Russell Winder <russel@winder.org.uk>
v1.0, 2015-10-18
:linkattrs:
:linkcss:
:toc: left
:toc-title: Document Index
:icons: font
:source-highlighter: coderay
:docslink: http://www.gpars.org/guide/[GPars Docs]
:description: GPars is a multi-paradigm concurrency framework offering several mutually cooperating high-level concurrency abstractions.
:doctitle: Menu of Guide Pages with Stale URLs


""".toString();

def find(String fn) {
        def i = fn.lastIndexOf("/");
}

def getName(String fn)
{
    String shorty = fn.substring(find(fn)+1);
}

// get substring of only the href value
def getRef(String ref)
{
    def i = ref.indexOf('\"');
    String shorty = ref.substring(i+1);
    i = shorty.indexOf('\"');
    shorty = shorty.substring(0,i);
    return shorty;
}

boolean writeThis(String msg)
{
    println msg;
    //outputfile.append(msg);
} // end of write

boolean urlExists(String url)
{
    try{
        //def rssFeed = url.toURL().text;
        //int j = (rssFeed.size() > 300) ? 300:rssFeed.size(); 
        writeThis "URL:$url\n";  // ="+ rssFeed.substring(0,j);
        
        def service = new RESTClient(url)
        def diagnostics = service.get(path: "");
        //writeThis("diagnostics[" + diagnostics + "]");
        diagnostics.getAllHeaders().each{ resp ->
            int j = resp.toString().size();
            if (j>60) {j=60;}
            writeThis " Unexpected error: ${resp.toString().substring(0,j)}"
        } // end of failed
        writeThis('\n');
        return true;
    }
    catch(Exception x) { writeThis("could not get $url because "+x.message);writeThis('\n');return false;}
    finally{}
}

boolean http(String shorty) {
    boolean flag = (shorty.startsWith("http")) ? true : false;
}

boolean checker(String fn) {
    if (fn.endsWith("navigation.html")) {return false;}
    if (fn.endsWith("master.html")) {return false;}
    return true;
}

new File("$path/.").eachFileRecurse(FILES) {
    if (!hdrDone) 
    {
        hdrDone=true;
        if ( outputfile.exists() ) { outputfile.delete(); }
        outputfile.append(hdr);
    } // end of if

    String filename = it.name.toString().toLowerCase();
    name = getName(filename);
    
    //if (name.endsWith('.html')) {count+=1;}
    
    if( (checker(filename)) && (name.endsWith('.html')) && (count<95 ) ) 
    {
        count+=1;
        outputfile.append("\n== link:./$name[$name Page]\n\n");
        writeThis("link:$name[$name] ")
        def fn = it.absolutePath
        def tx = new File(fn).text;
        
        // find every occurence of <a href and harvest and printout the href link plus the filename it's in
        def i = tx.indexOf("<a href");
        while (i>-1)
        {
            def tx2 = tx.substring(i);
            def k = tx2.indexOf(">");
            
            // get <a href
            tx2 = tx.substring(i,i+k+1);
            def tx3 = getRef(tx2);
            boolean flag = http(tx3);

            if (flag)
            {
                //writeThis('\n');    
                if (tx3.startsWith("http://gpars.org")) {gpars+=1;}

                outputfile.append(" * $tx3[$tx3] - [green]*ok*\n"); 
                href+=1;       
                writeThis(tx3);        //"\n=={$tx2}== \ngetRef=["+getRef(tx2)+"] ");
                //writeThis( "is it an http address ? ${flag} ");
                //flag = urlExists(getRef(tx2));
                //writeThis("does http address exists ? ${flag} ");
            } // end of if
            
            // READY FOR NEXT LOOP
            tx = tx.substring(i+k+2);
            i = tx.indexOf("<a href");
            //println " ";
        } // end of if        
    } // end of if
    
} // end of each

        outputfile.append("\n\n''''\n\nIMPORTANT: $count files have $href HREF links including $gpars GPars\n\n''''\n\n");
        writeThis("\nNumber of files =[$count] anf hrefs=$href  GPars=$gpars\n")