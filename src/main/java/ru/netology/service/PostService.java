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
    private final List<Post> unDeletedPosts = new CopyOnWriteArrayList<>();
    private final List<Post> deletedPosts = new CopyOnWriteArrayList<>();

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

//    public List<Post> all() {
//        return repository.all();
//    }

    public List<Post> all() {
        for (Post post : repository.all()
        ) {
            if (!post.isRemoved()) {
                unDeletedPosts.add(post);
            }
        }
        return unDeletedPosts;
    }

    public List<Post> allDeleted() {
        for (Post post : repository.all()
        ) {
            if (post.isRemoved()) {
                deletedPosts.add(post);
            }
        }
        return deletedPosts;
    }

//    public Post getById(long id) {
//        return repository.getById(id).orElseThrow(NotFoundException::new);
//    }

    public Post getById(long id) {
        Post postCurrent = null;
        for (Post post : repository.all()) {
            if (post.getId() == id) {
                if (!post.isRemoved()) {
                    postCurrent = post;
                }
            } else {
                System.out.println("Нет такого ID!");
            }
        }
        return postCurrent;
    }


    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}

