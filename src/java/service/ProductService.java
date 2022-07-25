/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Utils.GetParameter;
import com.google.gson.Gson;
import dal.ProductDAO;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author vietd
 */
@Path("/product")
public class ProductService {

    private final Gson gson;
    private ProductDAO productDAO;
    @Context
    private HttpServletRequest req;

    public ProductService() {
        productDAO = new ProductDAO();
        gson = new Gson();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("id") int id) {
        return Response.ok().build();
    }
    @GET
    @Path("/attribute/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttribute(@PathParam("id") int id) {
        return Response.ok(gson.toJson(productDAO.getASpecifiedAttributeValue(id))).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(gson.toJson(productDAO.getAll())).build();
    }

}
