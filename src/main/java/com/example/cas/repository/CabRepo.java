package com.example.cas.repository;

import com.example.cas.cfs.CabEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabRepo extends CrudRepository<CabEntity, String> {
}
