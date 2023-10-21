package edu.greenriver.sdev.dictionaryapistarter.controller;

import edu.greenriver.sdev.dictionaryapistarter.dtos.WordDefinition;
import edu.greenriver.sdev.dictionaryapistarter.services.DictionaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class DictionaryApi
{
    public DictionaryService service;

    @GetMapping("dict")
    public ResponseEntity<List<String>> getAllWords(){
        return new ResponseEntity<>(service.allWords(), HttpStatus.OK);
    }

    @GetMapping("dict/{word}")
    public ResponseEntity<String> getDefinition(@PathVariable String word){
        Optional<String> optional = service.getDefinition(word);
        if(optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error: Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("dict/{word}/{definition}")
    public ResponseEntity<String> addWord(@PathVariable String word, @PathVariable String definition){
        Optional<String> optional = service.getDefinition(word);
        if(optional.isPresent()){
            return new ResponseEntity<>("Error: This word already exists.", HttpStatus.BAD_REQUEST);
        }else{
            WordDefinition wordDefinition = service.addWord(word, definition);
            return new ResponseEntity<>(wordDefinition.toString(), HttpStatus.CREATED);
        }
    }

    @PostMapping("dict")
    public ResponseEntity<String> addWordQueryParam(@RequestParam String word, @RequestParam String definition){
        Optional<String> optional = service.getDefinition(word);
        if(optional.isPresent()){
            return new ResponseEntity<>("Error: This word already exists.", HttpStatus.BAD_REQUEST);
        }else{
            WordDefinition wordDefinition = service.addWord(word, definition);
            return new ResponseEntity<>(wordDefinition.toString(), HttpStatus.CREATED);
        }
    }

    @PostMapping("dict/body")
    public ResponseEntity<String> addWordBody(@RequestBody WordDefinition wordDefinition){
        Optional<String> optional = service.getDefinition(wordDefinition.getWord());
        if(optional.isPresent()){
            return new ResponseEntity<>("Error: This word already exists.", HttpStatus.BAD_REQUEST);
        }else{
            WordDefinition returnWordDefinition = service.addWord(wordDefinition.getWord(), wordDefinition.getDefinition());
            return new ResponseEntity<>(returnWordDefinition.toString(), HttpStatus.CREATED);
        }
    }




}
