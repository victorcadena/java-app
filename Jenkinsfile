node {
    echo "Starting Pipeline... "
    def mvnHome;
    def pom;

    def this_group;
    def this_artifact;
    def this_version; 
    def output;
    def fileproperties = "file.properties";
    def filePropertiesPathAndName = "${JENKINS_HOME}/workspace/import-subsystem/${fileproperties}";

    stage('Get Build Files') 
    { 
       echo "Getting Private Repo"
       git(
       url: 'git@github.com:ochoadevops/java-app.git',
       credentialsId: 'java-app',
       branch: "master"
       )


       mvnHome = tool 'M3'
    }

    stage('Build') 
    {
       // Run the maven build
       withEnv(["MVN_HOME=$mvnHome"]) 
       {
          if (isUnix()) 
          {
             sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
          } 
          else 
          {
             bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
          }
       }
    }


    stage('Test Results') 
    {
       junit '**/target/surefire-reports/TEST-*.xml'
       archiveArtifacts 'target/*.*'
    }
   
    stage('Publish to NEXUS') 
    {
 
       pom = readMavenPom file: "./pom.xml";
       // Read POM xml file using 'readMavenPom' step , 
       // this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps

       // Find built artifact under target folder
       filesByGlob = findFiles(glob: "target/*.${pom.packaging}");

       // Print some info from the artifact found
       echo "*** Print information found";
       echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"

       // Extract the path from the File found
       artifactPath = filesByGlob[0].path;
       artifactName = filesByGlob[0].name;
       echo "*** this artifactName is: ${artifactName}";
       echo "*** this artifactPath is: ${artifactPath}";
       // Assign to a boolean response verifying If the artifact name exists
       artifactExists = fileExists artifactPath;
                   
       if(artifactExists) 
       {
          echo "*** File: ${artifactPath}, Path found";
          echo "*** File: ${artifactPath}, artifactId: ${pom.artifactId}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
      
       // Upload artifact to Nexus using plugin 

       nexusArtifactUploader artifacts: [[artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging]], credentialsId:'NEXUS_USER', groupId: pom.groupId, nexusUrl: '54.153.96.36:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'devops-test-repo-snapshot/', version: pom.version 


       }
       else 
       {
          error "*** File: ${artifactPath}, could not be found";
       }
 
       withEnv(["MVN_HOME=$mvnHome"]) 
       {
          // sh '"$MVN_HOME/bin/mvn" -DrepositoryId=nexus -Dmaven.test.skip=true clean deploy'
       }

   }

    stage('Wait for Upload Artifacts to NEXUS to complete. Waiting 2 mins.') 
    {   
       echo "*** STARTED WAITING for artifacts to be fully loaded to NEXUS ***";
       sleep 120 
       echo "*** DONE WAITING for artifacts to be fully loaded to NEXUS ***";
    }
    
    
    stage('Download Artifacts') 
    {
     // Download artifacts from Nexus
     echo "Starting --- download artifacts"
     dir('./download')
     {
        this_group = pom.groupId;
        this_artifact = pom.artifactId;
        this_version = pom.version;

        sh "/usr/local/bin/download-artifacts.sh  $this_group $this_artifact $this_version"
        echo "*** Test: ${pom.artifactId}, group: ${pom.groupId}, version ${pom.version}";
        
        echo "*** Getting full build ID including timestamp";
        def this_time_version = sh(script: "ls -lrt devops-import-server-1.0* | tail -n 1  | awk '{print \$9}'", returnStdout: true)
        echo "*** this_time_version: $this_time_version";

        def outputGroupId = "Group=" + "$this_group";
        def outputVersion = "Version=" + "$this_version";
        def outputArtifact = "ArtifactId=" + "$this_artifact";
        def outputFullBuildId = "FullBuildId=" + "$this_time_version";
        def outputJenkinsBuildId = "JenkinsBuildId=${env.BUILD_ID}";
        def outputBuildNumber = "BuildNumber=${env.BUILD_NUMBER}";
        

        def allParams = "$outputGroupId" + "\n" + "$outputVersion" + "\n" + "$outputArtifact" + "\n" + "$outputFullBuildId" + "\n" + "$outputJenkinsBuildId" + "\n" + "$outputBuildNumber";

        //echo "PROJECT NAME from build: ${projectName}";
        //echo "PROJECT FULL NAME from build: ${fullProjectName}";
        echo "JOB_BASE_NAME from global: ${env.JOB_BASE_NAME}";
        echo "JOB NAME from global: ${env.JOB_NAME}";

        echo "this is allParams: $allParams";
        echo "File Properties designated location:  $filePropertiesPathAndName"

        // Create and Archive the properties file
        writeFile file: "$filePropertiesPathAndName", text: "$allParams"

   
        // Copy file properties to this directory and Archive 
        sh "cp $filePropertiesPathAndName .";
        archiveArtifacts artifacts: "${fileproperties}";
        echo "Completed --- download artifacts"


        sh "/usr/local/bin/download-artifacts.sh  $this_group $this_artifact $this_version"

        echo "*** List build download";
        sh 'ls -l'

     }

    }


}

