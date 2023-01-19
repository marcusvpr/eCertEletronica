package com.mpxds.basic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mpxds.basic.model.MpUser;
import com.mpxds.basic.service.MpSecurityService;
import com.mpxds.basic.service.MpUserService;
import com.mpxds.basic.validator.MpUserValidator;

@Controller
public class MpUserController {
	//
    @Autowired
    private MpUserService userService;

    @Autowired
    private MpSecurityService securityService;

    @Autowired
    private MpUserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
    	//
        model.addAttribute("userForm", new MpUser());
        //
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") MpUser userForm,
    												BindingResult bindingResult, Model model) {
    	//
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        //
        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        //
//      return "redirect:/welcome";
        return "redirect:/Dashboard";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	//
        if (error != null)
            model.addAttribute("error", "Usuário e senha inválidos.");

        if (logout != null)
            model.addAttribute("message", "Sucesso no acesso.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	//
        return "welcome";
    }
}
