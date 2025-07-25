USE [master]
GO
/****** Object:  Database [renting_db]    Script Date: 6/20/2025 10:34:47 PM ******/
CREATE DATABASE [renting_db]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'renting_db', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\renting_db.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'renting_db_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\renting_db_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [renting_db] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [renting_db].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [renting_db] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [renting_db] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [renting_db] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [renting_db] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [renting_db] SET ARITHABORT OFF 
GO
ALTER DATABASE [renting_db] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [renting_db] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [renting_db] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [renting_db] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [renting_db] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [renting_db] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [renting_db] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [renting_db] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [renting_db] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [renting_db] SET  DISABLE_BROKER 
GO
ALTER DATABASE [renting_db] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [renting_db] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [renting_db] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [renting_db] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [renting_db] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [renting_db] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [renting_db] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [renting_db] SET RECOVERY FULL 
GO
ALTER DATABASE [renting_db] SET  MULTI_USER 
GO
ALTER DATABASE [renting_db] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [renting_db] SET DB_CHAINING OFF 
GO
ALTER DATABASE [renting_db] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [renting_db] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [renting_db] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [renting_db] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'renting_db', N'ON'
GO
ALTER DATABASE [renting_db] SET QUERY_STORE = ON
GO
ALTER DATABASE [renting_db] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [renting_db]
GO
/****** Object:  Table [dbo].[renting_detail]    Script Date: 6/20/2025 10:34:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[renting_detail](
	[carid] [int] NOT NULL,
	[renting_transactionid] [int] NOT NULL,
	[end_date] [date] NOT NULL,
	[price] [numeric](19, 4) NULL,
	[start_date] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[carid] ASC,
	[renting_transactionid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[renting_transaction]    Script Date: 6/20/2025 10:34:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[renting_transaction](
	[renting_transactionid] [int] IDENTITY(1,1) NOT NULL,
	[customerid] [int] NOT NULL,
	[renting_date] [date] NULL,
	[renting_status] [smallint] NULL,
	[total_price] [numeric](19, 4) NULL,
PRIMARY KEY CLUSTERED 
(
	[renting_transactionid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (1, 4, CAST(N'2025-06-09' AS Date), CAST(1800.0000 AS Numeric(19, 4)), CAST(N'2025-06-05' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (1, 10, CAST(N'2025-06-28' AS Date), CAST(3600.0000 AS Numeric(19, 4)), CAST(N'2025-06-20' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (4, 5, CAST(N'2025-06-03' AS Date), CAST(1140.0000 AS Numeric(19, 4)), CAST(N'2025-05-31' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (6, 1, CAST(N'2025-06-21' AS Date), CAST(2080.0000 AS Numeric(19, 4)), CAST(N'2025-06-17' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (7, 3, CAST(N'2025-06-13' AS Date), CAST(2250.0000 AS Numeric(19, 4)), CAST(N'2025-06-10' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (10, 6, CAST(N'2025-06-14' AS Date), CAST(1600.0000 AS Numeric(19, 4)), CAST(N'2025-06-12' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (11, 2, CAST(N'2025-06-22' AS Date), CAST(4500.0000 AS Numeric(19, 4)), CAST(N'2025-06-15' AS Date))
INSERT [dbo].[renting_detail] ([carid], [renting_transactionid], [end_date], [price], [start_date]) VALUES (14, 5, CAST(N'2025-06-03' AS Date), CAST(1740.0000 AS Numeric(19, 4)), CAST(N'2025-05-31' AS Date))
GO
SET IDENTITY_INSERT [dbo].[renting_transaction] ON 

INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (1, 1, CAST(N'2025-06-17' AS Date), 1, CAST(2080.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (2, 2, CAST(N'2025-06-15' AS Date), 1, CAST(4500.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (3, 1, CAST(N'2025-06-10' AS Date), 2, CAST(2250.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (4, 3, CAST(N'2025-06-05' AS Date), 2, CAST(1800.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (5, 2, CAST(N'2025-05-31' AS Date), 2, CAST(3480.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (6, 3, CAST(N'2025-06-12' AS Date), 3, CAST(1600.0000 AS Numeric(19, 4)))
INSERT [dbo].[renting_transaction] ([renting_transactionid], [customerid], [renting_date], [renting_status], [total_price]) VALUES (10, 3, CAST(N'2025-06-20' AS Date), 1, CAST(3600.0000 AS Numeric(19, 4)))
SET IDENTITY_INSERT [dbo].[renting_transaction] OFF
GO
ALTER TABLE [dbo].[renting_detail]  WITH CHECK ADD  CONSTRAINT [FK7s1ndg7gd51gn2g3idb77t8gw] FOREIGN KEY([renting_transactionid])
REFERENCES [dbo].[renting_transaction] ([renting_transactionid])
GO
ALTER TABLE [dbo].[renting_detail] CHECK CONSTRAINT [FK7s1ndg7gd51gn2g3idb77t8gw]
GO
USE [master]
GO
ALTER DATABASE [renting_db] SET  READ_WRITE 
GO
