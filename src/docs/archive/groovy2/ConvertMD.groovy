/*
A module to convert markdown files into .adoc equivalents. The current directory where this script is run will determine where the .md files
are harvested from.
The .adoc files are written back into this same folder in a sub-folder named asciidocs where they can be converted into .html
/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/ConvertMD.groovy

The /Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindMD.groovy script only prints the name of any .md file with codehaus in it.

/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindHTML.groovy  finds any .html files in it's 'path' variable and 
harvests each href and prints that out. Charge var.value to parse different folders, guide,quick-start,etc.

lso see MissingURLSList.txt for full text of same.
*/
import static groovy.io.FileType.FILES

int count = 0;
def xx = """= GPars - Groovy Parallel Systems
Russell Winder <russel@winder.org.uk>
v1.0, 2015-10-01
:linkattrs:
:linkcss:
:toc: left
:toc-title: Document Index
:icons: font
:source-highlighter: coderay
:docslink: http://www.gpars.org/guide/[GPars Docs]
:description: GPars is a multi-paradigm concurrency framework offering several mutually cooperating high-level concurrency abstractions.
""".toString();

new File('.').eachFileRecurse(FILES) {
    if(it.name.endsWith('.md')) 
    {
        def fn = it.absolutePath
        //println '\n'+fn;
        def i = fn.lastIndexOf('.');
        if (i>-1)
        {
            def fno = fn.substring(0,i);
            def fnoa = fno+".adoc";
            i = fnoa.lastIndexOf('/');
            if (i>-1) { fnoa=fnoa.substring(0,i)+"/asciidocs"+fnoa.substring(i); }
            def yn = new File(fnoa).exists();
            //if (count<4) {println "i=$i yn=${yn} fn="+fn+" ="+fno;}
            count+=1;
            if (count<61 && !yn)
            {
                def file3 = new File(fnoa);
                println "\n\ngonna write "+fnoa;
                file3.withWriter('UTF-8') { writer ->
                    writer.write(xx);
                    def tx = new File(fn).text;
                    def str2 = tx.replaceAll('### ','=== ').replaceAll('## ','== ').replaceAll('# ','== ');
                    def str1 = str2.substring(3);
                    i = str1.indexOf('---');
                    if (i>-1)
                    {
                        def tx3 = str1.substring(0,i); print "tx3=[${tx3}] sz=${tx3.size()}";
                        int j = tx3.indexOf("title:");
                        if (j>-1) {tx3=tx3.substring(j+6);}
                        writer.write(":doctitle: "+tx3.trim()+'\n');
                        str2=str2.substring(i+6)
                    } // end of if
                
                    writer.write(str2);
                } // end of writer            
            } // end  of if
        }
        
        else
        {
            //if (count<3) {println "i=${i} "+fn;}
        }
    } // end of if
} // end of each