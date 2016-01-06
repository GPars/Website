/*
/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp/WEB-INF/groovy/FindGroovySource.groovy
parses out the code samples between the .adoc like :

[source,groovy]
----
println 'Hi Kids'
----

and writes it to a /code sub-folder within this 'path'. So file 'actors_75.groovy' is a code fragment from the actors.adoc
file starting at line 75. 

Tried to add logic to auto-compile the output files and present any compile failures, but no success yet. See MakeGroovySource 
script for better trial.
*/
import static groovy.io.FileType.FILES

String cud = System.properties.'user.dir';

int count = 0;
int src = 0;
 
lns = 0;
inBlock = -1;

String path = "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp/guide";
//String path =         "/Users/jim/Dropbox/Projects/GParsDocs/src/main/webapp/guide";

// binding entry points to folder holding code scripts
outputPath = path+"/code";

// binding name of output includes output path
outputfilename = "";

// current file in each{} loop
name = "";
currentname = "";

ofn = "$outputPath/livetest.groovy";
outputfile = new File(ofn);

def sb ="";

def clear()
{
    if (new File(outputPath).exists())
    {
        say "removing all .groovy files from "+outputPath;
        def files = []
        new File(outputPath).eachFileMatch(~/\.groovy$/) { files << it.name }
        // Delete all files:
        files.each { new File(it).delete() }
    }
}

// where do we write this stuff?
File getOutput(String name)
{
    if (!new File(outputPath).exists()) {new File(outputPath).mkdir(); }
    
    outputfilename = outputPath+'/'+name;
    println "; setting getOutput(String $name) to [$outputfilename]"
    outputfile = new File(outputfilename);
}

def find(String fn) {
        def i = fn.lastIndexOf("/");
}

def findTag(String ln) {
    return ln.startsWith("----");
}

def getName(String fn)
{
    String shorty = fn.substring(find(fn)+1);
}

def say(String msg)
{
    println msg;
} // end of write

def process(String fn, def tx)
{
    print "process()="+fn;
    getOutput(fn);
    println " giving "+outputfilename+" for ${tx.size()} bytes";
    if ( outputfile.exists() ) { outputfile.delete(); }
    outputfile.withWriter('UTF-8') { writer ->
        writer.write(tx);
    }
    def returncode = run("sh -c groovy '${fn}'");
} // end of process

boolean checker(String fn) {
    if (fn.endsWith("master.adoc")) {return false;}
    return true;
}

def run(String command) {
  return runner(command, new File(outputPath))
}

String fixup(String L)
{
    int j = L.indexOf('...');
    return (j<0)?L:L.substring(0,j)+L.substring(j+3)
} // end of fixup
 
private def runner(String command, File workingDir) {
  def processBuilder=new ProcessBuilder(command)
  processBuilder.redirectErrorStream(true)
  processBuilder.directory(workingDir) 
  def process = processBuilder.start()
  
//  def process = new ProcessBuilder(command)
//                                    .directory(workingDir)
//                                    .redirectErrorStream(true) 
//                                    .start()

  process.waitFor();
  process.inputStream.eachLine {println it}
  println "${command} exit Value:"+process.exitValue();
  return process.exitValue()
}

clear();
new File("$path/.").eachFileRecurse(FILES) {

    String filename = it.name.toString().toLowerCase();
    name = getName(filename);
    lns = 0;
            
    if( (checker(filename)) && (name.endsWith('.adoc')) && (count<2 ) ) 
    {
        count+=1;
        say("\n\nfile=[$name] ")
        outputfilename = name;
        def fn = it.absolutePath
        def tx = new File(fn).text;

        tx.eachLine(){e->
            lns+=1;
            
            switch(inBlock)
            {
                case -1:
                    if (e.toLowerCase().startsWith("[source,groovy"))
                    {
                        inBlock = 0;
                        src+=1;
                        int i = name.indexOf('.');
                        currentname = (i<0) ? name: name.substring(0,i);
                        currentname+=('_'+lns+".groovy")
                        say ".. writing [$currentname] groovy source set $src at "+lns;            
                    }
                    break;

                case 0:
                    // are we in code block delimited by first ---- ?
                    if (findTag(e))
                    {
                        inBlock = 1;                
                    } // end of if
                    break;

                case 1:
                    // since we are in code block, is the final ---- seq. found ?
                    if (findTag(e))
                    {
                        inBlock = -1; 
                        def xv = process(currentname,sb);
                        println "Exit Value:"+xv+'\n'              
                        sb="";
                    } // end of if
                    else
                    {
                        sb+=fixup(e);
                        sb+='\n';
                    }
                    break;
            
            } // end of switch
            
        } // end of eachLine
        
    } // end of if usable
    
} // end of each File

say("\nNumber of files =[$count] and sources=$src\n");