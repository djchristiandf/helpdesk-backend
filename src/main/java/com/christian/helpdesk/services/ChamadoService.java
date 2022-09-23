package com.christian.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Chamado;
import com.christian.helpdesk.repositories.ChamadoRepository;
import com.christian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj= repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado nao encontrado pelo Id: " +id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
	
}
