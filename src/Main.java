
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public boolean runGame = true;

    enum ships {
        AircraftCarrier(5), Battleship(4), Submarine(3), Cruiser(3), Destroyer(2);
        private final int cells;
        ships(int cells) {
            this.cells = cells;
        }
        public static String getShips(int ship) {
            ships cells[] = ships.values();
            return String.valueOf(cells[ship]);
        }
        public static int getCells(int ship) {
            ships cells[] = ships.values();
            return cells[ship].cells;
        }
    }

    public void instanceMain() {
        playerOne playOne = new playerOne();
        playerTwo playTwo = new playerTwo();
        System.out.println("Player 1, place your ships on the game field");
        playOne.createArray();
        playOne.gameStart();
        EnterKey();
        System.out.println("Player 2, place your ships on the game field");
        playTwo.createArray();
        playTwo.gameStart();
        EnterKey();

        while (runGame) {
            playTwo.displayFogArray();
            System.out.println("---------------------");
            playOne.displayArray();
            System.out.println("Player 1, it's your turn:");
            System.out.println();
            playTwo.takeShot();

            if (playOne.getX() >= playOne.getO()) {
                runGame = false;
                System.out.println("You sank the last ship player 1. You won. Congratulations!");
            } else if (playTwo.getX() >= playTwo.getO()) {
                runGame = false;
                System.out.println("You sank the last ship player 2, You won, congratulations");
            }
            EnterKey();
            playOne.displayFogArray();
            System.out.println("---------------------");
            playTwo.displayArray();
            System.out.println("Player 2, its your turn:");
            System.out.println();
            playOne.takeShot();

            if (playOne.getX() >= playOne.getO()) {
                runGame = false;
                System.out.println("You sank the last ship player 1. You won. Congratulations!");
            } else if (playTwo.getX() >= playTwo.getO()) {
                runGame = false;
                System.out.println("You sank the last ship player 2, You won, congratulations");
            }
            EnterKey();
        }

    }

    public static void main(String[] args) {
        Main ints = new Main();
        ints.instanceMain();
    }

    public static void EnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Game {
        public String[][] shipArray = new String[10][10];
        public String[][] fogArray = new String[10][10];
        public char[] alphabetArray = new char[10];

        public String[][] emptyArray = new String[10][10];

        public int O = 0;
        public int X = 0;
        public int getX() {
            return this.X;
        }
        public int getO() {
            return this.O;
        }

        //AircraftCarrier(5), Battleship(4), Submarine(3), Cruiser(3), Destroyer(2);

        public String[][] AircraftCarrier5 = new String[10][10];
        public boolean AircraftCarrierDied = false;
        public String[][] Battleship4 = new String[10][10];
        public boolean BattleshipDied = false;
        public String[][] Submarine3 = new String[10][10];
        public boolean SubmarineDied = false;
        public String[][] Cruiser3 = new String[10][10];
        public boolean CruiserDied = false;
        public String[][] Destroyer2 = new String[10][10];
        public boolean DestroyerDied = false;
        public String[][][] allShips = new String[][][]{
                AircraftCarrier5,
                Battleship4,
                Submarine3,
                Cruiser3,
                Destroyer2
        };
        public Boolean[] allShipsBoolean = new Boolean[] {
                AircraftCarrierDied,
                BattleshipDied,
                SubmarineDied,
                CruiserDied,
                DestroyerDied
        };

        public boolean ShipCheck (int verticalInput, int horizontalInput) {

            for (int i = 0; i < 4; i++) {
                if (allShips[i][verticalInput][horizontalInput].equals("O")) {
                    allShips[i][verticalInput][horizontalInput] = "~";
                }
                if (Arrays.deepEquals(emptyArray, allShips[i]) && !allShipsBoolean[i]) {
                    System.out.println("You sank a ship!");
                    allShipsBoolean[i] = true;
                    return false;
                }
            }
            return true;
        }

        public void gameStart() {
            displayArray();
            placementOperation(0);
            displayArray();
            placementOperation(1);
            displayArray();
            placementOperation(2);
            displayArray();
            placementOperation(3);
            displayArray();
            placementOperation(4);
            displayArray();
        }

        public void takeShot() {
            //System.out.println("Take a shot");
            //System.out.println();
            Scanner scanner = new Scanner(System.in);
            boolean inputFormatBad = true;
            int verticalInput = -1;
            int horizontalInput = -1;
            while (inputFormatBad) {
                try {
                    String input = scanner.nextLine();
                    System.out.println();
                    char inputChar = input.charAt(0);
                    int i = 0;
                    for (char alphabet = 'A'; alphabet <= 'J'; alphabet++) {
                        if (inputChar == alphabet) {
                            verticalInput = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    horizontalInput = Integer.parseInt(String.valueOf(input.charAt(1))) - 1;
                    if (input.length() == 3) {
                        horizontalInput = 9;
                    }
                    if (input.length() > 2) {
                        if (Integer.parseInt(String.valueOf(input.charAt(2))) > 0) {
                            throw new RuntimeException("Error, bad input");
                        }
                    }
                    if (verticalInput != -1 && horizontalInput != -1) {
                        if (shipArray[verticalInput][horizontalInput].equals("O")) {
                            shipArray[verticalInput][horizontalInput] = "X";
                            fogArray[verticalInput][horizontalInput] = "X";
                            if (ShipCheck(verticalInput, horizontalInput)) {
                                System.out.println("You hit a ship!");
                            }
                            System.out.println();
                            X++;
                            inputFormatBad = false;
                        } else if (shipArray[verticalInput][horizontalInput].equals("~")) {
                            shipArray[verticalInput][horizontalInput] = "M";
                            fogArray[verticalInput][horizontalInput] = "M";
                            System.out.println("You missed!");
                            System.out.println();
                            inputFormatBad = false;
                        } else if (shipArray[verticalInput][horizontalInput].equals("X")) {
                            System.out.println("You hit a ship!");
                            inputFormatBad = false;
                        } else if (shipArray[verticalInput][horizontalInput].equals("M")) {
                            System.out.println("You missed!");
                            inputFormatBad = false;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                    inputFormatBad = true;

                }
            }
        }
        public void createArray() {
            for (int i = 0; i < 10; i++) {
                for (int y = 0; y < 10; y++) {
                    shipArray[i][y] = "~";
                    fogArray[i][y] = "~";
                    AircraftCarrier5[i][y] = "~";
                    Battleship4[i][y] = "~";
                    Submarine3[i][y] = "~";
                    Cruiser3[i][y] = "~";
                    Destroyer2[i][y] = "~";
                    emptyArray[i][y] = "~";
                }
            }
            int i = 0;
            for (char alphabet = 'A'; alphabet <= 'J'; alphabet++) {
                alphabetArray[i] = alphabet;
                i++;

            }
        }

        public void displayArray() {
            int countX = 0;
            System.out.print("  ");
            for (int i = 1; i < 11; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i < 10; i++) {
                System.out.print(alphabetArray[i]);
                for (int y = 0; y < 10; y++) {
                    System.out.print(" ");
                    System.out.print(shipArray[i][y]);
                    if (shipArray[i][y].equals("X")) {
                        countX++;
                    }
                }
                System.out.println();
            }
            System.out.println();
        }

        public void displayFogArray() {
            System.out.print("  ");
            for (int i = 1; i < 11; i++) {
                System.out.print(i + " ");
            }
            System.out.println();
            for (int i = 0; i < 10; i++) {
                System.out.print(alphabetArray[i]);
                for (int y = 0; y < 10; y++) {
                    System.out.print(" ");
                    System.out.print(fogArray[i][y]);
                }
                System.out.println();
            }
        }

        public void placementOperation(int ship) {
            Scanner scanner = new Scanner(System.in);
            int shipCells = ships.getCells(ship);
            String shipName = ships.getShips(ship);
            if (ship == 0) {
                System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
            } else {
                System.out.printf("Enter the coordinates of the %s (%d cells):", shipName, shipCells);
            }

            String input;
            int[] fInput = new int[3];
            boolean inputFormatBad = true;
            boolean incorrectInput = true;
            while (incorrectInput) {
                inputFormatBad = true;
                while (inputFormatBad) {
                    input = scanner.nextLine();
                    fInput = convertCoordinates(input);
                    if (fInput != null) {
                        inputFormatBad = false;
                    } else {
                        System.out.println("Error, bad input format");
                    }
                }
                if (fInput[0] == fInput[2]) {
                    incorrectInput = horizontalCheck(fInput, ship); {
                    }
                } else if (fInput[1] == fInput[3]) {
                    incorrectInput = verticalCheck(fInput, ship); {
                    }
                } else {
                    System.out.println("Error! Wrong ship location! Try again:");
                }
            }

            //sadaw
        }
        public boolean verticalCheck(int[] fInput, int ship) {
            //fInput[3], 4 inputs, A(0) 5(1) C(2) 5(3)
            boolean badInput = false;
            int shipCells = ships.getCells(ship);
            String shipName = ships.getShips(ship);

            int mathMin = Math.min(fInput[0], fInput[2]);
            int mathMax = Math.max(fInput[0], fInput[2]);
            int cellSize = (mathMax + 1) - mathMin;

            if (cellSize > shipCells || cellSize < shipCells) {
                //checks if set ship size is right length
                System.out.println("Error, Wrong Length(vertical) of the " + shipName + "! Try again:");
                System.out.println("Cellsize is " + cellSize + " shipCells are " + shipCells);
                badInput = true;

            } else {
                //left side check
                if (fInput[1] != 0 && !badInput) {
                    for (int i = mathMin; i < mathMax; i++) {
                        if (shipArray[i][fInput[1] - 1].equals("O")) {
                            badInput = true;
                        }
                    }
                    //right side check
                    if (fInput[1] != 9 && !badInput) {
                        for (int i = mathMin; i < mathMax; i++) {
                            if (shipArray[i][fInput[1] - 1].equals("O")) {
                                badInput = true;
                            }
                        }
                        //checks up
                        if (fInput[0] != 0 && !badInput && shipArray[mathMin - 1][fInput[0]].equals("O")) {
                            badInput = true;
                        }
                        //checks down
                        if (fInput[2] != 9 && !badInput && shipArray[mathMax + 1][fInput[0]].equals("O")) {
                            badInput = true;
                        }
                        if (badInput) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                        }
                    }
                }
            }
            if (!badInput) {
                //if everything is good, sets the ship horizontal
                setShip(fInput,0, cellSize, ship);
            }
            return badInput;
        }

        public boolean horizontalCheck(int[] fInput, int ship) {
            //fInput[3], 4 inputs, A(0) 5(1) A(2) 5(3)
            boolean badInput = false;
            int shipCells = ships.getCells(ship);
            String shipName = ships.getShips(ship);

            int mathMin = Math.min(fInput[1], fInput[3]);
            int mathMax = Math.max(fInput[1], fInput[3]);
            int cellSize = (mathMax + 1) - mathMin;
            //debug
            //System.out.println("Ship cells are: " + shipCells + " user input: " + cellSize);

            if (cellSize > shipCells || cellSize < shipCells) {
                //checks if set ship size is right length
                System.out.println("Error, Wrong Length(horizontal) of the " + shipName + "! Try again:");
                badInput = true;

            } else {
                //checks if placed location has already ship placed in it
                if (fInput[0] != 0 && badInput == false) {
                    for (int i = mathMin; i < mathMax; i++) {
                        if (shipArray[fInput[0] - 1][i].equals("O")) {
                            badInput = true;
                        }
                    }
                    if (fInput[0] != 9 && !badInput) {
                        for (int i = mathMin; i < mathMax; i++) {
                            if (shipArray[fInput[0] + 1][i].equals("O")) {
                                badInput = true;
                            }
                        }
                        if (fInput[1] != 0 && !badInput && shipArray[fInput[0]][mathMin - 1].equals("O")) {
                            badInput = true;
                        }
                        if (fInput[1] != 9 && !badInput && shipArray[fInput[0]][mathMin + 1].equals("O")) {
                            badInput = true;
                        }
                        if (badInput) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                        }
                    }
                }
            }
            if (!badInput) {
                //if everything is good, sets the ship horizontal

                setShip(fInput,1, cellSize, ship);
            }
            //debug
            //System.out.println("Function horizontalCheck, badInput is " + badInput);
            return badInput;
        }
        public void setShip(int[] fInput, int position, int cellSize, int ship) {
            //after everything is checked this runs and places the ship

            //debug
            //System.out.println("The system reached setShip function successfully");
            //System.out.println("cellsize in setShip function is " + cellSize);
            //System.out.println("position on setShip is " + position);

            if (position == 1) {
                //horizontal is 1
                int max = Math.max(fInput[1], fInput[3]);
                int min = Math.min(fInput[1], fInput[3]);
                for (int i = max; min <= i; i--) {
                    shipArray[fInput[0]][i] = "O";
                    allShips[ship][fInput[0]][i] = "O";
                    O++;
                }
                //debug
                //System.out.println("HorizontalAfterForReached");
            } else {
                //vertical
                int max = Math.max(fInput[0], fInput[2]);
                int min = Math.min(fInput[0], fInput[2]);
                for (int y = max; y >= min; y--) {
                    shipArray[y][fInput[1]] = "O";
                    allShips[ship][y][fInput[1]] = "O";
                    O++;
                }
            }
        }


        public int[] convertCoordinates(String placement) {
            //converts inserted coordinates from A2 A3, to [1][2][1][3]
            // A part of the coordinate gets passed to charToInt to convert to numbers

            try {
                int[] convertedCoord = new int[4];

                String[] split = placement.split("\\s+");
                int[] convertedChar = charToInt(split).clone();

                convertedCoord[0] = convertedChar[0];
                convertedCoord[1] = Integer.parseInt(String.valueOf(split[0].charAt(1))) - 1;
                convertedCoord[2] = convertedChar[1];
                convertedCoord[3] = Integer.parseInt(String.valueOf(split[1].charAt(1))) - 1;

                if (split[0].length() == 3) {
                    convertedCoord[1] = 9;
                }

                if (split[1].length() == 3) {
                    convertedCoord[3] = 9;
                }

                return convertedCoord;

            } catch (Exception e) {
                System.out.println("Error, at convertCoordinates, try again " + e);
                return null;
            }


        }

        public int[] charToInt(String[] split) {
            //converts A B part of input chars to numbers
            int[] output = new int[2];
            try {
                for (int y = 0; y < output.length; y++) {
                    //System.out.println("y is " + y);
                    int i1 = 0;
                    for (char alphabet = 'A'; alphabet <= 'J'; alphabet++) {
                        if (split[y].charAt(0) == alphabet) {
                            output[y] = i1;
                            //System.out.println("AlphabetMatched " + i1 + " " + alphabet);
                            break;
                        } else {
                            i1++;
                            //System.out.println(i1 + " " + alphabet + " " + split[y].charAt(0));
                        }
                    }
                }
                //System.out.println("charPassed: " + output[0] + " " + output[1]);
                return output;
            } catch (Exception e) {
                System.out.println("Error at charToInt " + e );
                return null;
            }
        }

    }
    class playerOne extends Game {
    }

    class playerTwo extends Game {
    }

}


