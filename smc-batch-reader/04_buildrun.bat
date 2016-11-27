
call mvn clean install -DskipTests

cd target

java -cp smc-batch-reader.jar;./lib/* org.springframework.batch.core.launch.support.MultiFileCommandLineJobRunner classpath:/jobs/file-import-job.xml simpleFileImportJob inputDir=test-classes/

pause
