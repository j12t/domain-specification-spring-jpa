package com.j12t.domain.specification.jpa.converter;

import com.j12t.domain.specification.jpa.converter.impl.SpringJpaAndSpecification;
import com.j12t.domain.specification.jpa.converter.impl.SpringJpaContainsSpecification;
import com.j12t.domain.specification.jpa.converter.impl.SpringJpaEqualSpecification;
import com.j12t.domain.specification.jpa.converter.impl.SpringJpaNotSpecification;
import com.j12t.domain.specification.jpa.converter.impl.SpringJpaOrSpecification;
import com.jt93.domain.specification.AndDomainSpecification;
import com.jt93.domain.specification.DomainSpecification;
import com.jt93.domain.specification.NotDomainSpecification;
import com.jt93.domain.specification.OrDomainSpecification;
import com.jt93.domain.specification.impl.ContainsSpecification;
import com.jt93.domain.specification.impl.EqualSpecification;

import lombok.Data;

@Data
public class SpringJpaSpecificationFactory<E> {
	
	private E entity;
	
	public SpringJpaSpecificationFactory(E entity) {
		this.entity = entity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <D> SpringJpaSpecification<E, DomainSpecification<D>> convert(DomainSpecification<D> domainSpecification) {

		if (domainSpecification instanceof AndDomainSpecification<?>) {
			AndDomainSpecification<D> andSpecification = (AndDomainSpecification<D>) domainSpecification;
			
			return new SpringJpaAndSpecification(andSpecification, entity);
		}

		if (domainSpecification instanceof OrDomainSpecification<?>) {
			OrDomainSpecification<D> orSpecification = (OrDomainSpecification<D>) domainSpecification;
			
			return new SpringJpaOrSpecification(orSpecification, entity);
		}

		if (domainSpecification instanceof NotDomainSpecification<?>) {
			NotDomainSpecification<D> notSpecification = (NotDomainSpecification<D>) domainSpecification;
			
			return new SpringJpaNotSpecification(notSpecification, entity);
		}

		if (domainSpecification instanceof ContainsSpecification<?>) {
			ContainsSpecification<D> containsSpecification = (ContainsSpecification<D>) domainSpecification;

			return new SpringJpaContainsSpecification(containsSpecification, entity);
		}
		
		if (domainSpecification instanceof EqualSpecification<?, ?>) {
			EqualSpecification<D, ?> containsSpecification = (EqualSpecification<D, ?>) domainSpecification;

			return new SpringJpaEqualSpecification(containsSpecification, entity);
		}
		
		return null;

	}
}
