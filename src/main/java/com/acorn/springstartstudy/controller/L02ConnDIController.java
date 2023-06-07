package com.acorn.springstartstudy.controller;

import com.acorn.springstartstudy.dto.UsersDto;
import com.acorn.springstartstudy.mapper.UsersMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller // 컨트롤러  설정 // 우리가 만든 MVC. 경로들을 볼수있다.
@RequestMapping("/users") // 하위에 있는 모든 동적페이지에 /users 가 붙는다. (/users 하위에 있는것처럼)
public class L02ConnDIController { // DI : 의존성 주입
    // spring 설정으로 datasource 를 정의하면 컨테이너에 Connection 객체가 존재한다.(java.sql.Connection 을 반환)
    // @Autowired // 주입받을 객체(ex dataSource)가 컨테이너 안에 부모타입이 여러개 있을때 오류가 발생할 수 있다. (권장X)
    DataSource dataSource; // 데이터소스를 주입받는다. // 컨테이너가 관리하는 객체들을 주입받을 수 있다. // 어플리케이션에서 정의한 데이터 소스
    UsersMapper usersMapper; // Mybatis 의 session-factory 가 생성한 객체가 주입된다. // @AutoWired 쓰거나 생성자를 쓰거나
    public L02ConnDIController(DataSource dataSource, UsersMapper usersMapper) { // == 🍎@AutoWired 와 동일(권장)
        this.dataSource = dataSource;
        this.usersMapper = usersMapper;
    }


    // 🍏void 타입 맵핑한 주소와 동일한 주소로 기본경로( /templates )를 렌더
//    @GetMapping("/signup.do")
//    public void insertOne(UsersDto user){
//        //🍏 == return signup  (== "/templates/singup.html")
//    }

    // 🍎기본경로 /templates 가 아닌 /templates/users 폴더안에 파일을 렌더하려면
    // String 반환타입으로 설정

    @ResponseBody // json 형식 반환
    @GetMapping("/idCheck.do") // 아이디체크 동적페이지 - ajax 비동기 통신
    public String idCheck(String uId){
        UsersDto user=null;
        try{
            user=usersMapper.findByUId(uId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{\"idCheck\":" + (user != null) + "}";
    }


    @GetMapping("/signup.do")
    public void insertForm(){ // 처음 정보 입력시 파라미터 값은 넘어가기 전
//        return "/users/signup";
        // == "/templates/users/signup.html"
        // 자동으로 /templates 가 붙는다 , .html 생략가능
        // /templates/signup.html
    }

    @PostMapping("/signup.do")
    public String insertAction(UsersDto user){
        int insert=0;
        insert=usersMapper.insertOne(user);
        if(insert>0){
            return "redirect:/users/mybatisList.do";
        }else{
            return "redirect:/users/signup.do";
        }
    }

    @GetMapping("/delete.do")
    public void deleteForm(@RequestParam(name="u_id") String uId) {
        UsersDto user=usersMapper.findByUId(uId);
//        System.out.println("user = " + user); // 출력체크
    }

    @PostMapping("/delete.do")
    public String deleteAction(@RequestParam(name="u_id") String uId, // 파라미터 u_id 를 uId 이름으로 맵핑
                               String pw){ //  파라미터 pw
        int delete=0;
        try{
            delete=usersMapper.deleteByUIdAndPw(uId,pw);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(delete>0){
            return "redirect:/users/mybatisList.do";
        }else {
            return "redirect:/users/delete.do?u_id="+uId;
        }
    }

// 🍏왜 UsersDto user 로 전체 파라미터를 이용해서 uId 불러 오는것은 안돼는가?
    // => 삭제 폼에서 아이디 입력창의 이름을 uId 로 바꾸니 삭제가능하게됨. 이유는??
//    @PostMapping("/delete.do")
//    public String deleteAction(UsersDto user){ //  파라미터 pw
//        int delete=0;
//        delete=usersMapper.deleteByUIdAndPw(user);
//        if(delete>0){
//            return "redirect:/users/mybatisList.do";
//        }else {
//            return "redirect:/users/delete.do?u_id="+user.getUId();
//        }
//    }





    @GetMapping("/update.do")
    public void updateForm(  // 업데이트 폼 함수 // 함수이름은 상관없다 // 이름이 같으면 void
            @RequestParam(name="u_id") String uId,
            Model model){
        UsersDto user=usersMapper.findByUId(uId);
        model.addAttribute("user",user);
    }

    @PostMapping("/update.do")
    public String updateAction(UsersDto user){ // 폼의 파라미터를 맵핑
        // 🍏객체의 필드는 자료형 null, 기본형은 0 이 기본값 => 파라미터가 없으면 0 또는 null 로 처리.
        int update=0;
        update=usersMapper.updateOne(user);
        System.out.println("user = " + user);
        if(update>0){
            return "redirect:/users/detail.do?u_id="+user.getUId();
        }else{
            return "redirect:/users/update.do?u_id="+user.getUId();
        }
    }


    @GetMapping("/mybatisList.do") // 동적페이지 - 컨트롤러
    public String mybatisList(Model model){
        List<UsersDto> users=usersMapper.findAll();
        model.addAttribute("users",users);
        return "/users/mybatisList";
//        http://localhost:8080/users/detail.do?u_id=testUser02
        // 상세 페이지를 만들어보세요 - 동적페이지 생성 + 뷰페이지
    }

    @GetMapping("/detail.do") // 동적페이지
    public String detail(@RequestParam(name="u_id") String uId, // u_id=testUser02
                         Model model){
        UsersDto user=usersMapper.findByUId(uId);
        model.addAttribute("user",user);
        return "/users/detail"; // users 폴더 안에 detail.html 파일 // == users/detail (맨앞에 슬러시 없어도 자동으로 해준다)
    }

    @GetMapping("list.do") // 동적페이지
    public String list(Model model){ // Model 로 객체를 전달할거라서
        String sql="SELECT * FROM users";
        try {
            Connection conn=dataSource.getConnection(); // 접속객체를 주입받는다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery();
            List<UsersDto> users=new ArrayList<>();
            while(rs.next()){
                UsersDto user=new UsersDto(); // 유저 생성
                user.setUId(rs.getString("u_id"));
                user.setPw(rs.getString("pw"));
                user.setAddress(rs.getString("address"));
                user.setDetailAddress(rs.getString("detail_address"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getString("gender"));
                user.setPermission(rs.getString("permission"));
                user.setBirth(rs.getString("birth"));
                user.setName(rs.getString("name"));
                user.setImgPath(rs.getString("img_path"));
                user.setPostTime(rs.getDate("post_time"));
                users.add(user); // 유저리스트에 유저 저장
            }
//            System.out.println("users = " + users); // 출력체크
            model.addAttribute("users",users); // 유저리스트 /users/list 뷰에 넘어간다.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/users/list"; // html 생략가능 // 기본으로 /template 로
    }



}
