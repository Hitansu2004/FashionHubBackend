USE cart_and_checkout_db;
GO
 
CREATE TABLE shopping_cart(
	[cart_id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[cart_total] [decimal](10, 2) DEFAULT ((0.00)) NULL,
	[created_date] [datetime] NOT NULL,
	[last_updated_date] [datetime] NOT NULL
);
 
CREATE TABLE cart_items(
	[id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
	[cart_id] [int] NOT NULL,
	[product_id] [int] NOT NULL,
	[sku] [varchar](50) NOT NULL,
	[size] [varchar](50) NOT NULL,
	[quantity] [int] NOT NULL,
	[unit_price] [decimal](10, 2) NOT NULL,
	[discount] [decimal](10, 2) NOT NULL,
	[final_price] [decimal](10, 2) NOT NULL,
	[is_saved_for_later] [bit] NULL,
	CONSTRAINT [FK_CartItems_ShoppingCart] FOREIGN KEY([cart_id]) REFERENCES shopping_cart([cart_id])
);
 
 
CREATE TABLE user_addresses(
	[id] [int] PRIMARY KEY IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[address_lane_1] [varchar](255) NOT NULL,
	[address_lane_2] [varchar](255) NULL,
	[zipcode] [varchar](10) NOT NULL,
	[state] [varchar](100) NULL,
	[country] [varchar](100) NULL,
	[address_type] [varchar](50) NULL DEFAULT ('BILLING'),
	[contact_name] [varchar](200) NULL,
	[contact_phone_number] [varchar](15) NULL
);