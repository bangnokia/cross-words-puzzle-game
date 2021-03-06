USE [master]
GO
/****** Object:  Database [crossword572]    Script Date: 05/11/2015 5:20:34 SA ******/
CREATE DATABASE [crossword572]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'crossword572', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\crossword572.mdf' , SIZE = 3136KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'crossword572_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\crossword572_log.ldf' , SIZE = 784KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [crossword572] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [crossword572].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [crossword572] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [crossword572] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [crossword572] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [crossword572] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [crossword572] SET ARITHABORT OFF 
GO
ALTER DATABASE [crossword572] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [crossword572] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [crossword572] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [crossword572] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [crossword572] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [crossword572] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [crossword572] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [crossword572] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [crossword572] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [crossword572] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [crossword572] SET  ENABLE_BROKER 
GO
ALTER DATABASE [crossword572] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [crossword572] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [crossword572] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [crossword572] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [crossword572] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [crossword572] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [crossword572] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [crossword572] SET RECOVERY FULL 
GO
ALTER DATABASE [crossword572] SET  MULTI_USER 
GO
ALTER DATABASE [crossword572] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [crossword572] SET DB_CHAINING OFF 
GO
ALTER DATABASE [crossword572] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [crossword572] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
EXEC sys.sp_db_vardecimal_storage_format N'crossword572', N'ON'
GO
USE [crossword572]
GO
/****** Object:  Table [dbo].[admins]    Script Date: 05/11/2015 5:20:34 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[admins](
	[userid] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](35) NOT NULL,
	[password] [varchar](30) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[userid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[game]    Script Date: 05/11/2015 5:20:34 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[game](
	[gameid] [int] IDENTITY(1,1) NOT NULL,
	[gamename] [varchar](35) NOT NULL,
	[typeid] [int] NOT NULL,
	[status] [varchar](2) NULL,
PRIMARY KEY CLUSTERED 
(
	[gameid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gamecontents]    Script Date: 05/11/2015 5:20:34 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gamecontents](
	[gameid] [int] NOT NULL,
	[quesid] [int] IDENTITY(1,1) NOT NULL,
	[quesno] [int] NOT NULL,
	[direction] [bit] NULL,
	[startposition] [varchar](5) NULL,
	[quescontent] [nvarchar](200) NOT NULL,
	[quesanswer] [nvarchar](6) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[quesid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[scores]    Script Date: 05/11/2015 5:20:34 SA ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[scores](
	[scoresid] [int] IDENTITY(1,1) NOT NULL,
	[gameid] [int] NOT NULL,
	[playertime] [int] NOT NULL,
	[playername] [varchar](25) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[scoresid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[gamecontents]  WITH CHECK ADD  CONSTRAINT [FK_gamecontents_game] FOREIGN KEY([gameid])
REFERENCES [dbo].[game] ([gameid])
GO
ALTER TABLE [dbo].[gamecontents] CHECK CONSTRAINT [FK_gamecontents_game]
GO
ALTER TABLE [dbo].[scores]  WITH CHECK ADD  CONSTRAINT [FK_scores_game] FOREIGN KEY([gameid])
REFERENCES [dbo].[game] ([gameid])
GO
ALTER TABLE [dbo].[scores] CHECK CONSTRAINT [FK_scores_game]
GO
USE [master]
GO
ALTER DATABASE [crossword572] SET  READ_WRITE 
GO
