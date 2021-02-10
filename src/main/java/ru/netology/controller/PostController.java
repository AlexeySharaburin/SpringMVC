package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

@RestController
@RequestMapping("api/posts")
//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post removed")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Post> all() {
//        return service.all();
//    }

    @GetMapping
    public ResponseEntity<List<Post>> all() {
        return new ResponseEntity<>(service.all(), HttpStatus.OK);
    }

//    @GetMapping("{/id")
//    public Post getById(@PathVariable long id) {
//        return service.getById(id);
//    }

    @GetMapping("{/id")
    public ResponseEntity<Post> getById(@PathVariable long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

//    @PostMapping
//    public Post save(@RequestBody Post post) {
//        return service.save(post);
//    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        final Post currentPost = service.save(post);
        if (post.isRemoved()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(currentPost, HttpStatus.OK);
    }


    @DeleteMapping("/{id")
    public void removeById(long id) {
        service.removeById(id);
    }

}





