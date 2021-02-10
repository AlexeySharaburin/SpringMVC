package ru.netology.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

//        public List<Post> all() {
//        return repository.all();
//    }

    public ResponseEntity<List<Post>> all() {
        List<Post> unDeletePosts = new CopyOnWriteArrayList<>();
        for (Post post : repository.all()
        ) {
            if (!post.isRemoved()) {
                unDeletePosts.add(post);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return new ResponseEntity<>(unDeletePosts);
    }

//    public Post getById(long id) {
//        return repository.getById(id).orElseThrow(NotFoundException::new);
//    }

    public ResponseEntity<Post> getById(long id) {
        Post postCurrent = null;
        for (Post post : repository.all()) {
            if (post.getId() == id) {
                if (post.isRemoved()) {
                    return ResponseEntity.notFound().build();
                } else {
                    postCurrent = post;
                }
            } else {
                System.out.println("Нет такого ID!");
            }
        } return new ResponseEntity<>(postCurrent);
    }


    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

