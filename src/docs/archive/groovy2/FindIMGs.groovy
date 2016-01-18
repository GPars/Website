import static groovy.io.FileType.ANY

list=[]
int j = 0;
def payload

String addr = "/Users/jim/Dropbox/Projects/GPars/Website/src/main/webapp"

new File(addr).eachFileRecurse(ANY) {
    if(it.name.endsWith('.html')) {
        println it
        payload = it.text;
    
        payload.eachLine{ln->
            def list2 = ln.split( "<img " );    

            list2.each{
                j = it.indexOf('src=\"');
                if (j > -1)
                {
                    token = it.substring(j+5)
                    j = token.indexOf('\"')
                    token = token.substring(0,j);
                    if (!token.startsWith("data:"))
                    {
                        list << token;
                    } // end of if
                } // end of if
            } // end of each
        } // end of eachLine
        
    } // end of if
} // end of eachFile

println "\n "
list.sort()
list.each{println it;}
println "list.size=${list.size()}\n--- the end ---"