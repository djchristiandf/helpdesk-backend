package com.christian.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Chamado;
import com.christian.helpdesk.domain.Cliente;
import com.christian.helpdesk.domain.Tecnico;
import com.christian.helpdesk.domain.dtos.ChamadoDTO;
import com.christian.helpdesk.domain.enums.Prioridade;
import com.christian.helpdesk.domain.enums.Status;
import com.christian.helpdesk.repositories.ChamadoRepository;
import com.christian.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService; 
	
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj= repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado nao encontrado pelo Id: " +id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

	public Chamado create(@Valid ChamadoDTO objDTO) {
		return repository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findTecnicoById(obj.getTecnico());
		Cliente cliente = clienteService.findClienteById(obj.getCliente());
		Chamado chamado = new Chamado();
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacao(obj.getObservacao());
		return chamado;
	}

	public Chamado update(Integer id, @Valid ChamadoDTO objDto) {
		objDto.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDto);
		return repository.save(oldObj);
	}
}
