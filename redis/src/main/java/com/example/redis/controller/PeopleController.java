package com.example.redis.controller;

import com.example.redis.pojo.People;
import com.example.redis.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 金喆
 */

@Controller
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @GetMapping("/show")
    @ResponseBody
    public String select(Integer id , Model model)
    {
        People people = peopleService.findPeopleById(id);
        model.addAttribute("people" , people);

        return people.toString();
    }

}
