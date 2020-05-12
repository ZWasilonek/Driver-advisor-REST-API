package pl.coderslab.hero;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/hero")
public class HeroController {

    private final HeroRepository heroRepository;


    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }


    @RequestMapping("/list")
    public String listPage(Model model, Pageable pageable) {
        Page<Hero> page = heroRepository.findAll(pageable);
        model.addAttribute("page", page);
        return "hero/list-page";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable long id) {

        if (heroRepository.findById(id).isPresent()) {
            Hero hero = heroRepository.findById(id).get();
            heroRepository.delete(hero);
        }
        return "redirect:/hero/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("hero", new Hero());
        return "hero/add";
    }

    @PostMapping("/add")
    public String addPersonPerform(@ModelAttribute @Valid Hero hero, BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "hero/add";
        }

        redirectAttributes.addFlashAttribute("message", "Hero dodany prawidłowo ");

        heroRepository.save(hero);
        return "redirect:/hero/list";
    }

}
