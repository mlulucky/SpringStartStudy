package com.acorn.springstartstudy.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 스프링 웹컨테이너 에서 관리하는 컨트롤러 객체
public class L01SumController {
    @GetMapping("/sum.do") // 동적페이지 주소
    public void sum(int a, int b, Model model) { // url 의 파라미터를 받는 방법  // 모델은 뷰와 컨트롤러를 연결
        // void 로 하면 자동으로 렌더링하는 주소가 생긴다.
        // => req.getRequestDispatcher("/templates/sum.html").forward(req,resp) // sum.html 이라는 html 을 자동으로 렌더링 // 이 코드가 써져있는 것 과 같다.
        model.addAttribute("a", a);
        model.addAttribute("b", b);
        // == req.setAttribute("a",a)
        // model.addAttribute 타임리프 문서에서 a, b 라는 변수르 사용 가능
    }


    // 동적 페이지
    // 🍎 실제 int a, int b 에 형체
    // 맵핑한 url 주소와
    @GetMapping("/mult.do")
    public String multiply(@RequestParam(name = "a", required = true) int a,
                           // void 가 아니라 반환하는 타입이 문자열이면 html 을 렌더링?
                           // required=true 파라미터 a 가 없으면 400 에러
                           @RequestParam(name = "b", defaultValue = "0") int b, // 파라미터의 이름은 a. 파라미터에 옵션을 줄 수 있다. // @RequestParam 생략가능
                           // required = false : 기본형으로 파라미터를 받을때는 required = false 일 수 없다 왜??
                           // 자바의 기본형은 null 이 될수없어서
                           // 기본형으로 파라미터를 받고 싶으면 defaultValue 으로 기본값을 작성해야 한다. => 자동으로 required = false 가 된다.
                           Model model) {
        // html 에서 파라미터를 받고싶으면! Model 속성 추가!!
        model.addAttribute("a", a);
        model.addAttribute("b", b);
        return "/multiply"; // => /templates/multiply.html 을 렌더링한다.
    }


//    public void sum(HttpServletRequest req){ // HttpServletRequest req : 파라미터를 받으려면
//        String a=req.getParameter("a");
//    }
}
