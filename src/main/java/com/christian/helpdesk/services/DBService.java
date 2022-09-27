package com.christian.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.christian.helpdesk.domain.Chamado;
import com.christian.helpdesk.domain.Cliente;
import com.christian.helpdesk.domain.Tecnico;
import com.christian.helpdesk.domain.enums.Perfil;
import com.christian.helpdesk.domain.enums.Prioridade;
import com.christian.helpdesk.domain.enums.Status;
import com.christian.helpdesk.repositories.ChamadoRepository;
import com.christian.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
    @Autowired
    private ChamadoRepository chamadoRepository;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Tecnico 1", "633.490.050-12", "tec1@assistencia.com", encoder.encode("123456"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Tecnico 2", "662.300.520-08", "tec2@assistencia.com", encoder.encode("123456"));
		Tecnico tec3 = new Tecnico(null, "Tecnico 3", "625.712.140-04", "tec3@assistencia.com", encoder.encode("123456"));
		Tecnico tec4 = new Tecnico(null, "Tecnico 4", "404.896.060-16", "tec4@assistencia.com", encoder.encode("123456"));
		Tecnico tec5 = new Tecnico(null, "Tecnico 5", "624.263.160-12", "tec5@assistencia.com", encoder.encode("123456"));
		
		Cliente cli1 = new Cliente(null, "Cliente A", "499.789.420-68", "cliA@nda.com", encoder.encode("123456"));
		Cliente cli2 = new Cliente(null, "Cliente B", "549.695.050-39", "cliB@nda.com", encoder.encode("123456"));
		Cliente cli3 = new Cliente(null, "Cliente C", "013.549.900-30", "cliC@nda.com", encoder.encode("123456"));
		Cliente cli4 = new Cliente(null, "Cliente D", "737.949.210-11", "cliD@nda.com", encoder.encode("123456"));
		Cliente cli5 = new Cliente(null, "Cliente E", "501.315.960-12", "cliE@nda.com", encoder.encode("123456"));
		
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro Chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.ALTA,  Status.ABERTO,    "Chamado 02", "Segunda Chamado", tec1, cli2);
		Chamado c3 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO,    "Chamado 03", "Terceiro Chamado", tec1, cli3);
		Chamado c4 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO,    "Chamado 04", "Quarto Chamado", tec1, cli4);
		Chamado c5 = new Chamado(null, Prioridade.ALTA,  Status.ANDAMENTO, "Chamado 05", "Quinto Chamado", tec2, cli5);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 06", "Sexto Chamado", tec3, cli2);
	
		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
	}
}
