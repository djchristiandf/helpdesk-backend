package com.christian.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Cliente;
import com.christian.helpdesk.domain.Pessoa;
import com.christian.helpdesk.domain.dtos.ClienteDTO;
import com.christian.helpdesk.repositories.ClienteRepository;
import com.christian.helpdesk.repositories.PessoaRepository;
import com.christian.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.christian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findClienteById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found, id out of database: " + id));
	}
	
	public List<Cliente> findTodosClientes() {
		return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		//validar CPF e email antes de salvar
		validarPorCPFeEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj);
	}

	private void validarPorCPFeEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			//customizando exception spring para a nossa
			throw new DataIntegrityViolationException("CPF ja foi cadastrado em nosso sistema");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			//customizando exception spring para a nossa
			throw new DataIntegrityViolationException("E-mail ja foi cadastrado em nosso sistema");
		}
		
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findClienteById(id);
		validarPorCPFeEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findClienteById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui chamados, repasse para poder deleta-lo.");
		}
		repository.deleteById(id);
	}
}
