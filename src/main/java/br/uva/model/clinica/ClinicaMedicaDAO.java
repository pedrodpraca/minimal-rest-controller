/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uva.model.clinica;

import br.uva.model.clinica.especialidades.Especialidade;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author csiqueira
 */
@Repository
public interface ClinicaMedicaDAO extends CrudRepository<ClinicaMedica, Long> {
	Page<ClinicaMedica> findDistinctByEnderecoInIgnoreCaseOrNomeInIgnoreCaseOrEspecialidadesIn(Collection<String> endereco, Collection<String> busca, Collection<Especialidade> especialidade, Pageable pr);
	Page<ClinicaMedica> findDistinctByEnderecoInIgnoreCaseOrNomeInIgnoreCaseOrEspecialidadesInAndTipoAtendimento(Collection<String> endereco, Collection<String> busca, Collection<Especialidade> especialidade, TipoAtendimento tipoAtendimento, Pageable pr);

	@Query("SELECT DISTINCT c FROM ClinicaMedica c, Busca b INNER JOIN c.especialidades e WHERE c.tipoAtendimento IN :tipos AND b.uuid = :uuid AND (LOWER(c.endereco) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(c.nome) LIKE CONCAT('%', LOWER(b.termo), '%') OR LOWER(e.nome) LIKE CONCAT('%', LOWER(b.termo), '%'))")
	public Page<ClinicaMedica> busca(@Param("uuid") String uuid, @Param("tipos") Collection<TipoAtendimento> tipos, Pageable req);
}