/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import Controller.Customer.Wishlist;
import Utils.HashGeneratorUtils;
import dal.AccountDAO;
import dal.BrandDAO;
import dal.CategoryDAO;
import dal.FavouriteDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.Brand;
import model.Category;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Benjamin
 */
@WebFilter(filterName = "RouteConfig", urlPatterns = {"/*"})
public class ServerFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ServerFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8"); // set character encoding
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();
        boolean isLogged = session.getAttribute("ACCOUNTLOGGED") != null;

        String accountURI = httpRequest.getContextPath() + "/account";
        String loginURI = httpRequest.getContextPath() + "/login";
        String logoutURI = httpRequest.getContextPath() + "/logout";
        String registerURI = httpRequest.getContextPath() + "/register";
        String checkoutURI = httpRequest.getContextPath() + "/Checkout";
        String wishlistURI = httpRequest.getContextPath() + "/wishlist";

        boolean isAccountRequest = httpRequest.getRequestURI().equals(accountURI);
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isRegisterRequest = httpRequest.getRequestURI().equals(registerURI);
        boolean isLogoutRequest = httpRequest.getRequestURI().equals(logoutURI);
        boolean isCheckoutRequest = httpRequest.getRequestURI().equals(checkoutURI);
        boolean isWishlistRequest = httpRequest.getRequestURI().equals(wishlistURI);

        CategoryDAO categoryDAO = new CategoryDAO();
        BrandDAO brandDAO = new BrandDAO();
        ArrayList<Category> categories = categoryDAO.findAllCategoryAvailable();
        ArrayList<Brand> brands = brandDAO.findAllBrandAvailable();

        session.setAttribute("categories", categories);
        session.setAttribute("brands", brands);

        if (!isLogged && cookies != null) {
            // process auto login for remember me feature
            String selector = "";
            String validator = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("laptop-username")) {
                    selector = cookie.getValue();
                } else if (cookie.getName().equals("laptop-user-validator")) {
                    validator = cookie.getValue();
                }
            }
            if (!selector.isEmpty() && !validator.isEmpty()) {
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.findByUsername(selector);
                if (account != null) {
                    String hashedValidatorDatabase = account.getCookie();
                    String hashedValidatorCookie = HashGeneratorUtils.generateSHA256(validator);
                    if (hashedValidatorCookie.equals(hashedValidatorDatabase)) {
                        session.setAttribute("ACCOUNTLOGGED", account);
                        isLogged = true;
                        // update new token in database
                        String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(64);
                        String hashedValidator = HashGeneratorUtils.generateSHA256(randomAlphanumeric);
                        accountDAO.updateAccountCookie(account.getId(), hashedValidator);
                        // update cookie
                        Cookie cookieSelector = new Cookie("laptop-username", account.getUsername());
                        cookieSelector.setMaxAge(604800);
                        Cookie cookieValidator = new Cookie("laptop-user-validator", randomAlphanumeric);
                        cookieValidator.setMaxAge(604800);
                        httpResponse.addCookie(cookieSelector);
                        httpResponse.addCookie(cookieValidator);
                        if (account.getRole().getRoleID() == 3) {
                            //get customer wishlist
                            session.setAttribute("WISHLIST", new Wishlist(
                                    new FavouriteDAO().getFavouritesByUserId(account.getId())));
                        }
                    }
                }
            }
        }
        String servletPath = httpRequest.getServletPath();
        String returnUrl = request.getParameter("returnUrl");
        if (!isLogged && (isLogoutRequest || isAccountRequest || isCheckoutRequest || isWishlistRequest)) {
            httpResponse.sendRedirect("login?returnUrl=" + servletPath);
        } else if (isLogged && returnUrl != null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + returnUrl);
        } else if (isLogged && (isLoginRequest || isRegisterRequest)) {
            httpResponse.sendRedirect("home");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     *
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("RouteConfig:Initializing filter");
            }
        }
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
