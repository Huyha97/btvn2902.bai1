package cg.controller;

import cg.model.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import cg.service.IHumanService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/human")
public class HumanController {

    @Autowired
private IHumanService iHumanService;


    @GetMapping
    public ModelAndView showAll( @RequestParam(value = "search") Optional<String> search,
                                 @SortDefault(sort = {"name"}, direction = Sort.Direction.ASC)
                                 @PageableDefault(value = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("list");
        Page<Human> humans;
        if (search.isPresent()) {
          humans= iHumanService.findByName(pageable, search.get());
            modelAndView.addObject("search", search.get());
        } else {
            humans = iHumanService.findAll(pageable);
        }
        modelAndView.addObject("humans", humans);
        return modelAndView;
    }


    @GetMapping
    public ModelAndView showAllDesc( @RequestParam(value = "search") Optional<String> search,
                                 @SortDefault(sort = {"name"}, direction = Sort.Direction.DESC)
                                 @PageableDefault(value = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("list");
        Page<Human> humans;
        if (search.isPresent()) {
            humans= iHumanService.findByName(pageable, search.get());
            modelAndView.addObject("search", search.get());
        } else {
            humans = iHumanService.findAll(pageable);
        }
        modelAndView.addObject("humans", humans);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Human> student = iHumanService.findById(id);
        student.ifPresent(value -> modelAndView.addObject("human", value));
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PageableDefault(value = 3) Pageable pageable, @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("list");
        iHumanService.delete(id);
        Page<Human> humans = iHumanService.findAll(pageable);
        modelAndView.addObject("humans", humans);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("human", new Human());
        return modelAndView;
    }

    @PostMapping("/createPost")
    public String createHuman(@Valid @ModelAttribute("human") Human human, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("create");
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("human" , human);
            return "create";
        }
        iHumanService.save(human);
        return "redirect:/human";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("human   ", iHumanService.findById(id));
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editHuman(@PageableDefault(value = 3) Pageable pageable,
                                    @Valid @ModelAttribute("student") Human human, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("edit");
        return getModelAndView(pageable, modelAndView, human, bindingResult);
    }

    private ModelAndView getModelAndView(@PageableDefault(3) Pageable pageable, ModelAndView modelAndView,
                                         @ModelAttribute("student") @Valid Human human, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("human", human);
            return modelAndView;
        }
        iHumanService.save(human);
        ModelAndView viewAll = new ModelAndView("list");
        Page<Human> humans = iHumanService.findAll(pageable);
        viewAll.addObject("humans", humans);
        return viewAll;
    }





}
