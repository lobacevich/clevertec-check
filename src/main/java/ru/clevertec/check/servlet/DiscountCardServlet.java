package ru.clevertec.check.servlet;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.mapper.DiscountCardMapper;
import ru.clevertec.check.mapper.impl.DiscountCardMapperJson;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/products")
public class DiscountCardServlet extends HttpServlet {

    private final DiscountCardService service = DiscountCardServiceImpl.getInstance();
    private final DiscountCardMapper mapper = DiscountCardMapperJson.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = req.getParameter("id");
        try(PrintWriter out = resp.getWriter()) {
            DiscountCard discountCard = service.getById(Long.parseLong(id));
            String json = mapper.toJson(discountCard);
            resp.setStatus(200);
            out.write(json);
        } catch (NumberFormatException e) {
            resp.setStatus(400);
        } catch (BadRequestException e) {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(BufferedReader reader = req.getReader()) {
            String json = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            DiscountCard discountCard = mapper.toDiscontCard(json);
            service.saveEntity(discountCard);
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(BufferedReader reader = req.getReader()) {
            String json = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            DiscountCard discountCard = mapper.toDiscontCard(json);
            service.updateEntity(discountCard);
            resp.setStatus(200);
        } catch (Exception e) {
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
