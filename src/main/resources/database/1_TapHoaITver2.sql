
USE TapHoaIT;

CREATE TABLE role_user_tb (
                              type int PRIMARY KEY,
                              roleName nvarchar(20) NOT NULL UNIQUE
);
CREATE TABLE user_tb (
                         userID VARCHAR(100) PRIMARY KEY ,
                         userName NVARCHAR(50) NOT NULL,
                         address NVARCHAR(500) ,
                         phone VARCHAR(12) Unique,
                         gender NVARCHAR(3) ,
                         email VARCHAR(50) NOT NULL Unique,
                         password VARCHAR(255) NOT NULL,
                         type int not null,
                         active BOOLEAN NOT NULL DEFAULT TRUE,
                         CONSTRAINT FK_Role FOREIGN KEY (type)
                             REFERENCES role_user_tb(type)
);
CREATE TABLE brand_tb (
                          brandID VARCHAR(12) PRIMARY KEY,
                          brandName nvarchar(50) NOT NULL UNIQUE
);
CREATE TABLE category_tb (
                             categoryID VARCHAR(12) PRIMARY KEY,
                             categoryName nvarchar(50) NOT NULL UNIQUE
);
CREATE TABLE image_tb (
                          imageID int AUTO_INCREMENT PRIMARY KEY,
                          imageCode nvarchar(600) NOT NULL
);
CREATE TABLE discount_tb (
                             discountID VARCHAR(12) PRIMARY KEY,
                             percentage int NOT NULL,
                             startDate date not null,
                             endDate date not null,
                             active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE coupon_tb (
                           couponID VARCHAR(12) PRIMARY KEY,
                           percentage int NOT NULL,
                           active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE supplier_tb (
                             supplierID VARCHAR(100) PRIMARY KEY,
                             supplierName NVARCHAR(100) Not null Unique,
                             address NVARCHAR(100) NOT NULL ,
                             phone VARCHAR(12) NOT NULL Unique,
                             email VARCHAR(50) NOT NULL Unique
);
CREATE TABLE product_tb (
                            productID VARCHAR(100) PRIMARY KEY ,
                            productName NVARCHAR(500) NOT NULL UNIQUE,
                            brandID VARCHAR(12) NOT NULL,
                            categoryID VARCHAR(12) NOT NULL ,
                            discountID VARCHAR(12) ,
                            imageID int NOT NULL,
                            description LONGTEXT NOT NULL ,
                            unitPrice int NOT NULL,
                            unitCost int NOT NULL,
                            quantity int NOT NULL,
                            active BOOLEAN NOT NULL DEFAULT TRUE,
                            supplierID VARCHAR(100),
                            CONSTRAINT FK_Brand FOREIGN KEY (brandID)
                                REFERENCES brand_tb(brandID),
                            CONSTRAINT FK_Category FOREIGN KEY (categoryID)
                                REFERENCES category_tb(categoryID),
                            CONSTRAINT FK_Discount FOREIGN KEY (discountID)
                                REFERENCES discount_tb(discountID),
                            CONSTRAINT FK_Image FOREIGN KEY (imageID)
                                REFERENCES image_tb(imageID),
                            CONSTRAINT FK_Supplier FOREIGN KEY (supplierID)
                                REFERENCES supplier_tb(supplierID)
);
CREATE TABLE shopping_cart_tb (
                                  userID VARCHAR(100),
                                  productID VARCHAR(100),
                                  quantity int NOT NULL,
                                  CONSTRAINT PK_Shopping_Cart PRIMARY KEY (userID, productID),
                                  CONSTRAINT FK_User FOREIGN KEY (userID)
                                      REFERENCES user_tb(userID),
                                  CONSTRAINT FK_Product FOREIGN KEY (productID)
                                      REFERENCES product_tb(productID)
);

CREATE TABLE shop_order_tb (
                               orderID VARCHAR(100) PRIMARY KEY,
                               userID  VARCHAR(100) not null,
                               sellerID  VARCHAR(100) ,
                               orderDate date not null,
                               totalPrice int not null,
                               transportFee int not null,
                               deliveryTime date not null,
                               name VARCHAR(100) not null,
                               phone VARCHAR(12) not null,
                               address NVARCHAR(500)not null,
                               status  NVARCHAR(100) not null,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
                               CONSTRAINT FK_UserOrder FOREIGN KEY (userID)
                                   REFERENCES user_tb(userID),
                               CONSTRAINT FK_Seller FOREIGN KEY (sellerID)
                                   REFERENCES user_tb(userID)
);
CREATE TABLE payment_tb (
                            orderID VARCHAR(100) PRIMARY KEY,
                            method nvarchar(100) not null,
                            status  NVARCHAR(100) not null,
                            CONSTRAINT FK_Order FOREIGN KEY (orderID)
                                REFERENCES shop_order_tb(orderID) ON DELETE CASCADE

);
CREATE TABLE order_detail_tb (
                                 orderID VARCHAR(100) ,
                                 productID VARCHAR(100) ,
                                 quantity int not null,
                                 CONSTRAINT PK_OrderDetail PRIMARY KEY (orderID, productID),
                                 CONSTRAINT FK_ProductOrder FOREIGN KEY (productID)
                                     REFERENCES product_tb(productID),
                                 CONSTRAINT FK_OrderDetail FOREIGN KEY (orderID)
                                     REFERENCES shop_order_tb(orderID) ON DELETE CASCADE
);
CREATE TABLE tokens_tb (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           token VARCHAR(255) UNIQUE NOT NULL,
                           token_type VARCHAR(255) DEFAULT 'Bearer ',
                           revoked BOOLEAN NOT NULL DEFAULT FALSE,
                           expired BOOLEAN NOT NULL DEFAULT FALSE,
                           email VARCHAR(255) NOT NULL
);
CREATE TABLE user_coupon_tb (
                                userID VARCHAR(100),
                                couponID VARCHAR(100),
                                quantity int NOT NULL,
                                CONSTRAINT PK_User_Coupon PRIMARY KEY (userID, couponID),
                                CONSTRAINT FK_UserCoupon FOREIGN KEY (userID)
                                    REFERENCES user_tb(userID),
                                CONSTRAINT FK_Coupon FOREIGN KEY (couponID)
                                    REFERENCES coupon_tb(couponID)
);



