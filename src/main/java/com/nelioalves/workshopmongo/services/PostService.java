package com.nelioalves.workshopmongo.services;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.repository.PostRepository;
import com.nelioalves.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(String id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new ObjectNotFoundException("Post not found");
        }

        return post;
    }

    public List<Post> findByTitle(String title) {
        return postRepository.searchTitle(title);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 1000 * 60 * 60 * 24);
        return postRepository.fullSearch(text, minDate, maxDate);
    }

}
