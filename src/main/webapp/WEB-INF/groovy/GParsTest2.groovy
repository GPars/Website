import groovyx.gpars.GParsPool

String tx="Hi Kids";

int count=0;
def result=[:];
def starttask = System.currentTimeMillis();

['k1':'v1','k2':'v2','k3':'v3','k4':'v4'].each{k,v->
    result[k]=v;
}

GParsPool.withPool() {

    println "tx size()="+tx.size()+" tx="+tx
    println "result size()="+result.size()
    result.eachParallel(){e,v->
        println e+"="+e.value;
    } // end of each

    def finitask = ( System.currentTimeMillis() - starttask ) / 1000;
    println "elapsed in "+finitask+" sec.s";
    
} // withPool


println "--- the end ---"