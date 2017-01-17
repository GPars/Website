import java.io.*
files = []
allowed =["jar", "properties", "groovy", "pdf", "java", "zip", "txt", "adoc", "css", "png", "ico", "jpg", "svg", "html", "gif", "js", "jpeg", "psd", "less", "gtpl", "xml", "gradle", "md" ]
fn = '/Users/jimnorthrop/Dropbox/Projects/Website';
/*
new File(fn).eachFileMatch(~/^.*\.adoc$/) { files << it.name };

count = 0
files.each{
    count++;
    println it;
}

println "\n===================================\nFound $count files\n"
*/

def result
folders=0;
count = 0;
map=[:]

findTxtFileClos = {

        it.eachDir(findTxtFileClos);
        it.eachFile() {file ->            //Match(~/.*.adoc/) {file ->
                yn = (file.name.startsWith('.')) ? true :  false;
                if (!yn && !file.absolutePath.contains('/.git'))
                {
                    count++;
                    //println file.absolutePath;
                    int i = file.name.lastIndexOf('.');                                                                
                    if (i>-1) 
                    {
                        ss = file.name.substring(i+1).toLowerCase();
                        ok = (allowed.contains(ss)) ? true : false;
                        if (!ok){ok=true;ss="other";}
                        println "i=$i ok=${ok} "+ss+" ="+file.name;
                        if(map.containsKey(ss)) {
                          map[ss]+=1;
                        }
                        else
                        {
                            if (ok) map[ss]=1;
                        }
                    }
                    //result += "${file.absolutePath}\n"
                }
        }
    }

// Apply closure
findTxtFileClos(new File(fn))
println "\n===================================\n\n"
map.each{ k, v -> println "${k}:${v}" }
println "\n===================================\nFound $count files\n"


println "---  the end ---"
//assert ['groovy1.txt', 'groovy2.txt', groovy3.txt'] == files