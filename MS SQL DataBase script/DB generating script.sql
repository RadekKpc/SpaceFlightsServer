use master
IF NOT EXISTS (
   SELECT name
   FROM sys.databases
   WHERE name = 'SpaceFlights'
)
CREATE DATABASE SpaceFlights
GO
alter database SpaceFlights set query_store=on

use SpaceFlights

-- DELETING TABLES
ALTER TABLE FlightsReservation DROP CONSTRAINT FKFlightsRes462997;
ALTER TABLE FlightsReservation DROP CONSTRAINT FKFlightsRes358891;
ALTER TABLE FlightsReservation DROP CONSTRAINT CHK_RESERVATION_DATE;
ALTER TABLE Flights DROP CONSTRAINT CHK_DATE;
DROP TABLE FlightsReservation;
DROP TABLE Flights;
DROP TABLE Participants;

-- CREATING TABLES
CREATE TABLE Flights (FlightID int IDENTITY(1,1) NOT NULL, StartDate date NOT NULL, EndDate date NOT NULL, ParticipantCapacity int NOT NULL, Price money NOT NULL, PRIMARY KEY (FlightID),
CONSTRAINT CHK_DATE CHECK​ (​ DATEDIFF​ (​ day​ ,StartDate,EndDate) >= ​ 0​ ));

CREATE TABLE FlightsReservation (FlightsReservationID int NOT NULL IDENTITY(1,1), FlightID int NOT NULL, ParticipantID int NOT NULL, ReservationStartDate date NOT NULL, ReservationEndDate date NOT NULL, isPaid bit NOT NULL DEFAULT 0, PRIMARY KEY (FlightsReservationID),
CONSTRAINT CHK_RESERVATION_DATE CHECK​ (​ DATEDIFF​ (​ day​ ,ReservationStartDate,ReservationEndDate) >= ​ 0​ ));

CREATE TABLE Participants (ParticipantID int IDENTITY(1,1) NOT NULL, Name varchar(127) NOT NULL, Surname varchar(127) NOT NULL, Gender varchar(63) default NULL, Country varchar(63) NOT NULL, BirthDate date NOT NULL,Notes varchar(255) DEFAULT '', PRIMARY KEY (ParticipantID));

ALTER TABLE FlightsReservation ADD CONSTRAINT FKFlightsRes462997 FOREIGN KEY (ParticipantID) REFERENCES Participants (ParticipantID) ON DELETE CASCADE;
ALTER TABLE FlightsReservation ADD CONSTRAINT FKFlightsRes358891 FOREIGN KEY (FlightID) REFERENCES Flights (FlightID) ON DELETE CASCADE;


-- PROCEDURES
DROP PROCEDURE IF EXISTS AddParticipant

CREATE PROCEDURE AddParticipant @Name varchar(127), @Surname varchar(127), @Gender varchar(63), @Country varchar(63), @BirthDate date,@Notes varchar(255) AS
BEGIN
 SET NOCOUNT ON;
 BEGIN TRY
    INSERT INTO Participants(Name,Surname,Gender,Country,BirthDate,Notes) VALUES (@Name,@Surname,@Gender,@Country,@BirthDate,@Notes)
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Create Participant error: ' + ERROR_MESSAGE();
    ;THROW 52000,@msg, 1
 END CATCH
END

DROP PROCEDURE IF EXISTS RemoveParticipant

CREATE PROCEDURE RemoveParticipant @ParticipantID int AS
BEGIN
 SET NOCOUNT ON;
 BEGIN TRY
    IF NOT EXISTS
    (
        SELECT * FROM Participants WHERE ParticipantID = @ParticipantID
    )
    BEGIN
        ;THROW 52002 , 'Given Participant not exist',1
    END
	DELETE FROM Participants where ParticipantID = @ParticipantID
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Cannot delete Participant: Error message: ' + ERROR_MESSAGE();
    ;THROW 52002,@msg, 1
 END CATCH
END

DROP PROCEDURE IF EXISTS AddFlight

CREATE PROCEDURE AddFlight @StartDate date, @EndDate date, @ParticipantCapacity int, @Price money AS
BEGIN
 SET NOCOUNT ON;
 BEGIN TRY
    INSERT INTO Flights(StartDate,EndDate,ParticipantCapacity,Price) VALUES (@StartDate,@EndDate,@ParticipantCapacity,@Price)
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Create flight error: ' + ERROR_MESSAGE();
    ;THROW 52003,@msg, 1
 END CATCH
END

DROP PROCEDURE IF EXISTS RemoveFlight

CREATE PROCEDURE RemoveFlight @FlightID int AS
BEGIN
	SET NOCOUNT ON;
 BEGIN TRY
    IF NOT EXISTS
    (
        SELECT * FROM Flights WHERE FlightID = @FlightID
    )
    BEGIN
        ;THROW 52001 , 'Given Flight not exist',1
    END
	DELETE FROM Flights WHERE FlightID = @FlightID
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Cannot delete Flight: Error message: ' + ERROR_MESSAGE();
    ;THROW 52002,@msg, 1
 END CATCH
END


DROP PROCEDURE IF EXISTS AddReservation

CREATE PROCEDURE AddReservation @FlightID int,@ParticipantID int as
begin
 BEGIN TRY
    IF NOT EXISTS
    (
        SELECT * FROM Flights WHERE FlightID = @FlightID
    )
    BEGIN
        ;THROW 52001 , 'Given Flight not exist',1
    END

	IF NOT EXISTS
    (
        SELECT * FROM Participants WHERE ParticipantID = @ParticipantID
    )
    BEGIN
        ;THROW 52002 , 'Given Participant not exist',1
    END

	IF EXISTS
    (
        SELECT * FROM FlightsReservation WHERE ParticipantID = @ParticipantID AND FlightID = @FlightID
    )
    BEGIN
        ;THROW 52005 , 'Given Participant have already had a reservation for given flight',1
    END

	IF DATEDIFF(day,getDate(),(SELECT StartDate from Flights where FlightID = @FlightID)) < 0
    BEGIN
        ;THROW 52005 , 'this flight have already begun',1
    END

	IF dbo.FreePlaces(@FlightID) = 0
    BEGIN
        ;THROW 52007 , 'There is no place for this flight',1
    END

	INSERT INTO FlightsReservation(FlightID,ParticipantID,ReservationStartDate,ReservationEndDate) 
	VALUES (@FlightID,@ParticipantID,getDATE(),
	dbo.getEndReservationDate(@FlightID,getDate()))

 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Cannot add reservation: Error message: ' + ERROR_MESSAGE();
    ;THROW 52007,@msg, 1
 END CATCH
END

DROP PROCEDURE IF EXISTS RemoveReservation

CREATE PROCEDURE RemoveReservation @FlightsReservationID int AS
BEGIN
 BEGIN TRY
    IF NOT EXISTS
    (
        SELECT * FROM FlightsReservation WHERE FlightsReservationID = @FlightsReservationID
    )
    BEGIN
        ;THROW 52001 , 'Given reservation not exist',1
    END
	DELETE FROM FlightsReservation WHERE FlightsReservationID = @FlightsReservationID
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Cannot delete Reservation: Error message: ' + ERROR_MESSAGE();
    ;THROW 52002,@msg, 1
 END CATCH
END

DROP PROCEDURE IF EXISTS UpdateReservationToPaid
CREATE PROCEDURE UpdateReservationToPaid @FlightsReservationID int AS
BEGIN
 BEGIN TRY
    IF NOT EXISTS
    (
        SELECT * FROM FlightsReservation WHERE FlightsReservationID = @FlightsReservationID
    )
    BEGIN
        ;THROW 52001 , 'Given reservation not exist',1
    END
	UPDATE FlightsReservation SET isPaid=1 where FlightsReservationID =@FlightsReservationID
 END TRY
 BEGIN CATCH
    DECLARE @msg varchar(2048) = 'Cannot update Reservation: Error message: ' + ERROR_MESSAGE();
    ;THROW 52002,@msg, 1
 END CATCH
END

--FUNCTIONS
--Reservation is active 31 days ,but in case when to start of flight is less than 31 days reservation is active to flightDay
DROP FUNCTION IF EXISTS getEndReservationDate
CREATE FUNCTION getEndReservationDate (@FlightID int , @ReservationDay date) RETURNS date as
begin
	declare @filghtDay date = (SELECT StartDate from Flights where FlightID = @FlightID)
	IF DATEDIFF(DAY,@ReservationDay,@filghtDay) <= 31
	BEGIN
		RETURN @filghtDay
	END
	RETURN DATEADD(day,31,@ReservationDay)
end

--Fucntion which return nubmer of free places in filght
DROP FUNCTION IF EXISTS FreePlaces
CREATE FUNCTION FreePlaces ( @FlightID int) RETURNS integer as
begin
 declare @capacity integer = (select ParticipantCapacity FROM Flights where FlightID = @FlightID)
 
 declare @reservPlaces integer = (select count(*) FROM FlightsReservation 
 WHERE FlightID = @FlightID and DATEDIFF​(​ day​ ,getDate(),ReservationEndDate) >= ​ 0​ and isPaid = 0)
 
 declare @PaidPlaces integer = (select count(*) FROM FlightsReservation 
 WHERE FlightID = @FlightID and isPaid != 0)
 
 return @capacity - @reservPlaces - @PaidPlaces
end

DROP FUNCTION IF EXISTS TimeCollision
CREATE FUNCTION TimeCollision(@Flight1 int, @Flight2 int) RETURNS integer as
begin
	declare @start1 int = DATEDIFF(day,'01-01-2000',(Select StartDate from Flights where FlightID = @Flight1))
	declare @start2 int = DATEDIFF(day,'01-01-2000',(Select StartDate from Flights where FlightID = @Flight2))
	declare @end1 int = DATEDIFF(day,'01-01-2000',(Select EndDate from Flights where FlightID = @Flight1))
	declare @end2 int = DATEDIFF(day,'01-01-2000',(Select EndDate from Flights where FlightID = @Flight2))

	IF @start1 < @start2 and @start2 < @end1
	return  1
	IF @start2 < @start1 and @start1 < @end2
	return  1
	IF @start1 >= @start2 and @end2 >= @end1
	return  1
	IF @start2 >= @start1 and @end1 >= @end2
	return  1

	return 0
end

--TRIGGERS
--Triger which check is user already in another flight at the same time
DROP TRIGGER  IF EXISTS CheckFlightsCollision
CREATE TRIGGER CheckFlightsCollision on FlightsReservation AFTER INSERT AS
begin
	set NOCOUNT ON;

	IF EXISTS(
	SELECT * FROM  FlightsReservation fr
	where fr.FlightID != (SELECT FlightID from inserted)
	and fr.ParticipantID = (SELECT ParticipantID from inserted)
	and dbo.TimeCollision((SELECT FlightID from inserted),fr.FlightID) = 1
	
	)
	BEGIN 
	; THROW 50010, 'User already have another flight at the same time ',1
	end
end

--VIEWS
CREATE VIEW Flihgts AS 
	SELECT * FROM Flights

CREATE VIEW Participants AS 
	SELECT * FROM Participants

CREATE VIEW UnpaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 0

CREATE VIEW PaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 1

DROP VIEW IF EXISTS AcitveUnpaidReservations;
CREATE VIEW AcitveUnpaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 0 and DATEDIFF(day,getDate(),fr.ReservationEndDate) >= 0;

DROP VIEW IF EXISTS AcitvePaidReservations;
CREATE VIEW AcitvePaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 1 and DATEDIFF(day,getDate(),fr.ReservationEndDate) >= 0;

DROP VIEW IF EXISTS UnactiveUnpaidReservations;
CREATE VIEW UnactiveUnpaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 0 and DATEDIFF(day,getDate(),fr.ReservationEndDate) < 0;

DROP VIEW IF EXISTS	UnactivePaidReservations;
CREATE VIEW UnactivePaidReservations AS
	SELECT fr.FlightsReservationID,fr.FlightID,fr.ParticipantID,fr.isPaid,fr.ReservationStartDate,fr.ReservationEndDate,p.Name,p.Surname,p.Gender,p.Country,p.BirthDate,f.StartDate,f.EndDate,f.Price,f.ParticipantCapacity FROM FlightsReservation fr
	JOIN Flights f on f.FlightID = fr.FlightID
	join Participants p on p.ParticipantID = fr.ParticipantID
	WHERE isPaid = 0 and DATEDIFF(day,getDate(),fr.ReservationEndDate) < 0;

use SpaceFlights
select * from Flights
select * from Participants
select * from FlightsReservation
UPDATE FlightsReservation SET isPaid=1 where FlightsReservationID =1
exec RemoveParticipant @ParticipantId=1
exec AddParticipant @Name = 'sd',@Surname='sda',@Gender='asds',@Country='asda',@BirthDate='12.12.1999',@Notes='123sac'
exec AddFlight @StartDate = '12.12.1999',@EndDate='12.12.2000',@ParticipantCapacity=12,@Price=12312


exec RemoveFlight @FlightID=5
exec AddFlight @StartDate = 07-18-2020,@EndDate='12-12-2030',@ParticipantCapacity=3,@Price=12312
exec AddFlight @StartDate = '01-03-2020',@EndDate='12.12.2030',@ParticipantCapacity=2,@Price=12312
exec AddFlight @StartDate = '01-01-2021',@EndDate='12.12.2030',@ParticipantCapacity=3,@Price=12312
exec AddFlight @StartDate = '01.01.2020',@EndDate='12.12.2030',@ParticipantCapacity=12,@Price=12312
exec AddFlight @StartDate = '01.01.2020',@EndDate='12.12.2030',@ParticipantCapacity=12,@Price=12312

exec AddFlight @StartDate = '02.17.2020',@EndDate='12.12.2030',@ParticipantCapacity=12,@Price=12312
exec AddFlight @StartDate = '02.18.2020',@EndDate='12.12.2030',@ParticipantCapacity=12,@Price=12312
exec AddFlight @StartDate = '01.01.2032',@EndDate='12.12.2033',@ParticipantCapacity=12,@Price=12312
exec AddReservation @FlightID=1,@ParticipantID=6

select * from Flights
select * from Participants
select dbo.FreePlaces(24) from flights
