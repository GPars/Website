/*
/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindHTML.groovy  finds any .html files in the 'path' variable and 
harvests each href and prints that out.

Creates a Menu.adoc file in that path. But Note that re-running this module will destroy any manual changes made to this .adoc. 
Renamed tonites StaleURLs.adoc as Menus.adoc
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

hdrDone=false; // output adoc headers once

path1 = "/Users/jimnorthrop/Dropbox/Projects/GPars/Website/src/docs/asciidoc";
path =  "/Users/jimnorthrop/Dropbox/Projects/GPars/Website/src/main/webapp";
String name = "";
outputfile = new File("$path1/StaleURLs.adoc")

String hdr = """= GPars - Groovy Parallel Systems
The Whole GPars Team <gpars-developers@googlegroups.com> 
version 1.2.1, 2016-01-17 
:linkattrs:
:linkcss:
:toc: right
:toc-title: Document Index
:icons: font
:source-highlighter: coderay
:docslink: http://www.gpars.web/[GPars Documentation]
:description: GPars is a multi-paradigm concurrency framework offering several mutually cooperating high-level concurrency abstractions.
:doctitle: GPars Documentation Pages with Stale URLs

TIP: Pages with Stale URLs in ${path1}

''''

""".toString();

def badurl = "http://www.concertant.com/blog/2009-10-08-07-15";

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
    if (i>-1) {shorty = shorty.substring(0,i);}

    i = shorty.indexOf('>');
    if (i > -1) {shorty = shorty.substring(0,i-1);}

    return shorty;
}

boolean writeThis(String msg)
{
    //println msg;
    outputfile.append(msg);
} // end of write

boolean urlExists(String url)
{
    try{
        //def rssFeed = url.toURL().text;
        //int j = (rssFeed.size() > 300) ? 300:rssFeed.size(); 
        //writeThis "\n\n==== URL:$url\n";  // ="+ rssFeed.substring(0,j);
        
        def service = new RESTClient(url)
        def diagnostics = service.get(path: "");
        //writeThis("diagnostics[" + diagnostics + "]");
        diagnostics.getAllHeaders().each{ resp ->
            int j = resp.toString().size();
            if (j>60) {j=60;}
            //writeThis "\n . ${resp.toString().substring(0,j)}"
        } // end of failed
        writeThis("\n ");
        return true;
    }
    catch(Exception x) 
    { 
        def s = x.message.toLowerCase()
        if (s.indexOf('forbidden') > -1)
        {
            writeThis("\nIMPORTANT: exception :"+x.message+"\n\n");
            return false
        }
        else
        {
            println x.toString();
            return true;
        }
    }
    finally{}
}

boolean http(String shorty) {
    if (shorty.startsWith("https://fonts.")) return false;
    boolean flag = (shorty.startsWith("http")) ? true : false;
}

// avoid known trouble makers
boolean checker(String fn) {
    if (fn.endsWith("navigation.html")) {return false;}
    if (fn.endsWith("master.html")) {return false;}
    return true;
}

//println badurl;
//def b = urlExists(badurl);

new File("$path/.").eachFileRecurse(FILES) {
    if (!hdrDone) 
    {
        hdrDone=true;
        if ( outputfile.exists() ) { outputfile.delete(); }
        outputfile.append(hdr);
    } // end of if

    String filename = it.name.toString().toLowerCase();
    name = getName(filename);
        
    if (name.endsWith('.html')) {count+=1; println "file:"+name+" from "+it; }
    
    if( (checker(filename)) && (name.endsWith('.html')) && (count < 5000 ) ) 
    {
        count+=1;
        //outputfile.append("\n== link:./$name[$name Page]\n\n");
        //writeThis("\n link:$name[$name] ")
        def fn = it.absolutePath
        def tx = new File(fn).text;
        
        // find every occurence of <a href and harvest and printout the href link plus the filename it's in
        def i = tx.indexOf(" href");
        boolean showLink = true;
        
        while (i>-1)
        {
            def tx2 = tx.substring(i);
            def k = tx2.indexOf(">");
            
            // get <a href
            tx2 = tx.substring(i,i+k+1);
            def tx3 = getRef(tx2);
            boolean flag = http(tx3);
            println "flag="+flag+"  from "+tx3;

            if (flag)
            {
                if(showLink)
                {
                    showLink=false;
                    outputfile.append("\n== link:./$name[${it.name.toString()} Page]\n\n");
                }
                             
                //writeThis('\n');    
                if (tx3.startsWith("http://gpars.org")) {gpars+=1;}
                href+=1;       
                //writeThis(tx3);        //"\n=={$tx2}== \ngetRef=["+getRef(tx2)+"] ");
                                
                flag = urlExists(getRef(tx2));
                if (flag)
                {
                    outputfile.append("\n$tx3[$tx3] - image:./images/checkmarkgreen.png[ok,30,30]\n"); 
                }
                else
                {
                    outputfile.append("\n$tx3[$tx3] - image:./images/redcross.png[bad,30,30]\n"); 
                }
                
            } // end of if
            
            // READY FOR NEXT LOOP
            tx = tx.substring(i+k+2);
            i = tx.indexOf("<a href");
            //println " ";
        } // end of if        
    } // end of if
    
} // end of each

        outputfile.append("\n\n''''\n\nIMPORTANT: $count files have $href HREF links including $gpars GPars\n\n''''\n\n");
        writeThis("\nNumber of files =[$count] and hrefs=$href  GPars=$gpars\n")
        