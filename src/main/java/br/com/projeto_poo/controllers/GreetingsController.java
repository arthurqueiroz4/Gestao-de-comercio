package br.com.projeto_poo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto_poo.model.Estoque;
import br.com.projeto_poo.model.EstoqueRepository;
import br.com.projeto_poo.model.Login;
import br.com.projeto_poo.model.LoginRepository;

@RestController
public class GreetingsController {
	@Autowired
	private LoginRepository loginrepository;
	@Autowired
	private EstoqueRepository estoquerepository;
	
  
    @GetMapping(value = "/tentando/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String tentando(@PathVariable String name) {
        return "Eu to tentando, "+name;
    }
    
    @PostMapping(value="/salvarlogin")
    @ResponseBody
    public ResponseEntity<?> salvarlogin(@RequestBody Login login) {
    	List<Login> logins = loginrepository.buscarUsuario(login.getUsuario());
    	System.out.println(login.getUsuario()+ " "+login.getSenha());
		if (logins.size()==0) {
			Login log_in = loginrepository.save(login);
			return new ResponseEntity<Login>(log_in, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("USUARIO JA CADASTRADO", HttpStatus.OK);
		}
    }
    @PostMapping(value = "/buscarporusuario")
    @ResponseBody
    public ResponseEntity<List<Login>> buscarPorNome(@RequestParam String usuario){
    	List<Login> logins = loginrepository.buscarUsuario(usuario);
    	return new ResponseEntity<List<Login>>(logins, HttpStatus.OK);
    }
    @PostMapping(value = "/verificarproduto")
    @ResponseBody
    public ResponseEntity<List<Estoque>> verificarProduto(@RequestParam String nomeProduto, Long idlogin){
    	List<Estoque> estoque = estoquerepository.buscarProduto(nomeProduto, idlogin);
    	return new ResponseEntity<List<Estoque>>(estoque, HttpStatus.OK);
    }
    @DeleteMapping(value="/apagalogin")
    @ResponseBody
    public ResponseEntity<String> apagaLogin(@RequestParam Long id){
    	if (id == null) {
    		return new ResponseEntity<String>("ID NÃO INFORMADO.", HttpStatus.OK);
    	}
    	if(!loginrepository.existsById(id)){
    		return new ResponseEntity<String>("ID INEXISTENTE", HttpStatus.OK);
    	}
    	loginrepository.deleteById(id);
    	return new ResponseEntity<String>("LOGIN DELETADO.", HttpStatus.OK);
    }
    
    @GetMapping(value="/mostrarlogin")
    @ResponseBody
    public ResponseEntity<List<Login>> mostrarLogin(){
    	List<Login> logins = loginrepository.findAll();
    	return new ResponseEntity<List<Login>>(logins, HttpStatus.OK);
    }
    @PostMapping(value="/salvarproduto")
    @ResponseBody
    public ResponseEntity<?> salvarestoque(@RequestBody Estoque estoque) {
    	List<Long> ids = new ArrayList<>();
    	ids.add(estoque.getId_login());
    	List<Login> logins = loginrepository.findAllById(ids);
    	if (logins.size()!=0) {
    		Estoque est_oque = estoquerepository.save(estoque);
        	
        	return new ResponseEntity<Estoque>(est_oque, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>("LOGIN NÃO CADASTRADO", HttpStatus.OK);
    	}
    }
    @DeleteMapping(value="/deletarproduto")
    @ResponseBody
    public ResponseEntity<String> apagaProduto(@RequestParam Long id){
    	if (id == null) {
    		return new ResponseEntity<String>("ID NÃO INFORMADO.", HttpStatus.OK);
    	}
    	if(!estoquerepository.existsById(id)){
    		return new ResponseEntity<String>("ID INEXISTENTE", HttpStatus.OK);
    	}
    	estoquerepository.deleteById(id);
    	return new ResponseEntity<String>("PRODUTO DELETADO.", HttpStatus.OK);
    }
    @GetMapping(value="/mostrarprodutos")
    @ResponseBody
    public ResponseEntity<List<Estoque>> mostrarProdutos(){
    	List<Estoque> logins = estoquerepository.findAll();
    	return new ResponseEntity<List<Estoque>>(logins, HttpStatus.OK);
    }
    
    @GetMapping(value = "/validarlogin")
    @ResponseBody
    public ResponseEntity<?> validarLogin(@RequestBody Login login) {
    	List<Login> logins= loginrepository.buscarUsuario(login.getUsuario());
		if (logins.size() == 0)
			return new ResponseEntity<String>("LOGIN INVÁLIDO.", HttpStatus.OK);
		if (logins.size() != 0) {
			for (int i = 0; i < logins.size(); i++) {
				if (logins.get(i).getSenha().equals(login.getSenha())) {
					
					return new ResponseEntity<String>("LOGIN VALIDADO.", HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>("LOGIN INVÁLIDO.", HttpStatus.OK);
    }
}