package com.paul.repositories;

import com.paul.entities.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {

}
