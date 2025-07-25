USE [master]
GO
/****** Object:  Database [car_db]    Script Date: 6/20/2025 10:33:29 PM ******/
CREATE DATABASE [car_db]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'car_db', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\car_db.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'car_db_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\car_db_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [car_db] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [car_db].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [car_db] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [car_db] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [car_db] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [car_db] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [car_db] SET ARITHABORT OFF 
GO
ALTER DATABASE [car_db] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [car_db] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [car_db] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [car_db] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [car_db] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [car_db] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [car_db] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [car_db] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [car_db] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [car_db] SET  DISABLE_BROKER 
GO
ALTER DATABASE [car_db] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [car_db] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [car_db] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [car_db] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [car_db] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [car_db] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [car_db] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [car_db] SET RECOVERY FULL 
GO
ALTER DATABASE [car_db] SET  MULTI_USER 
GO
ALTER DATABASE [car_db] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [car_db] SET DB_CHAINING OFF 
GO
ALTER DATABASE [car_db] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [car_db] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [car_db] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [car_db] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'car_db', N'ON'
GO
ALTER DATABASE [car_db] SET QUERY_STORE = ON
GO
ALTER DATABASE [car_db] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [car_db]
GO
/****** Object:  Table [dbo].[car_information]    Script Date: 6/20/2025 10:33:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[car_information](
	[carid] [int] IDENTITY(1,1) NOT NULL,
	[car_description] [varchar](220) NULL,
	[car_name] [varchar](50) NOT NULL,
	[car_renting_price_per_day] [numeric](19, 4) NULL,
	[car_status] [smallint] NULL,
	[fuel_type] [varchar](20) NULL,
	[number_of_doors] [int] NULL,
	[seating_capacity] [int] NULL,
	[year] [int] NULL,
	[manufacturerid] [int] NOT NULL,
	[supplierid] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[carid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[manufacturer]    Script Date: 6/20/2025 10:33:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[manufacturer](
	[manufacturerid] [int] IDENTITY(1,1) NOT NULL,
	[description] [varchar](250) NULL,
	[manufacturer_country] [varchar](50) NULL,
	[manufacturer_name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[manufacturerid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[supplier]    Script Date: 6/20/2025 10:33:30 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[supplier](
	[supplierid] [int] IDENTITY(1,1) NOT NULL,
	[supplier_address] [varchar](80) NULL,
	[supplier_description] [varchar](250) NULL,
	[supplier_name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[supplierid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[car_information] ON 

INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (1, N'Reliable mid-size sedan perfect for business trips', N'Toyota Camry', CAST(450.0000 AS Numeric(19, 4)), 1, N'Gasoline', 4, 5, 2023, 1, 3)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (2, N'Compact and fuel-efficient car for city driving', N'Toyota Corolla', CAST(350.0000 AS Numeric(19, 4)), 1, N'Hybrid', 4, 5, 2024, 1, 2)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (3, N'Versatile SUV perfect for family adventures', N'Toyota RAV4', CAST(550.0000 AS Numeric(19, 4)), 1, N'Hybrid', 5, 7, 2023, 1, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (4, N'Sporty and reliable compact car', N'Honda Civic', CAST(380.0000 AS Numeric(19, 4)), 1, N'Gasoline', 4, 5, 2023, 2, 2)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (5, N'Premium sedan with advanced safety features', N'Honda Accord', CAST(480.0000 AS Numeric(19, 4)), 1, N'Hybrid', 4, 5, 2024, 2, 3)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (6, N'Spacious SUV with excellent fuel economy', N'Honda CR-V', CAST(520.0000 AS Numeric(19, 4)), 0, N'Hybrid', 5, 7, 2023, 2, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (7, N'Luxury sedan combining performance and comfort', N'BMW 3 Series', CAST(750.0000 AS Numeric(19, 4)), 1, N'Gasoline', 4, 5, 2024, 3, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (8, N'Premium SUV with cutting-edge technology', N'BMW X5', CAST(950.0000 AS Numeric(19, 4)), 1, N'Gasoline', 5, 7, 2023, 3, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (9, N'Electric luxury sedan for eco-conscious drivers', N'BMW i4', CAST(850.0000 AS Numeric(19, 4)), 2, N'Electric', 4, 5, 2024, 3, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (10, N'Elegant luxury sedan with premium amenities', N'Mercedes C-Class', CAST(800.0000 AS Numeric(19, 4)), 1, N'Gasoline', 4, 5, 2024, 4, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (11, N'Sophisticated SUV with advanced driver assistance', N'Mercedes GLC', CAST(900.0000 AS Numeric(19, 4)), 0, N'Gasoline', 5, 5, 2023, 4, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (12, N'Ultra-luxury electric sedan with exceptional range', N'Mercedes EQS', CAST(1200.0000 AS Numeric(19, 4)), 1, N'Electric', 4, 5, 2024, 4, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (13, N'Compact car with great handling and efficiency', N'Ford Focus', CAST(320.0000 AS Numeric(19, 4)), 1, N'Gasoline', 4, 5, 2023, 5, 2)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (14, N'Family-friendly SUV with spacious interior', N'Ford Explorer', CAST(580.0000 AS Numeric(19, 4)), 1, N'Gasoline', 5, 7, 2023, 5, 3)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (15, N'Iconic sports car for thrilling driving experience', N'Ford Mustang', CAST(700.0000 AS Numeric(19, 4)), 1, N'Gasoline', 2, 4, 2024, 5, 1)
INSERT [dbo].[car_information] ([carid], [car_description], [car_name], [car_renting_price_per_day], [car_status], [fuel_type], [number_of_doors], [seating_capacity], [year], [manufacturerid], [supplierid]) VALUES (16, N'Reliable mid-size sedan perfect for business trips', N'Toyota Camry Hybrid', CAST(450.0000 AS Numeric(19, 4)), 0, N'Gasoline', 4, 5, 2023, 1, 3)
SET IDENTITY_INSERT [dbo].[car_information] OFF
GO
SET IDENTITY_INSERT [dbo].[manufacturer] ON 

INSERT [dbo].[manufacturer] ([manufacturerid], [description], [manufacturer_country], [manufacturer_name]) VALUES (1, N'Japanese multinational automotive manufacturer', N'Japan', N'Toyota')
INSERT [dbo].[manufacturer] ([manufacturerid], [description], [manufacturer_country], [manufacturer_name]) VALUES (2, N'Japanese public multinational conglomerate manufacturer', N'Japan', N'Honda')
INSERT [dbo].[manufacturer] ([manufacturerid], [description], [manufacturer_country], [manufacturer_name]) VALUES (3, N'German multinational corporation which produces luxury vehicles', N'Germany', N'BMW')
INSERT [dbo].[manufacturer] ([manufacturerid], [description], [manufacturer_country], [manufacturer_name]) VALUES (4, N'German global automobile marque and a division of Daimler AG', N'Germany', N'Mercedes-Benz')
INSERT [dbo].[manufacturer] ([manufacturerid], [description], [manufacturer_country], [manufacturer_name]) VALUES (5, N'American multinational automaker', N'USA', N'Ford')
SET IDENTITY_INSERT [dbo].[manufacturer] OFF
GO
SET IDENTITY_INSERT [dbo].[supplier] ON 

INSERT [dbo].[supplier] ([supplierid], [supplier_address], [supplier_description], [supplier_name]) VALUES (1, N'12345 Premium Street, Stockholm, Sweden', N'High-end luxury car rental supplier', N'Super Premium Car Rentals')
INSERT [dbo].[supplier] ([supplierid], [supplier_address], [supplier_description], [supplier_name]) VALUES (2, N'456 Budget Avenue, Gothenburg, Sweden', N'Affordable car rental solutions for everyone', N'Economy Fleet Solutions')
INSERT [dbo].[supplier] ([supplierid], [supplier_address], [supplier_description], [supplier_name]) VALUES (3, N'789 Corporate Blvd, Malmö, Sweden', N'Professional car rental for business needs', N'Business Car Services')
SET IDENTITY_INSERT [dbo].[supplier] OFF
GO
ALTER TABLE [dbo].[car_information]  WITH CHECK ADD  CONSTRAINT [FKmx4g48cngyjnq88b9bp6xo6e1] FOREIGN KEY([manufacturerid])
REFERENCES [dbo].[manufacturer] ([manufacturerid])
GO
ALTER TABLE [dbo].[car_information] CHECK CONSTRAINT [FKmx4g48cngyjnq88b9bp6xo6e1]
GO
ALTER TABLE [dbo].[car_information]  WITH CHECK ADD  CONSTRAINT [FKtci02qhcu9h1a1pv45hodcpoj] FOREIGN KEY([supplierid])
REFERENCES [dbo].[supplier] ([supplierid])
GO
ALTER TABLE [dbo].[car_information] CHECK CONSTRAINT [FKtci02qhcu9h1a1pv45hodcpoj]
GO
USE [master]
GO
ALTER DATABASE [car_db] SET  READ_WRITE 
GO
