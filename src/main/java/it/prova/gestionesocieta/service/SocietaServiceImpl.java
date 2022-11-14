package it.prova.gestionesocieta.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.exceptions.SocietaConAlmenoUnDipendenteException;
import it.prova.gestionesocieta.model.Societa;
import it.prova.gestionesocieta.repository.SocietaRepository;


@Service
public class SocietaServiceImpl implements SocietaService {

	// questo mi serve per il findByExample2 che risulta 'a mano'
	// o comunque in tutti quei casi in cui ho bisogno di costruire custom query nel service
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private SocietaRepository societaRepository;
	
	
	
	
	
	@Transactional(readOnly = true)
	public List<Societa> listAllSocieta() {
		// TODO Auto-generated method stub
		return (List<Societa>) societaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Societa caricaSingolaSocieta(Long id) {
		// TODO Auto-generated method stub
		return societaRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Societa societaInstance) {
		societaRepository.save(societaInstance);
		
	}

	@Transactional
	public void inserisciNuovo(Societa societaInstance) {
		societaRepository.save(societaInstance);
		
	}

	
	@Transactional
	public void rimuovi(Societa societaInstance) {
		
		TypedQuery<Societa> query = entityManager
				.createQuery("select s from Societa s join fetch s.dipendenti d where s.id = :id", Societa.class)
				.setParameter("id", societaInstance.getId());
		if (!query.getResultList().isEmpty()) {
			throw new SocietaConAlmenoUnDipendenteException("Societa ha almeno un dipendente ad esso");
		}
		
		societaRepository.delete(societaInstance);
	}

	@Override
	public List<Societa> findByExample(Societa example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select s from Societa s where s.id = s.id");

		//is not blank
		if (StringUtils.isNotEmpty(example.getRagioneSociale())) {
			whereClauses.add(" s.ragioneSociale  like :ragionesociale ");
			paramaterMap.put("ragionesociale", "%" + example.getRagioneSociale() + "%");
		}
		if (StringUtils.isNotEmpty(example.getIndirizzo())) {
			whereClauses.add(" s.indirizzo like :indirizzo ");
			paramaterMap.put("indirizzo", "%" + example.getIndirizzo() + "%");
		}
		if (example.getDataFondazione() != null) {
			whereClauses.add("s.dataFondazione >= :datafondazione ");
			paramaterMap.put("datafondazione", example.getDataFondazione());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Societa> typedQuery = entityManager.createQuery(queryBuilder.toString(), Societa.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();

	
	}

	@Override
	public List<Societa> findAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(Integer ral) {
		// TODO Auto-generated method stub
		return societaRepository.findAllDistinctByDipendenti_redittoAnnuoLordoGreaterThan(ral);
	}

}
