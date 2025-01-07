package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
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

