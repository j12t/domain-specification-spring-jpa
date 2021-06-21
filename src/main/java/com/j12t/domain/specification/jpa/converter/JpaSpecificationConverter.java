package com.j12t.domain.specification.jpa.converter;

import org.springframework.data.jpa.domain.Specification;
import com.jt93.domain.specification.DomainSpecification;

public interface JpaSpecificationConverter<E, S extends DomainSpecification<?>> {

	public Specification<E> convert(DomainSpecification<?> domainSpecification);
}
