package br.com.projeto_poo.controllers;

import java.util.ArrayList;
import java.util.Iterator;
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
	
    //Cadastrar usuario
    @PostMapping(value="/cadastrar")
    @ResponseBody
    public ResponseEntity<Login> cadastrar(@RequestBody Login login) {
    	List<Login> logins = loginrepository.buscarUsuario(login.getUsuario());
    	System.out.println(login.getUsuario()+ " "+login.getSenha());
		if (logins.size()==0) {
			if (login.getCnpj().length()==14) {
				loginrepository.save(login);
				return new ResponseEntity<Login>(login, HttpStatus.OK);
			}else {
				return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
			}
		}else {
			return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
		}
    }
    
    //Buscar usuario
    @PostMapping(value = "/buscarporusuario")
    @ResponseBody
    public ResponseEntity<?> buscarPorNome(@RequestParam String usuario){
    	List<Login> logins = loginrepository.buscarUsuario(usuario);
    	if (logins.size()!=0) {
    		return new ResponseEntity<List<Login>>(logins, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>("LOGIN NÃO ENCONTRADO", HttpStatus.OK);
    	}
    }
    
    //Verificar produto
    @PostMapping(value = "/verificarproduto")
    @ResponseBody
    public ResponseEntity<?> verificarProduto(@RequestParam String nomeProduto, Long idlogin){
    	List<Estoque> estoque = estoquerepository.buscarProduto(nomeProduto, idlogin);
    	if (estoque.size()!= 0) {
    		return new ResponseEntity<List<Estoque>>(estoque, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>("PRODUTO NÃO ENCOTRADO.", HttpStatus.OK);
    	}
    }
    
    //Apagar login
    @DeleteMapping(value="/apagarlogin")
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
    
    //Mostrar login
    @GetMapping(value="/mostrarlogin")
    @ResponseBody
    public ResponseEntity<?> mostrarLogin(){
    	List<Login> logins = loginrepository.findAll();
    	if (logins.size()!=0) {
    		return new ResponseEntity<List<Login>>(logins, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>("LOGIN NÃO ENCONTRADO", HttpStatus.OK);
    	}
    }
    
    //Salvar produto
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
    
    //Deletar produto
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
    
    //Mostrar produtos
    @GetMapping(value="/mostrarprodutos")
    @ResponseBody
    public ResponseEntity<List<Estoque>> mostrarProdutos(){
    	List<Estoque> logins = estoquerepository.findAll();
    	return new ResponseEntity<List<Estoque>>(logins, HttpStatus.OK);
    }
    
    //Validar login
    @PostMapping(value = "/validarlogin")
    @ResponseBody
	public ResponseEntity<?> validarLogin(@RequestBody Login login) {
		List<Login> logins = loginrepository.buscarUsuario(login.getUsuario());
		if (logins.size() == 0) {
			System.out.println("LOGIN INVÁLIDO.");
			logins.clear();
			return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
		}
		if (logins.size() != 0) {
			for (int i = 0; i < logins.size(); i++) {
				System.out.println(logins.get(i).getUsuario()+" "+ logins.get(i).getSenha());
				if (logins.get(i).getUsuario().equals(login.getUsuario()) && logins.get(i).getSenha().equals(login.getSenha())) {
					System.out.println("LOGIN VALIDADO.");
					return new ResponseEntity<Login>(logins.get(i), HttpStatus.OK);
				}
			}
		}
		System.out.println("LOGIN INVÁLIDO.");
		logins.clear();
		return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
	}

    //Recuperar senha
    @PostMapping(value="/recuperarsenha")
    @ResponseBody
    public ResponseEntity<?> recuperarSenha(@RequestBody Login login){
    	if (login.getCnpj().length()!=14) {
    		return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
    	}
    	System.out.println(login.getCnpj());
    	List<Login> logins = loginrepository.buscarCNPJ(login.getCnpj());
    	for(int i=0; i<logins.size();i++) {
    		System.out.println(logins.get(i).getCnpj());
    		if (logins.get(i).getUsuario().equals(login.getUsuario())) {
    			logins.get(i).setSenha(login.getSenha());
    			loginrepository.save(logins.get(i));
    			return new ResponseEntity<Login>(logins.get(i), HttpStatus.OK);
    		}
    	}
    	return new ResponseEntity<Login>(login, HttpStatus.NOT_ACCEPTABLE);
    }
}