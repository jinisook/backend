package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Locker;

public interface LockerRepository extends JpaRepository<Locker, Long> {

}
