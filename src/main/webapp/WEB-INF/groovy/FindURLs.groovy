import groovy.io.FileType

def list = []
payload =""
i = -1;
tx = ""

def parse(String k, defpayload)
{
    tx=""
    i= -1;
    if ( k.size()>0 && payload.size()>0 )
    {
        boolean yn = payload.contains(k)
        i = payload.indexOf(k)
        if (yn)
        {
            tx = payload.substring(i,i+20)
        }
        return yn;        
    } // end of if
    else
    return false;
} // end of parse


def dir = new File(".")
dir.eachFileRecurse (FileType.FILES) { file ->
  list << file
}

int ct = 0;
list.each{ 
    if (it.toString().endsWith(".adoc"))
    {
        payload = it.text;  
        def sz = payload.size()      
        def yn = parse("http:",payload);

        print "${it} with ${sz} bytes has http:${yn} at ${i} ";
        println "| [${tx}] "
        ct++; 
    }
}

println "\nthere were $ct files"
println '--- the end ---'