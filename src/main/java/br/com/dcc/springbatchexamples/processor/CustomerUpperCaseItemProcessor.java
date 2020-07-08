package br.com.dcc.springbatchexamples.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.dcc.springbatchexamples.domain.Customer;

public class CustomerUpperCaseItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		return Customer.builder()
				.id(item.getId())
				.email(item.getEmail())
				.firstName(item.getFirstName().toUpperCase())
				.lastName(item.getLastName().toUpperCase())
				.build();
	}

}
