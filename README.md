# AsyncApi
Asynchronous API using Spring Boot

By default, Spring Boot creates a *single unique thread* to process requests sent to *differenet api endpoints*. Client needs to wait until the current request is processed.

To make request async, we can add @Async annotations on the methods. This means that the method will be executed in a separate thread, and the calling thread will not be blocked waiting for the method to complete.

<b>Pros</b>
1. The @Async annotation is a powerful tool that can be used to improve the performance of our Spring Boot application.
2. Asynchronous processing is helpful when we want to offload time-consuming tasks to separate threads, improving the overall responsiveness of our application. And we can free up the calling threads to process other requests. This can lead to a significant improvement in the overall performance of our application.

<b>The @Async annotation can be used with any method that is declared in a Spring bean. However, there are a few things to keep in mind when using the annotation:</b>

1. The method must be public.
2. The method must not throw checked exceptions. If the method throws a checked exception, the exception will be wrapped in a runtime exception and thrown to the calling thread.
3. The method must return a value that implements the Future interface *(basically CompletableFuture)*. This allows the calling thread to get the result of the asynchronous task once it has been completed.
4. Or the method must have no return type that is void.


<b>@EnableAsync</b><br>
This annotation will enable Spring to run methods that are annotated with @Async in a background thread pool.


 One also need to create Bean of Executor interface with method name taskExecutor() (naming convention must be followed) inside the Appication.java
 ```
@SpringBootApplication
@EnableAsync
public class AsyncApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncApiApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Lookup-");
		executor.initialize();
		return executor;
	}
}
```

<b>ThreadPoolTaskExecutor<b> is a bean that allows to configure a **ThreadPoolExecutor**. ThreadPoolExecutor is a service that executes each submitted task using one of possibly several pooled threads, normally configured using Executors factory methods.
<br>
New threads are created using **ThreadFactory**. If not otherwise specified, a Executors.defaultThreadFactory() is used, that creates threads to all be in the same ThreadGroup and with the same NORM_PRIORITY priority and non-daemon status
<br>

**CorePoolSize** - the number of threads to keep in the pool, even if they are idle.<br>
**MaximumPoolSize** - the maximum number of threads to allow in the pool.<br>
**QueueCapacity** - When all threads are occupied then async request is pushed to queue until any thread becomes idle.<br>

**Queuing**<br>
Any BlockingQueue may be used to transfer and hold submitted tasks. The use of this queue interacts with pool sizing:
1. If fewer than corePoolSize threads are running, the Executor always prefers adding a new thread rather than queuing.
2. If corePoolSize or more threads are running, the Executor always prefers queuing a request rather than adding a new thread.
3. If a request cannot be queued, a new thread is created unless this would exceed maximumPoolSize, in which case, the task will be rejected.

**There are three general strategies for queuing:**
1. **Direct handoffs**: A good default choice for a work queue is a SynchronousQueue that hands off tasks to threads without otherwise holding them. Here, an attempt to queue a task will fail if no threads are immediately
   available to run it, so a new thread will be constructed. Direct handoffs generally require unbounded maximumPoolSizes to avoid rejection of new submitted tasks. This in turn admits the possibility of unbounded thread
   growth when commands continue to arrive on average faster than they can be processed.
3. **Unbounded queues**: Using an unbounded queue (for example a LinkedBlockingQueue without a predefined capacity) will cause new tasks to wait in the queue when all corePoolSize threads are busy. Thus, no more than
   corePoolSize threads will ever be created. (And the value of the maximumPoolSize therefore doesn't have any effect.) While this style of queuing can be useful in smoothing out transient bursts of requests,
   it admits the possibility of unbounded work queue growth when commands continue to arrive on average faster than they can be processed.
4. **Bounded queues**: A bounded queue (for example, an ArrayBlockingQueue) helps prevent resource exhaustion when used with finite maximumPoolSizes, but can be more difficult to tune and control. Queue sizes and maximum pool
   sizes may be traded off for each other: Using large queues and small pools minimizes CPU usage, OS resources, and context-switching overhead, but can lead to artificially low throughput. If tasks frequently block
   (for example if they are I/O bound), a system may be able to schedule time for more threads than you otherwise allow. Use of small queues generally requires larger pool sizes, which keeps CPUs busier but may encounter
   unacceptable scheduling overhead, which also decreases throughput.





 
