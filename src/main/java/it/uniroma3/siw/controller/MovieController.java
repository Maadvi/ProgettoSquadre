package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;




@Controller
public class MovieController {/*
	@Autowired MovieRepository movieRepository;
	@Autowired ArtistRepository artistRepository;
	@Autowired ReviewRepository reviewRepository;
	@Autowired UserRepository userRepository;
	@Autowired PhotoRepository photoRepository;


	@Autowired
	private UserService userService;

	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/admin/formNewMovie")
	public String formNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "admin/formNewSquadra.html";
	}
	@PostMapping("/admin/movies")
	public String newMovie(@ModelAttribute("movie") Movie movie, Model model) {
		if (!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())) {
			this.movieRepository.save(movie);
			model.addAttribute("movie", movie);
			return "movie.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo film esiste già");
			return "admin/formNewSquadra.html";
		}
	}


	@GetMapping("/movies/{id}")
	public String getMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieRepository.findById(id).get());
		model.addAttribute("registra" ,this.movieRepository.findById(id).get().getDirector() );
		model.addAttribute("attori" ,this.movieRepository.findById(id).get().getActors() );
		if(!this.movieRepository.findById(id).get().getRecensioni().isEmpty()) {
			double media = 0;
			for(Review recensione : this.movieRepository.findById(id).get().getRecensioni()  ) {
				media+=recensione.getValutazione();
			}
			media = media/this.movieRepository.findById(id).get().getRecensioni().size() ;

			double media_arrotondata =  Math.round(media * 2) / 2.0;;
			model.addAttribute("media_arrotondata" , media_arrotondata);
			media = ((double)((int)(media *100.0)))/100.0;
			model.addAttribute("media" , media); 
		}
		return "movie.html";
	}
	@GetMapping("/movies")
	public String showMovies(Model model) {
		model.addAttribute("movies", this.movieRepository.findAll());
		return "movies.html";
	}
	@GetMapping("/formSearchMovies")
	public String formSearchMovies() {
		return "formSearchMovies.html";
	}
	@PostMapping("/searchMovies")
	public String searchMovies(Model model, @RequestParam Integer year) {
		model.addAttribute("movies", this.movieRepository.findByYear(year));
		return "foundMovies.html";
	}

	@GetMapping("/indexMovie")
	public String indexMovie() {
		return "indexMovie.html";
	}

	@GetMapping("/admin/indexMovieAd")
	public String indexMovieAd() {
		return "admin/indexMovieAd.html";
	}
	@GetMapping("/admin/indexAdmin")
	public String indexAdmin() {
		return "admin/indexAdmin.html";
	}
	@GetMapping("/admin/indexArtistAd")
	public String indexArtistAd() {
		return "admin/indexArtistAd.html";
	}

	@GetMapping("/admin/manageMovies")
	public String manageMovies(Model model) {
		model.addAttribute("movies", this.movieRepository.findAll());
		return "admin/manageMovies";
	}

	@GetMapping("/admin/formUpdateMovie/{id}")
	public String formUpdateMovie(@PathVariable("id") Long id, Model model) {
		model.addAttribute("movie", this.movieRepository.findById(id).get());
		model.addAttribute("artists" , artistRepository.findAll());
		return "admin/formUpdateMovie.html";
	}

	@GetMapping("/admin/addDirector/{id}")
	public String addDirector(@PathVariable("id") Long id, Model model) {
		model.addAttribute("artists" , artistRepository.findAll());
		model.addAttribute("movie", this.movieRepository.findById(id).get());
		return "admin/addDirector.html";

	}

	@GetMapping("/admin/setDirectorToMovie/{idArtist}/{idMovie}")
	public String setDirectorToMovie(@PathVariable("idArtist") Long idArtist, @PathVariable("idMovie") Long idMovie, Model model){
		Movie movie= movieRepository.findById(idMovie).get();
		Artist artist= artistRepository.findById(idArtist).get();
		movie.setDirector(artist);
		artist.getMoviesDirected().add(movie);
		artistRepository.save(artist);
		movieRepository.save(movie);
		model.addAttribute("movie", movie);
		model.addAttribute("artist", artist);
		return "admin/setDirectorToMovie.html";
	}

	@GetMapping("/admin/addActorToMovie/{idArtist}/{idMovie}")
	public String addActorToMovie(@PathVariable("idArtist") Long idArtist, @PathVariable("idMovie") Long idMovie, Model model){
		Movie movie= movieRepository.findById(idMovie).get();
		Artist artist= artistRepository.findById(idArtist).get();

		movie.getActors().add(artist);
		artist.getMoviesActed().add(movie);

		artistRepository.save(artist);
		movieRepository.save(movie);

		model.addAttribute("movie", movie);
		model.addAttribute("artist", artist);
		model.addAttribute("artists", this.artistRepository.findAll());

		return "admin/addActorToMovie.html";
	}

	@GetMapping("/admin/removeActorFromMovie/{idArtist}/{idMovie}")
	public String removeActorFromMovie(@PathVariable("idArtist") Long idArtist, @PathVariable("idMovie") Long idMovie, Model model){

		Movie movie= movieRepository.findById(idMovie).get();
		Artist artist= artistRepository.findById(idArtist).get();

		movie.getActors().remove(artist);
		artist.getMoviesActed().remove(movie);

		artistRepository.save(artist);
		movieRepository.save(movie);

		model.addAttribute("movie", movie);
		model.addAttribute("artist", artist);
		model.addAttribute("artists", this.artistRepository.findAll());

		return "admin/removeActorFromMovie.html";
	}



	@GetMapping("/formNewReview/{idMovie}")
	public String formNewReview( @PathVariable("idMovie") Long idMovie,Model model) {

		//controllo che questo utente non abbia già scritto una recensione per questo film

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		Movie movie= movieRepository.findById(idMovie).get();
		if(movie.getRecensioni().stream().anyMatch(user.getRecensioniScritte()::contains))
		{
			//salvo il messggio d'errore e la recensione pre esstente
			List<Review> recensioni =	movie.getRecensioni();
			recensioni.retainAll(user.getRecensioniScritte());

			model.addAttribute("review", recensioni.get(0));
			model.addAttribute("messaggioErrore", "Hai già scritto una recensione per questo film");

			return "review.html"; 
		}else {
			model.addAttribute("review", new Review());
			model.addAttribute("movie", movie);

			return "formNewReview.html"; }
	}


	@PostMapping("/reviewRiepilogo/{idMovie}")
	public String newReview( @PathVariable("idMovie") Long idMovie, @ModelAttribute("review") Review review, Model model) {

		//le recensioni possono essere uguali, l'importante che ogni utente ne possa scrivere al max una per ogni film
		//ma quest ultimo controllo è compito di altre funzioni

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();
		Movie movie= movieRepository.findById(idMovie).get();

		reviewRepository.save(review);

		model.addAttribute("review", review);
		movie.getRecensioni().add(review);
		user.getRecensioniScritte().add(review);

		userRepository.save(user);
		movieRepository.save(movie);
		model.addAttribute("movie", movie);

		return "reviewRiepilogo.html"; 

	}

	@GetMapping("/reviewRiepilogo/{movieId}")
	public String showReviewFatta(@PathVariable("movieId") Long movieId, @ModelAttribute("review") Review review, Model model) {
		model.addAttribute("review");
		model.addAttribute("movie", this.movieRepository.findById(movieId));

		return "reviewRiepilogo.html";
	}

	@GetMapping("/review/{reviewId}/{movieId}")
	public String showReview(@PathVariable("movieId") Long movieId ,@PathVariable("reviewId") Long reviewId, Model model) {

		Review review = reviewRepository.findById(reviewId).get();
		Movie movie= movieRepository.findById(movieId).get();
		List<User> utenti= userService.getAllUsers();

		//cerco l'utente che ha scritto di questa recensione
		for(User utente : utenti) {
			if(movie.getRecensioni().stream().anyMatch(utente.getRecensioniScritte()::contains))
				if(utente.getRecensioniScritte().contains(review))
					model.addAttribute("ScrittoreRecensione", utente.getName());
		}


		//PARTE DA COPIARE PER CONTROLLER PAGINA MODIFICA RECENSIONE
		//controllo se chi sta visualizzando la recensione ne è proprietario per modifica e/o cancellazione
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			model.addAttribute("admin", "ad");
		}


		User user = credentials.getUser();

		if(user.getRecensioniScritte().contains(review))
		{
			//salvo il messggio di indicazione che l'utente è proprietario
			model.addAttribute("messaggioProp", "Sei il proprietario di questa recensione, ");
		}		 
		//   


		model.addAttribute("movie", movieRepository.findById(movieId).get());
		model.addAttribute("review", review);
		return "review.html";
	}

	@GetMapping("/reviewsDelFilm/{movieId}")
	public String reviewsDelFilm(@PathVariable("movieId") Long movieId, Model model) {

		Movie movie = movieRepository.findById(movieId).get();

		List<Review> recensioni =	movie.getRecensioni();

		model.addAttribute("reviews" ,recensioni);
		model.addAttribute("movie", movie);

		return "reviewsDelFilm.html";
	}

	@GetMapping("/admin/formFotoFilm/{movieId}")
	public String formFotoFilm(@PathVariable("movieId") Long movieId, Model model) {

		Movie movie = movieRepository.findById(movieId).get();

		model.addAttribute("movie", movie);

		return "admin/formFotoFilm.html";
	}



	@PostMapping("/admin/aggiungiFotoFilm/{movieId}")
	public String aggiungiFotoFilm(@PathVariable("movieId") Long movieId,
			@RequestParam("image") MultipartFile multipartFile,
			Model model) throws IOException {
		//mi arriva una foto da aggiungere alle foto di questo film che trovo così:
		Movie movie = movieRepository.findById(movieId).get();

		//salvo la foto nella directory "movie-photos/'l'id del film'" di creare/aggiungere alla directory se ne occupa utile file upload
		String uploadDir = "movie-photos/" + movie.getId();
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

		//aggiungo un numero al nome altrimenti file con lo stesso nome potrebbero dare problemi in ricerca sostituzione ecc.
		fileName = Math.floor(Math.random() * 10)+ fileName ; 

		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		Photo foto=new Photo();
		String prova =  System.getProperty("user.dir");
		prova.replace("\\", "/");
		String absolute = prova.concat("/movie-photos/" + movie.getId() + "/" + fileName);	
		foto.setRelativePath("/movie-photos/" + movie.getId() + "/" + fileName);

		foto.setFileName(absolute);

		//salvo foto nel db
		this.photoRepository.save(foto);

		//aggiungo foto alla lista di foto del film
		movie.getFoto().add(foto);

		//salvo il film nel db
		this.movieRepository.save(movie);
		model.addAttribute("movie", movie);
		model.addAttribute("foto_aggiunta", foto);

		return "admin/aggiungiFotoFilm.html";
	}



	//metodi per eliminazione 
	@GetMapping("/admin/confermaEliminaFilm/{movieId}")
	public String confermaEliminaFilm(Model model, @PathVariable("movieId") Long movieId) {
		model.addAttribute("movie", this.movieRepository.findById(movieId).get());
		return "admin/confermaEliminaFilm.html";
	}


	@GetMapping("/admin/EliminaFilm/{movieId}")
	public String EliminaFilm(Model model, @PathVariable("movieId") Long movieId) {
		Movie movie= this.movieRepository.findById(movieId).get();
		try {
			FileUploadUtil.deleteFile(movie);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Artist> artisti = 	movie.getActors();
		for(Artist artista : artisti) {
			artista.getMoviesActed().remove(movie);
			this.artistRepository.save(artista);

		}

		if(movie.getDirector()!= null) movie.getDirector().getMoviesDirected().remove(movie);

		movie.getActors().removeAll(movie.getActors());
		movie.getRecensioni().removeAll(movie.getRecensioni());
		movie.getFoto().removeAll(movie.getFoto());

		this.movieRepository.delete(movie );


		return "admin/filmEliminato.html";
	}



	@GetMapping("/admin/filmEliminato")
	public String filmEliminato() {

		return "admin/filmEliminato.html";
	}




	@GetMapping("/admin/confermaCancellaReview/{reviewId}/{movieId}")
	public String confermaCancellaReview(Model model, @PathVariable("reviewId") Long reviewId, @PathVariable("movieId") Long movieId) {
		model.addAttribute("movie", this.movieRepository.findById(movieId).get());
		model.addAttribute("review", this.reviewRepository.findById(reviewId).get());
		return "admin/confermaCancellaReview.html";
	}


	@GetMapping("/admin/EliminaReview/{movieId}/{reviewId}")
	public String EliminaReview(Model model, @PathVariable("movieId") Long movieId , @PathVariable("reviewId") Long reviewId) {
		Review review = this.reviewRepository.findById(reviewId).get();
		Movie movie= this.movieRepository.findById(movieId).get();


		movie.getRecensioni().remove(review);

		List<User> utenti= userService.getAllUsers();


		for(User utente : utenti) {

			if(utente.getRecensioniScritte().contains(review))
				utente.getRecensioniScritte().remove(review);
			this.userRepository.save(utente);
		}


		this.movieRepository.save(movie );
		this.reviewRepository.delete(review);

		return "admin/reviewEliminata.html";
	}



	@GetMapping("/admin/reviewEliminata")
	public String reviewEliminata() {

		return "admin/reviewEliminata.html";
	}

	//FINE metodi per eliminazione 


	//metodi per modifica
	@GetMapping("/admin/modificaFilm/{movieId}")
	public String modificaFilm(Model model, @PathVariable("movieId") Long movieId) {
		model.addAttribute("movie", new Movie());
		model.addAttribute("movieog", this.movieRepository.findById(movieId).get());
		return "admin/formModificaFilm" ;

	}


	@PostMapping("/admin/formModificaFilm/{movieId}")
	public String formModificaFilm(@ModelAttribute("movie") Movie movie, Model model, @PathVariable("movieId") Long movieId ) {

		if (!movieRepository.existsByTitleAndYear(movie.getTitle(), movie.getYear())) {
			Movie moviee= this.movieRepository.findById(movieId).get();
			if(  !(movie.getTitle()== null)   )
				moviee.setTitle(movie.getTitle());
			if(movie.getYear()!=null)
				moviee.setYear(movie.getYear());
			this.movieRepository.save(moviee);

			model.addAttribute("movie", moviee);
			return "movie.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo film esiste già");
			return "movie.html";
		}
	}

	//FINE metodi per modifica
*/
}



