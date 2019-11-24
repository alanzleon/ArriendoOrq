package com.example.msarriendoorq.repository;

import com.example.msarriendoorq.entity.Arriendo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface IArriendoRepository  extends MongoRepository<Arriendo, Serializable> {
}
