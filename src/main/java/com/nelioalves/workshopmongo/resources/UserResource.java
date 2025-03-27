package com.nelioalves.workshopmongo.resources;

import com.nelioalves.workshopmongo.domain.Post;
import com.nelioalves.workshopmongo.domain.User;
import com.nelioalves.workshopmongo.dto.UserDTO;
import com.nelioalves.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> list = userService.findAll();
        List<UserDTO> userDTOList = list.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok().body(userDTOList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);

        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> findAllPosts(@PathVariable String id) {
        User user = userService.findById(id);

        return ResponseEntity.ok().body(user.getPosts());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody UserDTO dto) {
        User user = userService.fromDTO(dto);
        user = userService.insert(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
        User user = userService.fromDTO(dto);
        user = userService.update(user);

        return ResponseEntity.ok().body(new UserDTO(user));
    }
}
