/*
/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindHTML.groovy  finds any .html files in the 'path' variable and 
harvests each href and prints that out.

Creates a Menu.adoc file in that path. But Note that re-running this module will destroy any manual changes made to this .adoc. 
Renamed tonites StaleURLs.adoc as Menus.adoc
*/
import static groovy.io.FileType.FILES

m=[] as Set
int count = 0;
href = 0;
int gpars=0;
hdrDone=false;
path1 = "/Users/jim/Dropbox/Projects/GParsDocs/src/docs/asciidoc";
path = "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/docs/asciidoc";
String name = "";
outputfile = new File("$path1/Images.adoc")

String hdr = """= Images Used In Groovy Parallel Systems
The Whole GPars Team <jim@work.com>
v1.0, 2015-12-18
:linkattrs:
:linkcss:
:toc: left
:toc-title: Document Index
:icons: font
:source-highlighter: coderay
:docslink: http://www.gpars.org/guide/[GPars Docs]
:description: GPars is a multi-paradigm concurrency framework offering several mutually cooperating high-level concurrency abstractions.
:doctitle: GPars Documentation Using Images


""".toString();

def find(String t) {
    return t.indexOf("image:");
}

def parse(def pl)
{
    pl.eachLine{ln->
        def i = find(ln);
        while (i > -1)
        {
            def ss = ln.substring(i+6)
            int j = ss.indexOf("[");
            ss = ss.substring(0,j);
            def regex = ~/.*?checkmark?.*/
            boolean b = ss.matches(regex)  // returns true / false
            if (!b) 
            {
                println "ss=[${ss}] "; 
                href+=1;
                m<<ss;
            };
            i = find(ln.substring(i+6+j));
        } // end of if
        
    } // end of each
} // end of parse

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
    return shorty;
}

boolean writeThis(String msg)
{
    println msg;
    outputfile.append(msg);
} // end of write

def payload="";

new File("$path1/.").eachFileRecurse(FILES) {
    if (!hdrDone) 
    {
        hdrDone=true;
        if ( outputfile.exists() ) { outputfile.delete(); }
        outputfile.append(hdr);
    } // end of if

    def hflag = (it.name.toString().toLowerCase().endsWith('.adoc')) ? true : false;
    if (hflag) {
        count+=1; 
        String filename = it.name.toString();
        name = getName(filename);
        def fn = it.getCanonicalPath()
        payload = new File(fn).text;
        //println fn+" name=[$name] size()=${payload.size()} bytes"; 
        parse(payload);
    } // end of if
} // end of each

println "\n"
m.sort();
m.each{println  it;}


outputfile.append("\n\n''''\n\nIMPORTANT: $count files have $href images\n\n''''\n\n");
writeThis("\nNumber of files =[$count] and $href images\n")
        