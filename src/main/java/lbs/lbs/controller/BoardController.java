package lbs.lbs.controller;

import lbs.lbs.dao.BoardDAO_jpa;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

@Controller
@Setter
public class BoardController{

    @Autowired
    private BoardDAO_jpa boardDAO_jpa;

    public static void printMsg(){
        System.out.println("테스트3");
    }
}