package zoo.daroo.h2.mem.tryouts.csv;

import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.job.JobStep;
import org.springframework.core.task.SyncTaskExecutor;

public class SpringBatchJobTryout {
	
	public static void main(String[] args) throws Exception {
		final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setTaskExecutor(new SyncTaskExecutor());
		
		final JobRepository jobRepository = (JobRepository) new MapJobRepositoryFactoryBean().getObject(); 
		SimpleJob job = new SimpleJob();
		job.setJobRepository(jobRepository);
		//
		JobStep step = new JobStep();
		step.setJobLauncher(jobLauncher);
		step.setJobRepository(jobRepository);
		
		job.addStep(step);
		
		
		
		//step.setJob(job);;
		
		//job.afterPropertiesSet()
	}

}
