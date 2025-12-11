INSERT INTO Candy (CandyName, CandyType, MinStock, MaxStock) VALUES
('Lemon Drop', 'bolsje', 100, 500),
('Lakrids Slikkepind', 'slikkepind', 50, 250);

GO

INSERT INTO Recipe(CandyID, Difficulty) VALUES
(1, 3),
(2, 1);

GO

INSERT INTO RecipeLine(Ingredient, Quantity, RecipeID) VALUES 
('Sukker', 50, 1),
('Citron smag', 15, 1),
('Vand', 45, 1),
('Sukker', 50, 2),
('Lakrids pulver', 15, 2),
('Vand', 45, 2);

GO

INSERT INTO ProdPlan(date) VALUES
(GETDATE()),
(GETDATE());

GO

INSERT INTO PlanItem(PlanID, RecipeID, Quantity) VALUES
(1,1,50),
(1,2,75),
(2,1,100),
(2,2,50);

GO