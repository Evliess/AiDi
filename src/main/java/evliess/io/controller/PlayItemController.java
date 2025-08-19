package evliess.io.controller;


import evliess.io.service.PlayItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayItemController {


    private final PlayItemService playItemService;

    @Autowired
    public PlayItemController(PlayItemService playItemService) {
        this.playItemService = playItemService;
    }

    @GetMapping("/public/hello")
    public ResponseEntity<String> hello() {
        playItemService.getItems().forEach((k, v) -> System.out.println(k + " : " + v));
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/private/hello")
    public ResponseEntity<String> hello1() {

        return ResponseEntity.ok("hello");
    }


}
