package com.christian.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Pessoa;
import com.christian.helpdesk.domain.Tecnico;
import com.christian.helpdesk.domain.dtos.TecnicoDTO;
import com.christian.helpdesk.repositories.PessoaRepository;
import com.christian.helpdesk.repositories.TecnicoRepository;
import com.christian.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.christian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findTecnicoById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found, id out of database: " + id));
	}
	
	public List<Tecnico> findTodosTecnicos() {
		return repository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objTecDTO) {
		objTecDTO.setId(null);
		objTecDTO.setSenha(encoder.encode(objTecDTO.getSenha()));
		//validar CPF e email antes de salvar
		validarPorCPFeEmail(objTecDTO);
		Tecnico newObj = new Tecnico(objTecDTO);
		return repository.save(newObj);
	}

	private void validarPorCPFeEmail(TecnicoDTO objTecDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objTecDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objTecDTO.getId()) {
			//customizando exception spring para a nossa
			throw new DataIntegrityViolationException("CPF ja foi cadastrado em nosso sistema");
		}
		
		obj = pessoaRepository.findByEmail(objTecDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objTecDTO.getId()) {
			//customizando exception spring para a nossa
			throw new DataIntegrityViolationException("E-mail ja foi cadastrado em nosso sistema");
		}
		
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findTecnicoById(id);
		validarPorCPFeEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findTecnicoById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Tecnnico possui chamados, repasse para poder deleta-lo.");
		}
		repository.deleteById(id);
	}
}	