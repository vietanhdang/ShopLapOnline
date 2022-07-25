/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author Admin
 */
import com.google.gson.Gson;
import dal.TransactionDAO;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import model.Comment;
import model.Order;
import model.OrderDetails;
@Path("/order")
public class OrderService {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final Gson gson = new Gson();
    
    /* user want to see their review in a specific product in order */
    @GET
    @Path("/getReview")
    public Response getOrderDetailComment(@QueryParam("orderDetailId") int orderDetailId, @QueryParam("userId") int userId) {
        Comment comment = transactionDAO.getCommentByUserIAndOrderDetailId(orderDetailId, userId); // get review by userId and orderDetailId
        return Response.ok().entity(gson.toJson(comment)).build();
    }
    
    /* sale see an order detail*/
    @GET
    @Path("/getOrderDetail")
    public Response getOrderDetailBySale(@QueryParam("orderId") int orderId) {
        List<OrderDetails> orderDetails = transactionDAO.getDetailsInOrder(orderId); // get order detail by order id
        return Response.ok().entity(gson.toJson(orderDetails)).build();
    }
}
