package com.j12t.domain.specification.jpa.converter.impl;

import org.springframework.data.jpa.domain.Specification;

import com.j12t.domain.specification.jpa.converter.SpringJpaSpecification;
import com.j12t.domain.specification.jpa.converter.SpringJpaSpecificationFactory;
import com.jt93.domain.specification.DomainSpecification;
import com.jt93.domain.specification.OrDomainSpecification;

public class SpringJpaOrSpecification<E> extends SpringJpaSpecification<E, OrDomainSpecification<?>> {

	public SpringJpaOrSpecification(OrDomainSpecification<?> domainSpecification, E entity) {
		super(domainSpecification, entity);
	}

	@Override
	public Specification<E> filter() {

		SpringJpaSpecificationFactory<E> factory = new SpringJpaSpecificationFactory<E>(getEntity());

		Specification<E> finalSpecification = null;

		// TODO J12T pas que 0 et 1 mais toute la liste
		for (DomainSpecification<?> specification : getDomainSpecification().getSpecifications()) {
			if (finalSpecification == null) {
				finalSpecification = factory.convert(specification).filter();
			} else {
				finalSpecification = finalSpecification.and(factory.convert(specification).filter());
			}
		}

		return finalSpecification;
	}

}
