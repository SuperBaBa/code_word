package org.jarvis.consumer.controller;

import org.jarvis.consumer.dto.Book;
import org.jarvis.consumer.vo.Books;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * author:tennyson date:2020/6/23
 **/
@Controller
public class BookController {
    @GetMapping(value = "v1/book")
    public ModelAndView books() {
        Books book1 = new Books();
        book1.setAuthor("罗贯中");
        book1.setName("三国演义");
        book1.setId(1);
        Books book2 = new Books();
        book2.setAuthor("曹雪芹");
        book2.setName("红楼梦");
        book2.setId(2);
        ModelAndView mv = new ModelAndView();
        List<Books> books=Arrays.asList(book1,book2);
        mv.setViewName("book");
        mv.addObject("books", books);
        return mv;
    }

    @GetMapping(value = "v2/book")
    @ResponseBody
    public Book book() {
        Book book = new Book();
        book.setAuthor("吴承恩");
        book.setName("西游记");
        book.setPrice(39.49f);
        book.setPublishDate(new Date());
        return book;
    }
}
