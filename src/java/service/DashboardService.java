/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import dal.OrderDAO;
import dal.ProductDAO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hoang
 */
@Path("/dashboard")
public class DashboardService {

    private final Gson gson;
    private ProductDAO productDAO;
    @Context
    private HttpServletRequest req;

    public DashboardService() {
        productDAO = new ProductDAO();
        gson = new Gson();
    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(gson.toJson(new OrderDAO().getMonthlyRevenueOfCurrentYear())).build();
    }
    
}
