package br.com.dcc.springbatchexamples.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.dcc.springbatchexamples.domain.Customer;
import br.com.dcc.springbatchexamples.domain.mapper.CustomerRowMapper;
import br.com.dcc.springbatchexamples.listener.SimpleChunkListener;
import br.com.dcc.springbatchexamples.processor.CustomerUpperCaseItemProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ReadFromDbProcessorConfiguration {

	@Bean
	public JdbcPagingItemReader<Customer> readFromDbProcessorReader(DataSource dataSource) {
		JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();

		reader.setDataSource(dataSource);
		reader.setFetchSize(10);
		reader.setRowMapper(new CustomerRowMapper());

		PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
		queryProvider.setSelectClause("id, email, firstName, lastName");
		queryProvider.setFromClause("from customer");
		Map<String, Order> sortKeys = new HashMap<>(2);
		sortKeys.put("id", Order.ASCENDING);
		queryProvider.setSortKeys(sortKeys);
		reader.setQueryProvider(queryProvider);

		return reader;
	}

	@Bean
	public ItemWriter<Customer> readFromDbProcessorWriter() {
		return items -> {
			for (Customer item : items) {
				log.info("Writing item {}", item.toString());
			}
		};
	}

	@Bean
	public Step readFromDbProcessorStep1(StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
		return stepBuilderFactory.get("ReadFromDbProcessorStep1")
				.<Customer, Customer>chunk(10)
				.faultTolerant()
				.listener(new SimpleChunkListener())
				.reader(readFromDbProcessorReader(dataSource))
				.processor(new CustomerUpperCaseItemProcessor())
				.writer(readFromDbProcessorWriter())
				.build();
	}

	@Bean
	public Job readFromDbProcessorJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) {
		return jobBuilderFactory.get("ReadFromDbProcessorJob")
				.start(readFromDbProcessorStep1(stepBuilderFactory, dataSource))
				.build();

	}

}
