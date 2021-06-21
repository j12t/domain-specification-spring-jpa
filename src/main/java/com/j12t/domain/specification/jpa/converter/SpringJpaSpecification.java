package com.j12t.domain.specification.jpa.converter;

import org.springframework.data.jpa.domain.Specification;

import com.jt93.domain.specification.DomainSpecification;

import lombok.Data;

@Data
public abstract class SpringJpaSpecification<E, S extends DomainSpecification<?>> {

	private S domainSpecification;
	
	private E entity;
	
	public abstract Specification<E> filter();
	
	public SpringJpaSpecification(S domainSpecification, E entity) {
		this.domainSpecification = domainSpecification;
		this.entity = entity;
	}

}
