package de.deadlocker8.budgetmaster.migration;

import de.deadlocker8.budgetmaster.authentication.UserService;
import de.deadlocker8.budgetmaster.controller.BaseController;
import de.deadlocker8.budgetmaster.settings.SettingsService;
import de.deadlocker8.budgetmaster.utils.Mappings;
import de.deadlocker8.budgetmaster.utils.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(Mappings.MIGRATION)
public class MigrationController extends BaseController
{
	private static class ModelAttributes
	{
		public static final String ERROR = "error";
		public static final String MIGRATION_SETTINGS = "migrationSettings";
		public static final String STATUS = "status";
	}

	private static class ReturnValues
	{
		public static final String MIGRATION_SETTINGS = "migration/migration";
		public static final String REDIRECT_STATUS = "redirect:migration/status";
		public static final String STATUS = "migration/status";
		public static final String STATUS_FRAGMENT = "migration/statusFragment";
	}

	private final MigrationService migrationService;
	private final SettingsService settingsService;
	private final UserService userService;

	@Autowired
	public MigrationController(MigrationService migrationService, SettingsService settingsService, UserService userService)
	{
		this.migrationService = migrationService;
		this.settingsService = settingsService;
		this.userService = userService;
	}

	@GetMapping("/cancel")
	public String cancel(HttpServletRequest request)
	{
		settingsService.updateMigrationDeclined(true);
		return "redirect:" + request.getHeader("Referer");
	}

	@GetMapping
	public String migrate(Model model)
	{
		model.addAttribute(ModelAttributes.MIGRATION_SETTINGS, migrationService.getPrefilledMigrationSettings());
		return ReturnValues.MIGRATION_SETTINGS;
	}

	@PostMapping
	public String post(Model model,
					   @ModelAttribute("MigrationSettings") @Valid MigrationSettings migrationSettings, BindingResult bindingResult,
					   @RequestParam(value = "verificationPassword") String verificationPassword)
	{
		final MigrationSettingsValidator migrationSettingsValidator = new MigrationSettingsValidator();
		migrationSettingsValidator.validate(migrationSettings, bindingResult);

		final boolean isVerificationPasswordValid = userService.isPasswordValid(verificationPassword);
		if(!isVerificationPasswordValid)
		{
			final FieldError verificationError = new FieldError("MigrationSettings", "verificationPassword", verificationPassword, false, new String[]{Strings.WARNING_WRONG_MIGRATION_VERIFICATION_PASSWORD}, null, Strings.WARNING_WRONG_MIGRATION_VERIFICATION_PASSWORD);
			bindingResult.addError(verificationError);
		}

		model.addAttribute(ModelAttributes.MIGRATION_SETTINGS, migrationSettings);

		if(bindingResult.hasErrors())
		{
			model.addAttribute(ModelAttributes.ERROR, bindingResult);
			return ReturnValues.MIGRATION_SETTINGS;
		}

		final MigrationArguments migrationArguments = new MigrationArguments.MigrationArgumentBuilder()
				.withSourceUrl(migrationService.getDatabaseFromPreviousVersionPathWithoutExtension().toString())
				.withDestinationUrl(migrationSettings.hostname(), migrationSettings.port(), migrationSettings.databaseName())
				.withDestinationCredentials(migrationSettings.username(), migrationSettings.password())
				.build();
		migrationService.startMigration(migrationArguments);

		return ReturnValues.REDIRECT_STATUS;
	}

	@GetMapping("/status")
	public String status(Model model)
	{
		model.addAttribute(ModelAttributes.STATUS, migrationService.getMigrationStatus());
		return ReturnValues.STATUS;
	}

	@GetMapping("/getStatus")
	public String getMigrationStatus(Model model)
	{
		model.addAttribute(ModelAttributes.STATUS, migrationService.getMigrationStatus());
		return ReturnValues.STATUS_FRAGMENT;
	}
}