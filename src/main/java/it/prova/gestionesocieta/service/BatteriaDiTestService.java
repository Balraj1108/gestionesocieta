package it.prova.gestionesocieta.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionesocieta.exceptions.SocietaConAlmenoUnDipendenteException;
import it.prova.gestionesocieta.model.Dipendente;
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
	
	public void testRimuoviSocieta() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Conad" + nowInMillisecondi,
				"Via dei " + nowInMillisecondi, new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testRimuoviSocieta...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testRimuoviSocieta...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("mario","rossi", new Date(), 5);
		nuovoDipendente.setSocieta(nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		try {
			societaService.rimuovi(nuovaSocieta);
		} catch (SocietaConAlmenoUnDipendenteException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//societaService.rimuovi(nuovaSocieta);

		System.out.println("testRimuoviSocieta........OK");
	
	}
	
	
	public void testModificaDipendente() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Conad" + nowInMillisecondi,
				"Via dei " + nowInMillisecondi, new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testModificaDipendente...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testModificaDipendente...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("mario","rossi", new Date(), 5);
		nuovoDipendente.setSocieta(nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		
		nuovoDipendente.setCognome("bianchi");
		dipendenteService.aggiorna(nuovoDipendente);
		
		if (!(nuovoDipendente.getCognome().equals("bianchi"))) {
			throw new RuntimeException("testModificaDipendente...failed: aggiornamento fallito");
		}
		
		
		System.out.println("testModificaDipendente........OK");
		
		
	}
	
	public void testFindAllDipendentiRalMaggioreDi() {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Conad" + nowInMillisecondi,
				"Via dei " + nowInMillisecondi, new Date());
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testFindAllDipendentiRalMaggioreDi...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testFindAllDipendentiRalMaggioreDi...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("mario","rossi", new Date(), 5000);
		nuovoDipendente.setSocieta(nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		
		List<Societa> listaSocieta = new ArrayList<>();
		
		listaSocieta = societaService.findAllDistinctByDipendenti_RedditoAnnuoLordoGreaterThan(3000);
		
		if (listaSocieta.size() != 1) {
			throw new RuntimeException("testFindAllDipendentiRalMaggioreDi...failed: test fallito dati non coincidono");
		}
		
		System.out.println("testFindAllDipendentiRalMaggioreDi........OK");
		
	}
	
	public void testDipendentePiuAnzianoDataFondazione() throws Exception {
		Long nowInMillisecondi = new Date().getTime();

		Societa nuovaSocieta = new Societa("Conad" + nowInMillisecondi,
				"Via dei " + nowInMillisecondi);
		Date datadiprova1 = new SimpleDateFormat("yyyy/MM/dd").parse("1989/01/01");
		nuovaSocieta.setDataFondazione(datadiprova1);
		if (nuovaSocieta.getId() != null)
			throw new RuntimeException("testDipendentePiuAnzianoDataFondazione...failed: transient object con id valorizzato");
		// salvo
		societaService.inserisciNuovo(nuovaSocieta);
		if (nuovaSocieta.getId() == null || nuovaSocieta.getId() < 1)
			throw new RuntimeException("testDipendentePiuAnzianoDataFondazione...failed: inserimento fallito");
		
		Dipendente nuovoDipendente = new Dipendente("mario","rossi", new Date(), 5000);
		nuovoDipendente.setSocieta(nuovaSocieta);
		dipendenteService.inserisciNuovo(nuovoDipendente);
		Dipendente nuovoDipendente1 = new Dipendente("luca","rossi", 5000);
		nuovoDipendente1.setSocieta(nuovaSocieta);
		nuovoDipendente1.setDataAssunzione(datadiprova1);
		dipendenteService.inserisciNuovo(nuovoDipendente1);
		
		Date datadiprova = new SimpleDateFormat("yyyy/MM/dd").parse("1990/01/01");
		
		//System.out.println(dipendenteService.orderByDataAssunzione_dataFondazioneGreatherThan(datadiprova));
		
		if (!(dipendenteService.orderByDataAssunzione_dataFondazioneGreatherThan(datadiprova)
				.getNome().equals("luca"))) {
			throw new RuntimeException("testDipendentePiuAnzianoDataFondazione...failed: test fallito dati non coincidono");
		}
		
		
		System.out.println("testDipendentePiuAnzianoDataFondazione........OK");
	}
	
}
