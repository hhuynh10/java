package com.codingdojo.language.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.language.models.Language;
import com.codingdojo.language.services.LanguageService;

@Controller
public class LanguageController {
	@Autowired
	LanguageService languageService;
	
	@GetMapping("/")
	public String dashboard() {
		return "redirect:/languages";
	}
	
	@GetMapping("/languages")
	public String allExpenses(@ModelAttribute("language") Language language, Model model) {
		List<Language> languages = languageService.allLanguages();
		model.addAttribute("languages", languages);
		return "dashboard.jsp";
	}
	
	@PostMapping("/languages")
    public String create(@Valid @ModelAttribute("language") Language language, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	List<Language> languages = languageService.allLanguages();
    		model.addAttribute("languages", languages);
            return "dashboard.jsp";
        } else {
        	languageService.createLanguage(language);
            return "redirect:/languages";
        }
    }
	
	@DeleteMapping("/languages/{id}/delete")
    public String destroy(@PathVariable("id") Long id) {
		languageService.deleteLanguage(id);
        return "redirect:/languages";
    }
	
	@GetMapping("/languages/{id}/view")
	public String view(@PathVariable("id") Long id, Model model) {
		Language language = languageService.findLanguage(id);
        model.addAttribute("language", language);
        return "view.jsp";
	}
	
	@GetMapping("/languages/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
		Language language = languageService.findLanguage(id);
        model.addAttribute("language", language);
        return "edit.jsp";
	}
	
	@PutMapping("/languages/{id}")
    public String update(@Valid @ModelAttribute("language") Language language, BindingResult result) {
        if (result.hasErrors()) {
            return "edit.jsp";
        } else {
        	languageService.updateLanguage(language);
            return "redirect:/languages";
        }
    }
}
