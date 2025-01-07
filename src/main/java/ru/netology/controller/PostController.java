package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  public static final String PLAIN_TEXT = "text/plain";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    // Устанавливаем тип контента ответа в формате JSON
    response.setContentType(APPLICATION_JSON);
    // Получаем список всех постов
    final var data = service.all();
    // Создаем объект Gson для сериализации данных
    final var gson = new Gson();
    // Отдаем список постов в формате JSON
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    // Устанавливаем тип контента ответа в формате JSON
    response.setContentType(APPLICATION_JSON);
    // Создаем объект Gson для сериализации данных
    final var gson = new Gson();
    // Отдаем найденный пост в формате JSON
    response.getWriter().print(gson.toJson(service.getById(id)));
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    // Устанавливаем тип контента ответа в формате JSON
    response.setContentType(APPLICATION_JSON);
    // Создаем объект Gson для десериализации данных
    final var gson = new Gson();
    // Десериализуем тело запроса в объект Post
    final var post = gson.fromJson(body, Post.class);
    // Сохраняем пост в базе данных и получаем обратно то, что сохранили
    final var data = service.save(post);
    // Вернем в качестве ответа JSON сохраненный пост
    response.getWriter().print(gson.toJson(data));
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
   // Устанавливаем тип контента ответа в формате постой текст
    response.setContentType(PLAIN_TEXT);
    // Удаляем пост по id и получаем результат операции
    final var result = service.removeById(id);
    // Отдадим результат операции в качестве ответа
    response.getWriter().print(result);
  }
}
