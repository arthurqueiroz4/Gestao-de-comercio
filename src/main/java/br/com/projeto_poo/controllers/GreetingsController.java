package br.com.projeto_poo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto_poo.model.Login;
import br.com.projeto_poo.model.LoginRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	@Autowired
	private LoginRepository loginrepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    @GetMapping(value = "/tentando/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String tentando(@PathVariable String name) {
        return "Eu to tentando, "+name;
    }
    
    @PostMapping(value="/salvar")
    @ResponseBody
    public ResponseEntity<Login> salvar(@RequestBody Login login) {
    	
    	Login log_in = loginrepository.save(login);
    	
    	return new ResponseEntity<Login>(log_in, HttpStatus.OK);
    }
    
}
