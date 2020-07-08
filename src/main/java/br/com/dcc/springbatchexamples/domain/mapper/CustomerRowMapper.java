package br.com.dcc.springbatchexamples.domain.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.dcc.springbatchexamples.domain.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Customer.builder()
				.id(rs.getLong("id"))
				.email(rs.getString("email"))
				.firstName(rs.getString("firstName"))
				.lastName(rs.getString("lastName"))
				.build();
	}

}
