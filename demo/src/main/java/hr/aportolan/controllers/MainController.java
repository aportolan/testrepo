package hr.aportolan.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/")
	public String mainMapping() {
		return "/static/app/index.html";
	}
}
