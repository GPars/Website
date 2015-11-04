/*
A trial class definition to compile harvested code fragment files as .groovy from each .adoc and auto-compile them.
*/

public class MakeGroovy
{
    String inpath = "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp/guide/code";
    def sb = '\n';

    /** default constructor -  */
    public MakeGroovy()
    {
    } // end of non-def constructor
    
    /** non-default constructor - pass the name of a folder to the onstructor */
    public MakeGroovy(String path)
    {
        inpath = path;
    } // end of non-def constructor
    
    
    public setPath(String p)
    {
        inpath = p;
    } // end of set
    
    
    public addtext(String t)
    {
        println t;
        sb += t;
        sb += '\n';
    } // end of set

    
    public void process()
    {
        new File("$inpath/.").eachFileMatch(~/.*.groovy$/) 
        {
            def cmd = "groovyc ${inpath}/${it.name} "
            def tx = "1. compiling "+it.name;
            addtext(tx);
            
            boolean flag = false;
    
            def converter =cmd.execute();
            converter.waitFor();
    
            if (converter.exitValue()) {
                def tx1 = "2. Error creating groovy for ${it.name}:"+converter.text;
                addText(tx1);
            } 
            else 
            {
                flag = true;
                String msg = "3. Compiled groovy for "+it.name;
                addText(msg);
                
            }

            if (flag || !flag)
            {        
                cmd = "groovy ${inpath}/${it.name} "
                run(cmd);
            } // end of if
            
        } // end of each...        
    } // end of process


    // use ProcessBuilder to try to compile a groovy source and return all compiler output
    def run(myCommand)
    {
        def m = "4. run($myCommand)".toString();
        addText(m);
        
        ProcessBuilder builder = new ProcessBuilder( myCommand.split(' ') )
        builder.redirectErrorStream(true)
 
        Process process = builder.start()
 
        InputStream stdout = process.getInputStream ()
        BufferedReader reader = new BufferedReader (new InputStreamReader(stdout))
        def line = "";
        
        while ((line = reader.readLine ()) != null) {
            if (line.size() > 0 ) 
            { 
                def tx3 = "5. Stdout: " + line;
                addText(tx3); 
            } // end of if
        }
    } // end of process
    
    public static void main(String[] args)
    {
        println "--- the start ---"
        MakeGroovy mg = new MakeGroovy();
        
        mg.setPath( "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp/guide/code");
        mg.process();
        
        println "--- the end ---"
    } // end of main 
    
} // end of class