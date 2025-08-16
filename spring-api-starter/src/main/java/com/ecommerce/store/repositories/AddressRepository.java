package com.ecommerce.store.repositories;

import com.ecommerce.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}