package it.prova.gestionesocieta.repository;


import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import it.prova.gestionesocieta.model.Societa;

public interface SocietaRepository extends CrudRepository<Societa, Long>, QueryByExampleExecutor<Societa>{

	//c'è anche la possibilità di andare nelle 'nested' properties...MAGNIFICO
	//lo faccio eager per provare
	/*@Query("from Societa s left join fetch s.abitanti where s.id=:idSocieta")
	Societa findByIdEager(Long idSocieta);*/
	
	@EntityGraph(attributePaths = {"dipendenti"})
	List<Societa> findAllDistinctByDipendenti_redittoAnnuoLordoGreaterThan(Integer ral);
	
	
	
}
