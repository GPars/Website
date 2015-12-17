def url = "http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/"

boolean has(String l, String tx){ return (l.indexOf(tx)>-1)?true:false; }
boolean hasEnding(String l, String tx){ return (l.endsWith(tx))?true:false; }

def gpars = []
payload = "";

int getDownload(String addr) 
{   
    payload = download(addr);
    println "payload="+payload
    return payload.size();
} // end of get

boolean findDownload(String addr) { return (getDownload(addr) > -1)? true: false; }

String download(String addr) 
{
    try{
        println "geting:"+addr;
        def fn = addr.toURL().text();
        println "fn->"+fn.toString()
        return fn.text()
    }
    catch(Exception x) { return "bad news="+x; }
} //end of download

// start of main
if (findDownload(url))
{
    println "found "+payload.size()+" bytes in url "+url;
    payload.eachLine{l->
    
        int i = l.indexOf("href=");
        if (i>-1)
        {
            //println l;
            def ss = l.substring(i+6);
            i = ss.indexOf('"');
            if (i>-1)
            {    
                ss = ss.substring(0,i);
                if (has(ss,"beta") || has(ss,"-rc") || has(ss,"maven") || has(ss,"../"))
                {
                    //println ""
                }
                else
                {
                    //println hasEnding(ss,"/")
                    println "=====>["+ss+"]\n";
                    gpars+= ss;
                }  // end of if
            
            } // end of if
        }
    } // end of each

    println "\n\n\n"
} // end of if 


// examine each valid target GPars non-beta, non-rc release
gpars.each{e-> 
    println e; 
    String uri = url+e;
    payload = download(uri);
    println "Found "+payload.size()+" bytes at url="+uri;
    payload.eachLine{el->
        if (has(el,".jar</a>"))
        {    
            println "this is a jar:"+el;
        } // end of if
        else
        {
            //println "... this is not a .jar:"+el;
        }
    
    } // end of each
    
}


println "--- the end ---"