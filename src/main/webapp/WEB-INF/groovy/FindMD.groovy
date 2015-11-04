/*
/Users/jimnorthrop/Dropbox/Projects/gpars.github.io/FindMD.groovy is a simple script to find any .md file in current directory 
with the codehaus string and prints out file name. 
*/
import static groovy.io.FileType.FILES

int count = 0;


new File('.').eachFileRecurse(FILES) {
    if(it.name.endsWith('.md')) 
    {
        def fn = it.absolutePath
        def tx = new File(fn).text;
        def i = tx.indexOf('codehaus');
        if (i>-1)
        {
            count+=1;
            print count+' = '+fn+'        ';
            def j =  (i<13) ? i : 13;
            tx = tx.substring(i-j,i+16);
            println tx; 
        }
        
    } // end of if
} // end of each