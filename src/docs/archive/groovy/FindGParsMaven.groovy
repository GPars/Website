String url = "http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/";
def r = []
m=[:]

def decode(def u, def pl){
    pl.eachLine{el->
        int i = finder("href=",el);
        if (i > -1)
        {
            def ss = el.substring(i+6);
            i = ss.indexOf('"');
            if (i > -1)
            {
                def k = ss.substring(0,i);
                if (k.endsWith(".jar")) { say u+k; m[k]=u+k; }
            }
        }
        
    } // end of eachLine
} // end of decode

def say(txt) { println txt }

int finder(String k,String v) { return v.indexOf(k); }
boolean has(String k,String v) { (finder(k,v)>-1)?true:false; }

def getPayload(String u){
    u.toURL().text
}

def txt = getPayload(url); 

txt.eachLine{l->
    if (has("href=",l)) 
    { 
        int i = finder("href=",l);
        def ss = l.substring(i+6);
        i = ss.indexOf('"');
        if (i > -1)
        {
            def k = ss.substring(0,i);
            if (!has("beta",k) && !has("maven",k) && !has("../",k) ) 
            { 
                r << k;
            } // end of if
            
        } // end of if
         
    } // end of if

} // end of each

def dta="";
say "\n----------------"
def newurl="";
r.each{re->
    newurl = url+re;
    say "==>"+newurl;
    dta = getPayload(newurl);
    //say "... found "+dta.size()+" bytes\n"
    decode(newurl,dta);
    say "";
} // end of each

say "map has "+m.size()+" entries\n"
m.each{ k, v -> println "${k}:${v}" }

println "--- the end ---"