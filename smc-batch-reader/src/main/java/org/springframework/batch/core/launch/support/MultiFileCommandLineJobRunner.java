package org.springframework.batch.core.launch.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotFailedException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobExecutionNotStoppedException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.pcitc.smc.csv.CsvReaderConstant;
import com.pcitc.smc.csv.service.IESMEventService;

public class MultiFileCommandLineJobRunner {

	protected static final Log logger = LogFactory.getLog(MultiFileCommandLineJobRunner.class);

	private ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();

	private JobLauncher launcher;

	private JobLocator jobLocator;

	// Package private for unit test
	private static SystemExiter systemExiter = new JvmSystemExiter();

	private static String message = "";

	private JobParametersConverter jobParametersConverter = new DefaultJobParametersConverter();

	private JobExplorer jobExplorer;

	private JobRepository jobRepository;

	private final static List<String> VALID_OPTS = Arrays
			.asList(new String[] { "-restart", "-next", "-stop", "-abandon" });

	/**
	 * Injection setter for the {@link JobLauncher}.
	 *
	 * @param launcher
	 *            the launcher to set
	 */
	public void setLauncher(JobLauncher launcher) {
		this.launcher = launcher;
	}

	/**
	 * @param jobRepository
	 *            the jobRepository to set
	 */
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	/**
	 * Injection setter for {@link JobExplorer}.
	 *
	 * @param jobExplorer
	 *            the {@link JobExplorer} to set
	 */
	public void setJobExplorer(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	/**
	 * Injection setter for the {@link ExitCodeMapper}.
	 *
	 * @param exitCodeMapper
	 *            the exitCodeMapper to set
	 */
	public void setExitCodeMapper(ExitCodeMapper exitCodeMapper) {
		this.exitCodeMapper = exitCodeMapper;
	}

	/**
	 * Static setter for the {@link SystemExiter} so it can be adjusted before
	 * dependency injection. Typically overridden by
	 * {@link #setSystemExiter(SystemExiter)}.
	 *
	 * @param systemExiter
	 */
	public static void presetSystemExiter(SystemExiter systemExiter) {
		MultiFileCommandLineJobRunner.systemExiter = systemExiter;
	}

	/**
	 * Retrieve the error message set by an instance of
	 * {@link CommandLineJobRunner} as it exits. Empty if the last job launched
	 * was successful.
	 *
	 * @return the error message
	 */
	public static String getErrorMessage() {
		return message;
	}

	/**
	 * Injection setter for the {@link SystemExiter}.
	 *
	 * @param systemExiter
	 */
	public void setSystemExiter(SystemExiter systemExiter) {
		MultiFileCommandLineJobRunner.systemExiter = systemExiter;
	}

	/**
	 * Injection setter for {@link JobParametersConverter}.
	 *
	 * @param jobParametersConverter
	 */
	public void setJobParametersConverter(JobParametersConverter jobParametersConverter) {
		this.jobParametersConverter = jobParametersConverter;
	}

	/**
	 * Delegate to the exiter to (possibly) exit the VM gracefully.
	 *
	 * @param status
	 */
	public void exit(int status) {
		systemExiter.exit(status);
	}

	/**
	 * {@link JobLocator} to find a job to run.
	 * 
	 * @param jobLocator
	 *            a {@link JobLocator}
	 */
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	/*
	 * Start a job by obtaining a combined classpath using the job launcher and
	 * job paths. If a JobLocator has been set, then use it to obtain an actual
	 * job, if not ask the context for it.
	 */
	@SuppressWarnings("resource")
	int start(String jobPath, String jobIdentifier, String[] parameters, Set<String> opts) {

		ConfigurableApplicationContext context = null;

		try {
			try {
				context = new AnnotationConfigApplicationContext(Class.forName(jobPath));
			} catch (ClassNotFoundException cnfe) {
				context = new ClassPathXmlApplicationContext(jobPath);
			}
			context.getClassLoader().getResource("/");
			context.getAutowireCapableBeanFactory().autowireBeanProperties(this,
					AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

			Assert.state(launcher != null, "A JobLauncher must be provided.  Please add one to the configuration.");
			if (opts.contains("-restart") || opts.contains("-next")) {
				Assert.state(jobExplorer != null,
						"A JobExplorer must be provided for a restart or start next operation.  Please add one to the configuration.");
			}

			String jobName = jobIdentifier;

			JobParameters jobParameters = jobParametersConverter
					.getJobParameters(StringUtils.splitArrayElementsIntoProperties(parameters, "="));
			Assert.isTrue(parameters == null || parameters.length == 0 || !jobParameters.isEmpty(),
					"Invalid JobParameters " + Arrays.asList(parameters)
							+ ". If parameters are provided they should be in the form name=value (no whitespace).");

			if (opts.contains("-stop")) {
				List<JobExecution> jobExecutions = getRunningJobExecutions(jobIdentifier);
				if (jobExecutions == null) {
					throw new JobExecutionNotRunningException("No running execution found for job=" + jobIdentifier);
				}
				for (JobExecution jobExecution : jobExecutions) {
					jobExecution.setStatus(BatchStatus.STOPPING);
					jobRepository.update(jobExecution);
				}
				return exitCodeMapper.intValue(ExitStatus.COMPLETED.getExitCode());
			}

			if (opts.contains("-abandon")) {
				List<JobExecution> jobExecutions = getStoppedJobExecutions(jobIdentifier);
				if (jobExecutions == null) {
					throw new JobExecutionNotStoppedException("No stopped execution found for job=" + jobIdentifier);
				}
				for (JobExecution jobExecution : jobExecutions) {
					jobExecution.setStatus(BatchStatus.ABANDONED);
					jobRepository.update(jobExecution);
				}
				return exitCodeMapper.intValue(ExitStatus.COMPLETED.getExitCode());
			}

			if (opts.contains("-restart")) {
				JobExecution jobExecution = getLastFailedJobExecution(jobIdentifier);
				if (jobExecution == null) {
					throw new JobExecutionNotFailedException(
							"No failed or stopped execution found for job=" + jobIdentifier);
				}
				jobParameters = jobExecution.getJobParameters();
				jobName = jobExecution.getJobInstance().getJobName();
			}

			Job job = null;
			if (jobLocator != null) {
				try {
					job = jobLocator.getJob(jobName);
				} catch (NoSuchJobException e) {
				}
			}
			if (job == null) {
				job = (Job) context.getBean(jobName);
			}

			if (opts.contains("-next")) {
				JobParameters nextParameters = getNextJobParameters(job);
				Map<String, JobParameter> map = new HashMap<String, JobParameter>(nextParameters.getParameters());
				map.putAll(jobParameters.getParameters());
				for(Entry<String, JobParameter> entry : map.entrySet()){
					System.out.println("key===="+entry.getKey()+":"+"value===="+entry.getValue());
				}
				jobParameters = new JobParameters(map);
			}

			JobExecution jobExecution = launcher.run(job, jobParameters);
			return exitCodeMapper.intValue(jobExecution.getExitStatus().getExitCode());

		} catch (Throwable e) {
			String message = "Job Terminated in error: " + e.getMessage();
			logger.error(message, e);
			MultiFileCommandLineJobRunner.message = message;
			return exitCodeMapper.intValue(ExitStatus.FAILED.getExitCode());
		} finally {
			if (context != null) {
				context.close();
			}
		}
	}

	/**
	 * @param jobIdentifier
	 *            a job execution id or job name
	 * @param minStatus
	 *            the highest status to exclude from the result
	 * @return
	 */
	private List<JobExecution> getJobExecutionsWithStatusGreaterThan(String jobIdentifier, BatchStatus minStatus) {

		Long executionId = getLongIdentifier(jobIdentifier);
		if (executionId != null) {
			JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
			if (jobExecution.getStatus().isGreaterThan(minStatus)) {
				return Arrays.asList(jobExecution);
			}
			return Collections.emptyList();
		}

		int start = 0;
		int count = 100;
		List<JobExecution> executions = new ArrayList<JobExecution>();
		List<JobInstance> lastInstances = jobExplorer.getJobInstances(jobIdentifier, start, count);

		while (!lastInstances.isEmpty()) {

			for (JobInstance jobInstance : lastInstances) {
				List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
				if (jobExecutions == null || jobExecutions.isEmpty()) {
					continue;
				}
				for (JobExecution jobExecution : jobExecutions) {
					if (jobExecution.getStatus().isGreaterThan(minStatus)) {
						executions.add(jobExecution);
					}
				}
			}

			start += count;
			lastInstances = jobExplorer.getJobInstances(jobIdentifier, start, count);

		}

		return executions;

	}

	private JobExecution getLastFailedJobExecution(String jobIdentifier) {
		List<JobExecution> jobExecutions = getJobExecutionsWithStatusGreaterThan(jobIdentifier, BatchStatus.STOPPING);
		if (jobExecutions.isEmpty()) {
			return null;
		}
		return jobExecutions.get(0);
	}

	private List<JobExecution> getStoppedJobExecutions(String jobIdentifier) {
		List<JobExecution> jobExecutions = getJobExecutionsWithStatusGreaterThan(jobIdentifier, BatchStatus.STARTED);
		if (jobExecutions.isEmpty()) {
			return null;
		}
		List<JobExecution> result = new ArrayList<JobExecution>();
		for (JobExecution jobExecution : jobExecutions) {
			if (jobExecution.getStatus() != BatchStatus.ABANDONED) {
				result.add(jobExecution);
			}
		}
		return result.isEmpty() ? null : result;
	}

	private List<JobExecution> getRunningJobExecutions(String jobIdentifier) {
		List<JobExecution> jobExecutions = getJobExecutionsWithStatusGreaterThan(jobIdentifier, BatchStatus.COMPLETED);
		if (jobExecutions.isEmpty()) {
			return null;
		}
		List<JobExecution> result = new ArrayList<JobExecution>();
		for (JobExecution jobExecution : jobExecutions) {
			if (jobExecution.isRunning()) {
				result.add(jobExecution);
			}
		}
		return result.isEmpty() ? null : result;
	}

	private Long getLongIdentifier(String jobIdentifier) {
		try {
			return new Long(jobIdentifier);
		} catch (NumberFormatException e) {
			// Not an ID - must be a name
			return null;
		}
	}

	/**
	 * @param job
	 *            the job that we need to find the next parameters for
	 * @return the next job parameters if they can be located
	 * @throws JobParametersNotFoundException
	 *             if there is a problem
	 */
	private JobParameters getNextJobParameters(Job job) throws JobParametersNotFoundException {
		String jobIdentifier = job.getName();
		JobParameters jobParameters;
		List<JobInstance> lastInstances = jobExplorer.getJobInstances(jobIdentifier, 0, 1);

		JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
		if (incrementer == null) {
			throw new JobParametersNotFoundException("No job parameters incrementer found for job=" + jobIdentifier);
		}

		if (lastInstances.isEmpty()) {
			jobParameters = incrementer.getNext(new JobParameters());
			if (jobParameters == null) {
				throw new JobParametersNotFoundException(
						"No bootstrap parameters found from incrementer for job=" + jobIdentifier);
			}
		} else {
			List<JobExecution> lastExecutions = jobExplorer.getJobExecutions(lastInstances.get(0));
			jobParameters = incrementer.getNext(lastExecutions.get(0).getJobParameters());
		}
		return jobParameters;
	}

	public URL getResourcePath(String name) throws Exception {
		return this.getClass().getResource(name);
	}

	/**
	 * Launch a batch job using a {@link CommandLineJobRunner}. Creates a new
	 * Spring context for the job execution, and uses a common parent for all
	 * such contexts. No exception are thrown from this method, rather
	 * exceptions are logged and an integer returned through the exit status in
	 * a {@link JvmSystemExiter} (which can be overridden by defining one in the
	 * Spring context).<br>
	 * Parameters can be provided in the form key=value, and will be converted
	 * using the injected {@link JobParametersConverter}.
	 *
	 * @param args
	 *            <ul>
	 *            <li>-restart: (optional) if the job has failed or stopped and
	 *            the most should be restarted. If specified then the
	 *            jobIdentifier parameter can be interpreted either as the name
	 *            of the job or the id of the job execution that failed.</li>
	 *            <li>-next: (optional) if the job has a
	 *            {@link JobParametersIncrementer} that can be used to launch
	 *            the next in a sequence</li>
	 *            <li>jobPath: the xml application context containing a
	 *            {@link Job}
	 *            <li>jobIdentifier: the bean id of the job or id of the failed
	 *            execution in the case of a restart.
	 *            <li>jobParameters: 0 to many parameters that will be used to
	 *            launch a job.
	 *            </ul>
	 *            <p>
	 *            The options (<code>-restart, -next</code>) can occur anywhere
	 *            in the command line.
	 *            </p>
	 */
	public static void main(String[] args) throws Exception {
		
		String javaVersion = System.getProperty("java.version");//获取java版本号
		CsvReaderConstant.javaVersion = javaVersion;
		//调用linux命令获取字符编码
		try {
			String[] cmd = new String[]{"/bin/sh","-c","env|grep LANG"};
			Process ps = Runtime.getRuntime().exec(cmd);

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String result = sb.toString();
			CsvReaderConstant.lang = result.split("=")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用linux命令结束
		
		MultiFileCommandLineJobRunner command = new MultiFileCommandLineJobRunner();
		
		String path = null;
		String os = System.getProperties().getProperty("os.name");
		if("Windows".equals(os.split(" ")[0])){
			path = System.getProperty("java.class.path").split(";")[0];
		}else if("Linux".equals(os.split(" ")[0])){
			path = System.getProperty("java.class.path").split(":")[0];
		}
		int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
		int lastIndex = path.lastIndexOf(File.separator) + 1;
		path = path.substring(firstIndex, lastIndex);
		path += "conf"+File.separator+"args.txt";
		File f = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(f));
        String line = reader.readLine();
        String[] strings = line.split(" ");
        List<String> newargs = new ArrayList<String>(Arrays.asList(strings));
		
		
		//List<String> newargs = new ArrayList<String>(Arrays.asList(args));
		/*List<String> newargs = new ArrayList<String>();
		newargs.add("classpath:/jobs/file-import-job.xml");
		newargs.add("simpleFileImportJob");
		newargs.add("inputDir=test-classes/");*/

		/*try {
			if (System.in.available() > 0) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String line = " ";
				while (line != null) {
					if (!line.startsWith("#") && StringUtils.hasText(line)) {
						if (logger.isDebugEnabled()) {
							logger.debug("Stdin arg: " + line);
						}
						newargs.add(line);
					}
					line = reader.readLine();
				}
			}
		} catch (IOException e) {
			logger.warn("Could not access stdin (maybe a platform limitation)");
			if (logger.isDebugEnabled()) {
				logger.debug("Exception details", e);
			}
		}*/

		Set<String> opts = new HashSet<String>();
		List<String> params = new ArrayList<String>();

		int count = 0;
		String jobPath = null;
		String jobIdentifier = null;

		for (String arg : newargs) {
			if (VALID_OPTS.contains(arg)) {
				opts.add(arg);
			} else {
				switch (count) {
				case 0:
					jobPath = arg;
					break;
				case 1:
					jobIdentifier = arg;
					break;
				default:
					params.add(arg);
					break;
				}
				count++;
			}
		}

		if (jobPath == null || jobIdentifier == null) {
			String message = "At least 2 arguments are required: JobPath/JobClass and jobIdentifier.";
			logger.error(message);
			MultiFileCommandLineJobRunner.message = message;
			command.exit(1);
			return;
		}

		String[] parameters = params.toArray(new String[params.size()]);
		String inputDir = "";
		for (String parameter : parameters) {
			if (parameter.startsWith("inputDir")) {
				inputDir = parameter.split("=")[1];
				//break;
			}
			if(parameter.startsWith("logSize")){
				CsvReaderConstant.logSize = parameter.split("=")[1];
			}
			if(parameter.startsWith("delete")){
				CsvReaderConstant.delete = parameter.split("=")[1];
			}
			if(parameter.startsWith("sort")){
				CsvReaderConstant.sort = parameter.split("=")[1];
			}
		}

		int result = 0;
		if (!"".equals(inputDir)) {
			FileSystemResource inputDirResource = new FileSystemResource(inputDir);
			String[] inputFiles = inputDirResource.getFile().list();
			
			if("asc".equals(CsvReaderConstant.sort)){
				Arrays.sort(inputFiles);
			}else if("desc".equals(CsvReaderConstant.sort)){
				Arrays.sort(inputFiles);
				int len = inputFiles.length;
				String[] inputFilesDesc = new String[len];
				for(int i=0,j=len-1;i<len;i++){
					inputFilesDesc[i] = inputFiles[j];
					j--;
				}
				inputFiles = inputFilesDesc;
			}
			
			for (String inputFileName : inputFiles) {
				if (inputFileName.endsWith(".csv")) {
					List<String> fileParamList = new ArrayList<>();
					for (String parameter : parameters) {
						if (parameter.startsWith("inputDir")) {
							//fileParamList.add("inputFile=" + inputDir + CsvReaderConstant.FILE_DONE_DIR + inputFileName);
							fileParamList.add("inputSrcFile=" + inputDir + inputFileName);
						}
						fileParamList.add(parameter);
					}
					String[] fileParameter = fileParamList.toArray(new String[fileParamList.size()]);
					result = command.start(jobPath, jobIdentifier, fileParameter, opts);
				}
			}
		} else {
			result = command.start(jobPath, jobIdentifier, parameters, opts);
		}
		
		//程序执行完后执行存储过程
		/*ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:application.xml");
		IESMEventService bean = (IESMEventService)ctx.getBean("esmEventService");
		bean.callDataToType();*/
		
		command.exit(result);
	}
}
