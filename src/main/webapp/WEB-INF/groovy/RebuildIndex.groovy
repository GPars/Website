def fn = "/Users/jimnorthrop/Dropbox/Projects/GParsDocs/src/main/webapp/guide/index.html"
def fi = new File(fn);
def txt = fi.text;
def say(tx){println tx;}
lns = 0;

h1 = false;
h2 = false;
h3 = false;
h4 = false;
h5 = false;
h6 = false;
p = false;
div = false;
pre = false;

flag = false;
boolean hasTag(String l, String tag) { int j = l.indexOf(tag); return (j > -1) ? true : false;  }
def dropTag(String l, String tag) 
{ 
    int j = l.indexOf(tag);
    def t = (j>-1) ? l.substring(0,j) : l;
    return t;        
}
def dropEndTag(String l) 
{ 
    int j = l.indexOf("</pre>");
    def t = (j>-1) ? l.substring(0,j-6) : l.substring(j);
    return t;        
}


boolean findHdr(String l, String tag) {  return l.startsWith(tag); }

String findText(String l) 
{
    int i = l.indexOf('>');
    def part = l.substring(i+1);
    i = part.indexOf('<');
    part = part.substring(0,i);
    return part;
} // end of method

// main loop
txt.eachLine{ln->
    if (!flag)
    {
        if (ln.startsWith("<body ")) {flag = true;};
    } // end of 
    if
    else
    {
            h1 = findHdr(ln,"<h1")
            h2 = findHdr(ln,"<h2")
            h3 = findHdr(ln,"<h3")
            h4 = findHdr(ln,"<h4")
            h5 = findHdr(ln,"<h5")
            h6 = findHdr(ln,"<h6")
            div = findHdr(ln,"<div") || (ln.indexOf("</div>")>-1);
            pre = findHdr(ln,"<pre")

            lns+=1;

            if (pre)
            {
                if (ln.indexOf("</pre>") > -1) {pre = false;}
                if (hasTag(ln,"<pre>"))
                {
                    say "===========>"+dropTag(ln,"<pre ");
                }
                else
                if (hasTag(ln,"</pre>"))
                {
                    say "===========>"+dropEndTag(ln);
                }
                else
                {
                    say "code:"+ln;
                }
                
            }
            else
            if (h1 || h2 || h3 || h4 || h5 || h6)
            {
                say "";
                //say ln+"; ["+findText(ln)+"] @"+lns;
            } // end of if
            else
            {
                if (!p)
                {
                    p = findHdr(ln,"<p>")
                    if (p && !div) 
                    {
                        //say "->"+ln;
                    }
                    else
                    {
                        // loose <div lines here
                    } // end of else
                }
                else
                {
                    if (!div) {}  // {say ln;}
                    if (ln.trim().endsWith("</p>")) { p = false; }
                }        
            } // end of else
                    
    } // end of lese
    
} // end of each
say "--- the end ---"