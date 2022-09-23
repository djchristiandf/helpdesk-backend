/**
 * 
 */
package com.christian.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.helpdesk.domain.Tecnico;

/**
 * @author djchristiandf
 *
 */
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
