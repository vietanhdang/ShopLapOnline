USE [master]
GO
/****** Object:  Database [ShopLapOnline]    Script Date: 24/05/2022 11:04:22 CH ******/
CREATE DATABASE [ShopLapOnline]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ShopLapOnline', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ShopLapOnline.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'ShopLapOnline_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\ShopLapOnline_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [ShopLapOnline] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ShopLapOnline].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ShopLapOnline] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ShopLapOnline] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ShopLapOnline] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ShopLapOnline] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ShopLapOnline] SET ARITHABORT OFF 
GO
ALTER DATABASE [ShopLapOnline] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ShopLapOnline] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ShopLapOnline] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ShopLapOnline] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ShopLapOnline] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ShopLapOnline] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ShopLapOnline] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ShopLapOnline] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ShopLapOnline] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ShopLapOnline] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ShopLapOnline] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ShopLapOnline] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ShopLapOnline] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ShopLapOnline] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ShopLapOnline] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ShopLapOnline] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ShopLapOnline] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ShopLapOnline] SET RECOVERY FULL 
GO
ALTER DATABASE [ShopLapOnline] SET  MULTI_USER 
GO
ALTER DATABASE [ShopLapOnline] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ShopLapOnline] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ShopLapOnline] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ShopLapOnline] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [ShopLapOnline] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [ShopLapOnline] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'ShopLapOnline', N'ON'
GO
ALTER DATABASE [ShopLapOnline] SET QUERY_STORE = OFF
GO
USE [ShopLapOnline]
GO
/****** Object:  Table [dbo].[Accounts]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Accounts](
	[Account_ID] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [varchar](100) NOT NULL,
	[Password] [varchar](100) NOT NULL,
	[Status] [bit] NOT NULL,
	[Role_ID] [int] NOT NULL,
	[CreatedTime] [datetime] NULL,
	[UpdatedTime] [datetime] NOT NULL,
	[Validator] [varchar](64) NULL,
PRIMARY KEY CLUSTERED 
(
	[Account_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Admins]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admins](
	[Admin_ID] [int] NOT NULL,
	[FullName] [nvarchar](50) NOT NULL,
	[Address] [nvarchar](50) NOT NULL,
	[Phone] [varchar](20) NOT NULL,
	[Image] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
	[Gender] [bit] NOT NULL,
	[DateOfBirth] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Brands]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Brands](
	[Brand_ID] [int] IDENTITY(1,1) NOT NULL,
	[BrandName] [nvarchar](50) NOT NULL,
	[PreviewImage] [ntext] NOT NULL,
	[Description] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Brand_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[Category_ID] [int] IDENTITY(1,1) NOT NULL,
	[CategoryName] [nvarchar](50) NOT NULL,
	[PreviewImage] [varchar](100) NULL,
	[Description] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Category_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customers]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customers](
	[Customer_ID] [int] NOT NULL,
	[FullName] [nvarchar](50) NOT NULL,
	[Address] [nvarchar](50) NOT NULL,
	[Phone] [varchar](20) NOT NULL,
	[Image] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
	[Gender] [bit] NOT NULL,
	[DateOfBirth] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Customer_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Favourites]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Favourites](
	[Favourites_ID] [int] IDENTITY(1,1) NOT NULL,
	[Customer_ID] [int] NOT NULL,
	[Product_ID] [int] NOT NULL,
	[CreatedTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Favourites_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedbacks]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedbacks](
	[Feedback_ID] [int] IDENTITY(1,1) NOT NULL,
	[Customer_ID] [int] NOT NULL,
	[OrderDetail_ID] [int] NOT NULL,
	[Comment] [nvarchar](50) NULL,
	[Rating] [smallint] NOT NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Feedback_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[OrderDetail_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetails]    Script Date: 24/05/2022 11:04:22 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetails](
	[OrderDetail_ID] [int] IDENTITY(1,1) NOT NULL,
	[Order_ID] [int] NOT NULL,
	[Product_ID] [int] NULL,
	[Price] [money] NOT NULL,
	[Quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderDetail_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderHistory]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderHistory](
	[OrderHistory_ID] [int] IDENTITY(1,1) NOT NULL,
	[Order_ID] [int] NOT NULL,
	[OrderStatus_ID] [int] NOT NULL,
	[OrderHistoryTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderHistory_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[Order_ID] [int] IDENTITY(1,1) NOT NULL,
	[Customer_ID] [int] NOT NULL,
	[Sale_ID] [int] NULL,
	[OrderDate] [datetime] NOT NULL,
	[Address] [nvarchar](100) NULL,
	[Phone] [varchar](20) NULL,
	[FullName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Order_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderStatus]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderStatus](
	[OrderStatus_ID] [int] IDENTITY(1,1) NOT NULL,
	[OrderStatusName] [nvarchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[OrderStatus_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductDetails]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductDetails](
	[ProductDetail_ID] [int] NOT NULL,
	[Images] [ntext] NULL,
	[Description] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ProductDetail_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[Product_ID] [int] IDENTITY(1,1) NOT NULL,
	[ProductName] [nvarchar](200) NOT NULL,
	[PreviewImage] [ntext] NULL,
	[Insurance] [smallint] NULL,
	[OriginalPrice] [money] NOT NULL,
	[InitialPrice] [money] NULL,
	[UnitPrice] [money] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Status] [bit] NOT NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
	[Category_ID] [int] NOT NULL,
	[Brand_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Products_SpecifiedAttribute]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products_SpecifiedAttribute](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Product_ID] [int] NOT NULL,
	[SpecifiedAttributeValue_ID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[Role_ID] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Role_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sales]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sales](
	[Sale_ID] [int] NOT NULL,
	[FullName] [nvarchar](50) NOT NULL,
	[Address] [nvarchar](50) NOT NULL,
	[Phone] [varchar](20) NOT NULL,
	[Image] [ntext] NULL,
	[CreatedTime] [datetime] NOT NULL,
	[UpdatedTime] [datetime] NOT NULL,
	[Gender] [bit] NOT NULL,
	[DateOfBirth] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Sale_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SpecifiedAttribute]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SpecifiedAttribute](
	[SpecifiedAttribute_ID] [int] IDENTITY(1,1) NOT NULL,
	[SpecifiedAttributeName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SpecifiedAttribute_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SpecifiedAttributeValue]    Script Date: 24/05/2022 11:04:23 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SpecifiedAttributeValue](
	[SpecifiedAttributeValue_ID] [int] IDENTITY(1,1) NOT NULL,
	[SpecifiedAttribute_ID] [int] NOT NULL,
	[SpecifiedAttributeValueName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SpecifiedAttributeValue_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Accounts] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Accounts] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Admins] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Admins] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Brands] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Brands] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Categories] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Categories] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Customers] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Favourites] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Feedbacks] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Feedbacks] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[ProductDetails] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[ProductDetails] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Products] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Sales] ADD  DEFAULT (getdate()) FOR [CreatedTime]
GO
ALTER TABLE [dbo].[Sales] ADD  DEFAULT (getdate()) FOR [UpdatedTime]
GO
ALTER TABLE [dbo].[Accounts]  WITH CHECK ADD  CONSTRAINT [FK_Account_Role] FOREIGN KEY([Role_ID])
REFERENCES [dbo].[Roles] ([Role_ID])
GO
ALTER TABLE [dbo].[Accounts] CHECK CONSTRAINT [FK_Account_Role]
GO
ALTER TABLE [dbo].[Admins]  WITH CHECK ADD  CONSTRAINT [FK_Admin_Account] FOREIGN KEY([Admin_ID])
REFERENCES [dbo].[Accounts] ([Account_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Admins] CHECK CONSTRAINT [FK_Admin_Account]
GO
ALTER TABLE [dbo].[Customers]  WITH CHECK ADD  CONSTRAINT [FK_Customer_Account] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Accounts] ([Account_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Customers] CHECK CONSTRAINT [FK_Customer_Account]
GO
ALTER TABLE [dbo].[Favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favourite_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customers] ([Customer_ID])
GO
ALTER TABLE [dbo].[Favourites] CHECK CONSTRAINT [FK_Favourite_Customer]
GO
ALTER TABLE [dbo].[Favourites]  WITH CHECK ADD  CONSTRAINT [FK_Favourite_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Products] ([Product_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Favourites] CHECK CONSTRAINT [FK_Favourite_Product]
GO
ALTER TABLE [dbo].[Feedbacks]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customers] ([Customer_ID])
GO
ALTER TABLE [dbo].[Feedbacks] CHECK CONSTRAINT [FK_Feedback_Customer]
GO
ALTER TABLE [dbo].[Feedbacks]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_OrderDetail] FOREIGN KEY([OrderDetail_ID])
REFERENCES [dbo].[OrderDetails] ([OrderDetail_ID])
GO
ALTER TABLE [dbo].[Feedbacks] CHECK CONSTRAINT [FK_Feedback_OrderDetail]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Order] FOREIGN KEY([Order_ID])
REFERENCES [dbo].[Orders] ([Order_ID])
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetail_Order]
GO
ALTER TABLE [dbo].[OrderDetails]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Products] ([Product_ID])
ON DELETE SET NULL
GO
ALTER TABLE [dbo].[OrderDetails] CHECK CONSTRAINT [FK_OrderDetail_Product]
GO
ALTER TABLE [dbo].[OrderHistory]  WITH CHECK ADD  CONSTRAINT [FK_OrderHistory_Order] FOREIGN KEY([Order_ID])
REFERENCES [dbo].[Orders] ([Order_ID])
GO
ALTER TABLE [dbo].[OrderHistory] CHECK CONSTRAINT [FK_OrderHistory_Order]
GO
ALTER TABLE [dbo].[OrderHistory]  WITH CHECK ADD  CONSTRAINT [FK_OrderHistory_OrderStatus] FOREIGN KEY([OrderStatus_ID])
REFERENCES [dbo].[OrderStatus] ([OrderStatus_ID])
GO
ALTER TABLE [dbo].[OrderHistory] CHECK CONSTRAINT [FK_OrderHistory_OrderStatus]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Order_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customers] ([Customer_ID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Order_Customer]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Order_Sale] FOREIGN KEY([Sale_ID])
REFERENCES [dbo].[Sales] ([Sale_ID])
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Order_Sale]
GO
ALTER TABLE [dbo].[ProductDetails]  WITH CHECK ADD  CONSTRAINT [FK_ProductDetail_Product] FOREIGN KEY([ProductDetail_ID])
REFERENCES [dbo].[Products] ([Product_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ProductDetails] CHECK CONSTRAINT [FK_ProductDetail_Product]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Product_Brand] FOREIGN KEY([Brand_ID])
REFERENCES [dbo].[Brands] ([Brand_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Product_Brand]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Product_Category] FOREIGN KEY([Category_ID])
REFERENCES [dbo].[Categories] ([Category_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Product_Category]
GO
ALTER TABLE [dbo].[Products_SpecifiedAttribute]  WITH CHECK ADD  CONSTRAINT [FK_Products_SpecifiedAttribute_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Products] ([Product_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Products_SpecifiedAttribute] CHECK CONSTRAINT [FK_Products_SpecifiedAttribute_Product]
GO
ALTER TABLE [dbo].[Products_SpecifiedAttribute]  WITH CHECK ADD  CONSTRAINT [FK_Products_SpecifiedAttribute_SpecifiedAttributeValue] FOREIGN KEY([SpecifiedAttributeValue_ID])
REFERENCES [dbo].[SpecifiedAttributeValue] ([SpecifiedAttributeValue_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Products_SpecifiedAttribute] CHECK CONSTRAINT [FK_Products_SpecifiedAttribute_SpecifiedAttributeValue]
GO
ALTER TABLE [dbo].[Sales]  WITH CHECK ADD  CONSTRAINT [FK_Sale_Account] FOREIGN KEY([Sale_ID])
REFERENCES [dbo].[Accounts] ([Account_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Sales] CHECK CONSTRAINT [FK_Sale_Account]
GO
ALTER TABLE [dbo].[SpecifiedAttributeValue]  WITH CHECK ADD  CONSTRAINT [FK_SpecifiedAttributeValue_SpecifiedAttribute] FOREIGN KEY([SpecifiedAttribute_ID])
REFERENCES [dbo].[SpecifiedAttribute] ([SpecifiedAttribute_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SpecifiedAttributeValue] CHECK CONSTRAINT [FK_SpecifiedAttributeValue_SpecifiedAttribute]
GO
USE [master]
GO
ALTER DATABASE [ShopLapOnline] SET  READ_WRITE 
GO
