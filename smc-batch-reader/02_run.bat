
cd target

java -cp smc-batch-reader.jar;./lib/* org.springframework.batch.core.launch.support.CommandLineJobRunner classpath:/jobs/file-import-job.xml simpleFileImportJob inputFile=test-classes/event.done.csv

pause
