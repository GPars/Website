//log.info "Setting attribute decoder"
// see: https://github.com/cloudfoundry/cli/releases  for choices
int cfos = 0;
int cfplatform = 0;
int cfhardware = 0;
int cfbits = 0;

def tx = """Your OS: MacOS
Your OS Id: Mozilla/5.0 (Macintosh; PPC Mac OS X 10_6_8) AppleWebKit/534.59.10 (KHTML, like Gecko) Version/5.1.9 Safari/534.59.10
Your platform: MacIntel""".toString();
String txlc = tx.toLowerCase();
int i = 0;

def bits=['32 bit','64 bit']
def processor=['unknown','Intel','PPC']
def os=['unknown','Apple','Windows','Linux']

['mac','win','linux'].eachWithIndex{e,ix->

    if (txlc.indexOf(e) > -1 && cfos < 1) cfos = ix+1;
}
['intel','ppc'].eachWithIndex{e,ix->

    if (txlc.indexOf(e) > -1 && cfhardware < 1) cfhardware = ix+1;
}
['64'].eachWithIndex{e,ix->

    if (txlc.indexOf(e) > -1 && cfbits < 1) cfbits = ix+1;
}





println "decoder platform response will be :"+cfplatform.toString()+" "+os[cfos];
println "decoder hardware response will be :"+cfhardware.toString()+" "+processor[cfhardware];
println "decoder hardware bits response will be :"+cfbits.toString()+" "+bits[cfbits];

//request.setAttribute 'decoder', cfversion.toString()
//log.info "Forwarding to decoder"

//forward '/WEB-INF/pages/datetime.gtpl'