
call mvn clean install -DskipTests

cd target

java -cp smc-batch-reader.jar;./lib/* org.springframework.batch.core.launch.support.CommandLineJobRunner classpath:/jobs/file-import-job.xml simpleFileImportJob inputFile=D:/05-WorkSpace/02-Eclipse/02-Working/02-SMC_DEV/smc-batch-reader/target/test-classes/sample*.csv

pause
