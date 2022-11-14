package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.model.Societa;

@Service
public class BatteriaDiTestService {
	@Autowired
	private SocietaService societaService;
	@Autowired
	private DipendenteService dipendenteService;
	
	public void testInserisciNuovaSocieta() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Conad" + nowInMillisecondi,
				"Via dei " + nowInMillisecondi, new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testInserisciNuovaSocieta...failed: inserimento fallito");

		System.out.println("testInserisciNuovaSocieta........OK");
	}
	
	public void testFindByExampleSocieta() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("euronics" ,
				"Via dei gatti " , new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testFindByExampleSocieta...failed: transient object con id valorizzato");
		// salvo
		
		societaService.inserisciNuovo(nuovaSocieta);
		
		Societa societaProva = new Societa();
		try {
			Date datadiprova = new SimpleDateFormat("yyyy/MM/dd").parse("2000/01/01");
			societaProva.setDataFondazione(datadiprova);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//societaProva.setDataFondazione(datadiprova);
		societaProva.setRagioneSociale("euron");
		List<Societa> listaSocieta = new ArrayList<>();
		
		listaSocieta = societaService.findByExample(societaProva);
		
		if (listaSocieta.size() != 1)
			throw new RuntimeException("testFindByExampleSocieta...failed: inserimento fallito");

		System.out.println("testFindByExampleSocieta........OK");
	}
}
