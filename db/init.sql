IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'VarsityBetsDB')
BEGIN
    CREATE DATABASE VarsityBetsDB;
END
GO

USE VarsityBetsDB;
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'UserProfiles' AND xtype = 'U')
BEGIN
    CREATE TABLE UserProfiles (
        FirebaseUid VARCHAR(512) PRIMARY KEY,
        FullName VARCHAR(255) NOT NULL,
        Username VARCHAR(255) NOT NULL,
        CreatedAt DATETIME NOT NULL DEFAULT GETUTCDATE()
    );
END
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'BetCategories' AND xtype = 'U')
BEGIN
    CREATE TABLE BetCategories (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Name VARCHAR(255) NOT NULL,
        Description VARCHAR(MAX),
        HexColor VARCHAR(7) DEFAULT '#2ECC71',
        IconName VARCHAR(100)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'BetSessions' AND xtype = 'U')
BEGIN
    CREATE TABLE BetSessions (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        UserProfileFirebaseUid VARCHAR(512) NOT NULL,
        BetCategoryId INT NOT NULL,
        BuyInAmount DECIMAL(18,2) NOT NULL,
        CashOutAmount DECIMAL(18,2) NOT NULL,
        StartTime DATETIME NOT NULL DEFAULT GETUTCDATE(),
        EndTime DATETIME NULL,

        CONSTRAINT FK_BetSessions_UserProfiles FOREIGN KEY (UserProfileFirebaseUid) 
            REFERENCES UserProfiles(FirebaseUid) ON DELETE CASCADE,
        CONSTRAINT FK_BetSessions_BetCategories FOREIGN KEY (BetCategoryId) 
            REFERENCES BetCategories(Id) ON DELETE CASCADE
    );
END
GO