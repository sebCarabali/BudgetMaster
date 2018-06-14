package de.deadlocker8.budgetmaster.controller;

import de.deadlocker8.budgetmaster.authentication.User;
import de.deadlocker8.budgetmaster.authentication.UserRepository;
import de.deadlocker8.budgetmaster.entities.Settings;
import de.deadlocker8.budgetmaster.repositories.SettingsRepository;
import de.deadlocker8.budgetmaster.services.HelpersService;
import de.deadlocker8.budgetmaster.utils.LanguageType;
import de.deadlocker8.budgetmaster.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SettingsController extends BaseController
{
	@Autowired
	private SettingsRepository settingsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HelpersService helpers;

	@RequestMapping("/settings")
	public String settings(Model model)
	{
		model.addAttribute("settings", settingsRepository.findOne(0));
		return "settings";
	}

	@RequestMapping(value = "/settings/save", method = RequestMethod.POST)
	public String post(Model model, @ModelAttribute("Settings") Settings settings, BindingResult bindingResult,
					   @RequestParam(value = "password") String password,
					   @RequestParam(value = "languageType") String languageType)
	{
		if(password == null ||password.equals(""))
		{
			bindingResult.addError(new ObjectError("password", Strings.WARNING_SETTINGS_PASSWORD_EMPTY));
		}
		else if(password.length() < 3)
		{
			bindingResult.addError(new ObjectError("password", Strings.WARNING_SETTINGS_PASSWORD_LENGTH));
		}

		settings.setLanguage(LanguageType.fromName(languageType));

		if(bindingResult.hasErrors())
		{
			model.addAttribute("error", bindingResult);
			model.addAttribute("settings", settings);
			return "settings";
		}
		else
		{
			if(!password.equals("•••••"))
			{
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				String encryptedPassword = bCryptPasswordEncoder.encode(password);
				User user = userRepository.findByName("Default");
				user.setPassword(encryptedPassword);
				userRepository.save(user);
			}

			settingsRepository.delete(0);
			settingsRepository.save(settings);
		}

		return "redirect:/settings";
	}

	@RequestMapping("/settings/database/requestImport")
	public String requestImportDatabase(Model model)
	{
		return "settings";
	}

	@RequestMapping("/settings/database/requestExport")
	public String requestExportDatabase(Model model)
	{
		return "settings";
	}

	@RequestMapping("/settings/database/requestDelete")
	public String requestDeleteDatabase(Model model)
	{
		return "settings";
	}
}