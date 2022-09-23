package com.christian.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Chamado;
import com.christian.helpdesk.domain.Cliente;
import com.christian.helpdesk.domain.Tecnico;
import com.christian.helpdesk.domain.enums.Perfil;
import com.christian.helpdesk.domain.enums.Prioridade;
import com.christian.helpdesk.domain.enums.Status;
import com.christian.helpdesk.repositories.ChamadoRepository;
import com.christian.helpdesk.repositories.ClienteRepository;
import com.christian.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
    @Autowired
    private ChamadoRepository chamadoRepository;
    
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Tecnico 1", "633.490.050-12", "tec1@assistencia.com", "123456");
		tec1.addPerfil(Perfil.ADMIN);
		Cliente cli1 = new Cliente(null, "Cliente A", "370.414.300-65", "cli@nda.com", "123456");
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
	
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
}
