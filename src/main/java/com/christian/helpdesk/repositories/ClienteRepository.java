package com.christian.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
