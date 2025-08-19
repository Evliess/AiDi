package evliess.io.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayItemController {

    @GetMapping("/public/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/private/hello")
    public ResponseEntity<String> hello1() {
        return ResponseEntity.ok("hello");
    }
}
