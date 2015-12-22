package com.jim;

import groovy.transform.*;
//import groovyx.gpars.GParsPool

/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/** 
 * GParsDownloader class description
 *
 * Logic to Download a list of GPars releases from MavenCentral,
 * and produce an html string whose syntax is that of a JQuery accordion, i.e. H3 line is GPars Release
 * while <div> holds each possible clickable link for this H3
 *
 */ 
 @Canonical 
 public class GParsDownloader
 {
    /** an O/S specific char. as a file path divider */
    String fs = java.io.File.separator;

    /** an O/S specific location for the user's home folder name */ 
    String home = System.getProperty("user.home");
    
   /** 
    * Variable name Value of a variable.
    */  
    String name = "";

    /** a variable to hold the html built for the user's download accordion */ 
    String accordion = "<div id=\"accordion2\">\n"
    
    /** a variable to hold the address of the maven page showing the currently available GPars releases */ 
    String url = "http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/";
    
    /** a variable to hold the temporary list of each available (non-beta)  GPars release */ 
    def r = []
    
    /** a variable used to hold a flag that's set true to cause further logging msgs */ 
    boolean audit = false;


   /** 
    * Variable m Map of each available (non-beta) GPars release, where key is GPars release  
    * and value is full URL of target to be downloaded, it fills the xxx within <a href="xxx">
    * fragment 
    */  
    def m=[:]

    /** a variable used to hold the break processing string when walking thru each GPars release */ 
    def bk="";

    /** a variable used to hold the next break processing string */ 
    def nk="";

    /** a variable used to hold the text retrieved from the remote URL */ 
    String payload="";


   /** 
    * Default Constructor will acquire text from maven URL address 
    * then decode it and hold in a list structure
    *
    * @return GParsDownloader object
    */     
    public GParsDownloader()
    {
        name = "running GParsDownloader default constructor"
        payload = getPayload(url); 
        parser(payload);
        finish();
    } // end of constructor


   /** 
    * Non-Default Constructor will acquire text from method input 
    * then decode it into a list structure
    *
    * @return GParsDownloader object
    */     
    public GParsDownloader(String txt)
    {
        name = "running GParsDownloader non-default constructor"
        parser(txt);
        finish();
    } // end of constructor


   /** 
    * Method to examine payload to build list of each available (non-beta)  GPars release.
    * 
    * @return formatted content of internal variables
    */     
    public parser(String txt)
    {
        txt.eachLine{l->
            if (has("href=",l)) 
            { 
                int i = finder("href=",l);
                def ss = l.substring(i+6);
                i = ss.indexOf('"');
                if (i > -1)
                {
                    def k = ss.substring(0,i);
                    if (!has("maven",k) && !has("../",k) ) 
                    { 
                        r << k;
                    } // end of if
                } // end of if
         
            } // end of if
        } // end of each

        r.sort();
    }  // end of parser
    


   /** 
    * Method to display internal variables.
    * 
    * @return formatted content of internal variables
    */     
    public String toString()
    {
        return """name=${name}
user.home=${home}
accordion=${accordion}
url=${url}
m=${m.toString()}
bk=${bk}
nk=${nk}
r=${r}
audit=${audit}
java.io.File.separator=${java.io.File.separator}
"""
    }  // end of string
    

   /** 
    * Method to print audit log.
    * 
    * @param the text to be said
    * @return void
    */     
    public void say(txt)
    {
        if (audit)
        {
            println txt;
        } // end of if
    }  // end of method


   /** 
    * Method to retrieve the html stream representing a JQuery accordion.
    * 
    * @return String accordion html
    */     
    public getAccordion()
    {
        return accordion;
    }  // end of method


   /** 
    * Method to complete construction of the accordion.
    * 
    * @return String of html for GPars download accordion
    */     
    public finish()
    {
        def dta="";
        say "\n----------------"
        def newurl="";
        
//GParsPool.withPool(5) {
        // walk thru eachLine of payload
        r.each{re->  //Parallel()
            newurl = url+re;
            say "==>"+newurl;
            dta = getPayload(newurl);
            
            // now fill map m with each list entry
            decode(newurl,dta);
            
            say "";
        } // end of each
//} // end of GParsPool.withPool

        // report final map size
        say "map has "+m.size()+" entries\n"
        boolean ok = false;
        
//GParsPool.withPool(5) {
        // walk thru each map entry
        m.each{ k, v ->    // Parallel()
        
            // check for a key break 
            def flag = process(k.toString() ) 
            
            // break detected
            if (flag) 
            {
                // break found so if 'ok' meaning an entry was written before, we add ending div in our output accordion 
                if (ok)
                {
                    accordion+="</div>\n"                
                } // end of if
                
                // turn on flag and set break key to one just detected
                ok = true;        
                bk=nk;    
                say "\nbreaking bk=[$bk] at $k and v=$v";
                accordion+="<h3>GPars $bk</h3>\n<div>\n"
                
                // put each <a href inside the H3 for this break key
                accordion+="<a href=\"$v\">$k</a><br />\n"
                say "${k}:${v}";
            }
            
            // no break detected, output as part of this group
            else
            {
                accordion+="<a href=\"$v\">$k</a><br />\n"
                say "${k}:${v}";
            }; 
            
        } // end of each
//} // end of GParsPool.withPool

        // since output was in progress add final ending </div>
        if (ok)
        {
            accordion+="</div>\n"                
        } // end of if

        accordion+="</div>"
    }  // end of method


   /** 
    * Method to determine if there has been a change of breakpoint (GPars release prefix).
    * 
    * @return boolean flag true when break has been detected
    */     
    boolean process(String k)
    {    
        def v = k;
        if (k.startsWith("gpars-")) { v = k.substring(6); } // remove leading gpars-
        
        // strip off.jar if it exists
        int j = v.indexOf(".jar");
        v = (j > -1) ? v.substring(0,j) : v; // remove trailing .jar if any
        
        
        j = v.lastIndexOf("-sources");
        v = (j > -1) ? v.substring(0,j) : v;    // removing trailing -sources
        j = v.lastIndexOf("-javadoc");
        v = (j > -1) ? v.substring(0,j) : v;    // removing trailing -javadoc
        
        // fill next key 'nk' with key-to-be if break is found
        nk=v;
        
        // see if this 'key' of v differs from most recent breakpoint value of 'bk'; true reports a key break
        return (v!=bk)?true:false;
    } // end of method



   /** 
    * Method to decode payload and fill map m with key:value
    */     
    def decode(def u, def pl)
    {
        pl.eachLine{el->
        
            // find address within href="xxx" 
            int i = finder("href=",el);
            if (i > -1)
            {
                def ss = el.substring(i+6);
                i = ss.indexOf('"');
                if (i > -1)    // yes,properly formed href
                {
                    def k = ss.substring(0,i);
                    if (k.endsWith(".jar"))     // href address is a .jar
                    { 
                        say "decode k=[${k}]="+u;     
                        m[k]=u+k;                     // keep this entry in out map    
                    } // end of if .jar url found
                    
                } // end of if line has href quote
                 
            }// end of if line has href
        
        } // end of eachLine
    } // end of decode


   /** 
    * Method to determine if there has been a change of breakpoint (GPars release prefix).
    * 
    * @return int ix location of k within v payload or -1 if not found in this string
    */     
    int finder(String k,String v) { return v.indexOf(k); }


   /** 
    * Method to determine if there has been a change of breakpoint (GPars release prefix).
    * 
    * @return boolean true when string v with key has been detected
    */     
    boolean has(String k,String v) { (finder(k,v)>-1)?true:false; }


   /** 
    * Method to download a text payload from a remote URL, typically a maven addr (GPars release prefix).
    * 
    * @return boolean true when string v with key has been detected
    */     
    def getPayload(String u)
    {
        u.toURL().text
    }


   // ======================================
   /** 
    * Method to run class tests.
    * 
    * @param args Value is string array - possibly empty - of command-line values. 
    * @return void
    */     
    public static void main(String[] args)
    {
        println "--- starting GParsDownloader ---"

        GParsDownloader obj = new GParsDownloader();
        
        println "GParsDownloader = [${obj.toString()}]"
        
        println "\n------------------------------------------------\n"
        println obj.getAccordion();

        String payload = obj.payload;
        //obj = new GParsDownloader(payload);
        //println "\n------------------------------------------------\n"
        //println obj.getAccordion();
        
        println "--- the end of GParsDownloader ---"
    } // end of main

} // end of class