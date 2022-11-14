package it.prova.gestionesocieta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesocieta.model.Dipendente;
import it.prova.gestionesocieta.repository.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService {

	
	// questo mi serve per il findByExample2 che risulta 'a mano'
	// o comunque in tutti quei casi in cui ho bisogno di costruire custom query nel service
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private DipendenteRepository dipendenteRepository;
	
	
	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendenti() {
		// TODO Auto-generated method stub
		return (List<Dipendente>) dipendenteRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		// TODO Auto-generated method stub
		return dipendenteRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
		
	}

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		dipendenteRepository.save(dipendenteInstance);
		
	}

	@Transactional
	public void rimuovi(Dipendente dipendenteInstance) {
		dipendenteRepository.delete(dipendenteInstance);
	}

}
