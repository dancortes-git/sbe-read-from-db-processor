package br.com.dcc.springbatchexamples.processor;

import org.springframework.batch.item.ItemProcessor;

import br.com.dcc.springbatchexamples.domain.Customer;

public class CustomerFilteringItemProcessor implements ItemProcessor<Customer, Customer> {

	private final String customerToFilter = "ante.Nunc@amet.co.uk";

	@Override
	public Customer process(Customer item) throws Exception {

		if (customerToFilter.equals(item.getEmail())) {
			return null;
		} else {
			return item;
		}
	}

}
