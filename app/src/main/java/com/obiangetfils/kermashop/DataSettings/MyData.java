package com.obiangetfils.kermashop.DataSettings;

import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.models.AddressOBJ;
import com.obiangetfils.kermashop.models.BannersOBJ;
import com.obiangetfils.kermashop.models.CategoryOBJ;
import com.obiangetfils.kermashop.models.NewsCategoryOBJ;
import com.obiangetfils.kermashop.models.NewsObj;
import com.obiangetfils.kermashop.models.ProductOBJ;

import java.util.ArrayList;
import java.util.List;


public class MyData {

    private static List<BannersOBJ> dummyBanners = new ArrayList<>();
    private static List<BannersOBJ> dummyNewsBanners = new ArrayList<>();
    private static List<BannersOBJ> dummyDetailedProduct = new ArrayList<>();
    private static List<ProductOBJ> dummyProducts = new ArrayList<>();
    private static List<CategoryOBJ> allCategoriesList = new ArrayList<>();
    private static List<ProductOBJ> dummyCart = new ArrayList<>();
    private static List<ProductOBJ> favProducts = new ArrayList<>();
    private static List<ProductOBJ> orderedProducts = new ArrayList<>();
    private static List<NewsCategoryOBJ> newsCategories = new ArrayList<>();
    private static List<NewsObj> news = new ArrayList<>();
    private static List<AddressOBJ> addresses = new ArrayList<>();



    public static void fillData() {
        dummyBanners.clear();
        dummyProducts.clear();
        allCategoriesList.clear();
        dummyCart.clear();
        orderedProducts.clear();
        newsCategories.clear();
        news.clear();
        addresses.clear();
        dummyDetailedProduct.clear();


        dummyBanners.add(new BannersOBJ("Banner 1", R.drawable.banner_01));
        dummyBanners.add(new BannersOBJ("Banner 2", R.drawable.banner_02));
        dummyBanners.add(new BannersOBJ("Banner 3", R.drawable.banner_03));

        dummyNewsBanners.add(new BannersOBJ("Banner 1", R.drawable.img_app_feature));
        dummyNewsBanners.add(new BannersOBJ("Banner 2", R.drawable.img_news));
        dummyNewsBanners.add(new BannersOBJ("Banner 3", R.drawable.img_deals));

        dummyProducts.add(new ProductOBJ(1001, "Ladies Paints", R.drawable.img_bottom, "$85", "$108", true, false, false, false, 0, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1002, "Black Scirt", R.drawable.img_black_scirt, "$192", "$358", false, false, true, false, 1, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1003, "Printed T_Shirt", R.drawable.img_printed_t_shirt, "$35", "$40", false, true, false, false, 2, 0, "Single"));
        dummyProducts.add(new ProductOBJ(1004, "Ladies Stylish Shirt", R.drawable.img_dress_one, "$120", "$135", false, false, false, true, 3, 10, "Variable"));
        dummyProducts.add(new ProductOBJ(1005, "Party Dinner Shoes", R.drawable.img_party_dinner_shoes, "$85", "$90", true, false, false, false, 4, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1006, "Luxrey Ladies Watches", R.drawable.img_luxrey_ladies_watche, "$99", "$125", false, true, false, false, 5, 10, "Single"));

        dummyProducts.add(new ProductOBJ(1007, "Ladies Paint", R.drawable.img_jeans_paint, "$65", "$85", false, false, true, false, 6, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1008, "Ladies New Dress", R.drawable.img_ladies_dress, "$85", "$95", false, false, false, true, 0, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1009, "Rose Petal Shirt", R.drawable.img_ladies_shirt, "$85", "$95", true, false, false, false, 1, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1010, "Winter Wear Dress", R.drawable.img_ladies_t_shirt, "$135", "$140", false, true, false, false, 2, 10, "Variable"));
        dummyProducts.add(new ProductOBJ(1011, "Long Coat", R.drawable.img_long_coat, "$195", "$200", false, false, true, false, 3, 0, "Single"));
        dummyProducts.add(new ProductOBJ(1012, "Crystal High Hells", R.drawable.img_crystal_high_heels, "$135", "$150", false, false, false, true, 4, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1013, "Women Magnet Watch", R.drawable.img_women_magnet_buck_watch, "$125", "$135", true, false, false, false, 5, 10, "Single"));

        dummyProducts.add(new ProductOBJ(1014, "Ladies White Coat", R.drawable.img_white_coat, "$350", "$380", false, true, false, false, 6, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1015, "Denim Jacket", R.drawable.img_women_coat, "$95", "$100", false, false, true, false, 0, 10, "Variable"));
        dummyProducts.add(new ProductOBJ(1016, "New Beautiful Dress", R.drawable.img_women_dress, "$365", "$380", false, false, false, true, 1, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1017, "Yellow Coat", R.drawable.img_yellow_coat, "$275", "$280", true, false, false, false, 2, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1018, "Quilted Giltet Hoodie", R.drawable.img_quilted_gilet_hoodie, "$385", "$390", false, true, false, false, 3, 0, "Single"));
        dummyProducts.add(new ProductOBJ(1019, "Air Mesh Heels", R.drawable.img_air_mesh_high_heels, "$365", "$375", false, false, true, false, 4, 10, "Single"));
        dummyProducts.add(new ProductOBJ(1020, "Starry Sky Wrist", R.drawable.img_starry_sky_wrist_watch, "$135", "$140", false, false, false, true, 5, 10, "Variable"));

        dummyDetailedProduct.add(new BannersOBJ("Banner 1", R.drawable.productdetails_01));
        dummyDetailedProduct.add(new BannersOBJ("Banner 2", R.drawable.productdetails_02));
        dummyDetailedProduct.add(new BannersOBJ("Banner 3", R.drawable.productdetails_03));
        dummyDetailedProduct.add(new BannersOBJ("Banner 4", R.drawable.productdetails_04));



        allCategoriesList.add(new CategoryOBJ(R.drawable.img_tops, R.drawable.category_men_icon, "Tops", 23));
        allCategoriesList.add(new CategoryOBJ(R.drawable.img_bottoms, R.drawable.category_women_icon, "Bottoms", 14));
        allCategoriesList.add(new CategoryOBJ(R.drawable.img_shirts, R.drawable.category_boys_icon, "Shirts", 16));
        allCategoriesList.add(new CategoryOBJ(R.drawable.img_winter_wear, R.drawable.category_girls_icon, "Winter Wear", 10));
        allCategoriesList.add(new CategoryOBJ(R.drawable.img_shoes, R.drawable.category_babies_icon, "Shoes", 10));
        allCategoriesList.add(new CategoryOBJ(R.drawable.watches, R.drawable.category_household_icon, "Watches", 8));


        newsCategories.add(new NewsCategoryOBJ(2001, "Best Eid Dresses For Women", R.drawable.img_dress_one, "2019-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2002, "Best Eid Dresses For Women", R.drawable.img_black_scirt, "2018-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2003, "Best Eid Dresses For Women", R.drawable.img_dress_four, "2016-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2004, "Best Eid Dresses For Women", R.drawable.img_dress_three, "2015-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2005, "Best Eid Dresses For Women", R.drawable.img_quilted_gilet_hoodie, "2013-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2005, "Best Eid Dresses For Women", R.drawable.img_straight_long_coat, "2014-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2005, "Best Eid Dresses For Women", R.drawable.img_yellow_coat, "2010-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2005, "Best Eid Dresses For Women", R.drawable.img_printed_t_shirt, "2011-07-22 T 11:25:55", R.string.lorem_ipsum));
        newsCategories.add(new NewsCategoryOBJ(2005, "Best Eid Dresses For Women", R.drawable.img_white_black_shirt, "2012-07-22 T 11:25:55", R.string.lorem_ipsum));

        news.add(new NewsObj(5001, R.drawable.img_app_feature, "App Feature", "10 Posts"));
        news.add(new NewsObj(5002, R.drawable.img_deals, "Deals", "6 Posts"));
        news.add(new NewsObj(5003, R.drawable.img_news, "News", "8 Posts"));
        news.add(new NewsObj(5004, R.drawable.img_promotions, "Promotions", "9 Posts"));

        orderedProducts.add(dummyProducts.get(4));
        orderedProducts.add(dummyProducts.get(2));
        orderedProducts.add(dummyProducts.get(8));

        dummyCart.add(dummyProducts.get(3));
        dummyCart.add(dummyProducts.get(7));

        addresses.add(new AddressOBJ());
        addresses.add(new AddressOBJ());
        addresses.add(new AddressOBJ());
    }

    public static List<BannersOBJ> getDummyBanners() {
        return dummyBanners;
    }
    public static List<BannersOBJ> getDummyNewsBanners() {
        return dummyNewsBanners;
    }

    public static List<ProductOBJ> getDummyProductList() {
        return dummyProducts;
    }

    public static List<ProductOBJ> getDummyNewstProducts() {
        List<ProductOBJ> newestProducts = new ArrayList<>();
        for (ProductOBJ obj : dummyProducts) {
            if (obj.isNewTag()) {
                newestProducts.add(obj);
            }
        }
        return newestProducts;
    }

    public static List<ProductOBJ> getDumySaleProducts() {
        List<ProductOBJ> saleProducts = new ArrayList<>();
        for (ProductOBJ obj : dummyProducts) {
            if (obj.isSaleTag()) {
                saleProducts.add(obj);
            }
        }
        return saleProducts;
    }

    public static List<ProductOBJ> getDummyFeaturedProducts() {
        List<ProductOBJ> featuredProducts = new ArrayList<>();
        for (ProductOBJ obj : dummyProducts) {
            if (obj.isFeaturedTag()) {
                featuredProducts.add(obj);
            }
        }
        return featuredProducts;
    }

    public static List<ProductOBJ> getDumyFavProducts() {
        List<ProductOBJ> favProducts = new ArrayList<>();
        for (ProductOBJ obj : dummyProducts) {
            if (obj.isFavTag()) {
                favProducts.add(obj);
            }
        }
        return favProducts;
    }

    public static List<ProductOBJ> getDummyRecentProducts() {
        List<ProductOBJ> recentProducts = new ArrayList<>();
        recentProducts.add(dummyProducts.get(0));
        recentProducts.add(dummyProducts.get(1));
        return recentProducts;
    }

    public static List<CategoryOBJ> getDummyCategories() {
        return allCategoriesList;
    }

    public static List<ProductOBJ> getDummyProductsByCategory(int categoryId) {
        List<ProductOBJ> productsByCategory = new ArrayList<>();
        for (ProductOBJ obj : dummyProducts) {
            if (obj.getCategoryID() == categoryId) {
                productsByCategory.add(obj);
            }
        }
        return productsByCategory;
    }

    public static void addToCartProduct(ProductOBJ obj){
        dummyCart.add(obj);
    }

    public static List<ProductOBJ> getCartProducts() {
        return dummyCart;
    }

    public static void addToFavProducts(ProductOBJ obj) {
        favProducts.add(obj);
    }

    public static void removeFromFavProducts(ProductOBJ obj) {
        favProducts.remove(obj);
    }

    public static ProductOBJ getDummyProductById(int productId) {
        for (ProductOBJ obj : dummyProducts) {
            if (obj.getID() == productId) {
                return obj;
            }
        }
        return null;
    }

    public static boolean isCartContains(int productId) {
        for (ProductOBJ obj : dummyCart){
            if (obj.getID() == productId){
                return true;
            }
        }
        return false;
    }

    public static void placeOrder() {
        orderedProducts.addAll(dummyCart);
        dummyCart.clear();
    }

    public static List<ProductOBJ> getOrderedProducts() {
        return orderedProducts;
    }

    public static List<NewsCategoryOBJ> getNewsCategories() {
        return newsCategories;
    }

    public static List<NewsObj> getNews() {
        return news;
    }

    public static List<AddressOBJ> getAddresses() {
        return addresses;
    }

    public static List<BannersOBJ> getDummyDetailedProduct() {
        return dummyDetailedProduct;
    }
}
