package edu.agh.zp.controller;

import edu.agh.zp.objects.CitizenEntity;
import edu.agh.zp.objects.Log;
import edu.agh.zp.objects.Role;
import edu.agh.zp.repositories.LogRepository;
import edu.agh.zp.repositories.RoleRepository;
import edu.agh.zp.services.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping (value={"/signup"})
public class SignUpController {
	@Autowired
	private final CitizenService cS;
	@Autowired
	private RoleRepository rR;
	@Autowired
	private LogRepository lR;

	public SignUpController(CitizenService cS){
		this.cS = cS;
	}

	@GetMapping (value = {""})
	public ModelAndView index() {
		String viewName = "User/signup";
		Map<String, Object> model = new HashMap<>();
		model.put("user", new CitizenEntity());
		return new ModelAndView(viewName, model);
	}

	@PostMapping("")
	public ModelAndView submitRegister(@Valid @ModelAttribute("user") CitizenEntity citizen, BindingResult res){
		if( res.hasErrors()){
			return new ModelAndView("User/signup.html");
		}else{
			Optional<CitizenEntity> exists = cS.findByEmail(citizen.getEmail());
			if(exists.isPresent()){
				ModelAndView mv = new ModelAndView("User/signup.html");
				mv.addObject("error", "Obywatel z tym adresem e-mail już istnieje.");
				lR.save(Log.failedSignInOrSignUp(Log.Operation.ADD, "Sign Up - User with this email already exists"));
				return mv;
			}
			exists = cS.findByPesel(citizen.getPesel());
			if(exists.isPresent()){
				ModelAndView mv = new ModelAndView("User/signup.html");
				mv.addObject("error", "Obywatel z tym numerem PESEL już istnieje.");
				lR.save(Log.failedSignInOrSignUp(Log.Operation.ADD, "Sign Up - User with this PESEL already exists"));
				return mv;
			}
			exists = cS.findByIdNumer(citizen.getIdNumber());
			if(exists.isPresent()){
				ModelAndView mv = new ModelAndView("User/signup.html");
				mv.addObject("error", "Obywatel z tym numerem dowodu osobistego już istnieje.");
				lR.save(Log.failedSignInOrSignUp(Log.Operation.ADD, "Sign Up - User with this Citizen ID already exists"));
				return mv;
			} else {
				String salt = BCrypt.gensalt();
				citizen.setPassword(BCrypt.hashpw(citizen.getPassword(), salt));
				citizen.setRepeatPassword(BCrypt.hashpw(citizen.getRepeatPassword(), salt));
				List<Role> roles = new ArrayList <>();
				Optional<Role> temp = rR.findByName("ROLE_USER");
				temp.ifPresent(roles::add);
				citizen.setRoles(roles);

				if(cS.create(citizen).isPresent()) {
					lR.save(Log.successSignInOrSignUp(Log.Operation.ADD, "Sign Up - User created successfully", citizen));
				}else{
					lR.save(Log.failedSignInOrSignUp(Log.Operation.ADD, "Sign Up - User creation failed"));
				}
			}
		}
		RedirectView redirect = new RedirectView();
		redirect.setUrl("");
		return new ModelAndView(redirect);
	}
}
