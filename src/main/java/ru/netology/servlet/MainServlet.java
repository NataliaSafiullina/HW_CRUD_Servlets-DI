package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    // Создаем контекст приложения
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    // Получаем контроллер по его классу
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var path = req.getRequestURI();
    if (path.matches("/api/posts/\\d+")) {
      final var id = getIdFromPath(path);
      controller.getById(id, resp);
    } else if (path.matches("/api/posts")) {
      controller.all(resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (req.getRequestURI().matches("/api/posts")) {
      controller.save(req.getReader(), resp);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var path = req.getRequestURI();
    if (path.matches("/api/posts/\\d+")) {
      final var id = getIdFromPath(path);
      controller.removeById(id, resp);
    }
  }

  protected long getIdFromPath(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }
}

