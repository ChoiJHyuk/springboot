package com.rosoa0475.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rosoa0475.springboot.article.entity.Article;
import com.rosoa0475.springboot.article.repository.ArticleRepository;
// import com.rosoa0475.springboot.article.service.ArticleService;

@SpringBootTest
class SpringbootApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	// private ArticleService articleService;

	@Test
	void testJpa() {
		Article a1 = new Article();
		a1.setSubject("안녕하세요.");
		a1.setContent("만나서 반갑습니다 ^^");
		a1.setCreatedAt(LocalDateTime.now());
		this.articleRepository.save(a1);
		
		Article a2 = new Article();
		a2.setSubject("Spring Boot 재미있나요?");
		a2.setContent("스프링 부트 공부중인데 너무 재미있는 것 같아요.");
		a2.setCreatedAt(LocalDateTime.now());
		this.articleRepository.save(a2);
	}
	@Test
	void testJpa2() {
		List<Article> articles = this.articleRepository.findAll();
		assertEquals(2, articles.size());

		Article a = articles.get(0);
		assertEquals("안녕하세요.", a.getSubject());
    
		Optional<Article> oa = this.articleRepository.findById(1L);
		if(oa.isPresent()) {
			Article article = oa.get();
			assertEquals("안녕하세요.", article.getSubject());
		}
	}

	@Test
	void testJpa3() {
		Article a = this.articleRepository.findBySubject("안녕하세요.");
		assertEquals(3, a.getId());
	}
	
	@Test
	void testJpa4() {
		List<Article> aList = this.articleRepository.findBySubjectLike("안녕%");
		Article a = aList.get(0);
		assertEquals("안녕하세요.", a.getSubject());
	}
	
	@Test
	void testJpa5() {
		Optional<Article> oa = this.articleRepository.findById(1L);
		if(oa.isPresent()){
			Article a = oa.get();
			a.setSubject("수정된 제목");
			this.articleRepository.save(a);
		}
	}

	@SuppressWarnings("null")
	@Test
	void testJpa6() {
		assertEquals(2, this.articleRepository.count());
		Optional<Article> oa = this.articleRepository.findById(1L);
		if(oa.isPresent()){
			Article a = oa.get();
			this.articleRepository.delete(a);
			assertEquals(1, this.articleRepository.count());
		}
	}

	// @Test
	// void testJpa7() {
	// 	for(int i=0; i<300; i++){
	// 		String subject = String.format("테스트 데이터 [%03d]", i);
	// 		String content = String.format("테스트 데이터입니다.");
	// 		this.articleService.saveArticle(subject, content);
	// 	}
	// }
}
