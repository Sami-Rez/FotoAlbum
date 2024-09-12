package at.spengergasse.FotoAlbum.presentation.www;

import at.spengergasse.FotoAlbum.domain.Person;
import at.spengergasse.FotoAlbum.service.PersonDoesNotExistException;
import at.spengergasse.FotoAlbum.service.PersonService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Slf4j

@Controller
@RequestMapping(PersonController.BASE_ROUTE)
public class PersonController implements RedirectForwardSupport {

    protected static final String BASE_ROUTE="/persons";
    private final PersonService personService;

    @GetMapping
    public String getPersons(Model model) {
        List<Person> persons = personService.getPersons();
        model.addAttribute("persons", persons);
        return "persons/list";
    }

    @GetMapping("/new")
    public ModelAndView showNewPersonForm() {
        var mav = new ModelAndView();
        mav.addObject("form", CreatePersonForm.create());
        mav.setViewName("persons/new");
        return mav;
    }

    @PostMapping("/new")
    public String handleNewPersonFormSubmission(@Valid @ModelAttribute(name = "form") CreatePersonForm form,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "persons/new";
        }

        personService.register(form.username(), form.password(), form.firstName(), form.lastName(), form.nickname());
        return redirect(BASE_ROUTE);
    }

    @GetMapping("/show/{key}")
    public String showPerson(@PathVariable String key, Model model) {
        return personService.getPersonByKey(key)
                .map(person -> model.addAttribute("person", person))
                .map(_ -> "persons/show")
                .orElseThrow(() -> PersonDoesNotExistException.forKey(key));
    }

    @GetMapping("/edit/{key}")
    public String showEditPersonForm(@PathVariable String key, Model model) {
        return personService.getPersonByKey(key)
                .map(EditPersonForm::create)
                .map(form -> model.addAttribute("form", form))
                .map(_ -> "persons/edit")
                .orElse(redirect(BASE_ROUTE));
    }

    @PostMapping("/edit/{key}")
    public String handleNewPersonFormSubmission(@PathVariable String key, @Valid @ModelAttribute(name = "form") EditPersonForm form,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "persons/edit";
        }

        personService.updatePerson(key, form.firstName(), form.lastName(), form.nickname());
        return redirect(BASE_ROUTE);
    }

    @GetMapping("/delete/{key}")
    public String deletePerson(@PathVariable String key) {
        personService.deletePersonByKey(key);
        return redirect(BASE_ROUTE);
    }

    @ExceptionHandler(PersonDoesNotExistException.class)
    public String handlePersonDoesNotExistException(PersonDoesNotExistException ex, RedirectAttributes redirectAttributes) {
        log.warn("{} occured for key: {}", ex.getClass().getSimpleName(), ex.getKey());
        redirectAttributes.addFlashAttribute("nonExistingKey", ex.getKey());
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return redirect(BASE_ROUTE);
    }
}
