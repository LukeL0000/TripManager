/*drop table branch;

CREATE TABLE branch (
	branch_id integer not null PRIMARY KEY,
	branch_name varchar2(20) not null,
	branch_addr varchar2(50),
	branch_city varchar2(20) not null,
	branch_phone integer 
);


INSERT INTO branch VALUES (1, 'ABC', '123 varcharming Ave', 'Vancouver', '6041234567');
INSERT INTO branch VALUES (2, 'DEF', '123 Coco Ave', 'Vancouver', '6044567890');
INSERT INTO People (Name, UserID) VALUES ('123', '456');



BEGIN

    FOR c IN (SELECT table_name FROM user_tables) LOOP
            EXECUTE IMMEDIATE ('DROP TABLE '' || c.table_name || '' CASCADE CONSTRAINTS');
        END LOOP;

    FOR s IN (SELECT sequence_name FROM user_sequences) LOOP
            EXECUTE IMMEDIATE ('DROP SEQUENCE ' || s.sequence_name);
        END LOOP;

END;*/


drop table Equipment;
drop table TakeTo;
drop table Bring;
drop table LinesUp;
drop TABLE SupplyCost;
drop TABLE TransportCost;
drop TABLE EquipmentModels;
drop TABLE EquipmentCost;
drop TABLE ActivityCost;
drop table QualifiesFor;
drop TABLE TransportModels;
drop table Transportation;
drop table Requires;
drop TABLE SupplyModel;
drop TABLE Does;
drop table Recommends;
drop table TripEvent;
drop table Activity;
drop table Schedule;
drop table Supplies;
drop table GoOn;
drop table Has;
drop table Certifications;
drop table Conditions;
drop table Trip;
drop table Location;
drop table People;



CREATE TABLE Transportation (
    EquipmentID varchar(80) PRIMARY KEY,
    Type varchar(80) NOT NULL,
    Model varchar(80) NOT NULL,
    Cost DOUBLE PRECISION DEFAULT 0,
    UNIQUE (Type, Model)
);

CREATE TABLE Supplies (
    EquipmentID varchar(80) PRIMARY KEY,
    Type varchar(80) NOT NULL,
    Model varchar(80) NOT NULL,
    Cost DOUBLE PRECISION DEFAULT 0,
    UNIQUE (Type, Model)
);

CREATE TABLE Schedule (
                          startTime TIMESTAMP,
                          endTime TIMESTAMP,
                          PRIMARY KEY (startTime, endTime)
);

CREATE TABLE Conditions (
                            ConditionsID varchar(80) PRIMARY KEY,
                            Rules varchar(80) UNIQUE,
                            TerrainType  varchar(80) UNIQUE,
                            Weather varchar(80) UNIQUE,
                            Hazards varchar(80) UNIQUE
);

CREATE TABLE Activity (
    ActivityName varchar(80) PRIMARY KEY
);

CREATE TABLE People (
                        Name varchar(60) NOT NULL,
                        UserID varchar(60) PRIMARY KEY
);

CREATE TABLE Certifications (
    CertificationName varchar(60) PRIMARY KEY
);

CREATE TABLE SupplyModel(
                            Model varchar(60) PRIMARY KEY,
                            Type varchar(40)
);

CREATE TABLE SupplyCost(
                           EquipmentID varchar(80) PRIMARY KEY,
                           Model varchar(40),
                           Cost double precision DEFAULT 0
);

CREATE TABLE TransportModels(
                                Model varchar(80) PRIMARY KEY,
                                Type varchar(40)
);

CREATE TABLE TransportCost(
                              EquipmentID varchar(80) PRIMARY KEY,
                              Model varchar(40),
                              Cost double precision DEFAULT 0
);

CREATE TABLE Location (
                          Name varchar(80),
                          Latitude double precision,
                          Longitude double precision,
                          PRIMARY KEY (Name, Latitude, Longitude)
);

CREATE TABLE EquipmentCost(
                              supplyEquipmentID    varchar(80) DEFAULT '____',
                              transportEquipmentID varchar(80) DEFAULT '____',
                              equipmentName varchar(80) PRIMARY KEY,
                              Cost double precision DEFAULT 0
);

CREATE TABLE ActivityCost(
                             activityID varchar(80) PRIMARY KEY ,
                             activityName varchar(80) DEFAULT '____',
                             Cost double precision DEFAULT 0
);

CREATE TABLE TakeTo (
    EquipmentID varchar(80),
    UserID varchar(80),
    FromName varchar(80),
    FromLat DOUBLE PRECISION,
    FromLong DOUBLE PRECISION,
    ToName varchar(80),
    ToLat DOUBLE PRECISION,
    ToLong DOUBLE PRECISION,
    /*FOREIGN KEY(EquipmentID) REFERENCES Transportation(EquipmentID) ON DELETE CASCADE,
    FOREIGN KEY(UserID) REFERENCES People(UserID) ON DELETE CASCADE,
    FOREIGN KEY(FromName, FromLat, FromLong) REFERENCES Location(Name, Latitude, Longitude) ON DELETE CASCADE,
    FOREIGN KEY(ToName, ToLat, ToLong) REFERENCES Location(Name, Latitude, Longitude) ON DELETE CASCADE,*/
    PRIMARY KEY(EquipmentID, UserID, FromName, FromLat, FromLong, ToName, ToLat, ToLong)
);

CREATE TABLE Bring (
    UserID varchar(80),
    EquipmentID varchar(80),
    Quantity INT DEFAULT 1,
    PRIMARY KEY(UserID, EquipmentID, QUANTITY)
    /*FOREIGN KEY(UserID) REFERENCES People(UserID) ON
       DELETE CASCADE,
    FOREIGN KEY(EquipmentID) REFERENCES
       Supplies(EquipmentID) ON DELETE CASCADE*/
);

CREATE TABLE Trip (
                      name varchar(80),
                      tripID varchar(80) PRIMARY KEY,
                      organizerID varchar(80),
                      locationName varchar(80) NOT NULL,
                      Latitude double precision NOT NULL,
                      Longitude double precision NOT NULL,
                      FOREIGN KEY (organizerID) REFERENCES People(userID) ON DELETE CASCADE,
                      FOREIGN KEY (locationName, Latitude, Longitude) REFERENCES Location(Name, Latitude, Longitude) ON DELETE CASCADE
);

CREATE TABLE LinesUp (
    StartTime TIMESTAMP,
    EndTime TIMESTAMP,
    UserID varchar(80),
    ConditionID varchar(80),
    TripID varchar(80),
    PRIMARY KEY(StartTime, EndTime, UserID, conditionID, TripID)/*,
    FOREIGN KEY(StartTime, EndTime) REFERENCES Schedule(startTime, endTime) ON DELETE CASCADE,
    FOREIGN KEY(UserID) REFERENCES People (UserID) ON DELETE CASCADE,
    FOREIGN KEY(conditionID) REFERENCES Conditions(ConditionsID) ON DELETE CASCADE,
    FOREIGN KEY(TripID) REFERENCES Trip(TripID) ON DELETE CASCADE*/
);



CREATE TABLE Recommends (
    ActivityName varchar(80),
    ConditionsID varchar(80),
    PRIMARY KEY (ActivityName, ConditionsID)/*,
    FOREIGN KEY (ActivityName) REFERENCES Activity(ActivityName) ON DELETE CASCADE,
    FOREIGN KEY (ConditionsID) REFERENCES Conditions(ConditionsID) ON DELETE CASCADE*/
);

CREATE TABLE QualifiesFor (
    UserID varchar(80),
    Certification varchar(80),
    PRIMARY KEY (UserID, Certification),
    FOREIGN KEY (UserID) REFERENCES PEOPLE(UserID) ON DELETE CASCADE,
    FOREIGN KEY (Certification) REFERENCES Certifications(CertificationName) ON DELETE CASCADE
);

CREATE TABLE Requires (
    requirementID varchar(128) PRIMARY KEY,
    conditionID varchar(80),
    CertificationName varchar(80),
    EquipmentID varchar(80),
    userID varchar(80),
    activityName varchar(80)/*,
    FOREIGN KEY (conditionID) REFERENCES conditions ON DELETE CASCADE,
    FOREIGN KEY (CertificationName) REFERENCES Certifications ON DELETE CASCADE,
    FOREIGN KEY (EquipmentID) REFERENCES Supplies ON DELETE CASCADE,
    FOREIGN KEY (activityName) REFERENCES Activity ON DELETE CASCADE,
    UNIQUE (conditionID, equipmentID, userID, activityName)*/
);



CREATE TABLE Has (
    Name varchar(80),
    Latitude double precision,
    Longitude double precision,
    conditionID varchar(80),
    PRIMARY KEY (Name, Latitude, Longitude, conditionID)/*,
    FOREIGN KEY (Name, Latitude, Longitude) REFERENCES Location(Name, Latitude, Longitude) ON DELETE CASCADE,
    FOREIGN KEY (ConditionID) REFERENCES Conditions(ConditionsID) ON DELETE CASCADE*/
);

CREATE TABLE GoOn (
    UserID varchar(80),
    TripID varchar(80),
    PRIMARY KEY (UserID, TripID),
    FOREIGN KEY(UserID) REFERENCES People(UserID) ON DELETE CASCADE,
    FOREIGN KEY(TripID) REFERENCES Trip(TripID) ON DELETE CASCADE
);

CREATE TABLE Does (
    TripID varchar(80),
    ActivityName varchar(80),
    ConditionID varchar(80),
    Cost double precision DEFAULT 0,
    PRIMARY KEY(TripID, ActivityName, ConditionID),
    FOREIGN KEY (ActivityName) REFERENCES Activity(ActivityName) ON DELETE CASCADE,
    FOREIGN KEY (ConditionID) REFERENCES Conditions(ConditionsID) ON DELETE CASCADE
);



CREATE TABLE EquipmentModels(
    supplyModel varchar(80) DEFAULT '____',
    transportModel varchar(80) DEFAULT '____',
    Type varchar(40),
    PRIMARY KEY(supplyModel,transportModel),
    FOREIGN KEY(transportModel) REFERENCES TransportModels(Model) ON DELETE CASCADE,
    FOREIGN KEY(supplyModel) REFERENCES SupplyModel(Model) ON DELETE CASCADE
);


CREATE TABLE Equipment (
                           transportEquipmentID varchar(80),
                           supplyEquipmentID varchar(80),
                           Type varchar(40) NOT NULL,
                           Model varchar(40) NOT NULL,
                           Cost DOUBLE PRECISION DEFAULT 0,
                           UNIQUE (Type, Model),
                           PRIMARY KEY(transportEquipmentID, supplyEquipmentID),
                           FOREIGN KEY(transportEquipmentID) REFERENCES Transportation(EquipmentID) ON DELETE CASCADE,
                           FOREIGN KEY(supplyEquipmentID) REFERENCES Supplies(EquipmentID) ON DELETE CASCADE
);

CREATE TABLE TripEvent (
    TripID varchar(80),
    ActivityName varchar(80),
    OrganizerID varchar(80),
    ConditionsID varchar(80),
    Latitude double precision,
    Longitude double precision,
    PRIMARY KEY(TripID, ActivityName)
    /*
    FOREIGN KEY(TripID) REFERENCES Trip(TripID) ON DELETE CASCADE,
    FOREIGN KEY(ActivityName) REFERENCES Activity(ActivityName) ON DELETE CASCADE,
    FOREIGN KEY(ConditionsID) REFERENCES Conditions(ConditionsID) ON DELETE CASCADE*/
);

INSERT INTO Activity VALUES ('Backcountry_Skiing______________');
INSERT INTO Activity VALUES ('Alpine_Hiking___________________');
INSERT INTO Activity VALUES ('Restaurant_Outing_______________');
INSERT INTO Activity VALUES ('Beach_Picnic____________________');
INSERT INTO Activity VALUES ('Group_BBQ_______________________');

INSERT INTO Certifications VALUES ('Avalanche_Training______________');
INSERT INTO Certifications VALUES ('Mountaineer_Instructor__________');
INSERT INTO Certifications VALUES ('Lifeguard_______________________');
INSERT INTO Certifications VALUES ('Ski_Instructor__________________');
INSERT INTO Certifications VALUES ('Class_5_Drivers_License_________');

INSERT INTO People VALUES ('John_Smith', 'John_Smith______________________09F65B190101');
INSERT INTO People VALUES ('Katherine_Yu', 'Katherine_Yu____________________77217F201100');
INSERT INTO People VALUES ('Kobayashi_Ryuutarou', 'Kobayashi_Ryuutarou_____________20818ABC0111');
INSERT INTO People VALUES ('Donald_Trump', 'Donald_Trump____________________44121F241010');
INSERT INTO People VALUES ('Stor_Gendibal', 'Stor_Gendibal___________________09218F880011');

INSERT INTO Location VALUES ('Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO Location VALUES ('Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027);
INSERT INTO Location VALUES ('YVR_Domestic_Departures_________', 49.19307413422936, -123.18073016233866);
INSERT INTO Location VALUES ('YYZ_International_Arrivals______', 43.685978318504255, -79.6202378487424);
INSERT INTO Location VALUES ('Ripleys_Aquarium________________', 43.6422172618848, -79.38657001182602);

INSERT INTO SupplyModel VALUES ('Spaghetti_Pomodoro_W/_Meatballs_', 'Food____');
INSERT INTO SupplyModel VALUES ('Petzl_Glacier_Ice_Axe___________', 'MtnAxe__');
INSERT INTO SupplyModel VALUES ('Keen_Pyrenees_Hiking_Boots______', 'HikeShoe');
INSERT INTO SupplyModel VALUES ('2023_Nordica_Enforcer_104_Ultima', 'TourSkis');
INSERT INTO SupplyModel VALUES ('Zwilling_Tradition_8in_Chefs_Kni', 'ChfKnife');
INSERT INTO SupplyModel VALUES ('______________________________', '________');

INSERT INTO TransportModels VALUES ('________________________________', '________');
INSERT INTO TransportModels VALUES ('2021_Chevrolet_Malibu_RS________', 'Car_____');
INSERT INTO TransportModels VALUES ('Coach_LongHaul__________________', 'Bus_____');
INSERT INTO TransportModels VALUES ('Shinkansen______________________', 'Train___');
INSERT INTO TransportModels VALUES ('Boeing_737_MAX_8________________', 'Aircraft');
INSERT INTO TransportModels VALUES ('Nakamura_Royal_700C_Hybrid_Bike_', 'Bicycle_');

INSERT INTO EquipmentModels VALUES ('Spaghetti_Pomodoro_W/_Meatballs_', '2021_Chevrolet_Malibu_RS________', 'Car_____');
INSERT INTO EquipmentModels VALUES ('Petzl_Glacier_Ice_Axe___________', 'Coach_LongHaul__________________', 'Aircraft');
INSERT INTO EquipmentModels VALUES ('Keen_Pyrenees_Hiking_Boots______', 'Shinkansen______________________', 'Train___');
INSERT INTO EquipmentModels VALUES ('Zwilling_Tradition_8in_Chefs_Kni', 'Boeing_737_MAX_8________________', 'MtnAxe__');
INSERT INTO EquipmentModels VALUES ('Zwilling_Tradition_8in_Chefs_Kni', 'Nakamura_Royal_700C_Hybrid_Bike_', 'Food____');

INSERT INTO Schedule VALUES ('2023-06-15 11:00:00', '2023-06-15 11:00:00');
INSERT INTO Schedule VALUES ('2023-06-22 12:00:00', '2023-06-22 16:00:00');
INSERT INTO Schedule VALUES ('2023-06-11 13:00:00', '2023-06-16 17:00:00');
INSERT INTO Schedule VALUES ('2023-06-17 14:00:00', '2023-06-19 18:00:00');
INSERT INTO Schedule VALUES ('2023-07-12 15:00:00', '2023-07-14 19:00:00');

INSERT INTO QualifiesFor VALUES ('John_Smith______________________09F65B190101', 'Avalanche_Training______________');
INSERT INTO QualifiesFor VALUES ('Katherine_Yu____________________77217F201100', 'Mountaineer_Instructor__________');
INSERT INTO QualifiesFor VALUES ('Kobayashi_Ryuutarou_____________20818ABC0111', 'Lifeguard_______________________');
INSERT INTO QualifiesFor VALUES ('Donald_Trump____________________44121F241010', 'Ski_Instructor__________________');
INSERT INTO QualifiesFor VALUES ('Stor_Gendibal___________________09218F880011', 'Class_5_Drivers_License_________');

INSERT INTO EquipmentCost VALUES ('________________________________________________', 'Car_____2021_Chevrolet_Malibu_RS________7FC808F1', '2021_Chevrolet_Malibu_RS________', 29300);
INSERT INTO EquipmentCost VALUES ('________________________________________________', 'AircraftBoeing_737_MAX_8________________4FC06209', 'Boeing_737_MAX_8________________', 0);
INSERT INTO EquipmentCost VALUES ('________________________________________________', 'Train___Shinkansen______________________7C88AA28', 'Shinkansen______________________', 0);
INSERT INTO EquipmentCost VALUES ('Food____Spaghetti_Pomodoro_W/_Meatballs_7BBEF873', '________________________________________________', 'Spaghetti_Pomodoro_W/_Meatballs_', 12.75);
INSERT INTO EquipmentCost VALUES ('MtnAxe__Petzl_Glacier_Ice_Axe___________578AAF03', '________________________________________________', 'Petzl_Glacier_Ice_Axe___________', 129.99);

INSERT INTO TransportCost VALUES ('Car_____2021_Chevrolet_Malibu_RS________7FC808F1', '2021_Chevrolet_Malibu_RS________', 29300);
INSERT INTO TransportCost VALUES ('Bus_____Coach_LongHaul__________________696C45BB', 'Coach_LongHaul__________________', 0);
INSERT INTO TransportCost VALUES ('Train___Shinkansen______________________7C88AA28', 'Shinkansen______________________',0);
INSERT INTO TransportCost VALUES ('AircraftBoeing_737_MAX_8________________4FC06209', 'Boeing_737_MAX_8________________', 0);
INSERT INTO TransportCost VALUES ('Bicycle_Nakamura_Royal_700C_Hybrid_Bike_1D8EFF29', 'Nakamura_Royal_700C_Hybrid_Bike_', 354.99);

INSERT INTO SupplyCost VALUES ('Food____Spaghetti_Pomodoro_W/_Meatballs_7BBEF873', 'Spaghetti_Pomodoro_W/_Meatballs_', 12.75);
INSERT INTO SupplyCost VALUES ('MtnAxe__Petzl_Glacier_Ice_Axe___________578AAF03', 'Petzl_Glacier_Ice_Axe___________', 129.99);
INSERT INTO SupplyCost VALUES ('HikeShoeKeen_Pyrenees_Hiking_Boots______27B95032', 'Keen_Pyrenees_Hiking_Boots______', 249.99);
INSERT INTO SupplyCost VALUES ('TourSkis2023_Nordica_Enforcer_104_Ultima162A981C', '2023_Nordica_Enforcer_104_Ultima', 981.42);
INSERT INTO SupplyCost VALUES ('ChfKnifeZwilling_Tradition_8in_Chefs_Kni59BEB8AC', 'Zwilling_Tradition_8in_Chefs_Kni', 169.99);

INSERT INTO Conditions VALUES ('Sunny_25C_Treeline_Dehydration______________0FFF', 'Sunny_25C_______________________', 'Treeline________________________', NULL,  'Dehydration_____________________');
INSERT INTO Conditions VALUES ('Cloudy_12C_Alpine___________________________2FF8', 'Cloudy_12C______________________', 'Alpine__________________________', NULL, NULL);
INSERT INTO Conditions VALUES ('Sunny_-5C_Alpine_Avalanche___________________711A', 'Sunny_-5C_______________________', 'Outdoor_________________________', NULL, 'Avalanche________________________');
INSERT INTO Conditions VALUES ('Rainy_15C_Indoors_No_Smoking________________8AAB', 'Rain_15C________________________', 'Indoors_________________________', 'No_Smoking______________________', NULL);
INSERT INTO Conditions VALUES ('Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1', 'Snow_-2C________________________', 'Urban___________________________', 'Speed_limit_50__________________', 'Black_Ice_______________________');

INSERT INTO ActivityCost VALUES ('Backcountry_Skiing______________', '5C_Alpine_Avalance___________________711A', 70.99);
INSERT INTO ActivityCost VALUES ('Alpine_Hiking___________________', 'Cloudy_12C_Alpine___________________________2FF8', 69.99);
INSERT INTO ActivityCost VALUES ('Restaurant_Outing_______________', 'Rainy_15C_Indoors_No_Smoking________________8AAB', 52.99);
INSERT INTO ActivityCost VALUES ('Beach_Picnic____________________', 'Sunny_25C_Treeline_Dehydration______________0FFF', 45.95);
INSERT INTO ActivityCost VALUES ('Group_BBQ_______________________', 'Sunny_25C_Treeline_Dehydration______________0FFF', 100.99);

INSERT INTO Trip VALUES('Rubble_Creek_Trip', 'Rubble_Creek_Trip________1A2B', 'John_Smith______________________09F65B190101', 'Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO Trip VALUES('Rubble_Creek_Trip', 'Rubble_Creek_Trip________2A3B', 'Katherine_Yu____________________77217F201100', 'Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO Trip VALUES('Rubble_Creek_Trip', 'Rubble_Creek_Trip________3A4B', 'Kobayashi_Ryuutarou_____________20818ABC0111', 'Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO Trip VALUES('Metro_Vacay', 'Metro_Vacay_________________________________________________F2AC', 'Donald_Trump____________________44121F241010',  'Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027);
INSERT INTO Trip VALUES('Metro_Vacay', 'Metro_Vacay_________________________________________________G2BC', 'Stor_Gendibal___________________09218F880011',  'Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027);

INSERT INTO GoOn VALUES ('John_Smith______________________09F65B190101', 'Rubble_Creek_Trip________1A2B');
INSERT INTO GoOn VALUES ('Katherine_Yu____________________77217F201100', 'Rubble_Creek_Trip________2A3B');
INSERT INTO GoOn VALUES ('Kobayashi_Ryuutarou_____________20818ABC0111', 'Rubble_Creek_Trip________2A3B');
INSERT INTO GoOn VALUES ('Donald_Trump____________________44121F241010', 'Metro_Vacay_________________________________________________F2AC');
INSERT INTO GoOn VALUES ('Stor_Gendibal___________________09218F880011', 'Metro_Vacay_________________________________________________F2AC');
INSERT INTO GoOn VALUES ('Katherine_Yu____________________77217F201100', 'Metro_Vacay_________________________________________________F2AC');
INSERT INTO GoOn VALUES ('Katherine_Yu____________________77217F201100', 'Metro_Vacay_________________________________________________G2BC');
INSERT INTO GoOn VALUES ('Katherine_Yu____________________77217F201100', 'Rubble_Creek_Trip________1A2B');
INSERT INTO GoOn VALUES ('Katherine_Yu____________________77217F201100', 'Rubble_Creek_Trip________3A4B');

INSERT INTO TakeTo VALUES ('Car_____2021_Chevrolet_Malibu_RS________7FC808F1', 'John_Smith______________________09F65B190101', 'Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027, 'Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO TakeTo VALUES ('AircraftBoeing_737_MAX_8________________4FC06209', 'Katherine_Yu____________________77217F201100', 'YVR_Domestic_Departures_________', 49.19307413422936, -123.18073016233866, 'YYZ_International_Arrivals______', 43.685978318504255, -79.6202378487424);
INSERT INTO TakeTo VALUES ('AircraftBoeing_737_MAX_8________________4FC06209', 'Stor_Gendibal___________________09218F880011', 'YVR_Domestic_Departures_________', 49.19307413422936, -123.18073016233866, 'YYZ_International_Arrivals______', 43.685978318504255, -79.6202378487424);
INSERT INTO TakeTo VALUES ('AircraftBoeing_737_MAX_8________________4FC06209', 'Donald_Trump____________________44121F241010', 'YVR_Domestic_Departures_________', 49.19307413422936, -123.18073016233866, 'YYZ_International_Arrivals______', 43.685978318504255, -79.6202378487424);
INSERT INTO TakeTo VALUES ('Bicycle_Nakamura_Royal_700C_Hybrid_Bike_1D8EFF29', 'Donald_Trump____________________44121F241010', 'YVR_Domestic_Departures_________', 49.19307413422936, -123.18073016233866, 'Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027);


INSERT INTO Bring VALUES ('John_Smith______________________09F65B190101', 'Food____Spaghetti_Pomodoro_W/_Meatballs_7BBEF873', 10);
INSERT INTO Bring VALUES ('Donald_Trump____________________44121F241010', 'MtnAxe__Petzl_Glacier_Ice_Axe___________578AAF03', 5);
INSERT INTO Bring VALUES ('Katherine_Yu____________________77217F201100', 'HikeShoeKeen_Pyrenees_Hiking_Boots______27B95032', 5);
INSERT INTO Bring VALUES ('Stor_Gendibal___________________09218F880011', 'TourSkis2023_Nordica_Enforcer_104_Ultima162A981C', 3);
INSERT INTO Bring VALUES ('Kobayashi_Ryuutarou_____________20818ABC0111', 'ChfKnifeZwilling_Tradition_8in_Chefs_Kni59BEB8AC', 1);

INSERT INTO LinesUp VALUES ('2023-06-15 11:00:00', '2023-06-15 11:00:00', 'John_Smith______________________09F65B190101', 'Rubble_Creek_Trip___________________________________________02AB', 'Sunny_25C_Treeline_Dehydration______________0FFF');
INSERT INTO LinesUp VALUES ('2023-06-22 12:00:00', '2023-06-22 16:00:00', 'Katherine_Yu____________________77217F201100', 'Rubble_Creek_Trip___________________________________________004F', 'Sunny_25C_Treeline_Dehydration______________0FFF');
INSERT INTO LinesUp VALUES ('2023-06-11 13:00:00', '2023-06-16 17:00:00', 'Kobayashi_Ryuutarou_____________20818ABC0111', 'Rubble_Creek_Trip___________________________________________03FD', 'Sunny_25C_Treeline_Dehydration______________0FFF');
INSERT INTO LinesUp VALUES ('2023-06-17 14:00:00', '2023-06-19 18:00:00', 'Donald_Trump____________________44121F241010', 'Metro_Vacay_________________________________________________F2AC', 'Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1');
INSERT INTO LinesUp VALUES ('2023-07-12 15:00:00', '2023-07-14 19:00:00', 'Stor_Gendibal___________________09218F880011', 'Metro_Vacay_________________________________________________EB10', 'Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1');

INSERT INTO Has VALUES ('Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031, 'Cloudy_12C_Alpine___________________________2FF8');
INSERT INTO Has VALUES ('Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031, 'Sunny_-5C_Alpine_Avalance___________________711A');
INSERT INTO Has VALUES ('Ripleys_Aquarium________________', 43.6422172618848, -79.38657001182602, 'Rainy_15C_Indoors_No_Smoking________________8AAB');
INSERT INTO Has VALUES ('Uniqlo_Metrotown________________', 49.226006522343006, -122.99937635552027, 'Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1');
INSERT INTO Has VALUES ('Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031, 'Sunny_25C_Treeline_Dehydration______________0FFF');

INSERT INTO Recommends VALUES ('Backcountry_Skiing______________', 'Sunny_-5C_Alpine_Avalance___________________711A');
INSERT INTO Recommends VALUES ('Alpine_Hiking___________________', 'Cloudy_12C_Alpine___________________________2FF8');
INSERT INTO Recommends VALUES ('Restaurant_Outing_______________', 'Rainy_15C_Indoors_No_Smoking________________8AAB');
INSERT INTO Recommends VALUES ('Beach_Picnic____________________', 'Sunny_25C_Treeline_Dehydration______________0FFF');
INSERT INTO Recommends VALUES ('Group_BBQ_______________________', 'Sunny_25C_Treeline_Dehydration______________0FFF');

INSERT INTO TripEvent VALUES ('Rubble_Creek_Trip___________________________________________02AB', 'Backcountry_Skiing______________', 'John_Smith______________________', 'Rubble_Creek_Trailhead__________', 49.957281156243134, -123.12025172665031);
INSERT INTO TripEvent VALUES ('Rubble_Creek_Trip___________________________________________02FB', 'Alpine_Hiking___________________', 'John_Smith______________________', 'Sunny_25C_Treeline_Dehydration______________0FFF', 49.957281156243134, -123.12025172665031);
INSERT INTO TripEvent VALUES ('Rubble_Creek_Trip___________________________________________02AC', 'Beach_Picnic____________________', 'John_Smith______________________', 'Sunny_25C_Treeline_Dehydration______________0FFF', 49.957281156243134, -123.12025172665031);
INSERT INTO TripEvent VALUES ('Metro_Vacay_________________________________________________F2AC', 'Restaurant_Outing_______________', 'John_Smith______________________', 'Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1', 49.957281156243134, -123.12025172665031);
INSERT INTO TripEvent VALUES ('Metro_Vacay_________________________________________________EB10', 'Group_BBQ_______________________', 'John_Smith______________________', 'Snow_-2C_Urban_Speed_limit_50_Black_Ice_____7FA1', 49.957281156243134, -123.12025172665031);

INSERT INTO Requires VALUES('711A_Backcountry_Skiing_____________________________________162A981C_Avalanche_Training_________________________________________', 'Sunny_-5C_Alpine_Avalance___________________711A', 'Backcountry_Skiing______________', 'TourSkis2023_Nordica_Enforcer_104_Ultima162A981C', 'Avalanche_Training______________', NULL);
INSERT INTO Requires VALUES('2FF8_Alpine_Hiking__________________________________________162A981C_Mountaineering_Instructor__________________________________', 'Cloudy_12C_Alpine___________________________2FF8', 'Alpine_Hiking___________________', 'HikeShoeKeen_Pyrenees_Hiking_Boots______27B95032', 'Mountaineering_Instructor_______', NULL);
INSERT INTO Requires VALUES('8AAB_Restaurant_Outing______________________________________XXXXXXXX_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'Rainy_15C_Indoors_No_Smoking________________8AAB', 'Restaurant_Outing_______________', NULL, NULL, NULL);
INSERT INTO Requires VALUES('0FFF_Beach_Picnic___________________________________________7BBEF873_Lifeguard__________________________________________________', 'Sunny_25C_Treeline_Dehydration______________0FFF', 'Beach_Picnic____________________', 'Food____Spaghetti_Pomodoro_W/_Meatballs_7BBEF873', 'Lifeguard_______________________', NULL);
INSERT INTO Requires VALUES('0FFF_Group_BBQ______________________________________________59BEB8AC_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', 'Sunny_25C_Treeline_Dehydration______________0FFF', 'Group_BBQ_______________________', 'ChfKnifeZwilling_Tradition_8in_Chefs_Kni59BEB8AC', NULL, NULL);



