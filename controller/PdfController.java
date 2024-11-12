package com.udemy.learn.blogging.controller;
 
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import jakarta.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
import com.lowagie.text.DocumentException;
import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.repository.PostRepository;
import com.udemy.learn.blogging.service.PostService;
import com.udemy.learn.blogging.utils.UserPDFExporter;
 
@Controller
public class PdfController {
 
    @Autowired
    private PostRepository postRepository;
         
    @GetMapping("/post/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Post> listPost= postRepository.findAll();
         
        UserPDFExporter exporter = new UserPDFExporter(listPost);
        exporter.export(response);
         
    }
}