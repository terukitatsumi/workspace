package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Item;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	// SELECT * FROM ITEM WHERE DELETED_AT IS NULL
	public List<Item> findByDeletedAtIsNull();
}