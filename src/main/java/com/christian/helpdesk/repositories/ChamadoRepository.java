/**
 * 
 */
package com.christian.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.christian.helpdesk.domain.Chamado;

/**
 * @author djchristiandf
 *
 */
public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
