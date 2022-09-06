package com.cubic.microservices.customer_service.hystrix;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

@Configuration
public class ThreadLocalConfiguration {
	@Autowired(required = false)
	private HystrixConcurrencyStrategy existingConcurrencyStrategy;
	
	@Value("${cubic.app.useThreadLocalAwareStrategy}")
	private boolean useThreadLocalAwareStrategy;

	@PostConstruct
	public void init() {
		System.out.println("-----------------------------------");
		System.out.println("useThreadLocalAwareStrategy = " + this.useThreadLocalAwareStrategy);
		System.out.println("-----------------------------------");
		if (this.useThreadLocalAwareStrategy) {
			// Keeps references of existing Hystrix plugins.
			HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
			HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
			HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
			HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
			HystrixPlugins.reset();
			HystrixPlugins.getInstance()
			.registerConcurrencyStrategy(new ThreadLocalAwareStrategy(existingConcurrencyStrategy));
			HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
			HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
			HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
			HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
		}
	}
}
