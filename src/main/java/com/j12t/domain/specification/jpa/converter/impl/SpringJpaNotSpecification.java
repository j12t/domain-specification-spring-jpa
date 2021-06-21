package com.j12t.domain.specification.jpa.converter.impl;

import org.springframework.data.jpa.domain.Specification;

import com.j12t.domain.specification.jpa.converter.SpringJpaSpecification;
import com.j12t.domain.specification.jpa.converter.SpringJpaSpecificationFactory;
import com.jt93.domain.specification.NotDomainSpecification;

public class SpringJpaNotSpecification<E> extends SpringJpaSpecification<E, NotDomainSpecification<?>> {

	public SpringJpaNotSpecification(NotDomainSpecification<?> domainSpecification, E entity) {
		super(domainSpecification, entity);
	}

	@Override
	public Specification<E> filter() {

		SpringJpaSpecificationFactory<E> factory = new SpringJpaSpecificationFactory<E>(getEntity());

		return Specification.not(factory.convert(getDomainSpecification().getSpecifications().get(0)).filter());
	}

}
