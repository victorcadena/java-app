

<H1>README</H1>
<H1>Repo Name: java-app</H1>
<P>Purpose: To demostrate how to use Jenkinsfile and POM files to create a Dev Ops Build Pipeline. 
</P>

<H1>Before running this repo on your own repository:</H1>

<UL>

<LI>Update the jenkinsfile to specify your own GitHub Repo URL and name (1 location)
<LI>Update the jenkinsfile to specify the Nexus credentials ID on your Jenkins Credentials  (1 locatioin)
<LI>Update the Jenkinsfile to specify your own Nexus Repository URL (1 location)
<LI>Update the POM.xml file to specify your own Nexus URL or IP (3 locations)
<LI>Update the download script download-artifacts.sh located in /usr/local/bin on the Jenkins server to specify your own Nexus URL or IP (1 location)
</UL>
  
<H1>Additional Configuration:</H1>

<UL>
<LI>Add your Jenkins IP webhook to each repo

<H1>version 1.0.0 </H1>
