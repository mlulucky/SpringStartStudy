package com.acorn.springstartstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Bean : 스프링 컨테이너에서 관리하는 객체
@SpringBootApplication // 패키지 기본설정 // Bean 어노테이션을 상속하고 있는 어노테이션. 좀더 구체적인 어노테이션
// @SpringBootApplication(scanBasePackages = "com.acorn.springstartstudy") // 패키지 기본설정 // Bean 어노테이션을 상속하고 있는 어노테이션. 좀더 구체적인 어노테이션
public class SpringStartStudyApplication { // 메인 어플 (pom.xml 에서 메이븐프로젝트(종속성)추가해야한다!)
	// bean : 하나의 객체. 스프링에서 객체를 관리하고 있다.
	// mybatis 는 db 설정을 해줘야 켜진다.
	public static void main(String[] args) {
		SpringApplication.run(SpringStartStudyApplication.class, args);
	}
	// 실행이 안되는 이유
	// 스프링은 컨테이너에서 관리하는 객체를 주입하는 형태로 언어가 작성되는데 이것을 관점지향(필요한곳에 주는것) 언어라 부른다. (<-> 객체지향언어 : 객체를 만들어쓰는 언어)
	// 그런데 spring-mybatis 를 라이브러리로 사용하면 db 접속을 컨테이너에서 관리한다.
	// 이때 컨테이너에 db 접속 내역이 없으면 오류가 발생

	// war vs jar
	// war : main 함수가 톰캣에게 있고 우리가 작성한 웹앱을 war 로 톰캣에 배포 => 톰캣이 JVM 을 실행해서 웹앱을 배포 (톰캣에 의존적)
	// main 이 있다는 것은 spring boot 가 jar 로 배포된다는 뜻
	// spring boot jar : spring 이 jvm 을 실행하고 톰캣의 서블릿을 사용 => 스프링 메인 jar 를 배포 (톰캣 설정할 필요X, 톰캣 의존적 X)
	// 스프링부트 - 스프링의 자르파일을 배포
	// 웹앱. 톰캣 - 톰캣의 와 파일로 배포
}
