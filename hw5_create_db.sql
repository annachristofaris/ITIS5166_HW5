SET NAMES utf8mb4;
DROP DATABASE IF EXISTS ccDatabase;

CREATE DATABASE ccDatabase;

USE ccDatabase;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    userID VARCHAR(20),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(50),
    `password` VARCHAR(30),
    PRIMARY KEY (userID)
);

DROP TABLE IF EXISTS item;
CREATE TABLE item (
    itemCode VARCHAR(20) NOT NULL,
    itemName VARCHAR(50),
    category VARCHAR(50),
    title VARCHAR(100),
    descrip VARCHAR(10000),
    userID VARCHAR(20),
    PRIMARY KEY (itemCode),
    FOREIGN KEY (userID)
        REFERENCES `user` (userID)
);

DROP TABLE IF EXISTS userItems;
CREATE TABLE userItems (
    itemCode VARCHAR(20) NOT NULL,
    userID VARCHAR(20),
    madeIt BOOL DEFAULT FALSE,
    rating VARCHAR(1) DEFAULT '0',
    FOREIGN KEY (userID)
        REFERENCES `user` (userID),
    FOREIGN KEY (itemCode)
        REFERENCES item (itemCode)
);
    
BEGIN;
INSERT INTO `user` (userID, firstName, lastName, email, `password`)
VALUES ("1", "Ned", "Stark", "nedstark@gmail.com", "winterfell"),
				("2", "Jon", "Snow", "jonsnow@gmail.com", "ghost");
		
COMMIT;  

BEGIN;
INSERT INTO item (itemCode, itemName, category, title, descrip, userID)
VALUES ("5foolprooftips", "5foolprooftips", "tips", "5 Foolproof Decor Tips", "<ol>
                        <h2><li>Add greenery</h2>Plants, especially larger plants, are a great way to elavate your space.</li>
                       <h2><li>If hanging curtains, hang high</h2> When hanging your own curtains, hang your curtain rod six inches
                            wide and 12 inches high past the window frame,' says Shelby Girard, head interior designer at
                            Havenly. 'This will make your windows feel larger and maximize natural light—especially in the
                            warmer summer months when you want to let all the light into your home.' </li>
                       <h2><li>Scale your wall fixtures</h2> 'Art should be hung six to nine inches over a major furniture piece like
                            a sofa or console table, or at gallery height—which is when the center falls at eye-level.' </li>
                        <h2><li>Go lighter with paint in small spaces</h2>Dark colors make rooms look smaller, so pick lighter colors
                            in cramped spaces.</li>
                        <h2><li>Open your space up with mirrors.</h2>Amanda Breslow of Home Polish says: 'When placed properly, a mirror
                            can open up a wall, enlarge a room, or break up a room with reflections from windows.'</li>
                    </ol>", "1"),

				("besthouseplants", "besthouseplants", "popular", "The Best (and Easiest!) Houseplants to Liven Up Your Space", "
               <p class = tagline>Most people could use a little more green in their lives, but don't want the added time and commitment that may come with plant
               care. Luckily, there are low-matenience solutions. These low-to-no fuss houseplants will add color and breathe life into your home.  </p><ul> 
               <h2><li>Snake Plant</h2>
                <img src=""./images/snakeplant.JPG"" alt=""snake plant image""></li>
                  <h2><li>Bird of Paradise</h2>
                <img src=""./images/birdofparadise.JPG"" alt=""bird of paradise image""></li>
                </ul>", "1"),
                
				("kitchenreno", "kitchenreno", "popular", "Before & After: Kitchen Renovation", "additional renovation description", "2"),
                ("splitlevel", "splitlevel", "popular", "Inside a Split-Level Seattle Home",        
                "  <p class = tagline>The Zipper House is a modern remodel in Seattle, Washington offering a new take on the postwar split-level home.</p>
               <img src=""./images/seattle1.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle2.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle3.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle4.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle5.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle6.JPG"" alt=""split-level seattle home image"">
                <img src=""./images/seattle7.JPG"" alt=""split-level seattle home image"">", "2"),
                
                ("restorewood", "restorewood", "tips", "How-to: Restore Wood Furniture", "How-to guide to come", "1"),
                
                 ("smallspace", "smallspace", "tips", "Maximizing a Small Space", 
                 "<p class = tagline>From dual-functioning furnishings to differentiating color palettes, these 8 small-space design ideas make the most of a tiny apartment</p>
                 <p>When Jonathan Adler’s creative services manager Nicholas Obeid moved from a lofty two-bedroom to a 450-square-foot studio in New York’s Chelsea neighborhood, 
                 he brought with him more stuff than one might expect to fit in such a small space. Among his treasures: a pair of chrome Chippendale chairs, a nickel-plated credenza covered in geometric 
                 studs, tiered Lucite bedside tables, and a midcentury daybed that he reupholstered in a stunning emerald-green velvet. Somehow, the resourceful entertainer managed to find a place 
                 for each piece, creating three distinct living areas—plus a bar moment for good measure—without overwhelming the apartment. “The common approach to a studio is minimalism,” says 
				Obeid, “but I opted for the opposite. I think the variety of visual elements makes the apartment feel bigger.” Here, eight ways Obeid maximized his small space with statement-making style—
                and how you can do the same.</p>
                 <ol>
                 <h2><li>Paint it white</h2></li>
				<h2><li>Think in vignettes</h2></li>
				<h2><li>Consider floating</h2>Floating funiture is light and airy, and don’t weigh down the space.<br>
                <img src=""./images/floating.jpg"" alt=""floating table image""></li>
                <h2><li>Use full-size furniture</h2> It seems counterintuitive, but small furnishings can actually make a room look smaller. Bonus: Larger pieces help hide unsightly elements—for Obeid, 
                that includes wires and a cable box—that add clutter to a space.</li>
                <h2><li>Display artwork  at or above eye level</h2></li>
                <h2><li>Choose furniture that serves multiple purposes</h2>“A daybed with a slim profile tucks back in the window nook and doubles as a guest bed,” says Obeid of the emerald-green sofa in the living area.</li>
                <h2><li>Always edit</h2>“Studio living has forced me to be neater than I thought I was,” says Obeid. “If one thing comes in, another has to go.”</li>
                </ol>", "1");
COMMIT;  

