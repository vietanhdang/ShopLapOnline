/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Customer;

import dal.FavouriteDAO;
import java.util.List;
import model.Favourite;

/**
 *
 * @author pc
 */
public class Wishlist {

    private List<Favourite> favourites;

    private final FavouriteDAO favouriteDAO = new FavouriteDAO();

    public Wishlist() {
    }

    public Wishlist(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    public int findFavourite(List<Favourite> wishList, int userId, int productId) {
        for (int i = wishList.size() - 1; i > -1; i--) {
            if (wishList.get(i).getUserId() == userId && wishList.get(i).getProductId() == productId) {
                return i;
            }
        }
        return -1;
    }

    /*
     * add favourite to database and wishlist
     * @param favourite favourite to add
     * @throws Exception exception when add favourite failed
     */
    public void addFavourite(Favourite favourite) throws Exception {
        int find = findFavourite(favourites, favourite.getUserId(), favourite.getProductId());
        if (find == -1) { // if wishist don't have favourite yet
            /* if add favourite to database success */
            if (favouriteDAO.insertFavourite(favourite) != -1) {
                favourites.add(favourite); // add favourite to wishlist
            } else {
                throw new Exception("Không thể yêu thích sản phẩm");
            }
        } else {
            throw new Exception("Sản phẩm đã có trong Yêu thích");
        }
    }

    /*
     * delete favourite from database and wishlist
     * @param userId - id of favourite owned customer
     * @param productId - id of favourite's product
     * @throws Exception exception when delete favourite failed
     */
    public void deleteFavourite(int userId, int productId) throws Exception {
        int find = findFavourite(favourites, userId, productId);
        if (find != -1) { // if wishlist really has favourite
            /* if delete favourite from database success */
            if (favouriteDAO.deleteFavourite(favourites.get(find).getId()) != -1) {
                favourites.remove(find); // delete favourite from wishlist
            } else {
                throw new Exception("Không thể bỏ Yêu thích sản phẩm");
            }
        } else {
            throw new Exception("Sản phẩm không có trong Yêu thích");
        }
    }
}
