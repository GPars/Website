package com.jim;

import groovy.transform.*;

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
	String accordion = "<div id=\"accordion\">\n"
	
    /** a variable to hold the address of the maven page showing the currently available GPars releases */ 
	String url = "http://repo1.maven.org/maven2/org/codehaus/gpars/gpars/";
	
    /** a variable to hold the temporary list of each available (non-beta)  GPars release */ 
	def r = []
	

   /** 
    * Variable m Map of each available (non-beta) GPars release, where key is GPars release  
    * and value is full URL of target to be downloaded, it fills the xxx within <a href="xxx">
    * fragment 
    */  
	def m=[:]

    /** a variable used to hold the break processing string when walking thru each GPars release */ 
	def bk="";


   /** 
    * Default Constructor will acquire text from maven URL address 
    * then decode it and hold in a list structure
    *
    * @return GParsDownloader object
    */     
    public GParsDownloader()
    {
        name = "running GParsDownloader constructor"
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

    } // end of constructor


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
bk=${bk}
r=${r}
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
        println txt;
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
		r.each{re->
    		newurl = url+re;
		    say "==>"+newurl;
    		dta = getPayload(newurl);
    		//say "... found "+dta.size()+" bytes\n"
    		decode(newurl,dta);
    		say "";
		} // end of each

		// report map size
		say "map has "+m.size()+" entries\n"
		boolean ok = false;
		
		// walk thru each map entry
		m.each{ k, v -> 
    		def flag = process(k.toString() ) 
    		if (flag) 
    		{
    			if (ok)
    			{
	        		accordion+="</div>\n"    			
    			} // end of if
    			
    			ok = true;			
		        say "\nbreaking bk=[$bk] at $k and v=$v";
        		accordion+="<h3>GPars $bk</h3>\n<div>\n"
		        accordion+="<a href=\"$v\">$k</a><br />\n"
    		}
    		else
    		{
        		accordion+="<a href=\"$v\">$k</a><br />\n"
        		say "${k}:${v}";
    		}; 
		} // end of each

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
    	if (k.startsWith("gpars-")) { v = k.substring(6); }
    	int j = v.indexOf(".jar");
    	v = (j > -1) ? v.substring(0,j) : v;
    	j = v.lastIndexOf("-");
    	v = (j > -1) ? v.substring(0,j) : v;

	    boolean flag = (v!=bk)?true:false;
    	bk=v;
    	return flag;
	} // end of method


   /** 
    * Method to decode payload 
    */     
	def decode(def u, def pl)
	{
    	pl.eachLine{el->
        	int i = finder("href=",el);
        	if (i > -1)
        	{
            	def ss = el.substring(i+6);
            	i = ss.indexOf('"');
            	if (i > -1)
            	{
                	def k = ss.substring(0,i);
                	if (k.endsWith(".jar")) 
                	{ 
                		say u+k; 
                		m[k]=u+k; 
                	} // end of if .jar url found
            	} // end of if line has href quote "
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
        obj.finish();
        
        println "GParsDownloader = [${obj.toString()}]"
		println "\n------------------------------------------------"
		println obj.getAccordion();

        println "--- the end of GParsDownloader ---"
    } // end of main

} // end of class
