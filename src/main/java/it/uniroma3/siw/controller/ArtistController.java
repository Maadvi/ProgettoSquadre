package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;




@Controller
public class ArtistController {

    /*
	@Autowired
	private CredentialsService credentialsService;
	@Autowired MovieRepository movieRepository;	
	@Autowired ArtistRepository artistRepository;

	@GetMapping("/admin/formNewArtist")
	public String formNewArtist(Model model) {
		model.addAttribute("artist", new Artist());
		return "admin/formNewPresidente.html";
	}
	@PostMapping("/admin/artists")
	public String newArtist(@ModelAttribute("artist") Artist artist, 
			@RequestParam("image") MultipartFile multipartFile,//
			Model model) throws IOException {
		if (!artistRepository.existsByNomeAndCognome(artist.getNome(), artist.getCognome())) {
			//I diversi tipi di path mi servono per la cancellazione della foto e cartella
			//salvo la foto profilo dell'utente
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			//setto la foto passata come foto dell'utente e la salvo
			artist.setFotoPath(fileName);

			String nome_cartella_foto_artista= artist.getNome() + Math.floor(Math.random() * 10) + artist.getCognome();
			artist.setFotoPath("/artist-photos/" + nome_cartella_foto_artista + "/" + fileName); //prima di fare questo salvo l'user perchè mi serve il suo id per creare la cartella dove saranno le sue/a foto

			// salvo foto nella sua cartella
			String uploadDir = "artist-photos/" + nome_cartella_foto_artista;   
			String prova =  System.getProperty("user.dir");

			prova = prova +"/"+ uploadDir;
			artist.setPathAssoluto(prova);
			artist.setFileName(fileName);

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

			this.artistRepository.save(artist);
			model.addAttribute("artist", artist);
			return "artist.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo artista esiste già");
			return "admin/formNewPresidente.html";
		}
	}


	@GetMapping("/artists/{id}")
	public String getArtist(@PathVariable("id") Long id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {

		} else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				model.addAttribute("admin", "ad");
			} }

		model.addAttribute("artist", this.artistRepository.findById(id).get());
		return "artist.html";
	}
	@GetMapping("/artists")
	public String showArtists(Model model) {
		model.addAttribute("artists", this.artistRepository.findAll());
		return "artists.html";
	}
	@GetMapping("/formSearchArtists")
	public String formSearchArtists() {
		return "formSearchArtists.html";
	}
	@PostMapping("/searchArtists")
	public String searchArtists(Model model, @RequestParam String nome) {
		model.addAttribute("artists", this.artistRepository.findByNome(nome));
		return "foundArtists.html";
	}

	@GetMapping("/indexArtist")
	public String indexArtist() {
		return "indexArtist.html";
	}

	@GetMapping("/admin/updateActors/{idMovie}")
	public String updateActors( @PathVariable("idMovie") Long idMovie ,Model model) {
		Movie movie= movieRepository.findById(idMovie).get();
		model.addAttribute("artists", this.artistRepository.findAll());
		model.addAttribute("movie", movie);
		return "admin/updateActors.html";
	}



	@GetMapping("/admin/confermaEliminaArtista/{artistId}")
	public String confermaEliminaArtista(Model model, @PathVariable("artistId") Long artistId) {
		model.addAttribute("artist", this.artistRepository.findById(artistId).get());
		return "admin/confermaEliminaArtista.html";
	}


	@GetMapping("/admin/EliminaArtista/{artistId}")
	public String EliminaArtista(Model model, @PathVariable("artistId") Long artistId) {
		Artist artista= this.artistRepository.findById(artistId).get();

		// parte per cancellar5e foto e cartella artista
		try {
			FileUploadUtil.deleteFileArtista(artista);
		} catch (IOException e) {
			e.printStackTrace();
		}


		List<Movie> film = artista.getMoviesActed();
		for(Movie movie : film) {
			movie.getActors().remove(artista);
			this.movieRepository.save(movie);

		}

		if(artista.getMoviesDirected()!= null) {
			film = artista.getMoviesDirected();
			for(Movie movie : film) {
				movie.setDirector(null);
				this.movieRepository.save(movie);

			}

		}
		artista.getMoviesActed().removeAll(artista.getMoviesActed());
		artista.getMoviesDirected().removeAll(artista.getMoviesDirected());


		this.artistRepository.delete(artista );


		return "admin/artistaEliminato.html";
	}



	@GetMapping("/admin/artistaEliminato")
	public String artistaEliminato() {

		return "admin/artistaEliminato.html";
	}

	//metodi per modifica
	//metodi per modifica
	@GetMapping("/admin/modificaArtista/{artistId}")
	public String modificaArtista(Model model, @PathVariable("artistId") Long artistId) {
		model.addAttribute("artist", new Artist());
		model.addAttribute("artistog", this.artistRepository.findById(artistId).get());
		return "admin/formModificaArtista" ;

	}


	@PostMapping("/admin/formModificaArtista/{artistId}")
	public String formModificaArtista(@ModelAttribute("artist") Artist artist, Model model, @PathVariable("artistId") Long artistId , @RequestParam("image") MultipartFile multipartFile) throws IOException {

		if (!artistRepository.existsByNomeAndCognome(artist.getNome(), artist.getCognome())) {
			Artist artistt= this.artistRepository.findById(artistId).get();
			//modifica cognome
			if(  !(artist.getCognome().length() == 0)   )
				artistt.setCognome(artist.getCognome());
			//modifica nome
			if(!(artist.getNome().length()==0))
				artistt.setNome(artist.getNome());

			//modifica data nascita
			if(artist.getData_nascita()!=null )
				artistt.setData_nascita(artist.getData_nascita());

			//modifica data morte
			if(artist.getData_morte()!=null)
				artistt.setData_morte(artist.getData_morte());

			//modifica foto
			if(!(multipartFile.isEmpty()) ){
				//cancello vecchia foto
				 
				try {
					FileUploadUtil.deleteFileArtista(artistt);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//salvo la foto profilo dell'utente
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

				//setto la foto passata come foto dell'utente e la salvo
				artistt.setFotoPath(fileName);

				String nome_cartella_foto_artista= artistt.getNome() + Math.floor(Math.random() * 10) + artistt.getCognome();
				artistt.setFotoPath("/artist-photos/" + nome_cartella_foto_artista + "/" + fileName); //prima di fare questo salvo l'user perchè mi serve il suo id per creare la cartella dove saranno le sue/a foto

				// salvo foto nella sua cartella
				String uploadDir = "artist-photos/" + nome_cartella_foto_artista;   
				String prova =  System.getProperty("user.dir");

				prova = prova +"/"+ uploadDir;
				artistt.setPathAssoluto(prova);
				artistt.setFileName(fileName);

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

			} 	


			this.artistRepository.save(artistt);

			model.addAttribute("artist", artistt);
			return "artist.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo attore esiste già");
			return "artist.html";
		}
	}
	//fine metodi per modifica




*/

}
